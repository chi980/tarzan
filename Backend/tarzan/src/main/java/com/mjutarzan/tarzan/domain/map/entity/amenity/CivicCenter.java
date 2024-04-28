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
@DiscriminatorValue("civic_center")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class CivicCenter extends Amenity{

    @Column(name="civic_center_gu", nullable = true)
    private String gu;

    @Builder(builderMethodName = "civicCenterBuilder")
    public CivicCenter(String name, String address, Point location, String phoneNumber, String gu){
        super(name, address, location, phoneNumber);
        this.gu = gu;
    }
}
