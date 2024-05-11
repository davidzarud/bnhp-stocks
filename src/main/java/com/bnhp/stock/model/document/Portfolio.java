package com.bnhp.stock.model.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document(value = "portfolio")
public class Portfolio {

    private ObjectId id;
    private ObjectId userId;
    private ObjectId stockId;
    private Integer quantity;
    private BigDecimal averagePrice;
    private BigDecimal totalInvestment;

}
