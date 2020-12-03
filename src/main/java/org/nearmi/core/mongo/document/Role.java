package org.nearmi.core.mongo.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("role")
class Role {
    @Id
    private ObjectId id;
    private String role;

    public Role() {
        this.id = new ObjectId();
    }
}
