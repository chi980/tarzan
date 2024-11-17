<template>
  <div class="sub-container">
    <!-- <DropDown :options="userImgInputOptions">
      <template #default="{ toggleDropdown }">
        <div class="custom-container" @click="toggleDropdown">
          <div id="custom-buttom-img">
            <img :src="userDefaultSrc" alt="user-img" id="user-default-img" />
            <img
              :src="userInputSrc"
              alt="user-img-input-button"
              id="user-img-input"
            />
          </div>
        </div>
      </template>
    </DropDown> -->
    <form class="input-form" @submit.prevent="submitForm">
      <!-- 닉네임 입력 -->
      <div class="input-group">
        <h2 class="input-title">닉네임</h2>
        <div class="input-content-wrapper">
          <div class="input-content">
            <input
              type="text"
              placeholder="닉네임을 입력해주세요"
              v-model="nickname"
            />
            <div id="check-duplicate-btn"><div>중복확인</div></div>
          </div>
          <div class="input-description">
            <p>
              <i class="bi bi-info-circle"> </i>
              닉네임은 영문, 숫자로 이루어져야 합니다.
            </p>
            <p>
              <i class="bi bi-info-circle"> </i>
              닉네임은 6글자 이상 10글자 이하여야 합니다.
            </p>
          </div>
        </div>
      </div>

      <!-- 사는 곳 선택 -->
      <div class="input-group">
        <h2 class="input-title">사는 곳</h2>
        <div class="select-content">
          <CustomSelectBox
            :options="seoulDistrictOptions"
            @update:selected="handleSeoulDistrictSelectedIdx"
          />
        </div>
      </div>

      <!-- 반려동물 유무 선택 -->
      <div class="input-group">
        <h2 class="input-title">반려동물 유무</h2>
        <div class="select-content">
          <CustomSelectBox
            :options="petOptions"
            @update:selected="handlePetSelectedIdx"
          />
        </div>
      </div>

      <!-- 자차 유무 선택 -->
      <div class="input-group">
        <h2 class="input-title">자차 유무</h2>
        <div class="select-content">
          <CustomSelectBox
            :options="carOptions"
            @update:selected="handleCarSelectedIdx"
          />
        </div>
      </div>

      <!-- 학교/직장 주소 입력 -->
      <div class="input-group">
        <h2 class="input-title">학교/직장 주소</h2>
        <div class="input-content">
          <input
            type="text"
            placeholder="주소를 입력해주세요"
            v-model="searchQuery"
            @input="searchAddress"
          />
        </div>

        <!-- 검색 결과 표시 -->
        <ul v-if="searchResults.length" class="search-results">
          <li
            v-for="(result, index) in searchResults.slice(0, 10)"
            :key="index"
            @click="selectAddress(result)"
            class="search-result-item"
          >
            <span class="place-name">{{ result.place_name }}</span
            ><br />
            <span class="address-name">{{
              result.road_address_name || result.address_name
            }}</span>
          </li>
        </ul>
      </div>
      <button type="submit">제출</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
// import userImage from "@/assets/signup_user_default_img.png";
// import userInputImage from "@/assets/signup_user_img_input.png";

// 검색어와 결과를 저장할 상태 변수
const searchQuery = ref(""); // 사용자가 입력한 검색어
interface SearchResult {
  place_name: string;
  road_address_name?: string;
  address_name?: string;
  x: string; // 경도
  y: string; // 위도
}

const searchResults = ref<SearchResult[]>([]); // 검색 결과를 저장하는 배열

// 위도와 경도를 저장할 상태 변수
const selectedLocation = ref<{ latitude: string | null; longitude: string | null }>({
  latitude: null,
  longitude: null,
});

import { Option } from "@/data/options";
// import DropDown from "@/components/common/DropDown.vue";
import CustomSelectBox from "@/components/common/CustomSelectBox.vue";

import { seoulSiGunGu } from "@/data/seoulsigungu.js";
import axios from "axios";
const KAKAO_API_KEY = "7975d213af9c016408b981c2fe60f335"; // 여기에 본인의 카카오 REST API 키를 입력하세요.

// 주소 검색 함수
const searchAddress = async () => {
  if (!searchQuery.value) {
    searchResults.value = [];
    return;
  }

  try {
    // 키워드 검색 API 호출
    const response = await axios.get(
      "https://dapi.kakao.com/v2/local/search/keyword.json",
      {
        params: { query: searchQuery.value },
        headers: { Authorization: `KakaoAK ${KAKAO_API_KEY}` },
      }
    );
    searchResults.value = response.data.documents; // 검색 결과 저장
  } catch (error) {
    console.error("주소 검색 중 오류 발생: ", error);
    searchResults.value = [];
  }
};

// 주소 선택 함수
const selectAddress = (selectedPlace: SearchResult) => {
  searchQuery.value = `${selectedPlace.place_name} - ${
    selectedPlace.road_address_name || selectedPlace.address_name
  }`;
  searchResults.value = []; // 검색 결과 목록 초기화

  // 위도와 경도 저장
  selectedLocation.value.latitude = selectedPlace.y; // 위도
  selectedLocation.value.longitude = selectedPlace.x; // 경도
};

// 반응형 변수 정의
// const userDefaultSrc = ref(userImage);
// const userInputSrc = ref(userInputImage);

