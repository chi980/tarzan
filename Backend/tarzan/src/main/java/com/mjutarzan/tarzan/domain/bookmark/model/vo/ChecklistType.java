package com.mjutarzan.tarzan.domain.bookmark.model.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public enum ChecklistType {

    MOVER("이사 업체", Map.ofEntries(
            Map.entry("move_day_before_30days", List.of("이사 방식 선택", "이사 업체 선정 후 계약", "불필요한 물품 정리", "입주 청소 예약", "냉장고 파먹기", "정기 구독 서비스 확인", "인터넷 이전 신청")),
            Map.entry("move_day_before_7days", List.of("가구 배치 시뮬레이션", "관리사무소 이사 일정 통보", "은행 이체 한도 확인", "도시 가스 요금 정산")),
            Map.entry("move_day_before_1days", List.of("귀중품 점검", "냉장고, 세탁기 정리")),
            Map.entry("move_day_before_ddays", List.of("수도와 전기 요금 정산", "새 집 상태 점검", "쓰레기 봉투 구매", "이사 요금 지불", "전입신고와 확정일자", "우편물 이전 신청"))
    )),
    ITEM("자취 필수품", Map.ofEntries(
            Map.entry("home_appliances_before_move", List.of("공유기", "멀티탭3구, 6구", "헤어 드라이어", "전자레인지", "청소기", "냉장고", "에어컨", "세탁기")),
            Map.entry("home_appliances_after_move", List.of("인덕션(멀티쿠커, 가스레인지)", "스탠드 조명", "공기청정기", "전기포트", "미니다리미", "선풍기(써큘레이터)", "전기장판")),

            Map.entry("furniture_fabric_before_move", List.of("매트릭스(토퍼)", "침대프레임", "이불", "베개, 베개커버", "매트리스 커버", "매트리스 패드")),
            Map.entry("furniture_fabric_after_move", List.of("행거, 수납장", "2인용 식탁, 의자", "화장대, 책상거울", "전신거울", "주방, 욕실 발매트", "러그", "기본 공구, 줄가", "커튼(블라인드)")),

            Map.entry("bathroom_before_move", List.of("두루마리 휴지", "칫솔, 치약", "수건", "샴푸", "린스", "바디클렌저", "비누, 브러시", "대야")),
            Map.entry("bathroom_after_move", List.of("욕실슬리퍼", "욕실세정제", "청소용 고무장갑", "바닥청소솔", "뚫어뻥", "매직블럭", "면봉")),

            Map.entry("food_before_move", List.of("물", "김치", "햇반", "라면", "김")),
            Map.entry("food_after_move", List.of("식용유", "소금, 설탕", "고추장", "양조간장", "식초", "후추", "계란", "참치캔", "버터", "잼")),

            Map.entry("kitchen_before_move", List.of("밥, 국그릇세트 2개", "수저세트 2개", "음료컵, 물컵", "랩, 호일", "라면 1개용 냄비", "수세미, 주방세제", "위생봉투, 지퍼백", "키친타올, 물티슈")),
            Map.entry("kitchen_after_move", List.of("계란 2개용 후라이팬", "라면 2개용 냄비", "냄비받침", "작은 쟁반, 큰 쟁반", "과도, 수박칼", "긴 젓가락, 집게", "뒤집개, 국자", "도마, 보울, 채반", "주방가위", "밀폐용기(반찬통)", "일회용기", "식기건조대", "수저통", "베이킹소다", "행주, 고무장갑")),

            Map.entry("household_before_move", List.of("옷걸이", "커터칼", "빗자루(막대걸레, 정전기포)", "박스테이프", "비상약", "음식물, 일반 쓰레기봉투", "에프킬라(바퀴벌레, 모기용)")),
            Map.entry("household_after_move", List.of("세탁세제, 섬유유연제", "중성세제(울샴푸)", "빨래바구니, 빨래집게", "빨래건조대", "쓰레기통", "세탁망", "창문 잠금장치", "분리수거함", "물구멍방충망", "바퀴벌레 트랩", "미니반짇고리", "손톱깎이", "돌돌이", "우산"))

    ));


    private final String kor;
    private final Map<String, List<String>> names;

    public int getCnt() {
        return names.values().stream()
                .mapToInt(List::size)
                .sum();
    }
}
