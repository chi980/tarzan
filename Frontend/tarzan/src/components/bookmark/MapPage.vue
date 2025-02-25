<template>
  <div class="sub-container">
    <div ref="mapContainer" style="width: 100%; height: 100%"></div>
    <div v-if="popupVisible" class="popup-overlay" @click="closePopup">
      <div class="popup" @click.stop>
        <div class="addr">{{ address }}</div>
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
import { useRouter } from "vue-router"; // Vue Router 사용

// 타입 선언
let popupTimer: number | null = null; // popupTimer를 number 타입으로 지정
let currentMarker: any = null; // currentMarker의 타입을 any로 설정
const mapContainer = ref<HTMLElement | null>(null); // mapContainer의 타입을 HTMLElement로 지정
const popupVisible = ref(false);
const address = ref("");
const router = useRouter();

onMounted(() => {
  loadKakaoMap(mapContainer.value as HTMLElement);
});

const loadKakaoMap = (container: HTMLElement) => { // container의 타입을 HTMLElement로 명시
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

      // 지도에서 마우스를 눌렀을 때 타이머 시작
      window.kakao.maps.event.addListener(
        mapInstance,
        "mousedown",
        (mouseEvent: any) => { // mouseEvent의 타입을 any로 설정
          popupTimer = window.setTimeout(() => { // setTimeout의 반환값을 number 타입으로 지정
            const latlng = mouseEvent.latLng;

            // 이전 마커 제거
            if (currentMarker) {
              currentMarker.setMap(null);
            }

            // 역지오코딩 -> 도로명 주소
            const geocoder = new window.kakao.maps.services.Geocoder(); // services의 타입을 명시하지 않아도 오류 해결 가능
            geocoder.coord2Address(
              latlng.getLng(),
              latlng.getLat(),
              (result: any, status: any) => { // result와 status의 타입을 any로 설정
                if (status === window.kakao.maps.services.Status.OK) {
                  address.value = result[0].road_address
                    ? result[0].road_address.address_name
                    : "도로명 주소가 없습니다";

                  // 마커 생성
                  currentMarker = new window.kakao.maps.Marker({
                    position: latlng,
                  });

                  currentMarker.setMap(mapInstance);
                }
              }
            );

            popupVisible.value = true;
          }, 1000); // 1초 동안 클릭시 팝업 띄움
        }
      );

      // 마우스를 뗄 때 타이머 초기화
      window.kakao.maps.event.addListener(mapInstance, "mouseup", () => {
        if (popupTimer) {
          clearTimeout(popupTimer);
          popupTimer = null;
        }
      });
    });
  };
};

const closePopup = () => {
  popupVisible.value = false;
};

const addBookmark = () => {
  router.push({ path: "/bookmark/add", query: { address: address.value } });
};
</script>

<style lang="scss" scoped>
.sub-container {
  justify-content: space-between;
}

.addr {
  font-weight: bold;
  padding: 10px;
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
