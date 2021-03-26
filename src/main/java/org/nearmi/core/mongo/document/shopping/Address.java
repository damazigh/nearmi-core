package org.nearmi.core.mongo.document.shopping;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("address")
@Data
public class Address {
    @Id
    private String id;
    private String city;
    private String postalCode;
    private String country;
    private String line1;
    private String line2;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;


    public Address() {
        this.id = UUID.randomUUID().toString();
    }
}
