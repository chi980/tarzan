package com.mjutarzan.tarzan.global.common.dto;

import org.locationtech.jts.geom.Point;

public interface DataInstance {
    public DataInstance getInstance(String[] info, Point location);
}
