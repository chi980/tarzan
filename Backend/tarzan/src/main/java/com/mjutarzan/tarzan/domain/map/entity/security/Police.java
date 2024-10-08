package com.mjutarzan.tarzan.domain.map.entity.security;

import com.mjutarzan.tarzan.global.common.dto.DataInstance;
import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@DiscriminatorValue("police")
@NoArgsConstructor
@ToString(callSuper = true)
public class Police extends Security implements DataInstance {

    @Column(name = "police_category", nullable = true)
    private String category;

    @Builder(builderMethodName = "policeBuilder")
    public Police(String name, String address, Point location, String phoneNumber, SiGunGu gu, String category){
        super(name, address, location, phoneNumber, gu);
        this.category = category;
    }

    @Override
    public DataInstance getInstance(String[] info, Point location) {
//        num,gu,name,category,phone,addr,위도_user,경도_user
        String gu = info[5].split(" ")[1].trim();
        super.setGu(SiGunGu.fromKor(gu));
        super.setName(info[2]);
        this.category = info[3];
        super.setPhoneNumber(info[4]);
        super.setAddress(info[5]);
        super.setLocation(location);
        return this;
    }
}
