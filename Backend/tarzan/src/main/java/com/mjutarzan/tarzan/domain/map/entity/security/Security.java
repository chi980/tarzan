package com.mjutarzan.tarzan.domain.map.entity.security;

import com.mjutarzan.tarzan.domain.map.entity.Building;
import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@DiscriminatorValue("security")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Security  extends Building {

    @Enumerated(EnumType.STRING)
    @Column(name="security_gu", nullable = true)
    private SiGunGu gu;

    @Builder
    public Security(String name, String address, Point location, String phoneNumber, SiGunGu gu){
        super(name, address, location, phoneNumber);
        this.gu = gu;
    }
}
