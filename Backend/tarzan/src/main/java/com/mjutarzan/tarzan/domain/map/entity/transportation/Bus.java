package com.mjutarzan.tarzan.domain.map.entity.transportation;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@DiscriminatorValue("bus")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bus  extends Transportation{

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
}
