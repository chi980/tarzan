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
        Coordinate coordinate = new Coordinate(longitude, latitude);
        Point point = geometryFactory.createPoint(coordinate);
        point.setSRID(4326);  // SRID 4326 설정
        return point;
    }
}
