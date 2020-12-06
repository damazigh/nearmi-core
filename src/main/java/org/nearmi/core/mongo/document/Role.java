package org.nearmi.core.mongo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document("role")
class Role {
    @Id
    private String id;
    private String role;

    public Role() {
        this.id = UUID.randomUUID().toString();
    }
}
