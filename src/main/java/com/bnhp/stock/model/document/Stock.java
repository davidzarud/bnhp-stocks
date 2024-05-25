package com.bnhp.stock.model.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "stock")
public class Stock {

    public static final String STOCK_COLLECTION = "stock";

    @Id
    private String id;
    private String companyName;
    private String symbol;
    private String currency;
    private Double currentPrice;
    private Double yesterdayPrice;
    private Double difference;
    private Double differencePercent;

    @CreatedDate
    private LocalDateTime createDate;
}
