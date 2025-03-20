<template>
  <div class="sub-container">
    <div ref="mapContainer" style="width: 100%; height: 100%">
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
    </div>

    <!-- 클릭한 위치 정보 표시하는 팝업 -->
    <div v-if="popupVisible" class="popup-overlay" @click="closePopup">
      <div class="popup" @click.stop>
        <div class="addr">{{ house_address }}</div>
        <p>이 주소를 북마크에 추가할까요?</p>
        <div class="button-group">
          <button @click="closePopup" class="cancel-button">취소</button>
          <button @click="addBookmark" class="add-button">추가</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { axiosInstance } from "@/plugins/axiosPlugin";

let popupTimer: number | null = null;
let currentMarker: any = null;
const mapContainer = ref<HTMLElement | null>(null);
const popupVisible = ref(false);
const house_address = ref("");
const house_latitude = ref<number | null>(null);
const house_longitude = ref<number | null>(null);
const house_name = ref("");
const house_category = ref("");
const router = useRouter();

const showOverlay = ref(false);
const searchQuery = ref("");

onMounted(() => {
  loadKakaoMap(mapContainer.value as HTMLElement);
});

const loadKakaoMap = (container: HTMLElement) => {
  if (window.kakao && window.kakao.maps) {
    initMap(container); // 이미 로드된 경우 바로 지도 초기화
    return;
  }

  const script = document.createElement("script");
  script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=6fffd0278e1410b6884d13552414ecf2&libraries=services&autoload=false`;
  script.onload = () => {
    window.kakao.maps.load(() => initMap(container));
  };
  document.head.appendChild(script);
};

const initMap = (container: HTMLElement) => {
  const options = {
    center: new window.kakao.maps.LatLng(37.566535, 126.9779692),
    level: 3,
    maxLevel: 10,
  };
  const mapInstance = new window.kakao.maps.Map(container, options);
  const geocoder = new window.kakao.maps.services.Geocoder();

  window.kakao.maps.event.addListener(mapInstance, "mousedown", (mouseEvent: any) => {
    popupTimer = window.setTimeout(() => {
      const latlng = mouseEvent.latLng;

      if (currentMarker) {
        currentMarker.setMap(null);
      }

      house_latitude.value = latlng.getLat();
      house_longitude.value = latlng.getLng();

      geocoder.coord2Address(latlng.getLng(), latlng.getLat(), (result: any, status: any) => {
        if (status === window.kakao.maps.services.Status.OK) {
          house_address.value = result[0].road_address
            ? result[0].road_address.address_name
            : "도로명 주소가 없습니다";

          currentMarker = new window.kakao.maps.Marker({
            position: latlng,
          });
          currentMarker.setMap(mapInstance);
        }
      });

      popupVisible.value = true;
    }, 1000);
  });

  window.kakao.maps.event.addListener(mapInstance, "mouseup", () => {
    if (popupTimer) {
      clearTimeout(popupTimer);
      popupTimer = null;
    }
  });
};


// 팝업 닫기
const closePopup = () => {
  popupVisible.value = false;
};

const goToAddHousePage = () => {
  router.push({ 
    name: 'AddHousePage', 
    query: { 
      house_address: house_address.value,
      house_latitude: house_latitude.value,
      house_longitude: house_longitude.value,
      house_name: house_name.value,
      house_category: house_category.value,
    } 
  });
};

// 📌 북마크 추가 (DB 저장 없이 페이지 이동만 수행)
function addBookmark() {
  if (house_latitude.value === null || house_longitude.value === null) {
    console.error("Latitude or Longitude is null");
    return;
  }

  goToAddHousePage(); // 주소 및 좌표 정보를 AddHousePage로 전달
}


</script>

<style lang="scss" scoped>
.sub-container {
  justify-content: space-between;
}

.addr {
  font-weight: bold;
  padding: 10px;
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
input {
  width: 100%;
  appearance: none;
  border: none;
  outline: none;
  background: transparent;
  @include custom-text;
}
.icon-search {
  width: 16px;
  height: 16px;
  @include custom-margin-x;
  color: $input-placeholder-color;
}

.popup-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.7);
  display: flex;
  justify-content: center;
  align-items: flex-end;
  z-index: 10;
}

.popup {
  background-color: white;
  padding: 20px;
  border-radius: 10px 10px 0 0;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.3);
  width: 100%;
  max-width: 360px;
  margin: 0 10px; /* 양쪽에 여백 추가 */
  box-sizing: border-box; /* 패딩과 테두리 포함하여 너비 계산 */
}

.button-group {
  display: flex;
  justify-content: space-between; /* 버튼을 양쪽 끝으로 배치 */
}

.cancel-button {
  flex-basis: 160px;
  margin: 25px 10px 0 0;
}

.add-button {
  flex-basis: 160px;
  margin: 25px 10px 0 0;
  background-color: black;
  color: white;
}
</style>
