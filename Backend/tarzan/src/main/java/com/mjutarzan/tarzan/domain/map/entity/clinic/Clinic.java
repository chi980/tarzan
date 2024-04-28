package com.mjutarzan.tarzan.domain.map.entity.clinic;

import com.mjutarzan.tarzan.domain.map.entity.Building;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("clinic")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Clinic extends Building {

}
