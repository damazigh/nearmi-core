package org.nearmi.core.mongo.document.technical;

import lombok.Data;

@Data
public class Time {
    int hour;
    int minute;

    @Override
    public String toString() {
        return hour + ":" + minute;
    }
}
