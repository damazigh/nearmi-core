package org.nearmi.core.validator;

import org.nearmi.core.dto.AddressDto;
import org.nearmi.core.exception.MiException;
import org.nearmi.core.resource.GeneralResKey;

public class Validator {
    public static <T> void notNull(T value, String field) {
        if (value == null) {
            throwEx(GeneralResKey.NMI_G_0001, field);
        }
    }

    public static <T> void notEmpty(T value, String field) {
        notNull(value, field);
        if (value.getClass().isAssignableFrom(String.class)) {
            throwEx(GeneralResKey.NMI_G_0002, field);
        }
    }

    public static void validateAddress(AddressDto address) {
        notNull(address, "address");
        notEmpty(address.getPostalCode(), "postal code");
        notEmpty(address.getCity(), "city");
        notEmpty(address.getLine1(), "line 1");
        notEmpty(address.getCountry(), "country");
    }

    private static void throwEx(String key, String... args) {
        throw new MiException(key, args);
    }
}