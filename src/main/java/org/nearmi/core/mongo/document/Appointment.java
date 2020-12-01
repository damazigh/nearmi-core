package org.nearmi.core.mongo.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("appointment")
@Data
public class Appointment {
    @Id
    private ObjectId id;
    private String label;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
}
