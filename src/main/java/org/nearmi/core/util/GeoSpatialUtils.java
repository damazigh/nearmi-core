package org.nearmi.core.util;

public class GeoSpatialUtils {

    private static final double EARTH_RADIUS = 6371;

    public static double haversineDistance(double lon1, double lat1, double lon2, double lat2) {
        double latDistance = radian(lat2 - lat1);
        double lonDistance = radian(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(radian(lat1)) * Math.cos(radian(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c * 1000; // in meter
    }

    private static double radian(double val) {
        return val * Math.PI / 180;
    }
}
