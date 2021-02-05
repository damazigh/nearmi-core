package org.nearmi.core.mongo.document.shopping;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document("productCategory")
public class ProductCategory {
    private String id;
    private int order;
    private String name;
    @DBRef
    private Shop shop;

    public ProductCategory() {
        this.id = UUID.randomUUID().toString();
    }
}
