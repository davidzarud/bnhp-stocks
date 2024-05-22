package com.bnhp.stock.controller.stock;

import com.bnhp.stock.model.dto.sp500stock.response.Sp500StockResponse;
import com.bnhp.stock.model.dto.stockprice.response.StockPriceResponse;
import com.bnhp.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockController implements StockApi {

    private final StockService stockService;

    @Override
    public ResponseEntity<StockPriceResponse> getStockPrice(String ticker) {
        StockPriceResponse stockPrice = stockService.getCurrentStockPriceFromDB(ticker);
        if (null == stockPrice) {
            stockPrice = stockService.getCurrentStockPriceFromServer(ticker);
        }
        return ResponseEntity.ok(stockPrice);
    }

    @Override
    public ResponseEntity<List<String>> getSp500Tickers() {
        List<String> sp500Tickers = stockService.getSp500Tickers();
        return ResponseEntity.ok(sp500Tickers);
    }

    @Override
    public ResponseEntity<Sp500StockResponse> getSp500stocks() {
        Sp500StockResponse sp500stocks = stockService.getTopSp500StockPrices();
        return ResponseEntity.ok(sp500stocks);
    }
}
