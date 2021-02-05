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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    // no cascading on lists as it would result
    // on unnecessary operations for not updated rows
    @DBRef
    private Collection<Product> products;
    @DBRef
    private List<ProductCategory> productCategories;


    public Shop() {
        this.id = UUID.randomUUID().toString();
    }

    public void setResponsible(MiProUser responsible) {
        this.responsible = responsible;
        this.responsible.getShops().add(this);
    }

    public void addProductCategory(ProductCategory category) {
        if (productCategories == null) {
            productCategories = new ArrayList<>();
        }
        category.setShop(this);
        productCategories.add(category);
    }
}
