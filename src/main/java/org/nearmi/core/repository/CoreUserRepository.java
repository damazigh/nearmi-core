package org.nearmi.core.repository;

import org.bson.types.ObjectId;
import org.nearmi.core.mongo.document.MiUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoreUserRepository<T extends MiUser> extends MongoRepository<T, ObjectId> {
    T findByUsername(String username);
}