<script setup lang="ts">
import { ref, onMounted, defineEmits, watch } from "vue";
import { debounce } from "lodash"; // lodash의 debounce 사용
import { useRouter } from "vue-router";

import { axiosInstance } from "@/plugins/axiosPlugin";

import searchIconImg from "@/assets/icons/Magnifier.png";
import TopBarBack from "@/components/common/TopBarBack.vue";
import NonContent from "@/components/common/NonContent.vue";
import AddressSearchResult from "@/components/common/AddressSearchResult.vue";
import BottomDefaultButton from "@/components/common/BottomDefaultButton.vue";

const emit = defineEmits(["close", "selectAddress"]);

const router = useRouter();

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
    const response = await axiosInstance.get("/v1/houses/search", {
      params: { search: searchQuery.value.trim(), page: 0, size: 1 }, // 페이지 네이션 적용
    });
    console.log(response.data.data);
    console.log(response.data.data.count);
    console.log(response.data.data.list);
    if (response.data.data) {
      searchResults.value = response.data.data.list.map((house) => ({
        id: house.house_id,
        place_name: house.house_name,
        address_name: house.house_address,
        x: house.house_longitude,
        y: house.house_latitude,
        distance:
          userLocation.value.latitude && userLocation.value.longitude
            ? calculateDistance(
                userLocation.value.latitude,
                userLocation.value.longitude,
                parseFloat(house.house_latitude),
                parseFloat(house.house_longitude)
              )
            : "거리 계산 불가",
      }));
      console.log("AddressSearchApi.vue");
      console.log(searchResults.value);
    } else {
      throw new Error("결과를 불러오는데 실패했습니다.");
    }
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

      <div class="searchbar">
        <div class="input-icon-wrap">
          <img :src="searchIconImg" alt="search icon" class="icon-search" />
          <input
            v-model="searchQuery"
            @keyup.enter="searchAddress"
            type="text"
            placeholder="찾고 싶은 주소를 입력해주세요."
            class="search-input"
            aria-label="주소 검색" />
        </div>
      </div>

      <div class="modal-content">
        <AddressSearchResult
          :addresses="searchResults"
          @selectAddress="selectAddress" />
        <Transition name="fade">
          <NonContent
            v-if="searchResults.length === 0"
            :value="'검색 결과가 없습니다'">
          </NonContent>
        </Transition>
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

.non-content-sub-desc {
  @include custom-text($font-size: 12px);
  line-height: 100%;
  text-decoration-line: underline;
}
//scoped
.searchbar {
  @include custom-margin-y;
}
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

    input {
      @include custom-text($font-size: 14px, $font-color: $text-color-light);
      background-color: white;
      border: none;
      flex: 1;
      padding: 0;
    }
  }
}
</style>
