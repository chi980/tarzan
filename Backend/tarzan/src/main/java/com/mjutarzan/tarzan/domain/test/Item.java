package com.mjutarzan.tarzan.domain.test;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_type")
@Table(name = "item")
@ToString
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_name")
    private String name;

    @Column(name = "item_price")
    private Integer price;

    public Item(Long id, String name, Integer price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
