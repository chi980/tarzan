<template>
  <div class="sub-container">
    <TopBar></TopBar>
    <form class="search-box" action="" method="get">
      <input class="search-txt" type="text" name="" placeholder="찾고 싶은 주소를 입력하세요.">
    </form>
    <div ref="mapContainer" style="width: 100%; height: 83%"></div>
    <p>{{ message }}</p>
    <BottomBar></BottomBar>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue";
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

const mapContainer = ref(null)

onMounted(() => {
  loadKakaoMap(mapContainer.value)
})

const loadKakaoMap = (container) => {
  const script = document.createElement('script')
  script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=6fffd0278e1410b6884d13552414ecf2&autoload=false`
  document.head.appendChild(script)

  script.onload = () => {
    window.kakao.maps.load(() => {
      const options = {
        center: new window.kakao.maps.LatLng(37.566535, 126.9779692), // 지도 중심 좌표
        level: 3, // 지도 확대 레벨
        maxLevel: 10, // 지도 축소 제한 레벨
      }

      const mapInstance = new window.kakao.maps.Map(container, options) // 지도 생성
    })
  }
}

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
