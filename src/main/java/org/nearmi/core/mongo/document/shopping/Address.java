package org.nearmi.core.mongo.document.shopping;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("address")
@Data
public class Address {
    @Id
    private ObjectId id;
    private String city;
    private String postalCode;
    private String country;
    private String line1;
    private String line2;
    int longitude;
    int latitude;


    public Address() {
        this.id = new ObjectId();
    }
}
