package com.mjutarzan.tarzan.domain.map.entity.amenity;

import com.mjutarzan.tarzan.domain.map.entity.Building;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@DiscriminatorValue("amenity")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Amenity extends Building {

    @Builder
    public Amenity(String name, String address, Point location, String phoneNumber){
        super(name, address, location, phoneNumber);
    }
}
