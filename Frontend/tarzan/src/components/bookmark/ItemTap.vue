<template>
  <div class="tab-content">
    <ComplexAccordion
      accordionTitle="가전"
      :accordionContents="homeAppliances"
      @controllSubAccordion="controllSubAccordion"
    >
    </ComplexAccordion>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { Check, CheckList } from "@/data/check";
import ComplexAccordion from "@/components/common/ComplexAccordion.vue";
import CheckListItem from "@/components/common/CheckListItem.vue";

const homeAppliances: CheckList[] = [
  {
    idx: 1,
    title: "이사 전 필수품",
    contents: [
      {
        idx: 1,
        name: "공유기",
        value: false,
      },
      {
        idx: 2,
        name: "멀티탭 3구, 6구",
        value: false,
      },
      {
        idx: 3,
        name: "헤어 드라이어",
        value: false,
      },
      {
        idx: 4,
        name: "전자레인지",
        value: false,
      },
      {
        idx: 5,
        name: "청소기",
        value: false,
      },
      {
        idx: 6,
        name: "냉장고",
        value: false,
      },
      {
        idx: 7,
        name: "에어컨",
        value: false,
      },
      {
        idx: 8,
        name: "세탁기",
        value: false,
      },
    ],
    canSee: false,
  },
  {
    idx: 2,
    title: "이사 후 사도 되는 물품",
    contents: [
      {
        idx: 1,
        name: "인덕션/멀티쿠커/가스레인지",
        value: false,
      },
      {
        idx: 2,
        name: "스탠드 조명",
        value: false,
      },
      {
        idx: 3,
        name: "공기청정기",
        value: false,
      },
      {
        idx: 4,
        name: "미니다리미",
        value: false,
      },
      {
        idx: 5,
        name: "선풍기(써큘레이터)",
        value: false,
      },
      {
        idx: 6,
        name: "전기장판",
        value: false,
      },
    ],
    canSee: false,
  },
];

const controllSubAccordion = (data: { array: CheckList[]; idx: number }) => {
  const controlledCheckList = data.array[data.idx];
  controlledCheckList.canSee = !controlledCheckList.canSee;
  alert(controlledCheckList.title + " " + controlledCheckList.canSee);
};
</script>

<style lang="scss" scoped>
// 공통

// scoped
.tab-content {
  display: flex;
  flex-direction: column;
  gap: $padding-small;
}
</style>
