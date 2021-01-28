package org.nearmi.core.repository.custom.impl;

import org.nearmi.core.mongo.document.shopping.Address;
import org.nearmi.core.repository.custom.AddressCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collection;

public class AddressRepositoryImpl implements AddressCustomRepository {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private Environment env;

    @Override
    public Collection<Address> findByLocation(float longitude, float latitude) {
        mongoTemplate.indexOps(Address.class).ensureIndex(new GeospatialIndex("location").typed(GeoSpatialIndexType.GEO_2DSPHERE));
        double maxDistance = env.getRequiredProperty("nearmi.config.max-distance", Double.class);
        Query q = new Query();
        Point point = new GeoJsonPoint(longitude, latitude);
        Criteria criteria = Criteria.where("location").near(point).maxDistance(maxDistance);
        q.addCriteria(criteria);
        return mongoTemplate.find(q, Address.class);
    }
}
