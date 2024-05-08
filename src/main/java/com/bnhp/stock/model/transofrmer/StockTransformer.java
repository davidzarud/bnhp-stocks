package com.bnhp.stock.model.transofrmer;

import com.bnhp.stock.model.dto.stockprice.response.StockPriceResponse;
import com.bnhp.stock.model.dto.stockprice.response.StockPriceResponseData;

import java.math.RoundingMode;

import static com.bnhp.stock.service.UtilService.getDiff;
import static com.bnhp.stock.service.UtilService.getDiffPct;

public class StockTransformer {

    public static StockPriceResponse stockDataToStockResponse(StockPriceResponseData stockData) {
        return StockPriceResponse.builder()
                .companyName(stockData.getFullCompanyName())
                .currentPrice(stockData.getCurrentPrice().setScale(2, RoundingMode.HALF_UP))
                .yesterdayPrice(stockData.getYesterdayPrice().setScale(2, RoundingMode.HALF_UP))
                .currency(stockData.getCurrency())
                .diff(getDiff(stockData.getCurrentPrice(), stockData.getYesterdayPrice()))
                .diffPercent(getDiffPct(stockData.getCurrentPrice(), stockData.getYesterdayPrice()))
                .build();
    }
}
