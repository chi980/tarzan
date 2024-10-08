package com.mjutarzan.tarzan.domain.map.entity.transportation;

import com.mjutarzan.tarzan.domain.map.model.vo.BuildingType;
import com.mjutarzan.tarzan.global.common.dto.DataInstance;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@DiscriminatorValue("subway")
@NoArgsConstructor
@ToString(callSuper = true)
public class Subway extends Transportation implements DataInstance {

    // 1호선...
    @Column(name="subway_type", nullable = true)
    private String type;

    @Builder(builderMethodName = "subwayBuilder")
    public Subway(String name, String address, Point location, String phoneNumber, String type){
        super(name, address, BuildingType.SUBWAY.getKor(), location, phoneNumber);
        this.type = type;
    }
    @Override
    public DataInstance getInstance(String[] info, Point location) {
//        호선,역명,구주소,도로명주소,전화번호,위도_user,경도_user
        this.setType(info[0]);
        super.setName(info[1]);
        String address1 = info[3];
        String address2 = info[2];
        super.setAddress(address1.trim().length()!=0?address1:address2);
        super.setPhoneNumber(info[4]);
        super.setLocation(location);
        return this;
    }
}
