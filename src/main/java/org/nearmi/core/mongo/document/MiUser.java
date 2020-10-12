package org.nearmi.core.mongo.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
@Data
public class MiUser {
    @Id
    private ObjectId id;
    private String username;
}
