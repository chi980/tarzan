package com.mjutarzan.tarzan.domain.map.entity.amenity;

import com.mjutarzan.tarzan.global.common.dto.DataInstance;
import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@DiscriminatorValue("civic_center")
@NoArgsConstructor
@ToString(callSuper = true)
public class CivicCenter extends Amenity implements DataInstance {

    @Enumerated(EnumType.STRING)
    @Column(name="civic_center_gu", nullable = true)
    private SiGunGu gu;

    @Column(name="civic_center_dong", nullable = true)
    private String dong;

    @Builder(builderMethodName = "civicCenterBuilder")
    public CivicCenter(String name, String address, Point location, String phoneNumber, SiGunGu gu, String dong){
        super(name, address, location, phoneNumber);
        this.gu = gu;
        this.dong = dong;
    }

    @Override
    public DataInstance getInstance(String[] info, Point location) {
//        name,addr,gu,dong,위도_user,경도_user
        super.setName(info[0]);
        super.setAddress(info[1]);
        this.gu = SiGunGu.fromKor(info[2].replace(" ", ""));
        this.dong = info[3];
        super.setLocation(location);
        return this;
    }
}
