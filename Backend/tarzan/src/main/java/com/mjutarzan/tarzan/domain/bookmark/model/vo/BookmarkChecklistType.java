package com.mjutarzan.tarzan.domain.bookmark.model.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum BookmarkChecklistType {

    OPTION_UTILITY_ROOM("주방 및 세탁실 옵션", Arrays.asList("식탁", "인덕션/가스레인지", "전자레인지", "냉장고", "세탁기", "싱크대")),
    OPTION_LIVING_ROOM("거실 옵션", Arrays.asList("TV", "천장/벽걸이 에어컨")),
    OPTION_ROOM("방 옵션", Arrays.asList("식탁", "인덕션/가스레인지", "전자레인지", "냉장고", "세탁기", "싱크대")),
    OPTION_BATH_ROOM("화장실 옵션", Arrays.asList("샤워부스", "비데")),
    OPTION_SECURITY("보안 및 기타시설 옵션", Arrays.asList("경비원/사설경비", "CCTV", "무인택배함", "현관보안","인터폰/비디어폰","카드키","화재경보기","베란다","엘레베이터","WIFI")),
    CHECK_WATER("수도와 배수", Arrays.asList("싱크대/세면대/샤워기 물 잘 나오는지", "변기 물 잘 내려가는지", "싱크대/화장실 매수구 잘 내려가는지", "싱크대/화장실 온수 잘 나오는지")),
    CHECK_WINDOW("창문",Arrays.asList("햇빛 잘 들어오는지", "방충망/방범창 이상 없는지", "환기하기에 적합한 크기인지", "옆 건물에서 잘 보이는 구조인지")),
    CHECK_BATHROOM("화장실",Arrays.asList("화장실 내부에 창문 있는지","배수구 냄새 올라오는지", "샤워 여유 공간 충분한지", "곰팡이 흔적 있는지")),
    CHECK_SURROUNDINGS("주변환경", Arrays.asList("대중교통 이용 편리한지", "주변에 소음 시설 있는지", "편의점, 은행 등 편의시설 있는지", "무인 택배 보관함 있는지", "집 가는 길이 언덕인지", "골목이라면 가로등 있는지")),
    CHECK_OPTION("기본 옵션",Arrays.asList("옵션 가구 종류 확인하기", "에어컨/냉장고 작동 점검하기", "화구 종류 체크하기", "옵션 가구 필요 없다면 치워줄 수 있는지 확인하기", "수납 공간 충분한지")),
    CHECK_DETAIL("디테일", Arrays.asList("벽지에 곰팡이 흔적 있는지", "바퀴벌레 약 설치되어 있는지", "콘센트 개수는 충분한지", "인터폰 영상 지원되는지", "옆집 방음 잘 되는지")),
    CHECK_SECURITY("보안",Arrays.asList("공동 현관 비밀번호 있는지", "출입구와 복도에 CCTV 있는지", "관리자분 상주하시는지")),
    CHECK_ETC("기타사항", Arrays.asList("관리비 포함 항목 확인하기", "건물에 집주인분 사시는지 확인하기", "인테리어 가능 여부 체크하기" ,"분리수거 시스템 확인하기"));

    private final String kor;
    private final List<String> names;
}
