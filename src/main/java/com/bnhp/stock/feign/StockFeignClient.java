package com.bnhp.stock.feign;

import com.bnhp.stock.model.dto.sp500stock.request.Sp500StockRequest;
import com.bnhp.stock.model.dto.stockprice.response.StockPriceResponseData;
import com.bnhp.stock.model.dto.sp500.response.TickerData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "stockClient", url = "http://localhost:5000/api/v1")
public interface StockFeignClient {

    @GetMapping("/stock-price")
    StockPriceResponseData getStockPrice(@RequestParam("ticker") String ticker);

    @GetMapping("/sp500-tickers")
    TickerData getSp500Tickers();

    @PostMapping("/sp500-stock-price")
    List<StockPriceResponseData> getSp500stockPrices(@RequestBody Sp500StockRequest tickers);
}
