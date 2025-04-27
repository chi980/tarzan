package com.mjutarzan.tarzan.global.common.utils;

public class RadiusConverter {

    private static final double EARTH_RADIUS_IN_METERS = 111_000.0; // 대략 1도 ≈ 111km

    public static double meterToDegree(double meters) {
        return meters / EARTH_RADIUS_IN_METERS;
    }

    private RadiusConverter() {
        throw new UnsupportedOperationException("Utility class");
    }
}
