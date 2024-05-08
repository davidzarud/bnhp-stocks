package com.bnhp.stock.model.dto.stockprice.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockPriceResponse {

    private String companyName;
    private BigDecimal currentPrice;
    private BigDecimal yesterdayPrice;
    private BigDecimal diff;
    private BigDecimal diffPercent;
    private String ticker;
    private String currency;
}
