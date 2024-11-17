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
          <TagButtonGroupHome 
            :selectedType="selectedType" 
            @button-clicked="onButtonClicked" 
          />
        </div>
        <BuildingInfo :building="selectedBuilding" v-if="selectedBuilding" class="building-info"/>
      </div>
      <!-- 백엔드에서 가져온 빌딩 데이터 출력 -->
      <div>
        <div v-for="building in buildings" :key="building.name">
          <p>{{ building.name }} - {{ building.address }}</p>
        </div>
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
import { axiosInstance } from "@/plugins/axiosPlugin";
import { ref, onMounted } from "vue";
import TopBar from "@/components/common/TopBar.vue";
import SearchHouseBar from "@/components/home/SearchHouseBar.vue";
import BottomBar from "@/components/common/BottomBar.vue";
import TagButtonGroupHome from "@/components/common/TagButtonGroupHome.vue";
import BuildingInfo from "@/components/home/BuildingInfo.vue";
import BuildingList from "@/components/home/BuildingList.vue";

const buildings = ref([]);  
const selectedBuilding = ref(null); 
const showOverlay = ref(false);
const searchQuery = ref("");
const loading = ref(false);
const selectedType = ref('CIVIC_CENTER'); // 기본값 설정

// 빌딩 데이터 요청
async function fetchBuildings(type: string, latitude: number, longitude: number, radius: number) {
  if (loading.value) return;

  // type이 선택되지 않은 경우 메시지를 출력하고 함수 종료
  if (!type) {
    console.error("type is not selected.");
    return;
  }

  loading.value = true;

  try {
    const requestData = {
      type,
      latitude,
      longitude,
      radius,
    };
    console.log("Sending request with data:", requestData);

    const response = await axiosInstance.get('/v1/building', { params: requestData });

    if (response.data.status === 200 && response.data.message === "OK") {
      buildings.value = response.data.data;
      showInitialMarkers(buildings.value); // 마커 초기화
    } else {
      console.error("Error:", response.data.message);
      buildings.value = [];
    }
  } catch (error) {
    console.error("Request failed:", error);
    buildings.value = [];
  } finally {
    loading.value = false;
  }
}

function onButtonClicked(type) {
  if (loading.value) return;
  selectedType.value = type; 

  const latitude = 37.566535; 
  const longitude = 126.9779692;
  const radius = 150;

  fetchBuildings(type, latitude, longitude, radius);
}

declare global {
  interface Window {
    kakao: {
      maps: {
        load: (callback: () => void) => void;
        Map: new (container: HTMLElement, options: any) => any;
        LatLng: new (latitude: number, longitude: number) => any;
        Marker: new (options: { position: any }) => any;
        MarkerClusterer: new (options: { map: any; averageCenter: boolean; minLevel: number }) => any;
        event: {
          addListener: (marker: any, event: string, callback: (e: any) => void) => void;
        };
      };
    };
  }
}

const mapContainer = ref<HTMLElement | null>(null);
let mapInstance: kakao.maps.Map; // Kakao Map의 타입으로 변경
let clusterer: kakao.maps.MarkerClusterer; // Kakao Clusterer의 타입으로 변경
let isMarkersInitialized = false; // 마커가 이미 초기화되었는지 확인하는 변수


onMounted(() => {
  loadKakaoMap(mapContainer.value);
});

function loadKakaoMap(container) {
  if (!container) return;
  const script = document.createElement('script');
  script.src = 'https://dapi.kakao.com/v2/maps/sdk.js?appkey=6fffd0278e1410b6884d13552414ecf2&autoload=false&libraries=clusterer';
  document.head.appendChild(script);

  script.onload = () => {
    window.kakao.maps.load(() => {
      mapInstance = new window.kakao.maps.Map(container, { center: new window.kakao.maps.LatLng(37.566535, 126.9779692), level: 4 });
      clusterer = new window.kakao.maps.MarkerClusterer({ map: mapInstance, averageCenter: true, minLevel: 3 });
      fetchBuildings(null, 37.566535, 126.9779692, 150); 
    });
  };
}

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
