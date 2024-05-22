package com.bnhp.stock.model.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "stockHistory")
public class StockHistory {

    public static final String STOCK_HISTORY_COLLECTION = "stockHistory";

    @Id
    private String id;
    private String stockId;
    private BigDecimal openPrice;
    private BigDecimal closePrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private BigDecimal volume;

    @CreatedDate
    private LocalDateTime createDate;
}
