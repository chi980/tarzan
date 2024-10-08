package com.mjutarzan.tarzan.domain.map.entity.shopping;

import com.mjutarzan.tarzan.domain.map.model.vo.BuildingType;
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
@DiscriminatorValue("convenience_store")
@NoArgsConstructor
@ToString(callSuper = true)
public class ConvenienceStore extends Shopping  implements DataInstance {

    @Builder(builderMethodName = "convenienceStoreBuilder")
    public ConvenienceStore(String name, String address, Point location, String phoneNumber, String zipCode, String category){
        super(name, address, location, phoneNumber, zipCode, category!=null?category: BuildingType.CONVENIENCE_STORE.getKor());

    }

    @Override
    public DataInstance getInstance(String[] info, Point location) {
//        영업상태코드,영업상태명,소재지우편번호,지번주소,도로명주소,도로명우편번호,사업장명,업태구분명,좌표정보(X),좌표정보(Y),영업장주변구분명,전화번호,주소,위도_user,경도_user
        String zipCode1 = info[5];
        String zipCode2 = info[2];
        super.setZipCode(zipCode1.trim().length()!=0?zipCode1:zipCode2);

        String address1 = info[4];
        String address2 = info[3];
        super.setAddress(address1.trim().length()!=0?address1: address2);

        super.setName(info[6]);
        super.setCategory(info[7]);
        super.setLocation(location);
        super.setPhoneNumber(info[11]);
        return this;
    }
}
