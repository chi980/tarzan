import { ref } from "vue";

export interface Check {
  idx: number;
  name: string;
  value: boolean;
}

export interface CategoryCheckList {
  BEFO_MOVE: Check[];
  AFTER_MOVE: Check[];
}

export interface FullCheckListData {
  homeAppliances: CategoryCheckList;
  fabrics: CategoryCheckList;
  bathroomSupplies: CategoryCheckList;
  ingredients: CategoryCheckList;
  kitchenUtensils: CategoryCheckList;
  householdGoods: CategoryCheckList;
}

export const checkListData: FullCheckListData = {
  homeAppliances: {
    BEFO_MOVE: [
      { idx: 1, name: "공유기", value: false },
      { idx: 2, name: "멀티탭 3구, 6구", value: false },
      { idx: 3, name: "헤어 드라이어", value: false },
      { idx: 4, name: "전자레인지", value: false },
      { idx: 5, name: "청소기", value: false },
      { idx: 6, name: "냉장고", value: false },
      { idx: 7, name: "세탁기", value: false },
    ],
    AFTER_MOVE: [
      { idx: 1, name: "인덕션/멀티쿠커/가스레인지", value: false },
      { idx: 2, name: "스탠드 조명", value: false },
      { idx: 3, name: "공기청정기", value: false },
      { idx: 4, name: "전기포트", value: false },
      { idx: 5, name: "미니다리미", value: false },
      { idx: 6, name: "선풍기(써큘레이터)", value: false },
      { idx: 7, name: "전기장판", value: false },
    ],
  },
  fabrics: {
    BEFO_MOVE: [
      { idx: 1, name: "매트리스/토퍼", value: false },
      { idx: 2, name: "침대 프레임", value: false },
      { idx: 3, name: "이불", value: false },
      { idx: 4, name: "배게/배게 커버", value: false },
      { idx: 5, name: "매트리스 커버", value: false },
      { idx: 6, name: "매트리스 패드", value: false },
    ],
    AFTER_MOVE: [
      { idx: 1, name: "행거/수납장", value: false },
      { idx: 2, name: "2인용 식탁/의자", value: false },
      { idx: 3, name: "화장대, 책상거울", value: false },
      { idx: 4, name: "전신거울", value: false },
      { idx: 5, name: "주방, 욕실 발매트", value: false },
      { idx: 6, name: "러그", value: false },
      { idx: 7, name: "기본공구, 줄자", value: false },
      { idx: 8, name: "커튼/블라인드", value: false },
    ],
  },
  bathroomSupplies: {
    BEFO_MOVE: [
      { idx: 1, name: "두루마리 휴지", value: false },
      { idx: 2, name: "칫솔, 치약", value: false },
      { idx: 3, name: "수건", value: false },
      { idx: 4, name: "샴푸, 린스", value: false },
      { idx: 5, name: "바디 클렌저", value: false },
      { idx: 6, name: "비누, 브러쉬", value: false },
      { idx: 7, name: "대야", value: false },
    ],
    AFTER_MOVE: [
      { idx: 1, name: "욕실슬리퍼", value: false },
      { idx: 2, name: "욕실세정제", value: false },
      { idx: 3, name: "청소용 고무장갑", value: false },
      { idx: 4, name: "바닥청소솔", value: false },
      { idx: 5, name: "뚫어뻥", value: false },
      { idx: 6, name: "매직블럭", value: false },
      { idx: 7, name: "면봉", value: false },
    ],
  },
  ingredients: {
    BEFO_MOVE: [
      { idx: 1, name: "물", value: false },
      { idx: 2, name: "김치", value: false },
      { idx: 3, name: "햇반", value: false },
      { idx: 4, name: "라면", value: false },
      { idx: 5, name: "김", value: false },
    ],
    AFTER_MOVE: [
      { idx: 1, name: "식용유", value: false },
      { idx: 2, name: "소금, 설탕", value: false },
      { idx: 3, name: "고추장", value: false },
      { idx: 4, name: "양조간장", value: false },
      { idx: 5, name: "식초", value: false },
      { idx: 6, name: "후추", value: false },
      { idx: 7, name: "계란", value: false },
      { idx: 8, name: "참치캔", value: false },
      { idx: 9, name: "버터", value: false },
      { idx: 10, name: "잼", value: false },
    ],
  },
  kitchenUtensils: {
    BEFO_MOVE: [
      { idx: 1, name: "밥, 국그릇 세트 2개", value: false },
      { idx: 2, name: "수저 세트 2개", value: false },
      { idx: 3, name: "음료컵, 물컵", value: false },
      { idx: 4, name: "랩, 호일", value: false },
      { idx: 5, name: "냄비", value: false },
      { idx: 6, name: "수세미, 주방세제", value: false },
      { idx: 7, name: "위생봉투/지퍼백", value: false },
      { idx: 8, name: "키친타올", value: false },
      { idx: 9, name: "물티슈", value: false },
    ],
    AFTER_MOVE: [
      { idx: 1, name: "후라이팬", value: false },
      { idx: 2, name: "냄비 받침", value: false },
      { idx: 3, name: "쟁반", value: false },
      { idx: 4, name: "과도", value: false },
      { idx: 5, name: "긴 젓가락/집게", value: false },
      { idx: 6, name: "뒤집개/국자", value: false },
      { idx: 7, name: "도마, 보울, 채반", value: false },
      { idx: 8, name: "주방 가위", value: false },
      { idx: 9, name: "밀폐용기(반찬통)", value: false },
      { idx: 10, name: "일회용기", value: false },
      { idx: 11, name: "식기건조대", value: false },
      { idx: 12, name: "수저통", value: false },
      { idx: 13, name: "베이킹소다", value: false },
      { idx: 14, name: "행주, 고무장갑", value: false },
    ],
  },
  householdGoods: {
    BEFO_MOVE: [
      { idx: 1, name: "옷걸이", value: false },
      { idx: 2, name: "커터칼", value: false },
      { idx: 3, name: "빗자루/막대걸레/정전기포", value: false },
      { idx: 4, name: "박스테이프", value: false },
      { idx: 5, name: "비상약", value: false },
      { idx: 6, name: "음식물/일반 쓰레기봉투", value: false },
      { idx: 7, name: "에프킬라", value: false },
    ],
    AFTER_MOVE: [
      { idx: 1, name: "세탁 세제, 섬유유연제", value: false },
      { idx: 2, name: "중성 세제(울샴푸)", value: false },
      { idx: 3, name: "빨래 바구니, 빨래집게", value: false },
      { idx: 4, name: "빨래 건조대", value: false },
      { idx: 5, name: "쓰레기통", value: false },
      { idx: 6, name: "세탁망", value: false },
      { idx: 7, name: "분리수거함", value: false },
      { idx: 8, name: "물구멍 방충망", value: false },
      { idx: 9, name: "바퀴벌레 트랩", value: false },
      { idx: 10, name: "미니 반짇고리", value: false },
      { idx: 11, name: "손톱깎이", value: false },
      { idx: 12, name: "돌돌이", value: false },
      { idx: 13, name: "우산", value: false },
    ],
  },
};
