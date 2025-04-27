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
            :buttons="tagOptions"
            :multiple="false" />
        </div>
      </div>

      <div class="bottom-overlay-wrapper">
        <div
          class="info-refresh-button"
          @click="showInfoOverlay"
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
import { ref, onMounted, onUnmounted, nextTick, watch, computed } from "vue";

/** data, componenet load */
import { axiosInstance } from "@/plugins/axiosPlugin";

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
  addAddressMarker(mapInstance, selectedAddress);
  console.log("선택됨: " + JSON.stringify(selectedAddress));
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
watch(selectedButton, async (newValue) => {
  if (newValue == null) return;

  // 지도 중심 좌표
  const center = mapInstance.getCenter();
  const latitude = center.getLat(); // 예시 위도
  const longitude = center.getLng(); // 예시 경도
  // console.log("현재 지도 중심 좌표:", latitude, longitude);
  const radius = getScaleRatio(mapInstance.getLevel()).distance; // 단위: 미터
  // console.log("현재 지도 레벨: ", mapInstance.getLevel(), "radius:", radius);

  // 이전 매물, 건물 마커 제거
  // clearMarkers(houseMarkers.value);
  // clearMarkers(buildingMarkers.value);
  // clearClusterer(buildingClusterer);

  const type = newValue.value;
  if (type === "HOUSE") {
    // 매물 버튼 클릭
    await fetchHouses(latitude, longitude, radius);
    if (houses.value.length === 0) {
      alert("해당 지역에 매물이 없습니다.");
      return;
    }
    mapInstance.setCenter(
      new kakao.maps.LatLng(
        houses.value[0].house_latitude,
        houses.value[0].house_longitude
      )
    );
    addMarker(
      mapInstance,
      houses.value[0].house_latitude,
      houses.value[0].house_longitude,
      "HOUSE",
      houses.value[0]
    );
    // addHouseMarkers(mapInstance, houses.value);
  } else {
    await fetchBuildings(type, latitude, longitude, radius);
    if (buildings.value.length === 0) {
      alert("해당 지역에 건물이 없습니다.");
      return;
    }
    mapInstance.setCenter(
      new kakao.maps.LatLng(
        buildings.value[0].building_latitude,
        buildings.value[0].building_longitude
      )
    );
    addMarker(
      mapInstance,
      buildings.value[0].building_latitude,
      buildings.value[0].building_longitude,
      "BUILDING",
      buildings.value[0]
    );
  }
});

/** map 관련 */

const loading = ref(false); // 로딩 상태를 나타내는 변수
const buildings = ref([]);
const houses = ref([]);

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

  loading.value = true; // 로딩 상태 활성화
  console.log(type);
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

  console.log("latitude: ", latitude);
  console.log("longitude: " + longitude);
  console.log("나의 radius: ", radius);

  loading.value = true; // 로딩 상태 활성화

  // query parameters 생성
  const queryParams = new URLSearchParams({
    latitude: latitude.toString(),
    longitude: longitude.toString(),
    radius: radius.toString(),
  }).toString();

  try {
    // API 요청
    const response = await axiosInstance.get<ApiResponse>(
      `/v1/houses?${queryParams}`
    );

    console.log(response.data.data);

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
/** building, house info overlay */
const showInfoOverlay = async () => {
  console.log("새로 불러오기");
};

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
const overlayType = ref("full"); // "full", "small" 중 하나
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
  overlayType.value = "small";
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
      overlayType.value = "full";
      initHeights();
    }
  }
);

/** kakao map functions */
const mapContainer = ref<HTMLDivElement | null>(null); // 지도를 표시할 div
let mapInstance: any = null;
let markerClusterer: kakao.maps.MarkerClusterer;

// `onMounted`에서 카카오 맵 초기화
onMounted(() => {
  loadKakaoMap(mapContainer.value);
});

