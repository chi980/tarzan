package com.mjutarzan.tarzan.domain.bookmark.model.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ChecklistType {
    MOVER("이사 업체"), ITEM("자취 필수품");

    private final String kor;

}
