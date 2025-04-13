package com.mjutarzan.tarzan.domain.bookmark.model.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookmarkStatus {
    ALL("전체"),
    CHECK_PENDING("점검전"),
    CHECKING("점검중"),
    CHECK_COMPLETED("점검완료");

    private final String kor;
}
