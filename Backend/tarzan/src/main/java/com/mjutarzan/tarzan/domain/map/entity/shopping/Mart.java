package com.mjutarzan.tarzan.domain.map.entity.shopping;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@DiscriminatorValue("mart")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mart extends Shopping{


    @Builder(builderMethodName = "martBuilder")
    public Mart(String name, String address, Point location, String phoneNumber, String zipCode, String category){
        super(name, address, location, phoneNumber, zipCode, category);

    }

}
