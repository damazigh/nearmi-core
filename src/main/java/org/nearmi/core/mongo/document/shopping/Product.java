package org.nearmi.core.mongo.document.shopping;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("product")
@Data
class Product {
    @Id
    private String id;
    private String name;
    private String description;
    // TODO AJOUTER les photos avec grids fs
    private int price;
    private boolean available;

    public Product() {
        this.id = UUID.randomUUID().toString();
    }
}
