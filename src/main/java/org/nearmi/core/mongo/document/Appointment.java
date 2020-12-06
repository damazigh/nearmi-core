package org.nearmi.core.mongo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document("appointment")
@Data
public class Appointment {
    @Id
    private String id;
    private String label;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;

    public Appointment() {
        this.id = UUID.randomUUID().toString();
    }
}
