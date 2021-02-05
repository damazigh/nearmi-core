package org.nearmi.core.validator;

import org.apache.tika.Tika;
import org.nearmi.core.dto.AddressDto;
import org.nearmi.core.exception.MiException;
import org.nearmi.core.resource.GeneralResKey;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

public class Validator {
    public static <T> void notNull(T value, String field) {
        if (value == null) {
            throwEx(GeneralResKey.NMI_G_0001, field);
        }
    }

    public static <T> void notEmpty(T value, String field) {
        notNull(value, field);
        if (value.getClass().isAssignableFrom(String.class) && value.equals("")) {
            throwEx(GeneralResKey.NMI_G_0002, field);
        }
        if (value.getClass().isAssignableFrom(MultipartFile.class) && ((MultipartFile) value).isEmpty()) {
            throwEx(GeneralResKey.NMI_G_0006, field);
        }
    }

    public static <T> void positiveNumber(T value, String field) {
        notNull(value, field);
        if (value.getClass().isAssignableFrom(Integer.class) && (Integer) value <= 0) {
            throwEx(GeneralResKey.NMI_G_0009, field);
        }
    }

    public static void validateAddress(AddressDto address) {
        notNull(address, "address");
        notEmpty(address.getPostalCode(), "postal code");
        notEmpty(address.getCity(), "city");
        notEmpty(address.getLine1(), "line 1");
        notEmpty(address.getCountry(), "country");
    }

    public static void validateFileMime(MultipartFile file, String... acceptedType) {
        notEmpty(file, "file");
        try {
            String mime = new Tika().detect(file.getInputStream());
            boolean match = Arrays.stream(acceptedType).anyMatch(accepted -> accepted.equalsIgnoreCase(mime));
            if (!match) {
                throw new MiException(GeneralResKey.NMI_G_0007, mime);
            }
        } catch (IOException e) {
            throw new MiException(GeneralResKey.NMI_G_0001);
        }
    }

    private static void throwEx(String key, String... args) {
        throw new MiException(key, args);
    }
}
