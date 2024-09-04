<template>
  <div class="sub-container">
    <TopBar class="topbar"></TopBar>
    <SearchHouseBar class="search-house-bar" v-if="showOverlay"></SearchHouseBar>
    <div class="center-container">
      <div ref="mapContainer" class="map-container">
        <div class="searchbar" @click="showOverlay = true">
          <div class="input-icon-wrap">
            <font-awesome-icon :icon="['fas', 'magnifying-glass']" class="icon-search"/>
            <input
              v-model="searchQuery" 
              type="text"
              placeholder="찾고 싶은 집주소를 입력해주세요."/>
          </div>
        </div>
        <div class="tag-button-container">
          <TagButtonGroupHome @button-clicked="onButtonClicked" />
        </div>
        <BuildingInfo class="building-info"/>
      </div>
    </div>
    <BottomBar class="bottom-bar"></BottomBar>

    <div v-if="showOverlay" class="overlay">
      <div class="searchbar" @click="showOverlay = true">
        <div class="input-icon-wrap">
          <font-awesome-icon :icon="['fas', 'magnifying-glass']" class="icon-search"/>
          <input
            v-model="searchQuery" 
            type="text"
            placeholder="찾고 싶은 집주소를 입력해주세요."/>
        </div>
      </div>
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
import SearchHouseBar from "@/components/home/SearchHouseBar.vue";
import BottomBar from "@/components/common/BottomBar.vue";
import TagButtonGroupHome from "@/components/common/TagButtonGroupHome.vue";
import BuildingInfo from "@/components/home/BuildingInfo.vue";
import BuildingList from "@/components/home/BuildingList.vue";

const mapContainer = ref(null);
const showOverlay = ref(false);
const searchQuery = ref("");
let mapInstance;
let markers = []; // 지도에 표시된 마커를 관리할 배열
let isMarkersInitialized = false; // 마커가 이미 초기화되었는지 확인하는 변수


/* 더미 데이터 */
const roomData = [
  { id: 1, title: "원룸 A", category: "원룸", content: "서울특별시 중구 세종대로 110", latitude: 37.5665, longitude: 126.9780 },
  { id: 2, title: "원룸 B", category: "원룸", content: "서울특별시 중구 세종대로 100", latitude: 37.5666, longitude: 126.9781 },
  { id: 3, title: "원룸 C", category: "원룸", content: "서울특별시 중구 세종대로 90", latitude: 37.5667, longitude: 126.9782 }
];

const officetelData = [
  { id: 1, title: "오피스텔 A", category: "오피스텔", content: "서울특별시 중구 소공로 110", latitude: 37.5670, longitude: 126.9790 },
  { id: 2, title: "오피스텔 B", category: "오피스텔", content: "서울특별시 중구 소공로 100", latitude: 37.5671, longitude: 126.9791 },
  { id: 3, title: "오피스텔 C", category: "오피스텔", content: "서울특별시 중구 소공로 90", latitude: 37.5672, longitude: 126.9792 },
  { id: 4, title: "오피스텔 D", category: "오피스텔", content: "서울특별시 중구 소공로 80", latitude: 37.5673, longitude: 126.9793 },
  { id: 5, title: "오피스텔 E", category: "오피스텔", content: "서울특별시 중구 소공로 70", latitude: 37.5674, longitude: 126.9794 }
];

onMounted(() => {
  loadKakaoMap(mapContainer.value);
});

const loadKakaoMap = (container) => {
  const script = document.createElement('script');
  script.src = 'https://dapi.kakao.com/v2/maps/sdk.js?appkey=6fffd0278e1410b6884d13552414ecf2&autoload=false';
  document.head.appendChild(script);
  
  script.onload = () => {
    window.kakao.maps.load(() => {
      const options = {
        center: new window.kakao.maps.LatLng(37.566535, 126.9779692),
        level: 3,
        maxLevel: 10,
      };
      mapInstance = new window.kakao.maps.Map(container, options);
    });
  };
};

const clearMarkers = () => {
  markers.forEach(marker => marker.setMap(null)); // 기존 마커를 지도에서 제거
  markers = []; // 배열을 초기화하여 마커를 관리
};

const addMarkers = (data) => {
  clearMarkers(); // 새로운 마커를 추가하기 전에 기존 마커를 제거

  data.forEach(item => {
    const markerPosition = new window.kakao.maps.LatLng(item.latitude, item.longitude);
    const marker = new window.kakao.maps.Marker({
      position: markerPosition,
    });
    marker.setMap(mapInstance); // 마커를 지도에 추가
    markers.push(marker); // 마커를 배열에 저장하여 관리
  });
};

const filterDataByBounds = (data) => {
  const bounds = mapInstance.getBounds(); // 현재 지도 범위를 가져옴
  const filteredData = data.filter(item => {
    const position = new window.kakao.maps.LatLng(item.latitude, item.longitude);
    return bounds.contain(position); // 현재 범위 내에 있는 데이터를 필터링
  });
  return filteredData;
};

const showInitialMarkers = (data) => {
  if (!isMarkersInitialized) {
    const visibleData = filterDataByBounds(data);
    addMarkers(visibleData);
    isMarkersInitialized = true; // 마커가 초기화되었음을 표시
  }
};

const onButtonClicked = (index) => {
  isMarkersInitialized = false; // 버튼 클릭 시 마커 초기화 상태를 리셋

  if (index === 0) { // '원룸' 버튼 클릭 시
    showInitialMarkers(roomData); // 한 번만 마커를 표시
  } else if (index === 1) { // '오피스텔' 버튼 클릭 시
    showInitialMarkers(officetelData); // 한 번만 마커를 표시
  }
};
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
.building-info{
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
  height: 22%;
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
  height: calc(100% - 108px); /* Full height minus input-icon-wrap height and BottomBar height */
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

/* 필요 시 특정 요소만 상호작용 가능하게 설정 */
.searchbar input, .searchbar .icon-search {
  pointer-events: auto; /* 검색 입력 필드 및 아이콘은 상호작용 가능하게 설정 */
}
</style>