package com.bnhp.stock.model.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "topStock")
public class TopStock {

    public static final String TOP_STOCK_COLLECTION = "topStock";

    private String symbol;
    private String name;

    @CreatedDate
    private LocalDateTime createDate;
}
