package org.nearmi.core.mongo.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("gender")
@Data
class Gender {
    @DBRef
    private ObjectId id;
    private String code;
    private String label;
}
