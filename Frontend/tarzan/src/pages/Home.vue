<template>
  <div class="sub-container">
    <TopBar></TopBar>
    <div class="center-container">
      <div class="searchbar">
        <div class="input-icon-wrap">
          <font-awesome-icon :icon="['fas', 'magnifying-glass']" class="icon-search"/>
          <input
            v-model="searchQuery" 
            type="text"
            placeholder="찾고 싶은 집주소를 입력해주세요." />
        </div>
      </div>
      </div>
    <div ref="mapContainer" style="width: 100%; height: 83%"></div>
    <BuildingInfo />
    <BottomBar></BottomBar>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue";
import TopBar from "@/components/common/TopBar.vue";
import BottomBar from "@/components/common/BottomBar.vue";
import DescriptionComponent from "@/components/common/Description.vue";
import BuildingInfo from "@/components/home/BuildingInfo.vue";


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

.center-container .searchbar {
  display: flex;
  padding-top: $margin-small;
  padding-bottom: $margin-default;
  @include custom-padding-x;
  box-sizing: border-box;
}

.center-container .searchbar .input-icon-wrap {
  display: flex;
  align-items: center;
  width: 100%;
  height: 48px;
  border-radius: 13px;
  background-color: $input-color;
  padding-right: $padding-default;
}

.center-container .searchbar .input-icon-wrap .icon-search {
  width: 16px;
  height: 16px;
  @include custom-margin-x;
  color: $input-placeholder-color;
}

.center-container .searchbar .input-icon-wrap input {
  width: 100%;
  appearance: none;
  border: none;
  outline: none;
  background: transparent;
  @include custom-text;

}

</style>
