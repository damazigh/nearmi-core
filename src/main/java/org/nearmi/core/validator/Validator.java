package org.nearmi.core.validator;

import org.nearmi.core.exception.MiException;
import org.nearmi.core.resource.GeneralResKey;

public class Validator {
    public static <T> void notNull(T value, String field) {
        if (value == null) {
            throwEx(GeneralResKey.NMI_G_0001, field);
        }
    }

    private static void throwEx(String key, String... args) {
        throw new MiException(key, args);
    }
}
