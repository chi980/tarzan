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
@DiscriminatorValue("cctv")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cctv extends Security{

    @Builder(builderMethodName = "cctvBuilder")
    public Cctv(String name, String address, Point location, String phoneNumber, SiGunGu gu){
        super(name, address, location, phoneNumber, gu);
    }
}
