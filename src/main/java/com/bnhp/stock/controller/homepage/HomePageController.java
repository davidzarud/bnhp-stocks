package com.bnhp.stock.controller.homepage;

import com.bnhp.stock.model.dto.stockprice.response.StockPriceResponse;
import com.bnhp.stock.service.HomePageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class HomePageController {

    private final HomePageService homePageService;

    @GetMapping("/{userId}/portfolio-balance")
    public ResponseEntity<Double> getPortfolioBalance(@PathVariable String userId) {
        return ResponseEntity.ok().body(homePageService.getPortfolioBalance(userId));
    }

    @GetMapping("/most-active")
    public ResponseEntity<List<StockPriceResponse>> getMostActiveStocks() {
        return ResponseEntity.ok().body(homePageService.getMostActiveStocks());
    }

    @GetMapping("/daily-gainers")
    public ResponseEntity<List<StockPriceResponse>> getDailyGainers() {
        return ResponseEntity.ok().body(homePageService.getDailyGainers());
    }

    @GetMapping("/daily-losers")
    public ResponseEntity<List<StockPriceResponse>> getDailyLosers() {
        return ResponseEntity.ok().body(homePageService.getDailyLosers());
    }
}
