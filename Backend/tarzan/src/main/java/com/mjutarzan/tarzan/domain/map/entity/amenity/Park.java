package com.mjutarzan.tarzan.domain.map.entity.amenity;

import com.mjutarzan.tarzan.domain.map.model.vo.BuildingType;
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
@DiscriminatorValue("park")
@NoArgsConstructor
@ToString(callSuper = true)
public class Park extends Amenity implements DataInstance {

    @Enumerated(EnumType.STRING)
    @Column(name="park_gu", nullable = true)
    private SiGunGu gu;


    @Builder(builderMethodName = "parkBuilder")
    public Park(String name, String address, String category, Point location, String phoneNumber, SiGunGu gu){
        super(name, address, category!=null?category: BuildingType.PARK.getKor(), location, phoneNumber);
        this.gu = gu;
    }

    @Override
    public DataInstance getInstance(String[] info, Point location) {
//        name,gu,addr,phone,위도_user,경도_user
        super.setName(info[0]);
        this.gu = SiGunGu.fromKor(info[1].replace(" ", ""));
        super.setAddress(info[2]);
        super.setPhoneNumber(info[3]);
        super.setLocation(location);
        return this;
    }
}
