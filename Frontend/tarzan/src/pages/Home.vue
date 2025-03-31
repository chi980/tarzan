<template>
  <div class="sub-container">
    <TopBar class="topbar"></TopBar>
    <div class="center-container">
      <div class="top-overlay-wrapper">
        <div class="searchbar" @click="openAddressSearch">
          <div class="input-icon-wrap">
            <img :src="searchIconImg" alt="search icon" class="icon-search" />
            <p>찾고 싶은 주소를 입력해주세요.</p>
          </div>
        </div>
        <div class="tag-button-container-wrapper">
          <TagButtonGroup
            v-model:selectedButton="selectedButton"
            :buttons="tagOptions"
            :multiple="false" />
        </div>
      </div>
      <div class="bottom-overlay-wrapper">
        <div class="info-refresh-button" @click="showInfoOverlay">
          <img :src="refreshIconImg" alt="refresh icon" />
          <p>새로 불러오기</p>
        </div>
        <div
          class="info-content-wrapper"
          :class="{ animating: isAnimating }"
          :style="{ height: contentHeight + 'px' }">
          <div class="info-content-indicator" @mousedown="startDrag"></div>
          <div class="info-content">
            <p>바보야</p>
          </div>
        </div>
      </div>
      <div ref="mapContainer" class="map-container"></div>
    </div>

    <div style="background-color: white">
      <BottomBar class="bottom-bar"></BottomBar>
    </div>
    <!-- 주소 검색 팝업 -->
    <AddressSearch v-if="isAddressSearchOpen" @close="closeAddressSearch" />
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, onUnmounted, watch } from "vue";

/** data, componenet load */
import { axiosInstance } from "@/plugins/axiosPlugin";

import searchIconImg from "@/assets/icons/Magnifier.png";
import refreshIconImg from "@/assets/icons/Retry-refresh.png";

import TopBar from "@/components/common/TopBar.vue";
import BottomBar from "@/components/common/BottomBar.vue";
import TagButtonGroup from "@/components/common/TagButtonGroup.vue";
import AddressSearch from "@/components/common/AddressSearch.vue";
import BuildingInfo from "@/components/home/BuildingInfo.vue";

/** search bar */
const address = ref<string | null>(null);
const isAddressSearchOpen = ref<boolean>(false);
const openAddressSearch = () => {
  isAddressSearchOpen.value = true;
};
const closeAddressSearch = (selectedAddress: string) => {
  address.value = selectedAddress;
  isAddressSearchOpen.value = false;
};
/** tag button */
const tagOptions = ref([
  { label: "매물", value: "HOUSE" },

  { label: "주민센터", value: "CIVIC_CENTER" },
  { label: "체육관", value: "GYM" },
  { label: "공원", value: "PARK" },
  { label: "병원", value: "HOSPITAL" },
  { label: "약국", value: "PHARMACY" },
  { label: "응급의료시설", value: "MEDICAL_CLINIC" },
  { label: "CCTV", value: "CCTV" },
  { label: "경찰서", value: "POLICE" },
  { label: "편의점", value: "CONVENIENCE_STORE" },
  { label: "마트", value: "MART" },
  { label: "지하철 출구", value: "SUBWAY" },
  { label: "버스 정류장", value: "BUS" },
  { label: "따릉이", value: "BICYCLE" },
]);
const selectedButton = ref(null); // 배열이 아니라 문자열로 명시

/** tag button 값이 바뀔 때 데이터 요청 */
watch(selectedButton, (newValue) => {
  console.log("선택된 값 변경됨:", newValue);
  if (newValue == null) return;

  if (newValue === "HOUSE") {
    // 매물 버튼 클릭 시
    console.log("매물 버튼 클릭됨");
  } else {
    // 아래는 37.566535, 126.9779692 좌표를 기준으로 1000m 반경의 빌딩 데이터 요청
    // 다른 버튼 클릭 시
    const latitude = 37.566535; // 예시 위도
    const longitude = 126.9779692; // 예시 경도
    console.log("현재 지도 중심 좌표:", latitude, longitude);
    const radius = 1000; // 단위: 미터
    // fetchBuilding(newValue, latitude, longitude, radius);
  }
});

/** map 관련 */

const loading = ref(false); // 로딩 상태를 나타내는 변수
const buildings = ref([]);
const selectedBuilding = ref(null);

// 빌딩 데이터 타입 정의
interface Building {
  id: number;
  name: string;
  address: string;
  latitude: number;
  longitude: number;
}

