package org.nearmi.core.resource;

import lombok.Data;

/**
 * Represent a single functional error
 * @author A.Djebarri
 * @since 1.0
 */
@Data
public class DictionaryEntry {
    private String key;
    private String message;
    private String description;
    private int status;
}
