package com.bnhp.stock.service;

import com.bnhp.stock.feign.StockFeignClient;
import com.bnhp.stock.model.document.TopStock;
import com.bnhp.stock.model.dto.sp500.response.Sp500Response;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.bnhp.stock.model.document.TopStock.TOP_STOCK_COLLECTION;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataService {

    private final MongoTemplate mongoTemplate;
    private final StockFeignClient stockFeignClient;

    @PostConstruct
    public void initData() {
        reloadTopStocks();
    }

    @Scheduled(cron = "0 0 2 * * *")
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
}