// API 응답 타입 정의
interface ApiResponse {
  success: boolean;
  message?: string;
  data?: {
    list: Building[];
  };
}
const fetchBuildings = async (
  type: string,
  latitude: number,
  longitude: number,
  radius: number
): Promise<void> => {
  if (loading.value) return; // 이미 요청 중이라면 무시

  if (!type) {
    console.warn("Type is not selected."); // 타입 누락 경고
    return;
  }

  console.log("현재 위치: ", latitude, longitude);

  loading.value = true; // 로딩 상태 활성화

  // query parameters 생성
  const queryParams = new URLSearchParams({
    type,
    latitude: latitude.toString(),
    longitude: longitude.toString(),
    radius: radius.toString(),
  }).toString();

  try {
    // API 요청
    const response = await axiosInstance.get<ApiResponse>(
      `/v1/building?${queryParams}`
    );

    if (response.data.success && response.data.data) {
      buildings.value = response.data.data.list;
      console.log("타입별 빌딩 가져오기 성공!");
      console.log(response.data.data.length);
    } else {
      console.error("API 실패:", response.data.message || "알 수 없는 오류");
      buildings.value = [];
      alert(
        `Error: ${
          response.data.message || "데이터를 가져오는 중 문제가 발생했습니다."
        }`
      );
    }
  } catch (error: unknown) {
    // 요청 실패 처리
    if (error instanceof Error) {
      console.error("빌딩 데이터 요청 중 오류 발생:", error.message);
    } else {
      console.error("빌딩 데이터 요청 중 알 수 없는 오류 발생");
    }

    // 특정 오류 처리 (500, Illegal Argument)
    if (error instanceof AxiosError && error.response) {
      const status = error.response.status;

      if (status === 500) {
        alert("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
      } else if (
        status === 400 &&
        error.response.data.message.includes("Illegal Argument")
      ) {
        alert("잘못된 입력 값이 포함되었습니다. 입력 값을 다시 확인해주세요.");
      } else {
        alert(`요청 실패: ${status} - ${error.response.statusText}`);
      }
    } else if (error instanceof Error && error.code === "ECONNABORTED") {
      alert("요청 시간이 초과되었습니다. 네트워크 상태를 확인하세요.");
    } else {
      alert("요청을 처리하는 중 문제가 발생했습니다. 다시 시도해주세요.");
    }

    buildings.value = [];
  } finally {
    // 로딩 상태 해제
    loading.value = false;
  }
};

/** building, house info overlay */
const showInfoOverlay = () => {};

const contentHeight = ref(100); // info-content 초기 높이
const minHeight = 100; // 최소 높이
const maxHeight = 400; // 최대 높이
const startY = ref(0);
const startHeight = ref(0);
const isAnimating = ref(false);
const dragDirection = ref(null); // 'up' 또는 'down'

const startDrag = (event) => {
  isAnimating.value = false; // 드래그 중 애니메이션 제거
  startY.value = event.clientY; // 마우스 클릭 Y 좌표 저장
  startHeight.value = contentHeight.value; // 현재 높이 저장
  dragDirection.value = null; // 방향 초기화

  document.addEventListener("mousemove", onDrag);
  document.addEventListener("mouseup", endDrag);
};

const onDrag = (event) => {
  const deltaY = startY.value - event.clientY;

  // 드래그 방향 감지
  if (deltaY > 10) {
    dragDirection.value = "up"; // 위로 드래그
  } else if (deltaY < -10) {
    dragDirection.value = "down"; // 아래로 드래그
  }

  contentHeight.value = Math.min(
    maxHeight,
    Math.max(minHeight, startHeight.value + deltaY)
  );
};

const endDrag = () => {
  isAnimating.value = true; // 애니메이션 활성화

  // 드래그 방향에 따라 최종 높이 설정
  if (dragDirection.value === "up") {
    contentHeight.value = maxHeight; // 위로 올리면 최대 높이
  } else {
    contentHeight.value = minHeight; // 아래로 내리면 최소 높이
  }

  document.removeEventListener("mousemove", onDrag);
  document.removeEventListener("mouseup", endDrag);
};

// 언마운트 시 이벤트 제거
onUnmounted(() => {
  document.removeEventListener("mousemove", onDrag);
  document.removeEventListener("mouseup", endDrag);
});
/** kakao map functions */
const mapContainer = ref<HTMLElement | null>(null);
let mapInstance: kakao.maps.Map; // Kakao Map의 타입으로 변경
let clusterer: kakao.maps.MarkerClusterer; // Kakao Clusterer의 타입으로 변경
let isMarkersInitialized = false; // 마커가 이미 초기화되었는지 확인하는 변수

onMounted(() => {
  loadKakaoMap(mapContainer.value);
});

function loadKakaoMap(container) {
  if (!container) return;
  const script = document.createElement("script");
  script.src =
    "https://dapi.kakao.com/v2/maps/sdk.js?appkey=6fffd0278e1410b6884d13552414ecf2&autoload=false&libraries=clusterer";
  document.head.appendChild(script);

  script.onload = () => {
    window.kakao.maps.load(() => {
      mapInstance = new window.kakao.maps.Map(container, {
        center: new window.kakao.maps.LatLng(37.566535, 126.9779692),
        level: 5,
      });
      clusterer = new window.kakao.maps.MarkerClusterer({
        map: mapInstance,
        averageCenter: true,
        minLevel: 3,
      });
      fetchBuildings(null, 37.566535, 126.9779692, 150);
      mapInstance = new window.kakao.maps.Map(container, {
        center: new window.kakao.maps.LatLng(37.566535, 126.9779692),
        level: 4,
      });
      clusterer = new window.kakao.maps.MarkerClusterer({
        map: mapInstance,
        averageCenter: true,
        minLevel: 3,
      });
      fetchBuildings(null, 37.566535, 126.9779692, 150);
    });
  };
}

const clearMarkers = (): void => {
  clusterer.clear(); // 클러스터러에서 마커 제거
};

const addMarkers = (data: Array<any>): void => {
  // Specify the type here
  clearMarkers();

  const markers = data.map((item: any) => {
    // Specify the type here
    const markerPosition = new window.kakao.maps.LatLng(
      item.latitude,
      item.longitude
    );
    const marker = new window.kakao.maps.Marker({
      position: markerPosition,
    });

    // 마커 클릭 시, 해당 건물 정보 설정
    window.kakao.maps.event.addListener(marker, "click", () => {
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

const filterDataByBounds = (data: Array<any>): Array<any> => {
  // @ts-ignore: Ignoring the error for getBounds method
  const bounds = mapInstance.getBounds();
  const filteredData = data.filter((item: any) => {
    // Specify the type here
    const position = new window.kakao.maps.LatLng(
      item.latitude,
      item.longitude
    );
    return bounds.contain(position);
  });
  return filteredData;
};

const showInitialMarkers = (data: Array<any>): void => {
  // Specify the type here
  if (!isMarkersInitialized) {
    const visibleData = filterDataByBounds(data);
    addMarkers(visibleData);
    isMarkersInitialized = true;
  }
};
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
  overflow: visible;
}

/** scoped */
.top-overlay-wrapper {
  @include custom-padding-y;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: fit-content;

  display: flex;
  flex-direction: column;
  gap: $padding-small;

  /** search bar style */
  .searchbar {
    @include custom-margin-x;
    display: flex;
    cursor: pointer;
    .input-icon-wrap {
      @include custom-padding-x;
      display: flex;
      gap: $padding-default;
      align-items: center;
      width: 100%;
      height: 48px;
      border-radius: 13px;
      background-color: white;
      padding-right: $padding-default;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      z-index: 5; /* Higher than overlay */
      cursor: pointer;

      .icon-search {
        @include custom-icon-style;
        color: $input-placeholder-color;
      }

      p {
        @include custom-text($font-size: 14px, $font-color: $text-color-light);
      }
    }
  }

  /** tag button group style */
  .tag-button-container-wrapper {
    @include custom-padding-x;
    pointer-events: auto;
    overflow-x: auto;

    -webkit-overflow-scrolling: touch; // 모바일 부드러운 스크롤

    // 웹킷 브라우저에서 스크롤바 숨기기
    &::-webkit-scrollbar {
      width: 0;
      height: 0;
      display: none;
    }

    // 파이어폭스 및 다른 브라우저에서 스크롤바 숨기기
    scrollbar-width: none; // 파이어폭스
    -ms-overflow-style: none; // IE, Edge
  }
  z-index: 10; /* Ensure this is below the search bar */
}
/** kakao map style */
.map-container {
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  width: 100%;
  background-color: aqua;
}

.bottom-overlay-wrapper {
  position: absolute;
  bottom: 0;
  width: 100%;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  z-index: 5;

  /** refresh button */
  .info-refresh-button {
    width: fit-content;
    display: flex;
    align-items: center;
    gap: 6px;
    padding: 12px;
    margin-bottom: $margin-small;
    background-color: white;
    border-radius: 30px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.25);
    cursor: pointer;

    img {
      @include custom-icon-style(12px);
      color: $input-placeholder-color;
    }

    p {
      @include custom-text($font-size: 12px);
    }
  }

  .info-content-wrapper {
    padding-top: 8px;
    padding-bottom: $padding-default;
    display: flex;
    flex-direction: column;
    gap: $padding-default;
    align-items: center;
    justify-content: start;
    padding-left: 20;
    background-color: aqua;
    width: 100%;

    &.animating {
      transition: height 0.3s cubic-bezier(0.22, 1, 0.36, 1);
    }

    .info-content-indicator {
      width: 134px;
      height: 4px;
      border-radius: 100px;
      background-color: #242424;
    }
  }
}
</style>
