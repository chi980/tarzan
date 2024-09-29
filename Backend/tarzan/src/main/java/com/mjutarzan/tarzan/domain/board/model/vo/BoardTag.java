package com.mjutarzan.tarzan.domain.board.model.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum BoardTag {
    TRANSPORT("교통"), TASTE("맛집"),LIFE("생활팁"),QUESTION("질문"),MEETING("모임"),ETC("기타");

    private final String kor;

    public static BoardTag fromKor(String kor) {
        return Arrays.stream(BoardTag.values())
                .filter(tag -> tag.getKor().equals(kor))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 태그입니다: " + kor));
    }
}
