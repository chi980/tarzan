package com.mjutarzan.tarzan.domain.map.entity.clinic;

import com.mjutarzan.tarzan.global.common.dto.DataInstance;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@DiscriminatorValue("medical_clinic")
@NoArgsConstructor
@ToString(callSuper = true)
public class MedicalClinic extends Clinic implements DataInstance {
    @Builder
    public MedicalClinic(String name, String address, Point location, String phoneNumber){
        super(name, address, location, phoneNumber);
    }

    @Override
    public DataInstance getInstance(String[] info, Point location) {
//        지번주소,도로명주소,사업장명,위도_user,경도_user,전처리완_전화번호
        String address1 = info[1];
        String address2 = info[0];
        super.setAddress(address1.trim().length()!=0?address1: address2);

        super.setName(info[2]);
        super.setLocation(location);
        super.setPhoneNumber(info[5]);
        return this;
    }
}
