package com.mjutarzan.tarzan.domain.map.entity.amenity;

import com.mjutarzan.tarzan.domain.map.entity.Building;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@DiscriminatorValue("amenity")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Amenity extends Building {

    @Builder
    public Amenity(String name, String address, String category, Point location, String phoneNumber){
        super(name, address, category, location, phoneNumber);
    }

}
