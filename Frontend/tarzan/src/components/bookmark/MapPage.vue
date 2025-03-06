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
        <div class="addr">{{ house_address }}</div>
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
const house_address = ref(""); // 📌 선택한 주소
const house_latitude = ref<number | null>(null); // 📌 선택한 위도
const house_longitude = ref<number | null>(null); // 📌 선택한 경도
const house_name = ref(""); // 📌 건물 이름
const house_category = ref(""); // 📌 건물 카테고리
const router = useRouter();

onMounted(() => {
  loadKakaoMap(mapContainer.value as HTMLElement);
});

watch([house_latitude, house_longitude], ([newLat, newLng]) => {
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

            house_latitude.value = latlng.getLat();
            house_longitude.value = latlng.getLng();

            // 📌 Geocoder 사용 (window.kakao.maps.load 내부에서 생성된 geocoder 사용)
            geocoder.coord2Address(
              latlng.getLng(),
              latlng.getLat(),
              (result: any, status: any) => {
                if (status === window.kakao.maps.services.Status.OK) {
                  house_address.value = result[0].road_address
                    ? result[0].road_address.address_name
                    : "도로명 주소가 없습니다";

                  house_name.value = "클릭한 위치의 건물";
                  house_category.value = "아파트";

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
  console.log("Latitude:", house_latitude.value);
  console.log("Longitude:", house_longitude.value);
  console.log("Address:", house_address.value);
  console.log("Name:", house_name.value);
  console.log("Category:", house_category.value);

  // 개별적으로 null 체크
  if (house_latitude.value === null || house_longitude.value === null) {
    console.error("Latitude or Longitude is null");
    return; // latitude나 longitude가 null인 경우 함수 종료
  }

  if (!house_address.value || !house_name.value || !house_category.value) {
    console.error("Address, Name, or Category is missing");
    return; // 필요한 값이 없는 경우 함수 종료
  }

  try {
    const response = await axiosInstance.post('/v1/bookmark/user', {
      house_address: house_address.value,
      house_latitude: house_latitude.value,
      house_longitude: house_longitude.value,
      house_name: house_name.value,
      category: house_category.value,
    });
    console.log("Response:", response.data);
  } catch (error) {
    console.error("API 호출 중 오류 발생:", error);
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
