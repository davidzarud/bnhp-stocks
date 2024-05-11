package com.bnhp.stock.model.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(value = "watchlist")
public class Watchlist {

    private ObjectId id;
    private ObjectId userId;
    private ObjectId stockId;

    @CreatedDate
    private LocalDateTime addedDate;
}
