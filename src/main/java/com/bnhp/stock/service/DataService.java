package com.bnhp.stock.service;

import com.bnhp.stock.feign.StockFeignClient;
import com.bnhp.stock.model.document.Stock;
import com.bnhp.stock.model.document.StockImage;
import com.bnhp.stock.model.document.TopStock;
import com.bnhp.stock.model.dto.image.StockImageResponse;
import com.bnhp.stock.model.dto.sp500.response.Sp500Response;
import com.bnhp.stock.model.dto.sp500stock.request.Sp500StockRequest;
import com.bnhp.stock.model.dto.stockprice.response.StockPriceResponseData;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.bnhp.stock.model.document.TopStock.TOP_STOCK_COLLECTION;
import static com.bnhp.stock.service.UtilService.getDiff;
import static com.bnhp.stock.service.UtilService.getDiffPct;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataService {

    private final MongoTemplate mongoTemplate;
    private final StockFeignClient stockFeignClient;

    @PostConstruct
    public void initData() {
        reloadTopStocks();
        reloadTopStocksCurrentPrice();
        reloadStockImage();
    }

    private void reloadStockImage() {
        LocalDate today = LocalDate.now();
        Query query = new Query();
        query.addCriteria(
                Criteria.where("createDate")
                        .gte(today.atStartOfDay())
                        .lt(today.plusDays(1).atStartOfDay())
        );
        if (!mongoTemplate.exists(query, StockImage.class)) {
            mongoTemplate.dropCollection(StockImage.STOCK_IMAGE_COLLECTION);
            List<Stock> stocks = mongoTemplate.find(query, Stock.class);

            List<StockImage> stockImages = stocks
                    .stream()
                    .map(stock -> {
                        StockImageResponse stockImageResponse = stockFeignClient.getImageUrl(stock.getCompanyName() + " logo wall street");
                        return StockImage.builder()
                                .imgUrl(stockImageResponse.getImageUrl())
                                .symbol(stock.getSymbol())
                                .build();
                    }).toList();
            mongoTemplate.insertAll(stockImages);
        }
    }

    @Scheduled(cron = "0 0 6 * * *")
    public void reloadTopStocks() {

        LocalDate today = LocalDate.now();
        Query query = new Query();
        query.addCriteria(
                Criteria.where("createDate")
                        .gte(today.atStartOfDay())
                        .lt(today.plusDays(1).atStartOfDay())
        );
        if (!mongoTemplate.exists(query, TopStock.class)) {
            mongoTemplate.dropCollection(TOP_STOCK_COLLECTION);
            Sp500Response topTickers = stockFeignClient.getSp500Tickers();
            List<TopStock> topStocks = topTickers.getCompanies().stream().map(company ->
                    TopStock.builder()
                            .name(company.getName())
                            .symbol(company.getTicker())
                            .build()
            ).toList();
            mongoTemplate.createCollection(TOP_STOCK_COLLECTION);
            mongoTemplate.insert(topStocks, TOP_STOCK_COLLECTION);
        }
    }

    @Scheduled(cron = "0 0 6 * * *")
    public void reloadTopStocksCurrentPrice() {
        LocalDate today = LocalDate.now();
        Query query = new Query();
        query.addCriteria(
                Criteria.where("createDate")
                        .gte(today.atStartOfDay())
                        .lt(today.plusDays(1).atStartOfDay())
        );
        if (!mongoTemplate.exists(query, Stock.class)) {
            List<String> tickers = mongoTemplate.findAll(TopStock.class)
                    .stream()
                    .map(TopStock::getSymbol)
                    .toList();
            List<StockPriceResponseData> sp500stockPrices = stockFeignClient.getSp500stockPrices(Sp500StockRequest.builder()
                    .tickers(tickers)
                    .build());
            List<Stock> stocks = sp500stockPrices.stream().map(stockPriceResponseData -> Stock.builder()
                    .currentPrice(stockPriceResponseData.getCurrentPrice())
                    .companyName(stockPriceResponseData.getFullCompanyName())
                    .symbol(stockPriceResponseData.getTicker())
                    .yesterdayPrice(stockPriceResponseData.getYesterdayPrice())
                    .currency(stockPriceResponseData.getCurrency())
                    .difference(getDiff(stockPriceResponseData.getCurrentPrice(), stockPriceResponseData.getYesterdayPrice()))
                    .differencePercent(getDiffPct(stockPriceResponseData.getCurrentPrice(), stockPriceResponseData.getYesterdayPrice()))
                    .build()).toList();
            mongoTemplate.insert(stocks, Stock.STOCK_COLLECTION);
        }
    }

    @Scheduled(cron = "0 0 6 * * *")
    public void updateHistory() {


    }
}
