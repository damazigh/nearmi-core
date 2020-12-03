package org.nearmi.core.mongo.document.shopping;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class Category {
    @Id
    private ObjectId id;
    private String code;
    private String label;
    private LocalDateTime created;
    private LocalDateTime modified;

    public Category() {
        this.id = new ObjectId();
    }
}
