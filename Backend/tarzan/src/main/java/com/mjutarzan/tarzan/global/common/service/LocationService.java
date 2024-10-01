package com.mjutarzan.tarzan.global.common.service;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private final GeometryFactory geometryFactory;

    public LocationService() {
        this.geometryFactory = new GeometryFactory();
    }

    public Point createPoint(double latitude, double longitude) {
        Coordinate coordinate = new Coordinate(longitude, latitude); // 위도와 경도를 Coordinate로 생성 (X=경도, Y=위도)
        return geometryFactory.createPoint(coordinate); // Point 생성
    }
}