const loadKakaoMap = (container) => {
  const script = document.createElement("script");
  script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=${
    import.meta.env.VITE_KAKAO_MAP_KEY
  }&libraries=services,clusterer&autoload=false`; // libraries 추가
  document.head.appendChild(script);

  script.onload = () => {
    window.kakao.maps.load(() => {
      const options = {
        center: new window.kakao.maps.LatLng(37.5115, 127.0325), // 지도 중심 좌표
        level: 5, // 지도 확대 레벨
      };

      mapInstance = new window.kakao.maps.Map(container, options); // 지도 생성

      // 클러스터러 초기화
      const clusterer = new kakao.maps.MarkerClusterer({
        map: mapInstance, // 클러스터러가 적용될 지도
        averageCenter: true, // 클러스터의 중심을 평균 위치로 설정
        minLevel: 5,
      });
    });
  };
};

const addAddressMarker = (mapInstance, address) => {
  console.log("add Address Marker", JSON.stringify(address));
  const position = new window.kakao.maps.LatLng(
    Number(address.y),
    Number(address.x)
  );

  // 새 마커 생성
  const marker = new window.kakao.maps.Marker({
    position,
    map: mapInstance,
  });
  window.kakao.maps.event.addListener(marker, "click", async () => {
    houseContent.value = {
      house_id: address.id,
      house_name: address.place_name,
      house_address: address.address_name,
      house_category: "아파트",
      house_latitude: address.y,
      house_longitude: address.x,
    };
    buildingContent.value = null;

    console.log("addmaker ", JSON.stringify(houseContent.value));

    await nextTick();
    if (infoContent.value) {
      const newHeight = infoContent.value.scrollHeight;
      maxHeight.value = newHeight > MAX_HEIGHT ? MAX_HEIGHT : newHeight; // 최대 높이 설정
    }
  });
  // 지도 중심 이동
  mapInstance.setCenter(position);
};

const houseMarkers = ref<kakao.maps.Marker[]>([]);
const buildingMarkers = ref<kakao.maps.Marker[]>([]);

let houseClusterer: kakao.maps.MarkerClusterer | null = null;
let buildingClusterer: kakao.maps.MarkerClusterer | null = null;

const addHouseMarkers = (map, houses) => {
  houseMarkers.value = [];

  houses.forEach((house) => {
    const marker = new kakao.maps.Marker({
      map: map,
      position: new kakao.maps.LatLng(
        house.house_latitude,
        house.house_longitude
      ),
    });

    window.kakao.maps.event.addListener(marker, "click", async () => {
      houseContent.value = house;
      buildingContent.value = null;
    });

    houseMarkers.value.push(marker);
  });
};

const addBuildingMarkers = (map, buildings) => {
  // 클러스터러가 초기화되지 않았다면 초기화
  if (!buildingClusterer) {
    buildingClusterer = new window.kakao.maps.MarkerClusterer({
      map: map,
      averageCenter: true, // 클러스터의 중심을 평균 위치로 설정
      minLevel: 5, // 최소 지도 레벨
    });
  }

  clearMarkers(buildingMarkers.value); // 마커 제거
  clearClusterer(buildingClusterer); // 클러스터 제거

  buildingMarkers.value = [];

  // 새로운 마커 추가
  buildings.forEach((building) => {
    const marker = new kakao.maps.Marker({
      map: map,
      position: new kakao.maps.LatLng(
        building.building_latitude,
        building.building_longitude
      ),
    });

    // 마커 클릭 이벤트
    window.kakao.maps.event.addListener(marker, "click", async () => {
      buildingContent.value = building;
      houseContent.value = null;
    });

    buildingClusterer.addMarker(marker);
    buildingMarkers.value.push(marker);
  });
};

const addMarker = (mapInstance, latitude, longitude, type, content) => {
  const position = new window.kakao.maps.LatLng(latitude, longitude);

  const marker = new window.kakao.maps.Marker({
    position,
    map: mapInstance,
  });

  window.kakao.maps.event.addListener(marker, "click", () => {
    console.log("마커클릭됨, " + type);
    console.log(content);
    console.log(isVisibleOverlay.value);
    if (type == "HOUSE") {
      houseContent.value = content;
      buildingContent.value = null;
    } else if (type == "BUILDING") {
      houseContent.value = null;
      buildingContent.value = content;
    }
    overlayType.value = "full";
    isStretch.value = true;
  });

  return marker; // 필요 시 marker 반환
};
// 마커가 화면에 보이는지 확인하는 함수
const isMarkerInView = (position) => {
  const bounds = mapInstance.getBounds(); // 지도에서 현재 보이는 영역
  return bounds.contain(position); // 마커가 보이는 영역에 포함되는지 확인
};

// 클러스터러에서 마커 제거 함수
const clearClusterer = (clusterer: kakao.maps.MarkerClusterer | null) => {
  if (clusterer) {
    clusterer.clear(); // 클러스터러에서 모든 마커 제거
  }
};

// 마커 제거 함수
const clearMarkers = (markers: kakao.maps.Marker[]) => {
  markers.forEach((marker) => marker.setMap(null));
  markers.splice(0); // 마커 배열도 비워야 이후 문제가 없음
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
