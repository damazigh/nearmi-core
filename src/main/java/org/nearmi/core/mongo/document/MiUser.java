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
    protected ObjectId id;
    protected String username;
    protected String email;
    protected String name;
    protected String familyName;
    @DBRef
    protected Gender gender;
    protected LocalDateTime birthdate;
    protected LocalDateTime created;
    protected LocalDateTime modified;
    @DBRef
    protected Collection<Role> roles;
    protected boolean professional;
    @DBRef
    private Collection<Appointment> appointments;

    public MiUser() {
        this.id = new ObjectId();
    }
}
