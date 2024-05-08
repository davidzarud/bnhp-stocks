package com.bnhp.stock.model.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(value = "stockHistory")
public class StockHistory {

    private String name;

}
