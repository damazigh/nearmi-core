package org.nearmi.core.mongo.document;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.nearmi.core.mongo.cascade.Cascade;
import org.nearmi.core.mongo.document.shopping.Shop;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Data
@Document("miProUser")
public class MiProUser extends MiUser {
    @DBRef
    @Cascade
    @Getter(AccessLevel.NONE)
    private Collection<Shop> shops;

    public MiProUser() {
        this.professional = true;
        this.id = UUID.randomUUID().toString();

    }

    public Collection<Shop> getShops() {
        if (shops == null) {
            shops = new ArrayList<>();
        }
        return shops;
    }
}
