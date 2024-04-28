package com.mjutarzan.tarzan.domain.house.entity;

import com.mjutarzan.tarzan.global.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.geolatte.geom.Geometry;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Table(name = "house")
public abstract class House extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id")
    private Long id;

    @Column(name = "house_name", nullable = false)
    private String name;

    @Lob
    @Column(name = "house_address", nullable = false)
    private String address;

    @Column(name = "house_location", nullable = false)
    private Geometry location;

    protected House(String name, String address, Geometry location){
        this.name = name;
        this.address = address;
        this.location = location;
    }
}
