package com.mjutarzan.tarzan.domain.map.entity.security;

import com.mjutarzan.tarzan.domain.map.model.vo.BuildingType;
import com.mjutarzan.tarzan.global.common.dto.DataInstance;
import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@DiscriminatorValue("cctv")
@NoArgsConstructor
@ToString(callSuper = true)
public class Cctv extends Security implements DataInstance {

    @Builder(builderMethodName = "cctvBuilder")
    public Cctv(String name, String address, Point location, String phoneNumber, SiGunGu gu){
        super(name, address, BuildingType.CCTV.getKor(), location, phoneNumber, gu);
    }

    @Override
    public DataInstance getInstance(String[] info, Point location) {
//        자치구,안심 주소,위도,경도
        super.setName(info[1]);
        super.setAddress(info[1]);
        super.setLocation(location);
        super.setGu(SiGunGu.fromKor(info[0].replace(" ", "")));
        return this;
    }
}
