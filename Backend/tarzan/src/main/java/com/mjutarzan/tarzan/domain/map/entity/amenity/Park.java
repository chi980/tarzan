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
@DiscriminatorValue("park")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Park extends Amenity{

    @Enumerated(EnumType.STRING)
    @Column(name="park_gu", nullable = true)
    private SiGunGu gu;


    @Builder(builderMethodName = "parkBuilder")
    public Park(String name, String address, Point location, String phoneNumber, SiGunGu gu){
        super(name, address, location, phoneNumber);
        this.gu = gu;
    }
}
