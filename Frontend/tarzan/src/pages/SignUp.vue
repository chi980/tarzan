<template>
  <div class="sub-container">
    <DropDown :options="userImgInputOptions">
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
    </DropDown>
    <form class="input-form">
      <!-- 컴포넌트 화 하기 -->
      <div class="input-group">
        <h2 class="input-title">닉네임</h2>
        <div class="input-content-wrapper">
          <div class="input-content">
            <input type="text" placeholder="닉네임을 입력해주세요" />
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
      <!-- component 화 하기 // 들어가야 할것: title, options-->
      <div class="input-group">
        <h2 class="input-title">사는 곳</h2>
        <!--  select 박스 -->
        <div class="select-content">
          <CustomSelectBox :options="seoulDistrictOptions" />
        </div>
      </div>
      <div class="input-group">
        <h2 class="input-title">반려동물 유무</h2>
        <!--  select 박스 -->
        <div class="select-content">
          <CustomSelectBox :options="petOptions" />
        </div>
      </div>
      <div class="input-group">
        <h2 class="input-title">자차 유무</h2>
        <!--  select 박스 -->
        <div class="select-content">
          <CustomSelectBox :options="carOptions" />
        </div>
      </div>

      <div class="input-group">
        <h2 class="input-title">학교/직장 주소</h2>
        <div class="input-content">
          <input
            type="text"
            placeholder="주소 또는 건물 이름을 입력해주세요"
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
            <span class="place-name">{{ result.place_name }}</span><br />
            <span class="address-name">{{ result.road_address_name || result.address_name }}</span>
          </li>
        </ul>
      </div>
    </form>
  </div>
</template>


<script setup lang="ts">
import { ref } from "vue";
import axios from "axios";

// 검색어와 결과를 저장할 상태 변수
const searchQuery = ref("");  // 사용자가 입력한 검색어
const searchResults = ref([]); // 검색 결과를 저장하는 배열

// 위도와 경도를 저장할 상태 변수
const selectedLocation = ref({ latitude: null, longitude: null });

import userImage from "@/assets/signup_user_default_img.png";
import userInputImage from "@/assets/signup_user_img_input.png";

import { Option } from "@/data/options";
import DropDown from "@/components/common/DropDown.vue";
import CustomSelectBox from "@/components/common/CustomSelectBox.vue";

// 카카오 API 키 설정
const KAKAO_API_KEY = "7975d213af9c016408b981c2fe60f335"; // 여기에 본인의 카카오 REST API 키를 입력하세요.

// 주소 검색 함수
const searchAddress = async () => {
  if (!searchQuery.value) {
    searchResults.value = [];
    return;
  }

  try {
    // 키워드 검색 API 호출
    const response = await axios.get("https://dapi.kakao.com/v2/local/search/keyword.json", {
      params: { query: searchQuery.value },
      headers: { Authorization: `KakaoAK ${KAKAO_API_KEY}` },
    });
    searchResults.value = response.data.documents;  // 검색 결과 저장
  } catch (error) {
    console.error("주소 검색 중 오류 발생: ", error);
    searchResults.value = [];
  }
};

// 주소 선택 함수
const selectAddress = (selectedPlace) => {
  searchQuery.value = `${selectedPlace.place_name} - ${selectedPlace.road_address_name || selectedPlace.address_name}`;
  searchResults.value = [];  // 검색 결과 목록 초기화

    // 위도와 경도 저장
  selectedLocation.value.latitude = selectedPlace.y;  // 위도
  selectedLocation.value.longitude = selectedPlace.x; // 경도
};

// 반응형 변수 정의
const userDefaultSrc = ref(userImage);
const userInputSrc = ref(userInputImage);

const userImgInputOptions: Option[] = [
  { idx: 1, name: "갤러리 보기", value: "galary" },
  { idx: 2, name: "사진 촬영", value: "picture" },
];

// 부모 컴포넌트의 배열 데이터 정의
const seoulDistrictOptions: Option[] = [
  { idx: 1, name: "서울시 종로구", value: "JONGNO" },
  { idx: 2, name: "서울시 중구", value: "JUNG" },
  { idx: 3, name: "서울시 용산구", value: "YONGSAN" },
  { idx: 4, name: "서울시 성동구", value: "SEONGDONG" },
  { idx: 5, name: "서울시 광진구", value: "GWANGJIN" },
  { idx: 6, name: "서울시 동대문구", value: "DONGDAEMUN" },
  { idx: 7, name: "서울시 중랑구", value: "JUNGNANG" },
  { idx: 8, name: "서울시 성북구", value: "SEONGBUK" },
  { idx: 9, name: "서울시 강북구", value: "GANGBUK" },
  { idx: 10, name: "서울시 도봉구", value: "DOBONG" },
  { idx: 11, name: "서울시 노원구", value: "NOWON" },
  { idx: 12, name: "서울시 은평구", value: "EUNPYEONG" },
  { idx: 13, name: "서울시 서대문구", value: "SEODAEMUN" },
  { idx: 14, name: "서울시 마포구", value: "MAPO" },
  { idx: 15, name: "서울시 양천구", value: "YANGCHEON" },
  { idx: 16, name: "서울시 강서구", value: "GANGSEO" },
  { idx: 17, name: "서울시 구로구", value: "GURO" },
  { idx: 18, name: "서울시 금천구", value: "GEUMCHEON" },
  { idx: 19, name: "서울시 영등포구", value: "YEONGDEUNGPO" },
  { idx: 20, name: "서울시 동작구", value: "DONGJAK" },
  { idx: 21, name: "서울시 관악구", value: "GWANAK" },
  { idx: 22, name: "서울시 서초구", value: "SEOCHO" },
  { idx: 23, name: "서울시 강남구", value: "GANGNAM" },
  { idx: 24, name: "서울시 송파구", value: "SONGPA" },
  { idx: 25, name: "서울시 강동구", value: "GANGDONG" },
];

// 부모 컴포넌트의 배열 데이터 정의
const petOptions: Option[] = [
  { idx: 1, name: "반려동물 없음", value: false },
  { idx: 2, name: "반려동물 있음", value: true },
  // { idx: 3, name: "반려동물 고민 중", value: "CONSIDERING" },
  // { idx: 4, name: "기타", value: "ETC" },
];
const carOptions: Option[] = [
  { idx: 1, name: "차 없음", value: false },
  { idx: 2, name: "차 있음", value: true },
];

// refreshToken을 받아와서 설정, role 역시 변경
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
  overflow-y: auto;  /* 스크롤 가능하게 설정 */
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
  color: #9F9F9F;
  width: 100%;
  text-align: left;
  padding: 0 15px 5px; /* 좌우 여백 추가 */
}
</style>
