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
        <BuildingInfo :building="selectedBuilding" v-if="selectedBuilding" class="building-info"/>
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
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
/* Global declaration for kakao */
declare global {
  interface Window {
    kakao: {
      maps: {
        load: (callback: () => void) => void;
        Map: new (container: HTMLElement, options: any) => {
          getBounds: () => {
            extend: (latLng: kakao.maps.LatLng) => void;
            contain: (latLng: kakao.maps.LatLng) => boolean;
          };
        };
        LatLng: new (latitude: number, longitude: number) => any;
        Marker: new (options: { position: kakao.maps.LatLng }) => any;
        MarkerClusterer: new (options: { map: kakao.maps.Map; averageCenter: boolean; minLevel: number }) => any & {
          clear: () => void;
          addMarkers: (markers: any[]) => void;
        };
        event: {
          addListener: (marker: any, event: string, callback: (e: any) => void) => void;
        };
      };
    };
  }
}

import { ref, onMounted } from "vue";
import TopBar from "@/components/common/TopBar.vue";
import SearchHouseBar from "@/components/home/SearchHouseBar.vue";
import BottomBar from "@/components/common/BottomBar.vue";
import TagButtonGroupHome from "@/components/common/TagButtonGroupHome.vue";
import BuildingInfo from "@/components/home/BuildingInfo.vue";
import BuildingList from "@/components/home/BuildingList.vue";

const mapContainer = ref<HTMLElement | null>(null);
const showOverlay = ref(false);
const searchQuery = ref("");
let mapInstance: kakao.maps.Map; // Kakao Map의 타입으로 변경
let clusterer: kakao.maps.MarkerClusterer; // Kakao Clusterer의 타입으로 변경
let isMarkersInitialized = false; // 마커가 이미 초기화되었는지 확인하는 변수
const selectedBuilding = ref(null); // 선택된 건물 정보를 저장


/* 더미 데이터 */
const roomData = [
  { idx: 1, name: "원룸 A", type: "원룸", address: "서울특별시 중구 세종대로 110", latitude: 37.5665, longitude: 126.9780, radarData: { traffic: 80, commercial: 60, education: 40, medical: 70, safety: 50 }},
  { idx: 2, name: "원룸 B", type: "원룸", address: "서울특별시 중구 세종대로 100", latitude: 37.5666, longitude: 126.9781, radarData: { traffic: 75, commercial: 50, education: 55, medical: 60, safety: 65 }},
  { idx: 3, name: "원룸 C", type: "원룸", address: "서울특별시 중구 세종대로 90", latitude: 37.5667, longitude: 126.9782, radarData: { traffic: 90, commercial: 70, education: 65, medical: 80, safety: 85 }}
];

const officetelData = [
  { idx: 1, name: "오피스텔 A", type: "오피스텔", address: "서울특별시 중구 소공로 110", latitude: 37.5670, longitude: 126.9790, radarData: { traffic: 85, commercial: 65, education: 70, medical: 75, safety: 60 }},
  { idx: 2, name: "오피스텔 B", type: "오피스텔", address: "서울특별시 중구 소공로 100", latitude: 37.5671, longitude: 126.9791, radarData: { traffic: 60, commercial: 55, education: 50, medical: 65, safety: 55 }},
  { idx: 3, name: "오피스텔 C", type: "오피스텔", address: "서울특별시 중구 소공로 90", latitude: 37.5672, longitude: 126.9792, radarData: { traffic: 70, commercial: 75, education: 45, medical: 50, safety: 70 }},
  { idx: 4, name: "오피스텔 D", type: "오피스텔", address: "서울특별시 중구 소공로 80", latitude: 37.5673, longitude: 126.9793, radarData: { traffic: 95, commercial: 85, education: 60, medical: 80, safety: 90 }},
  { idx: 5, name: "오피스텔 E", type: "오피스텔", address: "서울특별시 중구 소공로 70", latitude: 37.5674, longitude: 126.9794, radarData: { traffic: 100, commercial: 90, education: 75, medical: 85, safety: 95 }}
];

