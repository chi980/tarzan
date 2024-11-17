package com.mjutarzan.tarzan.domain.map.entity.shopping;

import com.mjutarzan.tarzan.domain.map.entity.Building;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
@DiscriminatorValue("shopping")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Shopping  extends Building {
    @Column(name = "shopping_zip_code", nullable = true)
    private String zipCode;

    @Builder
    public Shopping(String name, String address, Point location, String phoneNumber, String zipCode, String category){
        super(name, address, category, location, phoneNumber);

        this.zipCode = zipCode;
    }
}
