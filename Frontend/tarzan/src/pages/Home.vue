<template>
  <div class="sub-container">
    <TopBar class="topbar"></TopBar>
    <SearchHouseBar
      class="search-house-bar"
      v-if="showOverlay"
    ></SearchHouseBar>
    <div class="center-container">
      <div ref="mapContainer" class="map-container">
        <div class="searchbar" @click="showOverlay = true">
          <div class="input-icon-wrap">
            <font-awesome-icon
              :icon="['fas', 'magnifying-glass']"
              class="icon-search"
            />
            <input
              v-model="searchQuery"
              type="text"
              placeholder="찾고 싶은 집주소를 입력해주세요."
            />
          </div>
        </div>
        <div class="tag-button-container">
          <TagButtonGroupHome />
        </div>
        <BuildingInfo class="building-info" />
      </div>
    </div>
    <BottomBar class="bottom-bar"></BottomBar>

    <div v-if="showOverlay" class="overlay">
      <div class="searchbar" @click="showOverlay = true">
        <div class="input-icon-wrap">
          <font-awesome-icon
            :icon="['fas', 'magnifying-glass']"
            class="icon-search"
          />
          <input
            v-model="searchQuery"
            type="text"
            placeholder="찾고 싶은 집주소를 입력해주세요."
          />
        </div>
      </div>
      <div class="overlay-content">
        <div class="overlay-body">
          <BuildingList />
          <button @click="showOverlay = false">닫기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue";
import TopBar from "@/components/common/TopBar.vue";
import SearchHouseBar from "@/components/home/SearchHouseBar.vue";
import BottomBar from "@/components/common/BottomBar.vue";
import TagButtonGroupHome from "@/components/common/TagButtonGroupHome.vue";
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

const oneroomlist = [{lat, lonfg...}]
</script>

<style lang="scss" scoped>
.topbar {
  z-index: 3;
}
.search-house-bar {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  z-index: 3; /* Higher than TopBar and overlay */
}
.building-info {
  position: absolute;
  bottom: -650px;
  z-index: 2;
}
.bottombar {
  z-index: 3;
  height: 60px; /* Adjust according to the actual height */
  position: fixed;
  bottom: 0;
  width: 100%;
}
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
  overflow: visible;
}
.searchbar {
  display: flex;
  position: absolute;
  top: 10px;
  left: 50%;
  transform: translateX(-50%);
  width: 90%;
  padding: 0px;
  z-index: 4; /* Ensure input-icon-wrap is above overlay */
  box-sizing: border-box;
  cursor: pointer;
}
.input-icon-wrap {
  display: flex;
  align-items: center;
  width: 100%;
  height: 48px;
  border-radius: 13px;
  background-color: white;
  padding-right: $padding-default;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  position: relative; /* Ensures it's positioned correctly */
  z-index: 5; /* Higher than overlay */
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
  display: flex;
  width: 100%;
  height: 100%;
  position: relative;
  z-index: 1;
  pointer-events: auto;
}
.tag-button-container {
  position: relative;
  margin-top: 25px;
  z-index: 2;
  width: 100%;
  overflow-x: auto;
  /* 스크롤바 숨기기 */
  &::-webkit-scrollbar {
    display: none;
  }
}
:deep(.tag-button-container) {
  overflow-x: auto;
}
.overlay {
  position: absolute; /* Keeping position relative */
  top: 60px; /* Position below input-icon-wrap */
  left: 0;
  width: 100%;
  height: calc(
    100% - 108px
  ); /* Full height minus input-icon-wrap height and BottomBar height */
  background-color: white;
  display: flex;
  align-items: flex-start;
  z-index: 1;
}
.overlay-content {
  background: white;
  padding: 5px;
  border-radius: 5px;
  width: 100%;
  z-index: 1;
}

/* 지도 위에 위치한 다른 요소들에 대해 pointer-events 설정 */
.tag-button-container {
  pointer-events: none; /* 지도와 상호작용하지 않도록 설정 */
}

/* 필요 시 특정 요소만 상호작용 가능하게 설정 */
.searchbar input,
.searchbar .icon-search {
  pointer-events: auto; /* 검색 입력 필드 및 아이콘은 상호작용 가능하게 설정 */
}
</style>
