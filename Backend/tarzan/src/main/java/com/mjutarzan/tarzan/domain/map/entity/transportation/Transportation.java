package com.mjutarzan.tarzan.domain.map.entity.transportation;

import com.mjutarzan.tarzan.domain.map.entity.Building;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@DiscriminatorValue("transportation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transportation extends Building {

    @Builder
    public Transportation(String name, String address, String category, Point location, String phoneNumber){
        super(name, address, category, location, phoneNumber);
    }
}
