package com.mjutarzan.tarzan.domain.map.entity.security;

import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@DiscriminatorValue("police")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Police extends Security{

    @Column(name = "police_category", nullable = true)
    private String category;

    @Builder(builderMethodName = "policeBuilder")
    public Police(String name, String address, Point location, String phoneNumber, SiGunGu gu, String category){
        super(name, address, location, phoneNumber, gu);
        this.category = category;
    }
}
