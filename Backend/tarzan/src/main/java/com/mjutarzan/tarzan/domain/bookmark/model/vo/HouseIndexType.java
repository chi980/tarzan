package com.mjutarzan.tarzan.domain.bookmark.model.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HouseIndexType {
    AMENITY("편의시설"), CLINIC("보건"), SECURITY("치안"), SHOPPING("상업시설"), TRANSPORTATION("교통");

    private final String kor;
}
