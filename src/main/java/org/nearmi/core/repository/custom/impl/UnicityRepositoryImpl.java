package org.nearmi.core.repository.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public abstract class UnicityRepositoryImpl<T> {
    @Autowired
    protected MongoTemplate template;

    /**
     * crud method to check if value exists in MiUserClass
     *
     * @param value filed value
     * @param field filed name
     * @return true if value doesn't exits for another document (specified field) false neiher
     */


    public <T> boolean isUnique(String value, String field, Class<T> clazz) {
        Query query = new Query();
        query.addCriteria(Criteria.where(field).is(value));
        return !template.exists(query, clazz);
    }
}
