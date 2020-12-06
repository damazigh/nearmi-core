package org.nearmi.core.mongo.document;

import org.nearmi.core.mongo.document.shopping.Shop;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.UUID;

@Document("miProUser")
public class MiProUser extends MiUser {
    private Collection<Shop> shops;
    private boolean owner;

    public MiProUser() {
        this.professional = true;
        this.id = UUID.randomUUID().toString();

    }
}
