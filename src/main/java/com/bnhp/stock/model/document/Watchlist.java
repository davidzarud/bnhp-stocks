package com.bnhp.stock.model.document;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(value = "watchlist")
public class Watchlist {

    private String id;
    private String userId;
    private String stockId;

    @CreatedDate
    private LocalDateTime addedDate;
}
