<template>
  <div class="sub-container non-input-sub-container">
    <div style="width: 100%">
      <TopBarBack title="점검하기" />
    </div>
    <div class="center-container accordion-container">
      <BasicAccordion accordionTitle="수도와 배수">
        <CheckListItem
          v-for="(item, index) in checkListAboutWater"
          :key="index"
          :checkListItem="item"
          @update:checkItem="updateCheckItem(checkListAboutWater, index, $event)"
        />
      </BasicAccordion>
      <BasicAccordion accordionTitle="창문">
        <CheckListItem
          v-for="(item, index) in checkListAboutWindow"
          :key="index"
          :checkListItem="item"
          @update:checkItem="updateCheckItem(checkListAboutWindow, index, $event)"
        />
      </BasicAccordion>
      <BasicAccordion accordionTitle="화장실">
        <CheckListItem
          v-for="(item, index) in checkListAboutBathroom"
          :key="index"
          :checkListItem="item"
          @update:checkItem="updateCheckItem(checkListAboutBathroom, index, $event)"
        />
      </BasicAccordion>
      <BasicAccordion accordionTitle="주변환경">
        <CheckListItem
          v-for="(item, index) in checkListAboutSurroundings"
          :key="index"
          :checkListItem="item"
          @update:checkItem="updateCheckItem(checkListAboutSurroundings, index, $event)"
        />
      </BasicAccordion>
      <BasicAccordion accordionTitle="기본옵션">
        <CheckListItem
          v-for="(item, index) in checkListAboutOption"
          :key="index"
          :checkListItem="item"
          @update:checkItem="updateCheckItem(checkListAboutOption, index, $event)"
        />
      </BasicAccordion>
      <BasicAccordion accordionTitle="디테일">
        <CheckListItem
          v-for="(item, index) in checkListAboutDetail"
          :key="index"
          :checkListItem="item"
          @update:checkItem="updateCheckItem(checkListAboutDetail, index, $event)"
        />
      </BasicAccordion>
      <BasicAccordion accordionTitle="보안">
        <CheckListItem
          v-for="(item, index) in checkListAboutSecurity"
          :key="index"
          :checkListItem="item"
          @update:checkItem="updateCheckItem(checkListAboutSecurity, index, $event)"
        />
      </BasicAccordion>
      <BasicAccordion accordionTitle="기타사항">
        <CheckListItem
          v-for="(item, index) in checkListAboutEtc"
          :key="index"
          :checkListItem="item"
          @update:checkItem="updateCheckItem(checkListAboutEtc, index, $event)"
        />
      </BasicAccordion>
    </div>
    <div class="bottom-button-wrapper">
      <div @click="submitChecklist">
        <p>점검완료</p>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, watchEffect } from "vue";
import { Check } from "@/data/check";
import { useRoute } from "vue-router";
import { axiosInstance } from "@/plugins/axiosPlugin";

import BasicAccordion from "@/components/common/BasicAccordion.vue";
import CheckListItem from "@/components/common/CheckListItem.vue";
import TopBarBack from "../common/TopBarBack.vue";

const route = useRoute();
const bookmarkIdx = ref<number | null>(null);

watchEffect(() => {
  if (route.params.bookmarkIdx) {
    bookmarkIdx.value = Number(route.params.bookmarkIdx);
    console.log("bookmarkIdx from route:", bookmarkIdx.value);
  }
});

// ✅ 체크리스트 데이터
const checkListAboutWater = ref<Check[]>([
  { idx: 1, name: "싱크대/세면대/샤워기 물이 잘 나오는가?", value: false },
  { idx: 2, name: "변기 물은 잘 내려가는가?", value: false },
  { idx: 3, name: "싱크대/화장실 배수구는 잘 내려가는가?", value: false },
  { idx: 4, name: "싱크대/화장실 온수는 잘 나오는가?", value: false },
]);


const checkListAboutWindow = ref<Check[]>([
  { idx: 1, name: "햇빛은 잘 들어오는가?", value: false },
  { idx: 2, name: "방충망/방범창은 이상이 없는가?", value: false },
  { idx: 3, name: "환기하기에 적합한 크기인가?", value: false },
  { idx: 4, name: "옆 건물에서 잘 보이지 않는 구조인가?", value: false },
]);

const checkListAboutBathroom = ref<Check[]>([
  { idx: 1, name: "화장실 내부에 창문이 있는가?", value: false },
  { idx: 2, name: "배수구 냄새가 올라오지 않는가?", value: false },
  { idx: 3, name: "샤워 여유 공간이 충분한가?", value: false },
  { idx: 4, name: "곰팡이 흔적이 없는가?", value: false },
]);

const checkListAboutSurroundings = ref<Check[]>([
  { idx: 1, name: "대중교통 이용이 편리한가?", value: false },
  { idx: 2, name: "주변에 소음 시설이 있는가?", value: false },
  { idx: 3, name: "편의점, 은행 등 편의시설이 있는가?", value: false },
  { idx: 4, name: "무인 택배 보관함이 있는가?", value: false },
  { idx: 5, name: "집 가는 길에 언덕이 없는가?", value: false },
  { idx: 6, name: "골목이라면 가로등이 있는가?", value: false },
]);

