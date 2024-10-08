package com.mjutarzan.tarzan.domain.review.model.vo;

public enum LeaseType {
    MONTHLY("월세"), KEY_MONEY("전세");

    private final String kor; // 한글 이름

    LeaseType(String kor) {
        this.kor = kor;
    }

    public String getName() {
        return name();
    }
    public String getKor() {
        return kor;
    }
}