const apartmentData = [
  { idx: 1, name: "아파트 A", type: "아파트", address: "서울특별시 종로구 종로 10", latitude: 37.5700, longitude: 126.9760, radarData: { traffic: 80, commercial: 70, education: 90, medical: 85, safety: 75 }},
  { idx: 2, name: "아파트 B", type: "아파트", address: "서울특별시 종로구 종로 20", latitude: 37.5710, longitude: 126.9770, radarData: { traffic: 65, commercial: 80, education: 60, medical: 70, safety: 85 }}
];

const hospitalData = [
  { idx: 1, name: "병원 A", type: "병원", address: "서울특별시 종로구 종로 30", latitude: 37.5720, longitude: 126.9780, radarData: { traffic: 75, commercial: 85, education: 55, medical: 90, safety: 60 }},
  { idx: 2, name: "병원 B", type: "병원", address: "서울특별시 종로구 종로 40", latitude: 37.5730, longitude: 126.9790, radarData: { traffic: 80, commercial: 90, education: 65, medical: 95, safety: 85 }}
];


onMounted(() => {
  loadKakaoMap(mapContainer.value);
});

const loadKakaoMap = (container: HTMLElement | null): void => {
  const script = document.createElement('script');
  script.src = 'https://dapi.kakao.com/v2/maps/sdk.js?appkey=6fffd0278e1410b6884d13552414ecf2&autoload=false&libraries=clusterer';
  document.head.appendChild(script);

  script.onload = () => {
    window.kakao.maps.load(() => {
      const options = {
        center: new window.kakao.maps.LatLng(37.566535, 126.9779692),
        level: 4,
        maxLevel: 10,
      };
      mapInstance = new window.kakao.maps.Map(container!, options); // Non-null assertion

      clusterer = new window.kakao.maps.MarkerClusterer({
        map: mapInstance,
        averageCenter: true,
        minLevel: 3,
      });
    });
  };
};

const clearMarkers = (): void => {
  clusterer.clear(); // 클러스터러에서 마커 제거
};

const addMarkers = (data: Array<any>): void => { // Specify the type here
  clearMarkers();

  const markers = data.map((item: any) => { // Specify the type here
    const markerPosition = new window.kakao.maps.LatLng(item.latitude, item.longitude);
    const marker = new window.kakao.maps.Marker({
      position: markerPosition,
    });

    window.kakao.maps.event.addListener(marker, 'click', () => {
      if (item.radarData) {
        selectedBuilding.value = {
          ...item,
          radarData: item.radarData,
        };
      } else {
        console.error("Radar data is missing for this building:", item.name);
      }
    });

    return marker;
  });

  clusterer.addMarkers(markers);
};


const filterDataByBounds = (data: Array<any>): Array<any> => { // Specify the type here
  // @ts-ignore: Ignoring the error for getBounds method
  const bounds = mapInstance.getBounds(); 
  const filteredData = data.filter((item: any) => { // Specify the type here
    const position = new window.kakao.maps.LatLng(item.latitude, item.longitude);
    return bounds.contain(position);
  });
  return filteredData;
};

const showInitialMarkers = (data: Array<any>): void => { // Specify the type here
  if (!isMarkersInitialized) {
    const visibleData = filterDataByBounds(data);
    addMarkers(visibleData);
    isMarkersInitialized = true; 
  }
}

// const oneroomlist = [{lat, lonfg...}]

const onButtonClicked = (index: number): void => { // Specify the type here
  isMarkersInitialized = false; // 버튼 클릭 시 마커 초기화 상태를 리셋

  if (index === 0) { // '원룸' 버튼 클릭 시
    showInitialMarkers(roomData); // 한 번만 마커를 표시
  } else if (index === 1) { // '오피스텔' 버튼 클릭 시
    showInitialMarkers(officetelData); // 한 번만 마커를 표시
  } else if (index === 2) { // '아파트' 버튼 클릭 시
    addMarkers(apartmentData);
  } else if (index === 3) { // '병원' 버튼 클릭 시
    addMarkers(hospitalData);
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
.building-info {
  position: absolute;
  bottom: -660px;
  z-index: 2;
}
.bottom-bar {
  z-index: 3;
  height: 60px; /* Adjust according to the actual height */
  position: relative;
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
  position: absolute;
  top: 60px; /* Position below input-icon-wrap */
  left: 0;
  width: 100%;
  height: 100%;
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
.searchbar input, .icon-search {
  pointer-events: auto; /* 검색 입력 필드 및 아이콘은 상호작용 가능하게 설정 */
}
</style>
