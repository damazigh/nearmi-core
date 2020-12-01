package org.nearmi.core.mongo.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Collection;

@Document("user")
@Data
public class MiUser {
    @Id
    private ObjectId id;
    private String username;
    private String email;
    private String name;
    private String familyName;
    @DBRef
    private Gender gender;
    private LocalDateTime birthdate;
    @DBRef
    private Collection<Role> roles;
    private boolean professional;
    @DBRef
    private Collection<Appointment> appointments;
}
