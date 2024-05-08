package com.bnhp.stock.model.dto.stockprice.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockPriceResponseData {

    @JsonProperty(value = "company_name")
    private String fullCompanyName;

    @JsonProperty(value = "current_price")
    private BigDecimal currentPrice;

    @JsonProperty(value = "yesterday_price")
    private BigDecimal yesterdayPrice;

    private String currency;
    private String ticker;
}
