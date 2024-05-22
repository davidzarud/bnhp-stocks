package com.bnhp.stock.model.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document(value = "portfolio")
public class Portfolio {

    private String id;
    private String userId;
    private String stockId;
    private Integer quantity;
    private BigDecimal averagePrice;
    private BigDecimal totalInvestment;

}
