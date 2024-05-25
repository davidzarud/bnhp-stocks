package com.bnhp.stock.model.document;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(value = "transaction")
public class Transaction {

    private String id;
    private String userId;
    private String stockId;
    private String transactionType;
    private Integer quantity;
    private Double price;

    @CreatedDate
    private LocalDateTime transactionDate;
}
