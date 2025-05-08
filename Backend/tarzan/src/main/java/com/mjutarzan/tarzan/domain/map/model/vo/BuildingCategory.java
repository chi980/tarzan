package com.mjutarzan.tarzan.domain.map.model.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BuildingCategory {
    AMENITY("상업시설"), CLINIC("보건"),SECURITY("치안"),SHOPPING("상업시설"),TRANSPORTATION("교통");
    private final String kor;
}
