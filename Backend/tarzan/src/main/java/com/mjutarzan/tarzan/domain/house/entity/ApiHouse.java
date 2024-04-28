package com.mjutarzan.tarzan.domain.house.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.geolatte.geom.Geometry;

@Entity
@Getter
@DiscriminatorValue(value = "api")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiHouse extends House{

    @Column(name = "house_category", nullable = true)
    private String category;

    @Builder
    public ApiHouse(String name, String address, Geometry location, String category){
        super(name, address, location);
        this.category = category;
    }
}
