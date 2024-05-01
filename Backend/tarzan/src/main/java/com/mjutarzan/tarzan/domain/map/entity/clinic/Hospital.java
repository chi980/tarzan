package com.mjutarzan.tarzan.domain.map.entity.clinic;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@DiscriminatorValue("hospital")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hospital extends Clinic{
    @Builder(builderMethodName = "hospitalBuilder")
    public Hospital(String name, String address, Point location, String phoneNumber){
        super(name, address, location, phoneNumber);
    }
}
