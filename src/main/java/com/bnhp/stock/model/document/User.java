package com.bnhp.stock.model.document;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(value = "user")
public class User {

    @Id
    private String id;
    private String username;
    private String email;
    private String passwordHash;
    private Double portfolioBalance;
    private Double accountBalance;

    @CreatedDate
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
