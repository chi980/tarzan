package com.mjutarzan.tarzan.domain.map.entity.transportation;

import com.mjutarzan.tarzan.global.common.dto.DataInstance;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@DiscriminatorValue("bus")
@NoArgsConstructor
@ToString(callSuper = true)
public class Bus  extends Transportation implements DataInstance {

    @Column(name="bus_stop_unique_number", nullable = true)
    private String uniqueNumber;

    @Column(name = "bus_stop_mobile_number", nullable = true)
    private String mobileNumber;

    @Builder(builderMethodName = "busBuilder")
    public Bus(String name, String address, Point location, String phoneNumber, String uniqueNumber, String mobileNumber){
        super(name, address, location, phoneNumber);
        this.uniqueNumber = uniqueNumber;
        this.mobileNumber = mobileNumber;
    }

    @Override
    public DataInstance getInstance(String[] info, Point location) {
//        정류장번호,정류장명,위도_user,경도_user,정보수집일,모바일단축번호,도시코드,도시명,관리도시명
        this.setUniqueNumber(info[0]);
        super.setName(info[1]);
        super.setLocation(location);
        this.setMobileNumber(info[5]);
        return this;
    }
}
