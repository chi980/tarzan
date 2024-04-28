package com.mjutarzan.tarzan.domain.map.entity.amenity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@DiscriminatorValue("gym")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gym extends Amenity{

    @Column(name="gym_category", nullable = true)
    private String category;

    @Builder(builderMethodName = "gymBuilder")
    public Gym(String name, String address, Point location, String phoneNumber, String category){
        super(name, address, location, phoneNumber);
        this.category = category;
    }
}
