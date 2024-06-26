package com.bnhp.stock.service;

import com.bnhp.stock.model.document.Stock;
import com.bnhp.stock.model.document.StockImage;
import com.bnhp.stock.model.document.User;
import com.bnhp.stock.model.dto.stockprice.response.StockPriceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.bnhp.stock.model.transofrmer.StockTransformer.stockToStockPriceResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomePageService {

    private final MongoTemplate mongoTemplate;
    private final StockService stockService;

    public Double getPortfolioBalance(String userId) {
        Criteria criteria = Criteria.where("_id").is(userId);
        Query query = new Query(criteria);
        return Objects.requireNonNull(mongoTemplate.findOne(query, User.class)).getPortfolioBalance();
    }

    public List<StockPriceResponse> getMostActiveStocks() {
        List<String> mostActiveStocks = stockService.getMostActiveStocks();
        return mostActiveStocks.stream().map(stock -> {
            StockPriceResponse stockPrice = stockService.getCurrentStockPriceFromDB(stock);
            if (null == stockPrice) {
                stockPrice = stockService.getCurrentStockPriceFromServer(stock);
            }
            return stockPrice;
        }).toList();
    }

    public List<StockPriceResponse> getDailyGainers() {
        List<Stock> dailyGainers = stockService.getDailyGainers();
        return dailyGainers
                .stream()
                .map(stock -> {
                    StockImage stockImage = stockService.getStockImage(stock.getSymbol());
                    return stockToStockPriceResponse(stock, stockImage);
                })
                .toList();
    }

    public List<StockPriceResponse> getDailyLosers() {
        List<Stock> dailyLosers = stockService.getDailyLosers();
        return dailyLosers
                .stream()
                .map(stock -> {
                    StockImage stockImage = stockService.getStockImage(stock.getSymbol());
                    return stockToStockPriceResponse(stock, stockImage);
                }).toList();
    }
}
