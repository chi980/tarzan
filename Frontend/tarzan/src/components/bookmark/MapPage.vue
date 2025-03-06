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

    <!-- 📌 클릭한 위치 정보 표시하는 팝업 -->
    <div v-if="popupVisible" class="popup-overlay" @click="closePopup">
      <div class="popup" @click.stop>
        <div class="addr">{{ address }}</div>
        <!--
        <div>위도: {{ houseLatitude }}</div>
        <div>경도: {{ houseLongitude }}</div>
        -->
        <p>이곳으로 등록할까요?</p>
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
import { watch } from "vue";


let popupTimer: number | null = null;
let currentMarker: any = null;
const mapContainer = ref<HTMLElement | null>(null);
const popupVisible = ref(false);
const address = ref(""); // 📌 선택한 주소
const houseLatitude = ref<number | null>(null); // 📌 선택한 위도
const houseLongitude = ref<number | null>(null); // 📌 선택한 경도
const houseName = ref(""); // 📌 건물 이름
const houseCategory = ref(""); // 📌 건물 카테고리
const router = useRouter();

onMounted(() => {
  loadKakaoMap(mapContainer.value as HTMLElement);
});

watch([houseLatitude, houseLongitude], ([newLat, newLng]) => {
  console.log("위도와 경도 변경됨:", newLat, newLng);
});

const loadKakaoMap = (container: HTMLElement) => {
  const script = document.createElement("script");
  script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=6fffd0278e1410b6884d13552414ecf2&libraries=services&autoload=false`;
  document.head.appendChild(script);

  script.onload = () => {
    window.kakao.maps.load(() => {
      const options = {
        center: new window.kakao.maps.LatLng(37.566535, 126.9779692),
        level: 3,
        maxLevel: 10,
      };
      const mapInstance = new window.kakao.maps.Map(container, options);

      // 📌 Geocoder 인스턴스를 미리 생성
      const geocoder = new window.kakao.maps.services.Geocoder();

      window.kakao.maps.event.addListener(
        mapInstance,
        "mousedown",
        (mouseEvent: any) => {
          popupTimer = window.setTimeout(() => {
            const latlng = mouseEvent.latLng;

            if (currentMarker) {
              currentMarker.setMap(null);
            }

            houseLatitude.value = latlng.getLat();
            houseLongitude.value = latlng.getLng();

            // 📌 Geocoder 사용 (window.kakao.maps.load 내부에서 생성된 geocoder 사용)
            geocoder.coord2Address(
              latlng.getLng(),
              latlng.getLat(),
              (result: any, status: any) => {
                if (status === window.kakao.maps.services.Status.OK) {
                  address.value = result[0].road_address
                    ? result[0].road_address.address_name
                    : "도로명 주소가 없습니다";

                  houseName.value = "클릭한 위치의 건물";
                  houseCategory.value = "아파트";
                  houseLatitude.value = 37.566535;
                  houseLongitude.value = 126.9779692;

                  // 마커 생성 및 표시
                  currentMarker = new window.kakao.maps.Marker({
                    position: latlng,
                  });
                  currentMarker.setMap(mapInstance);
                }
              }
            );

            popupVisible.value = true;
          }, 1000);
        }
      );

      window.kakao.maps.event.addListener(mapInstance, "mouseup", () => {
        if (popupTimer) {
          clearTimeout(popupTimer);
          popupTimer = null;
        }
      });
    });
  };
};

// 📌 팝업 닫기
const closePopup = () => {
  popupVisible.value = false;
};

// 📌 북마크 추가 (주소 + 위도·경도 함께 전달)
async function addBookmark() {
  console.log("Latitude:", houseLatitude.value);
  console.log("Longitude:", houseLongitude.value);
  console.log("Address:", address.value);
  console.log("Name:", houseName.value);
  console.log("Category:", houseCategory.value);

  // 개별적으로 null 체크
  if (houseLatitude.value === null) {
    console.error("Latitude is null");
  }
  if (houseLongitude.value === null) {
    console.error("Longitude is null");
  }
  if (address.value === null) {
    console.error("Address is null");
  }
  if (houseName.value === null) {
    console.error("Name is null");
  }
  if (houseCategory.value === null) {
    console.error("Category is null");
  }

  if (houseLatitude.value && houseLongitude.value && address.value && houseName.value && houseCategory.value) {
    try {
      const response = await axiosInstance.post('/v1/bookmark/user', {
        address: address.value,
        latitude: houseLatitude.value,
        longitude: houseLongitude.value,
        name: houseName.value,
        category: houseCategory.value,
      });
      console.log("Response:", response.data);
    } catch (error) {
      console.error("API 호출 중 오류 발생:", error);
    }
  } else {
    console.error("Some fields are null or undefined.");
  }
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
