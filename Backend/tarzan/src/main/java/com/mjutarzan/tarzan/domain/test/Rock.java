package com.mjutarzan.tarzan.domain.test;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@DiscriminatorValue("rock")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rock extends Album{

    @Column(name = "rock_festival")
    private String festival;

    @Builder(builderMethodName = "rockBuilder")
    public Rock(Long id, String name, Integer price, String artist, String festival) {
        super(id, name, price, artist);
        this.festival = festival;
    }
}
