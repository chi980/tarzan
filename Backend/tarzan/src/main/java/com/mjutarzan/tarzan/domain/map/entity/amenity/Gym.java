package com.mjutarzan.tarzan.domain.map.entity.amenity;

import com.mjutarzan.tarzan.global.common.dto.DataInstance;
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
@DiscriminatorValue("gym")
@NoArgsConstructor
@ToString(callSuper = true)
public class Gym extends Amenity implements DataInstance {

    @Column(name="gym_category", nullable = true)
    private String category;

    @Builder(builderMethodName = "gymBuilder")
    public Gym(String name, String address, Point location, String phoneNumber, String category){
        super(name, address, location, phoneNumber);
        this.category = category;
    }

    @Override
    public DataInstance getInstance(String[] info, Point location) {
//        name,phone,category,addr,위도_user,경도_user
        super.setName(info[0]);
        super.setPhoneNumber(info[1]);
        this.category = info[2];
        this.setAddress(info[3]);
        super.setLocation(location);
        return this;
    }
}
