package org.nearmi.core.repository;

import org.bson.types.ObjectId;
import org.nearmi.core.mongo.document.shopping.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends MongoRepository<Address, ObjectId> {
}
