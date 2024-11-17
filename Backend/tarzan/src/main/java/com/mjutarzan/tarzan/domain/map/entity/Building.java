package com.mjutarzan.tarzan.domain.map.entity;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "building_type")
@Table(name = "building")
@ToString
public abstract class Building{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_id")
    private Long id;

    @Column(name = "building_name", nullable = true)
    private String name;

    @Lob
    @Column(name = "building_address", nullable = true)
    private String address;

    @Column(name="building_category", nullable = true)
    private String category;

    @Column(columnDefinition = "geometry(Point,4326)", name = "building_location", nullable = false)
    private Point location;

    @Column(name = "building_phone_number", nullable = true)
    private String phoneNumber;

    protected Building(String name, String address, String category, Point location, String phoneNumber){
        this.name = name;
        this.address = address;
        this.category = category;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }
}
