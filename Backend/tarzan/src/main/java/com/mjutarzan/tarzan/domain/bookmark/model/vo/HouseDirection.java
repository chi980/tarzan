package com.mjutarzan.tarzan.domain.bookmark.model.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HouseDirection {
    NONE("모름"), NORTH("북향"), EAST("동향"), SOUTH("남향"),
    WEST("서향"), NORTHEAST("북동향"), SOUTHEAST("남동향"),
    SOUTHWEST("남서향"), NORTHWEST("북서향");

    private final String kor;
}
