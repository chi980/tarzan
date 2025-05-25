<template>
  <div class="sub-container">
    <TopBar class="topbar"></TopBar>
    <div class="center-container" ref="centerContainer">
      <div class="top-overlay-wrapper" ref="topOverlayWrapper">
        <div class="searchbar" @click="openAddressSearch">
          <div class="input-icon-wrap">
            <img :src="searchIconImg" alt="search icon" class="icon-search" />
            <p>찾고 싶은 주소를 입력해주세요.</p>
          </div>
        </div>
        <div class="tag-button-container-wrapper">
          <TagButtonGroup
            v-model:selectedButton="selectedButton"
            @tag-click="() => loadMarkers(selectedButton)"
            :buttons="tagOptions"
            :multiple="false" />
        </div>
      </div>
      <div ref="mapContainer" class="map-container"></div>
      <div class="bottom-overlay-wrapper">
        <div
          class="info-refresh-button"
          @click="() => loadMarkers(selectedButton)"
          ref="refreshButton">
          <img :src="refreshIconImg" alt="refresh icon" />
          <p>새로 불러오기</p>
        </div>
        <OverlayPanel
          v-show="isVisibleOverlay"
          :minHeight="minHeight"
          :midHeight="midHeight"
          :maxHeight="maxHeight"
          :initialHeight="initialHeight"
          :isStretch="isStretch"
          @updateIsStretch="handleIsStretch"
          :type="overlayType">
          <div class="overlay-content">
            <div class="indicator-wrapper" ref="indicatorWrapper">
              <div class="indicator"></div>
            </div>
            <div ref="buildingDetail">
              <BuildingDetail
                v-if="buildingContent"
                :building="buildingContent" />
            </div>
            <HouseDetail v-if="houseContent" :house="houseContent" />
          </div>
        </OverlayPanel>
      </div>
    </div>

    <div style="background-color: white">
      <BottomBar class="bottom-bar"></BottomBar>
    </div>

    <!-- 주소 검색 팝업 -->
    <AddressSearch v-if="isAddressSearchOpen" @close="closeAddressSearch" />
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, nextTick, watch, computed } from "vue";

/** data, componenet load */
import { axiosInstance } from "@/plugins/axiosPlugin";
import { useKakaoMarkerManager } from "@/composables/useKakaoMarkerManager";

import searchIconImg from "@/assets/icons/Magnifier.png";
import refreshIconImg from "@/assets/icons/Retry-refresh.png";

import TopBar from "@/components/common/TopBar.vue";
import BottomBar from "@/components/common/BottomBar.vue";
import TagButtonGroup from "@/components/common/TagButtonGroup.vue";
import BuildingDetail from "@/components/home/BuildingDetail.vue";
import HouseDetail from "@/components/home/HouseDetail.vue";
import AddressSearch from "@/components/common/AddressSearchApi.vue";
import OverlayPanel from "@/components/common/OverlayPannel.vue";
import { getScaleRatio } from "@/data/kakaoMap";

