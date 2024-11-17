package com.mjutarzan.tarzan.domain.fraud.entity.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum BuildingUse {
    APT("아파트"), SINGLE_FAMILY("단독다가구"), MULTIPLEX("연립다세대"), STUDIO("오피스텔");

    private final String kor;

    public static BuildingUse fromKor(String kor) {
        return Arrays.stream(BuildingUse.values())
                .filter(buildingUse -> buildingUse.getKor().equals(kor))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant for the Korean name: " + kor));
    }
}
