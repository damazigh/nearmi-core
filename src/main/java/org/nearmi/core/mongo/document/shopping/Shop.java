package org.nearmi.core.mongo.document.shopping;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.nearmi.core.mongo.cascade.Cascade;
import org.nearmi.core.mongo.document.MiProUser;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
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
    private String imageMetadata;
    private boolean validated;
    private LocalDate created;
    @DBRef
    @Cascade
    private Category category;
    @DBRef
    @Cascade
    private Address address;

    @Setter(AccessLevel.NONE)
    @Cascade
    @DBRef
    private MiProUser responsible;
    @DBRef
    @Cascade
    private ShopOptions options;
    @DBRef
    private Collection<Product> products;

    public Shop() {
        this.id = UUID.randomUUID().toString();
    }

    public void setResponsible(MiProUser responsible) {
        this.responsible = responsible;
        this.responsible.getShops().add(this);
    }
}
