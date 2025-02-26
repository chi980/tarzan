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
          <font-awesome-icon :icon="['fas', 'magnifying-glass']" class="icon-search"/>
          <input v-model="searchQuery" type="text" @keyup.enter="fetchHouses" placeholder="찾고 싶은 집주소를 입력해주세요." />
        </div>
      </div>
      <div class="overlay-content">
        <div class="overlay-body">
          <BuildingList :buildings="buildings" />
          <button v-if="!isLastPage" @click="loadMore">더보기</button>
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
const loading = ref(false);
const selectedType = ref('CIVIC_CENTER'); // 기본값 설정

const showOverlay = ref(false);
const searchQuery = ref(""); // 검색어 상태
const page = ref(0); // 페이지 번호
const size = ref(10); // 한 페이지에 보여줄 개수
// const buildings = ref([]); // 검색 결과 데이터
const totalCount = ref(0); // 총 검색 결과 수


// 빌딩 데이터 요청
async function fetchBuildings(type: string, latitude: number, longitude: number, radius: number) {
  if (loading.value) return; // 이미 요청 중이라면 무시
  if (!type) {
    console.warn("Type is not selected."); // 타입 누락 경고
    return;
  }

  loading.value = true; // 로딩 상태 활성화

  const requestData = { type, latitude, longitude, radius };
  console.log("Sending request with data:", requestData);

  try {
    // API 경로 설정
    const endpoint = type === "HOUSE" ? "/v1/houses" : "/v1/building";

    // API 요청 +타임아웃 설정 추가
    const response = await axiosInstance.get(endpoint, {
      params: requestData,
      timeout: 5000, // 5초로 타임아웃 설정
    });
    
    console.log("Response received from backend:", response.data);

    // 응답 데이터 유효성 검사 및 처리
    const responseData = response.data;
    if (responseData?.success && responseData.message === "완료되었습니다.") {
      buildings.value = responseData.data || [];
      console.log("Buildings fetched successfully:", buildings.value);

      // 마커 표시
      addMarkers(buildings.value);
    } else {
      console.error("Backend returned an error:", responseData?.message || "Unknown error");
      buildings.value = [];
      alert(`Error: ${responseData?.message || "데이터를 가져오는 중 문제가 발생했습니다."}`);
    }
  } catch (error: any) {
    // 요청 실패 처리
    console.error("Request failed:", error.message);

    // 에러 응답 정보 확인
    if (error.response) {
      console.error("Response data:", error.response.data);
      console.error("Response status:", error.response.status);

      const status = error.response.status;
      if (status === 500) {
        alert("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
      } else if (status === 400) {
        alert("잘못된 요청입니다. 입력값을 확인해주세요.");
      } else {
        alert(`요청 실패: ${status} - ${error.response.statusText}`);
      }
    } else if (error.code === "ECONNABORTED") {
      alert("요청 시간이 초과되었습니다. 네트워크 상태를 확인하세요.");
    } else {
      alert("요청을 처리하는 중 문제가 발생했습니다. 다시 시도해주세요.");
    }

    buildings.value = [];
  } finally {
    // 로딩 상태 해제
    loading.value = false;
  }
}



function onButtonClicked(type) {
  if (loading.value) return;
  selectedType.value = type; 

  const latitude = 37.566535; 
  const longitude = 126.9779692;
  const radius = 50; // 단위: 미터

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
      mapInstance = new window.kakao.maps.Map(container, { center: new window.kakao.maps.LatLng(37.566535, 126.9779692), level: 5 });
      clusterer = new window.kakao.maps.MarkerClusterer({ map: mapInstance, averageCenter: true, minLevel: 3 });
      fetchBuildings(null, 37.566535, 126.9779692, 150); 
    });
  };
}

const clearMarkers = (): void => {
  clusterer.clear(); // 클러스터러에서 마커 제거
};

const addMarkers = (data: Array<any>): void => {
  // 기존 마커들을 클러스터에서 지우는 함수
  clearMarkers();

  // 마커를 추가하는 로직
  const markers = data.map((item: any) => {
    console.log("Adding marker for:", item);  // 데이터 확인

    // 위도, 경도를 기반으로 마커의 위치 생성
    const markerPosition = new window.kakao.maps.LatLng(item.longitude, item.latitude);
    const marker = new window.kakao.maps.Marker({
      position: markerPosition,
    });

    // 마커 클릭 시, 해당 건물 정보 설정
    window.kakao.maps.event.addListener(marker, 'click', () => {
      if (item.radarData) {
        // radarData가 있을 경우, 선택된 건물 정보 설정
        selectedBuilding.value = {
          ...item,
          radarData: item.radarData,
        };

        // 추가적인 UI 업데이트 필요 (예: BuildingInfo 컴포넌트에 표시)
        console.log("Selected building:", selectedBuilding.value);
      } else {
        // radarData가 없을 경우, 사용자에게 알림 (UI로 알리는 것이 좋음)
        console.error("Radar data is missing for this building:", item.name);
        alert(`No radar data available for ${item.name}`);
      }
    });

    return marker;
  });

  // 클러스터에 마커 추가
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
