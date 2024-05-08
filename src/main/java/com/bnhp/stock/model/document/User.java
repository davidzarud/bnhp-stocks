package com.bnhp.stock.model.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(value = "user")
public class User {
}
