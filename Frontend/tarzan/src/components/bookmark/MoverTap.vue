<template>
  <div class="accordion-wrapper">
    <BasicAccordion accordionTitle="이사 D-30">
      <CheckListItem
        v-for="checkItem in checkListBefore30Days"
        :key="checkItem.idx"
        :checkListItem="checkItem"
      ></CheckListItem>
    </BasicAccordion>
    <BasicAccordion accordionTitle="이사 D-7">
      <CheckListItem
        v-for="checkItem in checkListBefore7Days"
        :key="checkItem.idx"
        :checkListItem="checkItem"
      ></CheckListItem>
    </BasicAccordion>
    <BasicAccordion accordionTitle="이사 D-1">
      <CheckListItem
        v-for="checkItem in checkListBefore1Days"
        :key="checkItem.idx"
        :checkListItem="checkItem"
      ></CheckListItem>
    </BasicAccordion>
    <BasicAccordion accordionTitle="이사 D-DAY">
      <CheckListItem
        v-for="checkItem in checkListDdays"
        :key="checkItem.idx"
        :checkListItem="checkItem"
      ></CheckListItem>
    </BasicAccordion>

    <div>
      <div class="button-in-tab">
        <p>전입 신고 하기</p>
      </div>

      <div class="button-in-tab">
        <p>우편물 이전 신청하기</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { Check } from "@/data/check";
import BasicAccordion from "@/components/common/BasicAccordion.vue";
import CheckListItem from "@/components/common/CheckListItem.vue";

const checkListBefore30Days: Check[] = [
  { idx: 1, name: "이사 방식 선택", value: false },
  { idx: 2, name: "이사 업체 선정 후 계약", value: false },
  { idx: 3, name: "불필요한 물품 정리", value: false },
  { idx: 4, name: "입주 청소 예약", value: false },
  { idx: 5, name: "냉장고 파먹기", value: false },
  { idx: 6, name: "정기 구독 서비스 확인", value: false },
  { idx: 7, name: "인터넷 이전 신청", value: false },
];

const checkListBefore7Days: Check[] = [
  { idx: 1, name: "가구 배치 시뮬레이션", value: false },
  { idx: 2, name: "관리사무소 이사 일정 통보", value: false },
  { idx: 3, name: "은행 이체 한도 확인", value: false },
  { idx: 4, name: "도시 가스 요금 정산", value: false },
];

const checkListBefore1Days: Check[] = [
  { idx: 1, name: "귀중품 점검", value: false },
  { idx: 2, name: "냉장고, 세탁기 정리", value: false },
];

const checkListDdays: Check[] = [
  { idx: 1, name: "수도와 전기 요금 정산", value: false },
  { idx: 2, name: "새 집 상태 점검", value: false },
  { idx: 3, name: "쓰레기 봉투 구매", value: false },
  { idx: 4, name: "이사 요금 지불", value: false },
  { idx: 5, name: "전입신고와 확정일자", value: false },
  { idx: 6, name: "우편물 이전 신청", value: false },
];
</script>

<style lang="scss" scoped>
// 공통

.button-in-tab {
  @include custom-button-style(
    $bg-color: #f2f2f2,
    $icon-size: 18px,
    $height: 52px
  );
  margin-bottom: $padding-small;
}
// scoped
.accordion-wrapper {
  display: flex;
  flex-direction: column;
  gap: $padding-small;
}
</style>
