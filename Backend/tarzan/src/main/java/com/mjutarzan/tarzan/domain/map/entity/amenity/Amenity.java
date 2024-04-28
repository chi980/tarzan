package com.mjutarzan.tarzan.domain.map.entity.amenity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("amenity")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Amenity {
}
