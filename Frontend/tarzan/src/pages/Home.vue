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
            class="info-content-indicator"
            @mousedown="startDrag"
            @touchstart="startDragHandler"></div>
          <div
            class="info-content"
            ref="infoContent"
            :style="{
              height: contentHeight,
            }">
            <p>{{ buildingContent }}</p>
          </div>
        </div>
      </div>
      <div ref="mapContainer" class="map-container"></div>
    </div>

    <div style="background-color: white">
      <BottomBar class="bottom-bar"></BottomBar>
    </div>
    <!-- 주소 검색 팝업 -->
    <!-- <AddressSearch v-if="isAddressSearchOpen" @close="closeAddressSearch" /> -->
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
import BuildingInfo from "@/components/home/BuildingInfo.vue";
// import { getScaleRatio } from "@/data/kakaoMap";

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
  // const radius = getScaleRatio(mapInstance.getLevel()).distance; // 단위: 미터
  // console.log("현재 지도 레벨: ", mapInstance.getLevel(), "radius:", radius);

  if (newValue === "HOUSE") {
    // 매물 버튼 클릭 시
  } else {
    // fetchBuildings(newValue, latitude, longitude, radius);
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
      buildings.value = response.data.data;
      console.log("타입별 빌딩 가져오기 성공!");
      console.log(response.data.data.length);

      addMarkers(mapInstance, buildings.value); // 마커 추가
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

/** building, house info overlay */
const showInfoOverlay = () => {};

const buildingContent = ref("");

const minHeight = 0; // 최소 높이
const maxHeight = ref(445); // 최대 높이 (기본값 445)
// const contentHeight = ref("auto"); // 초기값은 auto
const contentHeight = ref(0); // 초기값은 auto
const startY = ref(0);
const startHeight = ref(0);
// const isDraggable = ref(false);
const infoContent = ref(null);

const updateInitialHeight = () => {
  if (infoContent.value) {
    const actualHeight = infoContent.value.scrollHeight;
    // isDraggable.value = actualHeight > 66; // 드래그 가능 여부 확인
    maxHeight.value = actualHeight > 445 ? 445 : actualHeight; // 최대 높이 설정
    contentHeight.value = actualHeight > 445 ? 125 : actualHeight; // 초기 높이 설정
  }
};

onMounted(async () => {
  await nextTick();
  updateInitialHeight();
});

const startDrag = (event) => {
  // if (!isDraggable.value) return;
  console.log("드래그 시작");
  console.log();

  startY.value = event.clientY;
  startHeight.value =
    contentHeight.value === "auto"
      ? infoContent.value.scrollHeight
      : contentHeight.value;

  document.addEventListener("mousemove", onDrag);
  document.addEventListener("mouseup", endDrag);
  document.addEventListener("touchmove", onDrag);
  document.addEventListener("touchend", endDrag);
};

const startDragHandler = (event) => {
  event.preventDefault(); // 기본 이벤트 방지
  startDrag(event);
};

const onDrag = (event) => {
  const deltaY = startY.value - event.clientY;
  contentHeight.value = Math.min(
    maxHeight.value,
    Math.max(minHeight, startHeight.value + deltaY)
  );
};

const endDrag = () => {
  document.removeEventListener("mousemove", onDrag);
  document.removeEventListener("mouseup", endDrag);
  document.removeEventListener("touchmove", onDrag);
  document.removeEventListener("touchend", endDrag);
};

onUnmounted(() => {
  document.removeEventListener("mousemove", onDrag);
  document.removeEventListener("mouseup", endDrag);
  document.removeEventListener("touchmove", onDrag);
  document.removeEventListener("touchend", endDrag);
});

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

// 버튼 클릭 시 마커 추가
const addMarkers = (mapInstance, buildings) => {
  if (!mapInstance) {
    console.error("지도 객체가 아직 초기화되지 않았습니다.");
    return;
  }
  console.log("순회합니다.");
  console.log(buildings);
  // buildings 배열을 순회하며 마커 추가
  buildings.forEach((building) => {
    console.log(building);
    const markerPosition = new window.kakao.maps.LatLng(
      33.450696253381196,
      126.57066123419618
    ); // 마커의 위치
    console.log(markerPosition);
    // 마커 객체 생성
    const marker = new window.kakao.maps.Marker({
      position: markerPosition, // 마커 위치
      map: mapInstance, // 마커를 표시할 지도
    });
    console.log(marker);
    console.log("마커 객체 생성 완료");
    // 마커 클릭 시 이벤트 추가 (선택 사항)
    window.kakao.maps.event.addListener(marker, "click", () => {
      console.log("marker click");
      buildingContent.value = building;
      contentHeight.value = "fit-content";
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
    padding-top: 11px;
    display: flex;
    flex-direction: column;
    gap: 11px;
    align-items: center;
    justify-content: start;
    padding-left: 20;
    background-color: white;
    border-top-left-radius: 12px;
    border-top-right-radius: 12px;
    width: 100%;
    height: fit-content;

    overflow: hidden;
    transition: height 0.3s ease-out;

    .info-content-indicator {
      width: 134px;
      height: 4px;
      border-radius: 100px;
      background-color: #e8e8e8;

      cursor: grab;
    }

    .info-content {
      width: 100%;
      // flex: 1;
      background-color: aqua;
      overflow-y: auto;
    }
  }
}
</style>
