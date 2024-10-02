package com.mjutarzan.tarzan.domain.map.entity.clinic;

import com.mjutarzan.tarzan.domain.map.entity.Building;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
@DiscriminatorValue("clinic")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Clinic extends Building {
    @Builder(builderMethodName = "clinicBuilder")
    public Clinic(String name, String address, Point location, String phoneNumber){
        super(name, address, location, phoneNumber);
    }
}
