package com.bnhp.stock.service;

import com.bnhp.stock.feign.StockFeignClient;
import com.bnhp.stock.model.dto.sp500.response.TickerData;
import com.bnhp.stock.model.dto.sp500stock.request.Sp500StockRequest;
import com.bnhp.stock.model.dto.sp500stock.response.Sp500StockResponse;
import com.bnhp.stock.model.dto.stockprice.response.StockPriceResponse;
import com.bnhp.stock.model.dto.stockprice.response.StockPriceResponseData;
import com.bnhp.stock.model.transofrmer.StockTransformer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.bnhp.stock.model.transofrmer.StockTransformer.stockDataToStockResponse;
    import static com.bnhp.stock.service.UtilService.partitionList;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

    private final StockFeignClient stockFeignClient;
    private List<String> sp500Tickers;
    private Map<String, StockPriceResponseData> sp500Stocks = new ConcurrentHashMap<>();

    public StockPriceResponse getStockPrice(String ticker) {
        StockPriceResponseData stockPrice = stockFeignClient.getStockPrice(ticker);
        return stockDataToStockResponse(stockPrice);
    }

    public List<String> getSp500Tickers() {
        if (CollectionUtils.isEmpty(sp500Tickers)) {
            TickerData tickerData = stockFeignClient.getSp500Tickers();
            List<String> filteredTickers = tickerData.getTickers().stream().filter(s -> !s.contains(".")).toList();
            sp500Tickers = new ArrayList<>(filteredTickers);
        }
        return sp500Tickers;
    }

    public Sp500StockResponse getSp500Stocks() {

        return Sp500StockResponse.builder()
                .stockPrices(sp500Stocks.values()
                        .stream()
                        .map(StockTransformer::stockDataToStockResponse)
                        .toList())
                .build();


//        Sp500StockRequest sp500StockRequest = Sp500StockRequest.builder()
//                .tickers(getSp500Tickers().subList(0, 50))
//                .build();
//        List<StockPriceResponseData> sp500stockPrices = stockFeignClient.getSp500stockPrices(sp500StockRequest);
//        return Sp500StockResponse.builder()
//                .stockPrices(sp500stockPrices
//                        .stream()
//                        .map(StockTransformer::stockDataToStockResponse).toList())
//                .build();
    }

    @PostConstruct
    public void fetchSp500Data() {

        partitionList(getSp500Tickers(), 50).forEach(partition -> {
            Sp500StockRequest sp500StockRequest = Sp500StockRequest.builder()
                    .tickers(partition)
                    .build();
            List<StockPriceResponseData> sp500stockPrices = stockFeignClient.getSp500stockPrices(sp500StockRequest);
            sp500stockPrices.forEach(stock ->
                    sp500Stocks.put(stock.getTicker(), stock));
        });
    }
}
