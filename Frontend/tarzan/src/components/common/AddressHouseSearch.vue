<script setup lang="ts">
import { ref, onMounted, defineEmits, watch } from "vue";
import axios from "axios";
import { debounce } from "lodash";
import { useRouter } from "vue-router";
import searchIconImg from "@/assets/icons/Magnifier.png";
import TopBarBack from "@/components/common/TopBarBack.vue";
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
        return {
        mainAddress,
        subAddress,
        buildingName: "", // 주소 검색이므로 건물명 없음
        x,
        y,
        distance: "", // 위치 기반 거리 없음
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

onMounted(getCurrentLocation);

watch(searchQuery, debouncedSearch);
</script>

<template>
  <div class="modal-container" @click.self="closeModal">
    <div class="modal-wrapper">
      <TopBarBack title="주소 검색" @back="closeModal" />

      <div class="search-container">
        <input
          v-model="searchQuery"
          @keyup.enter="searchAddress"
          type="text"
          placeholder="주소를 입력해 주세요"
          class="search-input"
        />
      </div>

      <div class="modal-content">
        <AddressHouseSearchResult
          :addresses="searchResults"
          @selectAddress="selectAddress"
        />
      </div>

      <div class="button-wrapper">
        <button class="button-default" @click="searchAddress">검색</button>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
// @import "@/styles/mixins.scss"; // 스타일 믹스인 사용하는 경우

.modal-container {
  @include custom-modal;
}

.modal-wrapper {
  display: flex;
  flex-direction: column;
  height: 100%;

  .modal-content {
    height: 100%;
    overflow-y: auto;
    @include custom-scrollbar-style;
  }
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

.button-wrapper {
  @include custom-padding-y;
  display: flex;
  justify-content: center;

  .button-default {
    @include custom-button-style(
      $bg-color: $secondary-color-default,
      $font-color: white
    );
    width: 90%;
  }
}
</style>
