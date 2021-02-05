package org.nearmi.core.mongo.document.shopping;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document("product")
@Data
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private double price;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean available;
    private int amount;
    private List<String> metadata;

    public boolean isAvailable() {
        return amount > 0;
    }

    public Product() {
        this.id = UUID.randomUUID().toString();
    }
}
