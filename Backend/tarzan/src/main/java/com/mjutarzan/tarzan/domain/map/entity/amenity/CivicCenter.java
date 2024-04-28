package com.mjutarzan.tarzan.domain.map.entity.amenity;

import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    @Column(name="civic_center_gu", nullable = true)
    private SiGunGu gu;

    @Builder(builderMethodName = "civicCenterBuilder")
    public CivicCenter(String name, String address, Point location, String phoneNumber, SiGunGu gu){
        super(name, address, location, phoneNumber);
        this.gu = gu;
    }
}
