package com.mjutarzan.tarzan.domain.map.entity.shopping;

import com.mjutarzan.tarzan.domain.map.entity.Building;
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
@DiscriminatorValue("shopping")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shopping  extends Building {
    @Column(name = "shopping_zip_code", nullable = true)
    private String zipCode;

    @Column(name = "shopping_category", nullable = true)
    private String category;
    @Builder
    public Shopping(String name, String address, Point location, String phoneNumber, String zipCode, String category){
        super(name, address, location, phoneNumber);

        this.zipCode = zipCode;
        this.category = category;
    }
}
