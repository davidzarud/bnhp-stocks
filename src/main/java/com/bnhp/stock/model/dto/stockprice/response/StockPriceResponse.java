package com.bnhp.stock.model.dto.stockprice.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockPriceResponse {

    private String companyName;
    private Double currentPrice;
    private Double yesterdayPrice;
    private Double diff;
    private Double diffPercent;
    private String ticker;
    private String currency;
    private String imgUrl;
}
