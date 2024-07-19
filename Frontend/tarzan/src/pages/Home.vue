<template>
  <div class="sub-container">
    <TopBar></TopBar>
    <div class="center-container">
      <div class="searchbar" @click="showOverlay = true">
        <div class="input-icon-wrap">
          <font-awesome-icon :icon="['fas', 'magnifying-glass']" class="icon-search"/>
          <input
            v-model="searchQuery" 
            type="text"
            placeholder="찾고 싶은 집주소를 입력해주세요."/>
        </div>
      </div>
    </div>
    <div ref="mapContainer" class="map-container" style="width: 100%; height: 100%"></div>
    <BuildingInfo />
    <BottomBar></BottomBar>
    

    <div v-if="showOverlay" class="overlay">
      <div class="overlay-content">
        <div class="overlay-body">
          <BuildingList />
        </div>
      </div>
    </div>


  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue";
import TopBar from "@/components/common/TopBar.vue";
import BottomBar from "@/components/common/BottomBar.vue";
import SearchBar from "../components/common/SearchBar.vue";
import DescriptionComponent from "@/components/common/Description.vue";
import BuildingInfo from "@/components/home/BuildingInfo.vue";
import BuildingList from "@/components/home/BuildingList.vue";
import { KakaoMap } from 'vue3-kakao-maps';

const mapContainer = ref(null)
const showOverlay = ref(false);
const searchQuery = ref("");

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
</script>

<style lang="scss" scoped>
.sub-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  justify-content: space-between;
}

.center-container {
  position: relative;
  flex-grow: 1;
  width: 100%;
  display: flex;
  flex-direction: column;
}

.searchbar {
  display: flex;
  padding-top: $margin-small;
  padding-bottom: $margin-default;
  @include custom-padding-x;
  box-sizing: border-box;
  cursor: pointer;
  z-index: 2;
}

.input-icon-wrap {
  display: flex;
  align-items: center;
  width: 100%;
  height: 48px;
  border-radius: 13px;
  background-color: $input-color;
  padding-right: $padding-default;
}

.icon-search {
  width: 16px;
  height: 16px;
  @include custom-margin-x;
  color: $input-placeholder-color;
}

input {
  width: 100%;
  appearance: none;
  border: none;
  outline: none;
  background: transparent;
  @include custom-text;
}

.map-container {
  width: 100%;
  height: 83%;
  position: relative;
  z-index: 0;
}

.overlay {
  position: absolute;
  top: 110px;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: white;
  display: flex;
  align-items: flex-start;
  justify-content: center;
  z-index: 1;
}

.overlay-content {
  background: white;
  padding: 5px;
  border-radius: 5px;
  width: 100%;
}

</style>
