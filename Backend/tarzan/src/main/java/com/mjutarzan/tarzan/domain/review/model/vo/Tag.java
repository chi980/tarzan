package com.mjutarzan.tarzan.domain.review.model.vo;


public enum Tag {
    NONE("없음"), BUG("벌레"), BUILDING_MANAGEMENT("건물관리"),PARKING("주차"),
    SOUND_PROOF("방음"), SECURITY("치안"), TRAIL("산책로"), SECURITY_GUARD("경비실"),
    VENTILATION("환기"),FLAT("평지"),QUITE("조용한 동네"), PREVENT_HEAT("단열"),
    PET("반료동물 키우기"),ELEVATOR("엘레베이터"), MOLD("곰팡이") ;
    private final String kor; // 한글 이름

    Tag(String kor) {
        this.kor = kor;
    }

    public String getName() {
        return name();
    }
    public String getKor() {
        return kor;
    }
}
