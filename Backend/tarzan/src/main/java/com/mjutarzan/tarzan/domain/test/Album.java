package com.mjutarzan.tarzan.domain.test;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@DiscriminatorValue("album")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Album extends Item{

    @Column(name = "album_artist")
    private String artist;

    @Builder(builderMethodName = "albumBuilder")
    public Album(Long id, String name, Integer price, String artist) {
        super(id, name, price);
        this.artist = artist;
    }
}
