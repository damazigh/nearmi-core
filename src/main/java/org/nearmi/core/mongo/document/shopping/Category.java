package org.nearmi.core.mongo.document.shopping;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document("category")
@Data
public class Category {
    @Id
    private String id;
    private String code;
    private String label;
    private LocalDateTime created;
    private LocalDateTime modified;

    public Category() {
        this.id = UUID.randomUUID().toString();
    }
}
