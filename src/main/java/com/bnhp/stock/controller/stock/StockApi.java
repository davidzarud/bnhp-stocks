package com.bnhp.stock.controller.stock;

import com.bnhp.stock.model.dto.sp500stock.response.Sp500StockResponse;
import com.bnhp.stock.model.dto.stockprice.response.StockPriceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/v1/stock")
public interface StockApi {

    @Operation(summary = "Get stock price", description = "Get the current stock price by providing ticker")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = StockPriceResponse.class))),
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @Parameters(value = {
            @Parameter(
                    name = "ticker",
                    description = "Stock ticker name (ie 'AAPL')",
                    schema = @Schema(implementation = String.class)
            )
    })
    @GetMapping(value = "/price",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StockPriceResponse> getStockPrice(@RequestParam("ticker") String ticker);

    @Operation(summary = "Get S&P500 tickers", description = "Get all S&P50 ticker values")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))),
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping(value = "/sp500",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<String>> getSp500Tickers();

    @Operation(summary = "Get S&P500 stocks", description = "Get all S&P500 stock prices")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = Sp500StockResponse.class))),
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping(value = "/sp500-stocks",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Sp500StockResponse> getSp500stocks();

}
