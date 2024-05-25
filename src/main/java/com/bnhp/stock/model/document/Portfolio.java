package com.bnhp.stock.model.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(value = "portfolio")
public class Portfolio {

    private String id;
    private String userId;
    private String stockId;
    private Integer quantity;
    private Double averagePrice;
    private Double totalInvestment;

}
