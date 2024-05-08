package com.bnhp.stock.model.dto.sp500stock.response;

import com.bnhp.stock.model.dto.stockprice.response.StockPriceResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sp500StockResponse {

    private List<StockPriceResponse> stockPrices;
}
