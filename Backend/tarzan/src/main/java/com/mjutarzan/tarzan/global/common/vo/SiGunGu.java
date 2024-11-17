package com.mjutarzan.tarzan.global.common.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum SiGunGu {
    JONGNO("종로구", "11110"),
    JUNG("중구", "11140"),
    YONGSAN("용산구", "11170"),
    SEONGDONG("성동구", "11200"),
    GWANGJIN("광진구", "11215"),
    DONGDAEMUN("동대문구", "11230"),
    JUNGNANG("중랑구", "11260"),
    SEONGBUK("성북구", "11290"),
    GANGBUK("강북구", "11305"),
    DOBONG("도봉구", "11320"),
    NOWON("노원구", "11350"),
    EUNPYEONG("은평구", "11380"),
    SEODAEMUN("서대문구", "11410"),
    MAPO("마포구", "11440"),
    GANGSEO("강서구", "11500"),
    GURO("구로구", "11530"),
    GEUMCHEON("금천구", "11545"),
    YANGCHEON("양천구", "11470"),
    GANGNAM("강남구", "11680"),
    SONGPA("송파구", "11710"),
    GANGDONG("강동구", "11740"),
    DONGJAK("동작구", "11590"),
    GWANAK("관악구", "11620"),
    YEONGDEUNGPO("영등포구", "11560"),
    SEOCHO("서초구", "11650");
    private final String kor; // 한글 이름
    private final String code;

    // Static method to convert a Korean name to a SiGunGu enum using streams
    public static SiGunGu fromKor(String kor) {
        return Arrays.stream(SiGunGu.values())
                .filter(sigungu -> sigungu.getKor().equals(kor))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant for the Korean name: " + kor));
    }

    public static SiGunGu fromCode(String code) {
        return Arrays.stream(SiGunGu.values())
                .filter(sigungu -> sigungu.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant for the code: " + code));
    }
}

