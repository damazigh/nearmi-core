package org.nearmi.core.mongo.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("gender")
@Data
class Gender {
    @DBRef
    private String id;
    private String code;
    private String label;

    public Gender() {
        this.id = UUID.randomUUID().toString();
    }
}