const checkListAboutOption = ref<Check[]>([
  { idx: 1, name: "옵션 가구 종류를 확인했는가?", value: false },
  { idx: 2, name: "에어컨/냉장고는 작동하는가?", value: false },
  { idx: 3, name: "화구는 잘 작동하는가?", value: false },
  { idx: 4, name: "필요없는 옵션 가구를 임의로 치울 수 있는가?", value: false },
  { idx: 5, name: "수납 공간은 충분한가?", value: false },
]);

const checkListAboutDetail = ref<Check[]>([
  { idx: 1, name: "벽지에 곰팡이 흔적이 없는가?", value: false },
  { idx: 2, name: "바퀴벌레 약이 없는가?", value: false },
  { idx: 3, name: "콘센트 개수는 충분한가?", value: false },
  { idx: 4, name: "인터폰 영상이 지원되는가?", value: false },
  { idx: 5, name: "옆집 방음이 잘 되는가?", value: false },
]);

const checkListAboutSecurity = ref<Check[]>([
  { idx: 1, name: "공동 현관 비밀번호가 있는가?", value: false },
  { idx: 2, name: "현관문 잠금장치가 있는가?", value: false },
  { idx: 3, name: "출입구와 복도에 CCTV가 있는가?", value: false },
  { idx: 4, name: "관리자분이 상주하는가?", value: false },
]);

const checkListAboutEtc = ref<Check[]>([
  { idx: 1, name: "관리비 포함 항목을 확인했는가?", value: false },
  { idx: 2, name: "건물에 집주인분이 사는가?", value: false },
  { idx: 3, name: "셀프 인테리어 가능한가?", value: false },
  { idx: 4, name: "분리 수거 시스템을 확인했는가?", value: false },
]);

const checkLists = ref([
  { category: "수도와 배수", list: checkListAboutWater },
  { category: "창문", list: checkListAboutWindow },
  { category: "화장실", list: checkListAboutBathroom },
  { category: "주변환경", list: checkListAboutSurroundings },
  { category: "기본옵션", list: checkListAboutOption },
  { category: "디테일", list: checkListAboutDetail },
  { category: "보안", list: checkListAboutSecurity },
  { category: "기타사항", list: checkListAboutEtc },
]);


// ✅ 체크리스트 업데이트 함수
const updateCheckItem = (array: Check[], index: number, updatedItem: Check) => {
  array.splice(index, 1, updatedItem);
  console.log(array[index]);
};

// ✅ 체크리스트 데이터 모으기
const getFormattedChecklist = () => {
  return {
    water: checkListAboutWater.value,
    window: checkListAboutWindow.value,
    bathroom: checkListAboutBathroom.value,
    surroundings: checkListAboutSurroundings.value,
    option: checkListAboutOption.value,
    detail: checkListAboutDetail.value,
    security: checkListAboutSecurity.value,
    etc: checkListAboutEtc.value,
  };
};

// ✅ API 요청 함수
const submitChecklist = async () => {
  console.log("bookmarkIdx:", bookmarkIdx.value);

  if (!bookmarkIdx.value || isNaN(bookmarkIdx.value)) {
    console.error("Invalid bookmarkIdx:", bookmarkIdx.value);
    return;
  }

  const requestData = getFormattedChecklist();
  console.log("PUT 요청 데이터:", JSON.stringify(requestData, null, 2));

  try {
    const response = await axiosInstance.put(
      `/v1/bookmark/${bookmarkIdx.value}`,
      requestData
    );
    console.log("Successfully updated:", response.data);
    alert("점검 리스트가 성공적으로 저장되었습니다!");
  } catch (error) {
    console.error("Error updating checklist:", error);
    alert("점검 리스트 저장 중 오류가 발생했습니다.");
  }
};
</script>

<style lang="scss" scoped>
// 공통
.top-bar-back {
  @include custom-bar-style(
    $height: $height-top-bar,
    $z-index: $z-index-top-bar
  );

  background-color: rgba(0, 0, 0, 0.1); /* 검은색 배경, 50% 투명도 */
}

.center-container {
  position: relative;
  flex-grow: 1;
  width: 100%;

  display: flex;
  flex-direction: column;

  overflow-y: auto;
  /* 스크롤바 전체 영역 */
  &::-webkit-scrollbar {
    width: 4px; /* 세로축 스크롤바 폭 너비 */
    height: 100%; /* 가로축 스크롤바 폭 너비 */
  }
  &::-webkit-scrollbar-button {
    display: none;
  }
  /* 스크롤바 막대 제외 부분 */
  &::-webkit-scrollbar-track {
    background: transparent;
  }
  /* 스크롤바 막대 */
  &::-webkit-scrollbar-thumb {
    border-radius: calc($border-radius-default * 2);
    background: #f2f2f2;
  }
}
.bottom-button-wrapper {
  @include custom-padding-x;

  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;

  div {
    @include custom-button-style($height: 54px, $font-color: white);
  }
}

.accordion-container {
  justify-content: flex-start;
  gap: $padding-default;
  padding-top: calc(#{$padding-default}* 2);
  padding-bottom: calc(54px + #{$margin-default} + #{$padding-default});
}
// scoped
</style>
