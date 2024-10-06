<template>
  <div class="sub-container non-input-sub-container">
    <TopBar></TopBar>
    <div class="center-container">
      <div>
        <DescriptionComponent
          descriptionImgSrc="/src/assets/etc/Saly-26.png"
          descriptionTitle="타잔이와 함께 체크해봐요!"
          descriptionContent="집/이사업체/자취필수품<br/>이사에 대한 모든 것을 체크할 수 있어요!"
          backgroundColor="#f2ecff"
        />
        <TabBar :tabs="tabs"></TabBar>
      </div>
    </div>
    <div class="center-container-fix-button">
      <img :src="CompareImgSrc" />비교하기
    </div>
    <BottomBar></BottomBar>
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
import CompareImgSrc from "@/assets/icons/Filter/Style=Outlined.svg";

// 탭 데이터 배열 초기화
const tabs: Tab[] = [
  { name: "집 선택", component: HouseTap },
  { name: "이사 업체 선택", component: MoverTap },
  { name: "자취 필수품 선택", component: ItemTap },
];
</script>

<style lang="scss" scoped>
// 공통
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
  @include custom-text($font-color: white, $font-weight: 800, $font-size: 14px);
  @include custom-none-select-basic;
  position: absolute;
  bottom: calc(#{$height-bottom-bar} + #{$padding-default});
  right: $padding-default;
  z-index: $z-index-button;

  display: flex;
  justify-content: center; /* 가로축 중앙 정렬 */
  align-items: center; /* 세로축 중앙 정렬 */
  padding: 12px 14px 12px 12px;
  gap: $padding-small;

  border-radius: 20px;
  background-color: $primary-color-400;

  box-shadow: 0px 0px 10px rgba(166, 166, 166, 0.3);

  img {
    @include custom-icon-style;
  }
}
</style>
