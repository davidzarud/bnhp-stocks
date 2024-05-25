package com.bnhp.stock.service;

import com.bnhp.stock.feign.StockFeignClient;
import com.bnhp.stock.model.document.Stock;
import com.bnhp.stock.model.document.StockImage;
import com.bnhp.stock.model.document.TopStock;
import com.bnhp.stock.model.dto.sp500stock.response.Sp500StockResponse;
import com.bnhp.stock.model.dto.stockprice.response.StockPriceResponse;
import com.bnhp.stock.model.dto.stockprice.response.StockPriceResponseData;
import com.bnhp.stock.model.transofrmer.StockTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.bnhp.stock.model.transofrmer.StockTransformer.stockDataToStockResponse;
import static com.bnhp.stock.model.transofrmer.StockTransformer.stockToStockPriceResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

    private final MongoTemplate mongoTemplate;
    private final StockFeignClient stockFeignClient;

    public StockPriceResponse getCurrentStockPriceFromServer(String ticker) {
        StockPriceResponseData stockPrice = stockFeignClient.getStockPrice(ticker);
        StockImage stockImage = getStockImage(ticker);
        return stockDataToStockResponse(stockPrice, stockImage);
    }

    public StockImage getStockImage(String ticker) {
        Criteria criteria = Criteria.where("symbol").is(ticker);
        StockImage stockImage = mongoTemplate.findOne(Query.query(criteria), StockImage.class);
        if (null == stockImage) {
            return StockImage.builder()
                    .imgUrl(stockFeignClient.getImageUrl(ticker + " stock").getImageUrl())
                    .symbol(ticker)
                    .build();
        }
        return stockImage;
    }

    public StockPriceResponse getCurrentStockPriceFromDB(String ticker) {
        Criteria criteria = Criteria.where("symbol").is(ticker)
                .and("createDate")
                .gte(LocalDate.now().atStartOfDay())
                .lt(LocalDate.now().plusDays(1).atStartOfDay());
        Stock stock = mongoTemplate.findOne(Query.query(criteria), Stock.class);
        StockImage stockImage = getStockImage(ticker);
        return stock != null ? stockToStockPriceResponse(stock, stockImage) : null;
    }

    public List<String> getSp500Tickers() {
        return mongoTemplate.findAll(TopStock.class)
                .stream()
                .map(TopStock::getSymbol).toList();
    }

    public Sp500StockResponse getTopSp500StockPrices() {
        LocalDate today = LocalDate.now();
        Query query = new Query();
        query.addCriteria(Criteria.where("createDate")
                .gte(today.atStartOfDay())
                .lt(today.plusDays(1).atStartOfDay()));
        List<Stock> stocks = mongoTemplate.find(query, Stock.class);
        List<StockPriceResponse> stockPriceResponseList = stocks
                .stream()
                .map(stock -> {
                    StockImage stockImage = getStockImage(stock.getSymbol());
                    return stockToStockPriceResponse(stock, stockImage);
                })
                .toList();
        return Sp500StockResponse.builder()
                .stockPrices(stockPriceResponseList)
                .build();
    }

    public List<String> getMostActiveStocks() {
        return stockFeignClient.getMostActiveStocks();
    }

    public List<Stock> getDailyGainers() {

        Query query = new Query().limit(5).with(Sort.by(Sort.Direction.DESC, "differencePercent"));
        return mongoTemplate.find(query, Stock.class);
    }

    public List<Stock> getDailyLosers() {
        Query query = new Query().limit(5).with(Sort.by(Sort.Direction.ASC, "differencePercent"));
        return mongoTemplate.find(query, Stock.class);
    }
}
