package org.nearmi.core.mongo.cascade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

@Slf4j
public class CascadeMongoEventListener extends AbstractMongoEventListener<Object> {
    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();
        ReflectionUtils.doWithFields(source.getClass(), (field) -> {
            ReflectionUtils.makeAccessible(field);
            if (field.isAnnotationPresent(DBRef.class) &&
                    field.isAnnotationPresent(Cascade.class)) {
                Object fieldValue = field.get(source);
                if (fieldValue != null) {
                    // ensure sub object is a mongo collection (has an id)
                    HasIdFieldCallback callback = new HasIdFieldCallback();
                    ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
                    if (callback.hasIdField()) {
                        log.debug("saving field {}", field.getName());
                        mongoOperations.save(fieldValue);
                    } else {
                        log.warn("field {} is annotated with @Cascade without id subfield", field.getName());
                    }
                }
            }
        });
    }

    private static class HasIdFieldCallback implements ReflectionUtils.FieldCallback {
        private boolean idField;

        @Override
        public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
            if (!idField) {
                ReflectionUtils.makeAccessible(field);
                idField = field.isAnnotationPresent(Id.class);
            }
        }

        public boolean hasIdField() {
            return idField;
        }
    }
}