/** search bar */
const isAddressSearchOpen = ref<boolean>(false);
const openAddressSearch = () => {
  isAddressSearchOpen.value = true;
};
const closeAddressSearch = (selectedAddress) => {
  isAddressSearchOpen.value = false;

  const lat = Number(selectedAddress.x);
  const lng = Number(selectedAddress.y);

  markerManager.addMarker(
    lat,
    lng,
    {
      house_id: selectedAddress.id,
      house_name: selectedAddress.place_name,
      house_address: selectedAddress.address_name,
      house_category: "아파트",
      house_latitude: selectedAddress.y,
      house_longitude: selectedAddress.x,
    },
    (picked) => {
      houseContent.value = picked;
      buildingContent.value = null;
      isStretch.value = true;
    }
  );

  // 지도 중심 이동
  mapInstance.setCenter(new kakao.maps.LatLng(lat, lng));
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
const selectedButton = ref(null);

const loadMarkers = async (type) => {
  if (type == null) return;

  // 지도 중심 좌표
  const center = mapInstance.getCenter();
  const latitude = center.getLat();
  const longitude = center.getLng();
  const radius = getScaleRatio(mapInstance.getLevel()).distance; // 단위: 미터

  if (type === "HOUSE") {
    await fetchHouses(latitude, longitude, radius);
    if (houses.value.length === 0) {
      markerManager.clearAll();
      return;
    }
    markerManager.addMarkers(
      houses.value,
      "house_latitude",
      "house_longitude",
      (house) => {
        houseContent.value = house;
        buildingContent.value = null;
        isStretch.value = true;
      }
    );
  } else {
    await fetchBuildings(type, latitude, longitude, radius);
    if (buildings.value.length === 0) {
      markerManager.clearAll();
      return;
    }
    markerManager.addMarkers(
      buildings.value,
      "building_latitude",
      "building_longitude",
      (building) => {
        houseContent.value = null;
        buildingContent.value = building;
        isStretch.value = true;
      }
    );
  }
};

/** fetch */
const loading = ref(false); // 로딩 상태를 나타내는 변수
const buildings = ref([]);
const houses = ref([]);

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
    const response = await axiosInstance.get(`/v1/building?${queryParams}`);
    if (response.data.success && response.data.data) {
      buildings.value = response.data.data;
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
const fetchHouses = async (
  latitude: number,
  longitude: number,
  radius: number
): Promise<void> => {
  if (loading.value) return; // 이미 요청 중이라면 무시

  loading.value = true; // 로딩 상태 활성화

  // query parameters 생성
  const queryParams = new URLSearchParams({
    latitude: latitude.toString(),
    longitude: longitude.toString(),
    radius: radius.toString(),
  }).toString();

  try {
    // API 요청
    const response = await axiosInstance.get(`/v1/houses?${queryParams}`);

    if (response.data.success && response.data.data) {
      houses.value = response.data.data;
    } else {
      console.error("API 실패:", response.data.message || "알 수 없는 오류");
      houses.value = [];
      alert(
        `Error: ${
          response.data.message || "데이터를 가져오는 중 문제가 발생했습니다."
        }`
      );
    }
  } catch (error: unknown) {
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

    houses.value = [];
  } finally {
    // 로딩 상태 해제
    loading.value = false;
  }
};

/** overlay 관련 */
const buildingContent = ref(null);
const houseContent = ref(null);

const centerContainer = ref<HTMLDivElement | null>(null);
const topOverlayWrapper = ref<HTMLDivElement | null>(null);
const refreshButton = ref<HTMLDivElement | null>(null);
const BOTTOM_OVERLAY_MAX_HEIGHT = ref(400);

const buildingDetail = ref<HTMLDivElement | null>(null);

const minHeight = ref(0);
const midHeight = computed(() => {
  return (minHeight.value + maxHeight.value) / 2;
});
const maxHeight = ref(400);
const initialHeight = ref(0);

const isVisibleOverlay = ref<boolean | null>(null);
const overlayType = ref("none"); // "full", "small", "none" 중 하나
const isStretch = ref(false);
const handleIsStretch = (value: boolean) => {
  isStretch.value = value;
};

onMounted(async () => {
  await nextTick();
  isVisibleOverlay.value = false;
  initHeights();
  isVisibleOverlay.value = true;
});

const initHeights = () => {
  BOTTOM_OVERLAY_MAX_HEIGHT.value =
    centerContainer.value.clientHeight -
    topOverlayWrapper.value.clientHeight -
    refreshButton.value.clientHeight;

  initialHeight.value = 0;
  maxHeight.value = 0;
};
watch(
  () => buildingContent.value, // 여기!
  async (newVal) => {
    if (newVal) {
      isVisibleOverlay.value = true;
      await nextTick(); // DOM 업데이트 후 높이 계산
      const newHeight = buildingDetail.value?.scrollHeight ?? 0;
      maxHeight.value = newHeight; // 최대 높이 설정
      overlayType.value = "small";
    } else {
      overlayType.value = "full";
      initHeights();
    }
  }
);

watch(
  () => houseContent.value, // 여기!
  async (newVal) => {
    if (newVal) {
      isVisibleOverlay.value = true;
      await nextTick(); // DOM 업데이트 후 높이 계산
      maxHeight.value = BOTTOM_OVERLAY_MAX_HEIGHT.value;
      overlayType.value = "full";
    } else {
      overlayType.value = "small";
      initHeights();
    }
  }
);

/** kakao map functions */
const mapContainer = ref<HTMLDivElement | null>(null); // 지도를 표시할 div
let mapInstance: any = null;
let markerManager!: ReturnType<typeof useKakaoMarkerManager>;

// `onMounted`에서 카카오 맵 초기화
onMounted(() => {
  loadKakaoMap(mapContainer.value);
});

// const loadKakaoMap = (container) => {
//   const script = document.createElement("script");
//   script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=${
//     import.meta.env.VITE_KAKAO_MAP_KEY
//   }&libraries=services,clusterer&autoload=false`; // libraries 추가
//   document.head.appendChild(script);

//   script.onload = () => {
//     window.kakao.maps.load(() => {

//       const options = {
//         center: new window.kakao.maps.LatLng(37.5115, 127.0325), // 지도 중심 좌표
//         level: 5, // 지도 확대 레벨
//       };

//       mapInstance = new window.kakao.maps.Map(container, options); // 지도 생성
//       markerManager = useKakaoMarkerManager(mapInstance);
//     });
//   };
// };
/**
 * container: 지도가 붙을 DOM 엘리먼트
 */
const loadKakaoMap = (container) => {
  // 1) SDK 스크립트 동적 로드
  const script = document.createElement("script");
  script.src = [
    "https://dapi.kakao.com/v2/maps/sdk.js",
    `?appkey=${import.meta.env.VITE_KAKAO_MAP_KEY}`,
    "&libraries=services,clusterer",
    "&autoload=false",
  ].join("");
  document.head.appendChild(script);

  script.onload = () => {
    window.kakao.maps.load(() => {
      // 2) 기본 센터 좌표 정의
      const defaultCenter = new window.kakao.maps.LatLng(37.5115, 127.0325);
      const options = { center: defaultCenter, level: 5 };

      // 3) Geolocation 지원하면 현재 위치 시도
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          ({ coords }) => {
            // 성공 시
            options.center = new window.kakao.maps.LatLng(
              coords.latitude,
              coords.longitude
            );
            initializeMap();
          },
          (err) => {
            // 실패 시 (권한 거부, 타임아웃 등)
            console.warn("현재 위치 가져오기 실패:", err.message);
            initializeMap();
          },
          {
            enableHighAccuracy: true,
            timeout: 5000,
            maximumAge: 0,
          }
        );
      } else {
        // Geolocation 미지원 브라우저
        initializeMap();
      }

      // 4) 지도 생성 + 마커 매니저 초기화
      function initializeMap() {
        mapInstance = new window.kakao.maps.Map(container, options);
        markerManager = useKakaoMarkerManager(mapInstance);
      }
    });
  };
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
}
</style>
