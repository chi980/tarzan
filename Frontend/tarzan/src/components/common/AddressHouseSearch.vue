<script setup lang="ts">
import { ref, onMounted, defineEmits, watch } from "vue";
import axios from "axios";
import { debounce } from "lodash";
import { useRouter } from "vue-router";
import searchIconImg from "@/assets/icons/Magnifier.png";
import TopBarBack from "@/components/common/TopBarBack.vue";
import BottomDefaultButton from "@/components/common/BottomDefaultButton.vue";
import AddressHouseSearchResult from "./AddressHouseSearchResult.vue"; // 재사용
import AddressSearchResult from "./AddressSearchResult.vue"; // ✅ 수정된 부분

const emit = defineEmits(["close", "selectAddress"]);

const searchQuery = ref("");
const searchResults = ref([]);
const userLocation = ref({ latitude: null, longitude: null });
const router = useRouter();

const getCurrentLocation = () => {
  if (!navigator.geolocation) {
    alert("이 브라우저는 위치 서비스를 지원하지 않습니다.");
    return;
  }
  navigator.geolocation.getCurrentPosition(
    ({ coords }) => {
      console.log("📍 현재 위치:", coords); // ✅ 콘솔에 찍어보자
      userLocation.value = {
        latitude: coords.latitude,
        longitude: coords.longitude,
      };
    },
    (error) => {
      console.error("위치 정보를 가져오는 데 실패했습니다.", error);
    }
  );
};

onMounted(() => {
  getCurrentLocation();
});

// 위치 받아온 후 자동 재검색
watch(userLocation, (newVal) => {
  if (
    newVal.latitude !== null &&
    newVal.longitude !== null &&
    searchQuery.value.trim()
  ) {
    searchAddress(); // 위치 정보 준비되면 검색 다시 수행
  }
});

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
      "https://dapi.kakao.com/v2/local/search/address.json",
      {
        params: { query: searchQuery.value.trim() },
        headers: {
          Authorization: `KakaoAK ${import.meta.env.VITE_KAKAO_REST_KEY}`,
        },
      }
    );

    searchResults.value = data.documents.map(
      ({ address, road_address, x, y }) => {
        const mainAddress =
          road_address?.address_name || address?.address_name || "주소 없음";
        const subAddress = address?.address_name || "";
        const type = road_address ? "도로명" : "지번";

        // ✅ 거리 계산 여부 체크
        const distance =
          userLocation.value.latitude !== null &&
          userLocation.value.longitude !== null
            ? calculateDistance(
                userLocation.value.latitude,
                userLocation.value.longitude,
                parseFloat(y),
                parseFloat(x)
              )
            : "거리 계산 불가";

        return {
          mainAddress,
          subAddress,
          buildingName: "", // 주소 검색 결과에 건물명 없음
          x,
          y,
          distance,
          type,
        };
      }
    );
  } catch (error) {
    console.error("주소 검색 중 오류:", error);
    searchResults.value = [];
  }
};

const debouncedSearch = debounce(searchAddress, 500);

const selectAddress = (selectedAddress) => {
  if (selectedAddress) {
    router.push({
      name: "AddHousePage",
      query: {
        house_address: selectedAddress.mainAddress,
        house_latitude: selectedAddress.x,
        house_longitude: selectedAddress.y,
        type: selectedAddress.type,
      },
    });
  }
};

const closeModal = () => emit("close");

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
        <AddressHouseSearchResult
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

.search-container {
  @include custom-margin-x;
  @include custom-margin-y;
  @include custom-padding-x;
  display: flex;
  align-items: center;
  background: white;
  border-radius: $border-radius-default;
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);

  input {
    width: 100%;
    height: 48px;
    border: none;
    font-size: 16px;
    padding: 0 10px;
  }
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
