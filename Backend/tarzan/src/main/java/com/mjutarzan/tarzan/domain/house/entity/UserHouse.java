package com.mjutarzan.tarzan.domain.house.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.geolatte.geom.Geometry;
import org.locationtech.jts.geom.Point;
@Entity
@Getter
@DiscriminatorValue(value = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserHouse extends House{

    @Builder
    public UserHouse(String name, String address, Point location){
        super(name, address, location);
    }
}
