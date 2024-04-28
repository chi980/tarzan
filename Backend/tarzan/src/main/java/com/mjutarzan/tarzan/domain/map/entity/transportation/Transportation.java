package com.mjutarzan.tarzan.domain.map.entity.transportation;

import com.mjutarzan.tarzan.domain.map.entity.Building;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("transportation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transportation extends Building {
}
