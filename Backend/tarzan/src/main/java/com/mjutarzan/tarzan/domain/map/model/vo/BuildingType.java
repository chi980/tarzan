package com.mjutarzan.tarzan.domain.map.model.vo;

public enum BuildingType {
    ALL("전체"),CIVIC_CENTER("주민센터"), GYM("체육관"), PARK("공원"),
    HOSPITAL("병원"), PHARMACY("약국"),MEDICAL_CLINIC("응급의료시설"),
    CCTV("cctv"),POLICE("경찰서"),
    CONVENIENCE_STORE("편의점"), MART("마트"),
    SUBWAY("지하철 출구"), BUS("버스 정류장"), BICYCLE("따릉이");
    private final String kor; // 한글 이름

    BuildingType(String kor) {
        this.kor = kor;
    }

    public String getName() {
        return name();
    }
    public String getKor() {
        return kor;
    }
}
