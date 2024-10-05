package com.mjutarzan.tarzan.domain.map.entity.security;

import com.mjutarzan.tarzan.domain.map.entity.Building;
import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
@DiscriminatorValue("security")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
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
