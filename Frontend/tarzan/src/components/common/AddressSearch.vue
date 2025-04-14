<script setup lang="ts">
import { ref, onMounted, defineEmits, watch } from "vue";
import axios from "axios";
import searchIconImg from "@/assets/icons/Magnifier.png";
import AddressSearchResult from "@/components/common/AddressSearchResult.vue";
import { debounce } from "lodash"; // lodash의 debounce 사용
import TopBarBack from "@/components/common/TopBarBack.vue";
import BottomDefaultButton from "@/components/common/BottomDefaultButton.vue";

const emit = defineEmits(["close", "selectAddress"]);

const searchQuery = ref("");
const searchResults = ref([]);
const userLocation = ref({ latitude: null, longitude: null });

const getCurrentLocation = () => {
  if (!navigator.geolocation) {
    alert("이 브라우저는 위치 서비스를 지원하지 않습니다.");
    return;
  }
  navigator.geolocation.getCurrentPosition(
    ({ coords }) => {
      userLocation.value = {
        latitude: coords.latitude,
        longitude: coords.longitude,
      };
    },
    (error) => console.error("위치 정보를 가져오는 데 실패했습니다.", error)
  );
};

const toRad = (value: number) => (value * Math.PI) / 180;
const calculateDistance = (
  lat1: number,
  lon1: number,
  lat2: number,
  lon2: number
) => {
  const R = 6371; // 지구의 반지름 (단위: km)
  const dLat = toRad(lat2 - lat1);
  const dLon = toRad(lon2 - lon1);
  const a =
    Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.cos(toRad(lat1)) *
      Math.cos(toRad(lat2)) *
      Math.sin(dLon / 2) *
      Math.sin(dLon / 2);
  const distanceInMeters =
    R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)) * 1000; // 거리 (단위: 미터)

  // 1000m 이상이면 km 단위로 변환
  return distanceInMeters >= 1000
    ? `${(distanceInMeters / 1000).toFixed(1)}km`
    : `${distanceInMeters.toFixed(0)}m`;
};

const searchAddress = async () => {
  if (!searchQuery.value.trim()) return;
  searchResults.value = []; // 이전 검색 결과 지우기

  try {
    const { data } = await axios.get(
      "https://dapi.kakao.com/v2/local/search/keyword.json",
      {
        params: { query: searchQuery.value.trim() },
        headers: {
          Authorization: `KakaoAK ${import.meta.env.VITE_KAKAO_REST_KEY}`,
        },
      }
    );

    searchResults.value = data.documents.map(
      ({ place_name, road_address_name, address_name, x, y }) => ({
        place_name,
        address_name: road_address_name || address_name,
        x,
        y,
        distance:
          userLocation.value.latitude && userLocation.value.longitude
            ? calculateDistance(
                userLocation.value.latitude,
                userLocation.value.longitude,
                parseFloat(y),
                parseFloat(x)
              )
            : "거리 계산 불가",
      })
    );
  } catch (error) {
    console.error("주소 검색 중 오류 발생:", error);
    if (error.response) {
      console.error("응답 데이터:", error.response.data);
      console.error("응답 상태 코드:", error.response.status);
      console.error("응답 헤더:", error.response.headers);
    }
    searchResults.value = [];
  }
};

// 디바운스 적용
const debouncedSearch = debounce(searchAddress, 500);

// 주소 선택 시 부모 컴포넌트로 주소 전달
const selectAddress = (selectedAddress) => {
  if (selectedAddress) {
    emit("close", selectedAddress); // 'close' 이벤트로 selectedAddress 전달
  } else {
    console.error("선택된 주소가 없습니다");
  }
};

const closeModal = () => {
  emit("close"); // 부모에게 'close' 이벤트 전달
};

onMounted(getCurrentLocation);

// 검색어가 변경될 때마다 디바운스된 검색 함수 호출
watch(searchQuery, debouncedSearch);
</script>

<template>
  <div class="modal-container" @click.self="closeModal">
    <div class="modal-wrapper">
      <!-- 모달 제목 -->
      <div class="modal-title">
        <TopBarBack title="주소 검색" @back="closeModal" />
      </div>

      <div class="search-container">
        <img :src="searchIconImg" alt="search icon" class="icon-search" />
        <input
          v-model="searchQuery"
          @keyup.enter="searchAddress"
          type="text"
          placeholder="찾고 싶은 주소를 입력해주세요."
          class="search-input"
          aria-label="주소 검색" />
      </div>

      <div class="modal-content">
        <AddressSearchResult
          :addresses="searchResults"
          @selectAddress="selectAddress" />
      </div>
      <BottomDefaultButton :label="'검색'" :onClick="searchAddress" />
    </div>
  </div>
</template>

<style scoped lang="scss">
.modal-wrapper {
  display: flex;
  flex-direction: column;

  height: 100%;

  .modal-content {
    display: flex;
    flex-direction: column;

    height: 100%;
    overflow-y: auto; /* 세로 스크롤을 추가 */
    @include custom-scrollbar-style; /* 스크롤바 스타일 적용 */
  }
}
.modal-container {
  @include custom-modal;
}

.search-input {
  @include custom-input-style;
  position: sticky;
  top: 0;
  z-index: 1;
  width: 100%;
  // @include custom-shadow-style;
}

.search-container {
  @include custom-margin-x;
  @include custom-margin-y;
  @include custom-padding-x;

  display: flex;
  flex-direction: row;
  align-items: center;
  gap: $padding-default;

  height: 48px;

  border-radius: $border-radius-default;

  background: white;
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);

  img {
    @include custom-icon-style;
  }

  input[type="text"] {
    padding: 0;
    margin: 0;
    border: none;
    height: 100%;
  }
}
</style>
