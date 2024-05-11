package com.bnhp.stock.model.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document(value = "transaction")
public class Transaction {

    private ObjectId id;
    private ObjectId userId;
    private ObjectId stockId;
    private String transactionType;
    private Integer quantity;
    private BigDecimal price;

    @CreatedDate
    private LocalDateTime transactionDate;
}
