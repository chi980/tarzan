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

        <div class="info-content-wrapper">
          <div
            class="info-content-indicator-wrppaer"
            @click="toggleContentHeight">
            <div class="info-content-indicator"></div>
          </div>
          <div
            class="info-content"
            ref="infoContent"
            :style="{
              height: contentHeight + 'px',
              transition: 'height 0.3s ease',
            }">
            <BuildingDetail
              v-if="buildingContent"
              :building="buildingContent" />
            <HouseDetail v-if="houseContent" :house="houseContent" />
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
import { ref, onMounted, onUnmounted, nextTick, watch } from "vue";

/** data, componenet load */
import { axiosInstance } from "@/plugins/axiosPlugin";

import searchIconImg from "@/assets/icons/Magnifier.png";
import refreshIconImg from "@/assets/icons/Retry-refresh.png";

import TopBar from "@/components/common/TopBar.vue";
import BottomBar from "@/components/common/BottomBar.vue";
import TagButtonGroup from "@/components/common/TagButtonGroup.vue";
import BuildingDetail from "@/components/home/BuildingDetail.vue";
import HouseDetail from "@/components/home/HouseDetail.vue";
import AddressSearch from "@/components/common/AddressSearch.vue";
import { getScaleRatio } from "@/data/kakaoMap";

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

  const center = mapInstance.getCenter();
  const latitude = center.getLat(); // 예시 위도
  const longitude = center.getLng(); // 예시 경도
  console.log("현재 지도 중심 좌표:", latitude, longitude);
  const radius = getScaleRatio(mapInstance.getLevel()).distance; // 단위: 미터
  console.log("현재 지도 레벨: ", mapInstance.getLevel(), "radius:", radius);
  contentHeight.value = 0; // 정보창 닫기
  if (newValue === "HOUSE") {
    // 매물 버튼 클릭 시
    // fetchHouses(latitude, longitude, radius);
    houses.value = [
      {
        house_id: 1,
        house_latitude: latitude,
        house_longitude: longitude,
      },
    ];
    addHouseMarkers(mapInstance, houses.value);
  } else {
    // fetchBuildings(newValue, latitude, longitude, radius);
    buildings.value = [
      {
        building_name: "CNP차앤박피부과 도곡양재점",
        building_category: "종합병원",
        building_address: "서울 강남구 강남대로 248 목원빌딩 3층 (도곡동)",
        building_latitude: mapInstance.getCenter().getLat(),
        building_longitude: mapInstance.getCenter().getLng(),
        building_type: newValue,
      },
    ];
    addBuildingMarkers(mapInstance, buildings.value); // 마커 추가
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
      buildings.value = response.data.data;
      console.log("타입별 빌딩 가져오기 성공!");
      console.log(response.data.data.length);

      addBuildingMarkers(mapInstance, buildings.value); // 마커 추가
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
    // if (error instanceof Error) {
    //   console.error("빌딩 데이터 요청 중 오류 발생:", error.message);
    // } else {
    //   console.error("빌딩 데이터 요청 중 알 수 없는 오류 발생");
    // }

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
    const response = await axiosInstance.get<ApiResponse>(
      `/v1/houses?${queryParams}`
    );

    if (response.data.success && response.data.data) {
      houses.value = response.data.data;

      addHouseMarkers(mapInstance, houses.value); // 마커 추가
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
import { useAuthStore } from "@/stores/authStore";
const showInfoOverlay = async () => {
  try {
    const authStore = useAuthStore();
    const response = await axiosInstance.get(`/check`);
    console.log(response);
    console.log(authStore.getUser);
  } catch (error) {
    console.error(error.message);
  }
};

const contentHeight = ref(0);
const maxHeight = ref(0);
const MAX_HEIGHT = 500;
const infoContent = ref<HTMLElement | null>(null);

const buildingContent = ref(null);
const houseContent = ref(null);

const updateInitialHeight = () => {
  if (infoContent.value) {
    contentHeight.value = 0; // 처음에는 숨긴 상태
  }
};

onMounted(async () => {
  await nextTick();
  updateInitialHeight();
});
onUnmounted(() => {
  contentHeight.value = 0; // 컴포넌트 언마운트 시 초기화
});
const toggleContentHeight = async () => {
  console.log("indicator click");

  if (contentHeight.value === 0) {
    await nextTick(); // DOM 업데이트 기다림
    if (infoContent.value) {
      const newHeight = infoContent.value.scrollHeight;
      maxHeight.value = newHeight > MAX_HEIGHT ? MAX_HEIGHT : newHeight; // 최대 높이 설정
      contentHeight.value = maxHeight.value; // 최대 높이로 설정
    }
  } else {
    contentHeight.value = 0;
  }
};

/** kakao map functions */
const mapContainer = ref<HTMLDivElement | null>(null); // 지도를 표시할 div
let mapInstance: any = null;

// `onMounted`에서 카카오 맵 초기화
onMounted(() => {
  loadKakaoMap(mapContainer.value);
});

const loadKakaoMap = (container) => {
  const script = document.createElement("script");
  script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=${
    import.meta.env.VITE_KAKAO_MAP_KEY
  }&autoload=false`;
  document.head.appendChild(script);

  script.onload = () => {
    window.kakao.maps.load(() => {
      const options = {
        center: new window.kakao.maps.LatLng(33.450701, 126.570667), // 지도 중심 좌표
        level: 3, // 지도 확대 레벨
        maxLevel: 5, // 지도 축소 제한 레벨
      };

      mapInstance = new window.kakao.maps.Map(container, options); // 지도 생성
    });
  };
};
const addHouseMarkers = (mapInstance, houses) => {
  houses.forEach((house) => {
    const position = new window.kakao.maps.LatLng(
      house.house_latitude,
      house.house_longitude
    );
    const marker = new window.kakao.maps.Marker({ position, map: mapInstance });

    window.kakao.maps.event.addListener(marker, "click", async () => {
      houseContent.value = house;
      buildingContent.value = null;

      await nextTick();
      if (infoContent.value) {
        const newHeight = infoContent.value.scrollHeight;
        maxHeight.value = newHeight > MAX_HEIGHT ? MAX_HEIGHT : newHeight; // 최대 높이 설정
        contentHeight.value = maxHeight.value;
      }
    });
  });
};

const addBuildingMarkers = (mapInstance, buildings) => {
  buildings.forEach((building) => {
    const position = new window.kakao.maps.LatLng(
      building.building_latitude,
      building.building_longitude
    );
    const marker = new window.kakao.maps.Marker({ position, map: mapInstance });

    window.kakao.maps.event.addListener(marker, "click", async () => {
      buildingContent.value = building;
      houseContent.value = null;

      await nextTick();
      if (infoContent.value) {
        const newHeight = infoContent.value.scrollHeight;
        maxHeight.value = newHeight > MAX_HEIGHT ? MAX_HEIGHT : newHeight; // 최대 높이 설정
        contentHeight.value = maxHeight.value;
      }
    });
  });
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
    @include custom-text;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: start;
    padding-left: 20;
    background-color: white;
    border-top-left-radius: 12px;
    border-top-right-radius: 12px;
    width: 100%;
    height: fit-content;

    overflow: hidden;

    background-color: white;

    .info-content-indicator-wrppaer {
      @include custom-padding-y($padding-small);
      width: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      cursor: grab;

      .info-content-indicator {
        width: 134px;
        height: 4px;
        border-radius: 100px;
        background-color: #e8e8e8;
      }
    }

    .info-content {
      width: 100%;
      // flex: 1;
      transition: height 0.3s ease-out;

      overflow-y: auto;
    }
  }
}
</style>
