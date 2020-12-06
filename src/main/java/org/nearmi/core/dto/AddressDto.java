package org.nearmi.core.dto;

import lombok.Data;

@Data
public class AddressDto {
    private String country;
    private String postalCode;
    private String city;
    private String line1;
    private String line2;
    private float longitude;
    private float latitude;
}
