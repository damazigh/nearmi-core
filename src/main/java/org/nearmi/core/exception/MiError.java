package org.nearmi.core.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Formalized error response object.<br/>
 * Each exception in rest api call is converted into {@link MiError} before resent it to the request issuer
 * @author A.Djebarri
 * @since 1.0
 */
@Data
public class MiError {
    private String key;
    private String message;
    private String description;
    @JsonIgnore
    private int status;
}
