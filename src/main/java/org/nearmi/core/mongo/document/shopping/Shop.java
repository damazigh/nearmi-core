package org.nearmi.core.mongo.document.shopping;

import lombok.Data;
import org.nearmi.core.mongo.document.MiUser;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.UUID;

@Document("shop")
@Data
public class Shop {
    @Id
    private String id;
    private String name;
    private String registrationNumber;
    private String description;
    private String shortDesc;
    private boolean validated;
    @DBRef
    private Category category;
    @DBRef
    private Address address;
    @DBRef
    private MiUser responsible;
    @DBRef
    private ShopOptions options;
    @DBRef
    private Collection<Product> products;

    public Shop() {
        this.id = UUID.randomUUID().toString();
    }
}
