<template>
  <div class="sub-container">
    <TopBar></TopBar>
    <div>
    <KakaoMap :lat="33.450701" :lng="126.570667" @onLoadKakaoMap="onLoadKakaoMap" />
    </div>
    <div>
      <button class="demo-button" @click="zoomIn">지도레벨 - 1</button>
      <button class="demo-button" @click="zoomOut">지도레벨 + 1</button>
    </div>
    <p>{{ message }}</p>
    <BottomBar></BottomBar>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import TopBar from "@/components/common/TopBar.vue";
import BottomBar from "@/components/common/BottomBar.vue";
import TabBar from "@/components/common/TabBar.vue";
import { Tab } from "@/data/tabs";
import HouseTap from "@/components/bookmark/HouseTap.vue";
import MoverTap from "@/components/bookmark/MoverTap.vue";
import ItemTap from "@/components/bookmark/ItemTap.vue";
import DescriptionComponent from "@/components/common/Description.vue";
import CompareImgSrc from "@/assets/icons/Filter/Style=Outlined.svg";

///////////////////////////////////////////////////

import { KakaoMap } from 'vue3-kakao-maps';

const map = ref<kakao.maps.Map>();
const message = ref<string>('');

const onLoadKakaoMap = (mapRef: kakao.maps.Map) => {
  map.value = mapRef;
  displayLevel();
};

const zoomIn = () => {
  // 현재 지도의 레벨을 얻어옵니다
  if (map.value) {
    const level = map.value.getLevel();

    // 지도를 1레벨 내립니다 (지도가 확대됩니다)
    map.value.setLevel(level - 1);
  }

  // 지도 레벨을 표시합니다
  displayLevel();
};

const zoomOut = () => {
  // 현재 지도의 레벨을 얻어옵니다
  if (map.value) {
    const level = map.value.getLevel();

    // 지도를 1레벨 올립니다 (지도가 축소됩니다)
    map.value.setLevel(level + 1);
  }

  // 지도 레벨을 표시합니다
  displayLevel();
};

const displayLevel = () => {
  message.value = `현재 지도 레벨은 ${map.value?.getLevel()} 레벨 입니다.`;
};

////////////////////////////////////////////////////





</script>

<style lang="scss" scoped>
.sub-container {
  justify-content: space-between;
}

.center-container {
  position: relative;
  flex-grow: 1;
  width: 100%;

  display: flex;
  flex-direction: column;
}

.center-container-fix-button {
  @include custom-text($font-color: white, $font-weight: 800, $font-size: 14px);
  @include custom-none-select-basic;
  position: absolute;
  right: $padding-default;
  bottom: $padding-default;
  z-index: $z-index-button;

  display: flex;
  justify-content: center; /* 가로축 중앙 정렬 */
  align-items: center; /* 세로축 중앙 정렬 */
  padding: 12px 14px 12px 12px;
  gap: $padding-small;

  border-radius: 20px;
  background-color: $primary-color-400;

  box-shadow: 0px 0px 10px rgba(166, 166, 166, 0.3);
}

.center-container-fix-button > img {
  @include custom-icon-style;
}
</style>
