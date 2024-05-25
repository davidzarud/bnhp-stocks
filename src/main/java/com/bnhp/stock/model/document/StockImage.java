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
@Document(value = "stockImage")
public class StockImage {

    public static final String STOCK_IMAGE_COLLECTION = "stockImage";

    private String symbol;
    private String imgUrl;

    @CreatedDate
    private LocalDateTime createDate;
}