// const userImgInputOptions: Option[] = [
// { idx: 1, name: "갤러리 보기", value: "galary" },
// { idx: 2, name: "사진 촬영", value: "picture" },
// ];

const seoulDistrictOptions: Option[] = seoulSiGunGu;

const petOptions: Option[] = [
  { idx: 1, name: "반려동물 없음", value: false },
  { idx: 2, name: "반려동물 있음", value: true },
];
const carOptions: Option[] = [
  { idx: 1, name: "차 없음", value: false },
  { idx: 2, name: "차 있음", value: true },
];

const nickname = ref<string | null>(null);

const selectedSeoulSiGunGuIdx = ref<number | null>(null);
const handleSeoulDistrictSelectedIdx = (idx: number) => {
  selectedSeoulSiGunGuIdx.value = idx;
  console.log("Selected idx:", selectedSeoulSiGunGuIdx.value);
};

const selectedPetIdx = ref<number | null>(null);
const handlePetSelectedIdx = (idx: number) => {
  selectedPetIdx.value = idx;
  console.log("Selected idx:", selectedPetIdx.value);
};

const selectedCarIdx = ref<number | null>(null);
const handleCarSelectedIdx = (idx: number) => {
  selectedCarIdx.value = idx;
  console.log("Selected idx:", selectedCarIdx.value);
};

const address = ref<string | null>(null);

import { getCurrentInstance } from "vue";
const instance = getCurrentInstance();
import { useAuthStore } from "@/stores/authStore";
const authStore = useAuthStore();

const submitForm = async () => {
  try {
    const user_nickname = nickname.value == null ? "d" : nickname.value;
    const gu =
      seoulDistrictOptions[
        selectedSeoulSiGunGuIdx.value == null
          ? 0
          : selectedSeoulSiGunGuIdx.value
      ].value;
    const formData = {
      user_image_url: "https://example.com/image.jpg",
      user_nickname,
      user_gu: gu,
      user_have_animal:
        petOptions[selectedPetIdx.value == null ? 0 : selectedPetIdx.value]
          .value,
      user_have_car:
        carOptions[selectedCarIdx.value == null ? 0 : selectedCarIdx.value]
          .value,
      user_job_address: address.value == null ? "d" : address.value,
      user_latitude: 37.5665,
      user_longitude: 126.978,
    };
    console.log(formData);
    if (instance && instance.proxy) {
      const response = await (instance.proxy.$axios as any).post(
        "/v1/user",
        formData
      );
      // const response = await proxy.$axios.post("/v1/user", formData);
      console.log(response.data);
      const refreshToken = response.data.refresh_token;
      const role = response.data.user_role;
      authStore.registerUser(refreshToken, role, gu, user_nickname);
    } else {
      throw new Error("proxy 객체가 없습니다");
    }
  } catch (error) {
    console.error("Error submitting form:", error);
  }
};
</script>

<style lang="scss" scoped>
.custom-container {
  display: flex;
  flex-direction: column; /* 기본값 */
  align-items: center;
  justify-content: center;
  width: 100%;
}
#custom-buttom-img {
  @include custom-none-select-basic;
  height: 94px;
  width: 82px;
  margin: 0 auto;
  position: relative;
}
#user-default-img {
  width: 82px;
  height: 82px;
}
#user-img-input {
  width: 32px;
  height: 32px;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  bottom: 0;
}

#check-duplicate-btn {
  @include custom-none-select-basic;
  @include custom-text($font-size: 14px);
  width: 72px;
  height: 40px;

  display: flex;
  justify-content: center;
  align-items: center;

  margin-left: $margin-small;

  background-color: $primary-color-75;
  color: $primary-color-default;
  border-radius: 14px;
}
.input-form {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: $padding-big;
  .input-group {
    @include custom-padding-x();
    display: flex;
    flex-direction: column;
    gap: 12px;

    .input-title {
      @include custom-text-bold($font-size: 18px);
      text-align: left;
    }

    .input-content-wrapper {
      display: flex;
      flex-direction: column;
      gap: $padding-small;

      .input-content {
        margin: 0;
      }
    }

    .input-content {
      display: flex;
      align-items: center;
      gap: $padding-small;

      input[type="text"] {
        @include custom-input-style;
        flex-grow: 1; /* 남은 공간을 모두 차지하도록 설정 */
      }

      .select-content {
        flex-grow: 1;
      }
    }
    .input-description {
      @include custom-text($font-size: 12px, $font-color: $text-color-light);

      p {
        display: block;
        @include custom-padding-y(4px);
        text-align: left;
      }
    }
  }
}

// scoped
.sub-container {
  display: flex;
  flex-direction: column;
  gap: $padding-default;
}

.search-results {
  list-style: none;
  padding: 0;
  margin: 0;
  max-height: 200px; /* 최대 높이 지정 (5개의 항목) */
  overflow-y: auto; /* 스크롤 가능하게 설정 */
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #fff;
}

.search-result-item {
  padding: 10px 15px; /* 검색 결과 항목의 패딩 */
  cursor: pointer;
  border-bottom: 1px solid #f1f1f1; /* 항목 간의 구분선 */
}

.search-result-item:hover {
  background-color: #f8f8f8; /* 항목을 hover했을 때 배경색 변경 */
}

.place-name {
  font-size: 14px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.address-name {
  font-size: 12px;
  color: #9f9f9f;
  width: 100%;
  text-align: left;
  padding: 0 15px 5px; /* 좌우 여백 추가 */
}
</style>
