package com.mjutarzan.tarzan.domain.map.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "building_type")
@Table(name = "building")
public abstract class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_id")
    private Long id;

    @Column(name = "building_name", nullable = false)
    private String name;

    @Lob
    @Column(name = "building_address", nullable = false)
    private String address;

    @Column(columnDefinition = "geometry(Point,4326)", name = "building_location", nullable = false)
    private Point location;

    @Column(name = "building_phone_number", nullable = true)
    private String phoneNumber;

    protected Building(String name, String address, Point location, String phoneNumber){
        this.name = name;
        this.address = address;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }
}
