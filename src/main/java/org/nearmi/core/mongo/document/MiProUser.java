package org.nearmi.core.mongo.document;

import org.nearmi.core.mongo.document.shopping.Shop;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document("miProUser")
public class MiProUser extends MiUser {
    private Collection<Shop> shops;

    public MiProUser() {
        this.professional = true;
    }
}
