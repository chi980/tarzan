package com.mjutarzan.tarzan.domain.test;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@DiscriminatorValue("classic")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Classic extends Album{

    @Column(name = "classic_music")
    private String music;

    @Builder(builderMethodName = "classicBuilder")
    public Classic(Long id, String name, Integer price, String artist, String music) {
        super(id, name, price, artist);
        this.music = music;
    }
}
