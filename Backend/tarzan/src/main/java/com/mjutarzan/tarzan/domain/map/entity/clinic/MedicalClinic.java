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
@DiscriminatorValue("medical_clinic")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MedicalClinic extends Clinic{
    @Builder
    public MedicalClinic(String name, String address, Point location, String phoneNumber){
        super(name, address, location, phoneNumber);
    }
}
