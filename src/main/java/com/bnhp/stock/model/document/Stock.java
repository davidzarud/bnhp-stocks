package com.bnhp.stock.model.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "stock")
public class Stock {

    public static final String STOCK_COLLECTION = "stock";

    @Id
    private ObjectId id;
    private String companyName;
    private String symbol;
    private String currency;
    private BigDecimal currentPrice;
    private BigDecimal yesterdayPrice;
    private BigDecimal difference;
    private BigDecimal differencePercent;

    @CreatedDate
    private LocalDateTime createDate;
}
