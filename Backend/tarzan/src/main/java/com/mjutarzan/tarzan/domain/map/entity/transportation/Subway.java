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
@DiscriminatorValue("subway")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subway extends Transportation{

    // 1호선...
    @Column(name="subway_type", nullable = true)
    private String type;

    @Builder(builderMethodName = "subwayBuilder")
    public Subway(String name, String address, Point location, String phoneNumber, String type){
        super(name, address, location, phoneNumber);
        this.type = type;
    }
}
