<template>
  <div class="sub-container non-input-sub-container">
    <div class="top-bar-wrapper">
      <TopBar></TopBar>
    </div>
    <div class="center-container">
      <div>
        <DescriptionComponent
          descriptionImgSrc="/etc/Saly-26.png"
          descriptionTitle="타잔이와 함께 체크해봐요!"
          descriptionContent="집/이사업체/자취필수품<br/>이사에 대한 모든 것을 체크할 수 있어요!"
          backgroundColor="#f2ecff" />
      </div>

      <TabBar :tabs="tabs" v-model:selectedTabIdx="selectedTabIndex"></TabBar>
      <div
        class="center-container-fix-button"
        @click="tabs[selectedTabIndex].onClick">
        <p>{{ tabs[selectedTabIndex].buttonLabel }}</p>
        <img :src="tabs[selectedTabIndex].imgSrc" />
      </div>
    </div>

    <div class="bottom-bar-wrapper">
      <BottomBar></BottomBar>
    </div>
  </div>
</template>

<script lang="ts" setup>
import TopBar from "@/components/common/TopBar.vue";
import BottomBar from "@/components/common/BottomBar.vue";
import TabBar from "@/components/common/TabBar.vue";
import { Tab } from "@/data/tabs";
import HouseTap from "@/components/bookmark/HouseTap.vue";
import MoverTap from "@/components/bookmark/MoverTap.vue";
import ItemTap from "@/components/bookmark/ItemTap.vue";
import DescriptionComponent from "@/components/common/Description.vue";

import chevronImgSrc from "@/assets/icons/chevron-right.png";
import exportImgSrc from "@/assets/icons/corner_up_arrows.png";

import { ref, watch, computed } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const list = ref([]);

watch(
  () => route.query.list,
  (newHouses) => {
    console.log("북마크한 집 목록: ", newHouses);
    if (newHouses) {
      list.value = JSON.parse(newHouses as string);
    }
  },
  { immediate: true }
);
const selectedTabIndex = ref(0); // 선택된 탭 인덱스 추적

interface ExtendedTab extends Tab {
  buttonLabel: string;
  imgSrc: string;
  onClick: () => void;
}
// 탭 데이터 배열 초기화
const tabs: ExtendedTab[] = [
  {
    name: "집 선택",
    component: HouseTap,
    buttonLabel: "비교하기",
    imgSrc: chevronImgSrc,
    onClick: () => {
      console.log("🏠 집 비교 로직 실행");
    },
  },
  {
    name: "이사 업체 선택",
    component: MoverTap,
    buttonLabel: "내보내기",
    imgSrc: exportImgSrc,
    onClick: () => {
      console.log("🚚 이사 업체 비교 로직 실행");
    },
  },
  {
    name: "자취 필수품 선택",
    component: ItemTap,
    buttonLabel: "내보내기",
    imgSrc: exportImgSrc,
    onClick: () => {
      console.log("📦 필수품 비교 로직 실행");
    },
  },
];
</script>

<style lang="scss" scoped>
// 공통
.top-bar-wrapper {
  width: 100%;
}
.bottom-bar-wrapper {
  display: flex;
  justify-content: center; /* 가로 방향 중앙 정렬 */
  // height: 100px;
  width: 100%;
  z-index: $z-index-bottom-bar-wrapper;
  box-shadow: 0px -2px 4px rgba(0, 0, 0, 0.1);
}

.non-input-sub-container {
  @include custom-none-select-basic;
  justify-content: space-between;
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

.center-container-fix-button {
  @include custom-padding(12px);
  @include custom-text($font-size: 12px);
  position: sticky; // ✅ fixed로 바꾸기!
  bottom: $padding-default;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  flex-direction: row;
  gap: $padding-small;

  width: fit-content;

  border-radius: 30px;
  background: rgba(255, 255, 255, 0.7);
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);

  img {
    @include custom-icon-style(12px);
  }
}
</style>
