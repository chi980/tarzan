package com.mjutarzan.tarzan.global.common.vo;

import java.util.Arrays;

public enum SiGunGu {
    JONGNO("종로구"),
    JUNG("중구"),
    YONGSAN("용산구"),
    SEONGDONG("성동구"),
    GWANGJIN("광진구"),
    DONGDAEMUN("동대문구"),
    JUNGNANG("중랑구"),
    SUNGDONG("성북구"),
    GANGBUK("강북구"),
    DOBONG("도봉구"),
    NOWON("노원구"),
    EUNPYEONG("은평구"),
    SEODAEMUN("서대문구"),
    MAPO("마포구"),
    GANGSEO("강서구"),
    GURO("구로구"),
    GEUMCHEON("금천구"),
    YANGCHEON("양천구"),
    GANGNAM("강남구"),
    SONGPA("송파구"),
    GANGDONG("강동구");

    private final String kor; // 한글 이름

    SiGunGu(String kor) {
        this.kor = kor;
    }
    public String getName() {
        return name();
    }
    public String getKor() {
        return kor;
    }

    // Static method to convert a String to a SiGunGu enum using streams
    public static SiGunGu fromName(String name) {
        return Arrays.stream(SiGunGu.values())
                .filter(sigungu -> sigungu.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant for the name: " + name));
    }

    // Static method to convert a Korean name to a SiGunGu enum using streams
    public static SiGunGu fromKor(String kor) {
        return Arrays.stream(SiGunGu.values())
                .filter(sigungu -> sigungu.getKor().equals(kor))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant for the Korean name: " + kor));
    }
}

