package org.nearmi.core.exception;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.UUID;

/**
 * Base exception class used in nearmi app.<br/>
 * The exception handler is configured to catch
 * every exception class of type {@link MiException} (or its sub classes) and translate it to an instance of {@link MiError}
 *
 * @author A.Djebarri
 * @since 1.0
 */
@Data
public class MiException extends RuntimeException {
    @Setter(AccessLevel.NONE)
    private String id;
    private String key;
    private String message;
    private String[] descParams;


    public MiException(String key, Exception e) {
        super(e);
        this.assignKeyAndId(key);
    }

    public MiException(String key) {
        this.assignKeyAndId(key);
    }

    public MiException(String key, String... args) {
        this.assignKeyAndId(key);
        this.descParams = args;
    }

    private void assignKeyAndId(String key) {
        this.id = UUID.randomUUID().toString();
        this.key = key;
    }
}
