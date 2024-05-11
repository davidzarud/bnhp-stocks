package com.bnhp.stock.service;

import com.bnhp.stock.feign.StockFeignClient;
import com.bnhp.stock.model.document.Stock;
import com.bnhp.stock.model.document.TopStock;
import com.bnhp.stock.model.dto.sp500stock.response.Sp500StockResponse;
import com.bnhp.stock.model.dto.stockprice.response.StockPriceResponse;
import com.bnhp.stock.model.dto.stockprice.response.StockPriceResponseData;
import com.bnhp.stock.model.transofrmer.StockTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.bnhp.stock.model.transofrmer.StockTransformer.stockDataToStockResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

    private final MongoTemplate mongoTemplate;
    private final StockFeignClient stockFeignClient;

    public StockPriceResponse getCurrentStockPrice(String ticker) {
        StockPriceResponseData stockPrice = stockFeignClient.getStockPrice(ticker);
        return stockDataToStockResponse(stockPrice);
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
        List<StockPriceResponse> stockPriceResponseList = stocks.stream().map(StockTransformer::stockToStockPriceResponse)
                .toList();
        return Sp500StockResponse.builder()
                .stockPrices(stockPriceResponseList)
                .build();
    }
}
