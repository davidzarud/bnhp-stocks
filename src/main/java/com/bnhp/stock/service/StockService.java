package com.bnhp.stock.service;

import com.bnhp.stock.feign.StockFeignClient;
import com.bnhp.stock.model.document.TopStock;
import com.bnhp.stock.model.dto.sp500stock.request.Sp500StockRequest;
import com.bnhp.stock.model.dto.sp500stock.response.Sp500StockResponse;
import com.bnhp.stock.model.dto.stockprice.response.StockPriceResponse;
import com.bnhp.stock.model.dto.stockprice.response.StockPriceResponseData;
import com.bnhp.stock.model.transofrmer.StockTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bnhp.stock.model.transofrmer.StockTransformer.stockDataToStockResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

    private final MongoTemplate mongoTemplate;
    private final StockFeignClient stockFeignClient;

    public StockPriceResponse getStockPrice(String ticker) {
        StockPriceResponseData stockPrice = stockFeignClient.getStockPrice(ticker);
        return stockDataToStockResponse(stockPrice);
    }

    public List<String> getSp500Tickers() {
        return mongoTemplate.findAll(TopStock.class)
                .stream()
                .map(TopStock::getSymbol).toList();
    }

    public Sp500StockResponse getSp500Stocks() {
        Sp500StockRequest sp500StockRequest = Sp500StockRequest.builder()
                .tickers(getSp500Tickers())
                .build();
        List<StockPriceResponseData> sp500stockPrices = stockFeignClient.getSp500stockPrices(sp500StockRequest);
        return Sp500StockResponse.builder()
                .stockPrices(sp500stockPrices
                        .stream()
                        .map(StockTransformer::stockDataToStockResponse).toList())
                .build();
    }
}
