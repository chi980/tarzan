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
          <input type="text" placeholder="주소를 입력해주세요" />
        </div>
      </div>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import userImage from "@/assets/signup_user_default_img.png";
import userInputImage from "@/assets/signup_user_img_input.png";

import { Option } from "@/data/options";
import DropDown from "@/components/common/DropDown.vue";
import CustomSelectBox from "@/components/common/CustomSelectBox.vue";

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
  { idx: 1, name: "반려동물 없음", value: "DONTHAVE" },
  { idx: 2, name: "반려동물 있음", value: "HAVE" },
  // { idx: 3, name: "반려동물 고민 중", value: "CONSIDERING" },
  // { idx: 4, name: "기타", value: "ETC" },
];
const carOptions: Option[] = [
  { idx: 1, name: "차 없음", value: "DONTHAVE" },
  { idx: 2, name: "차 있음", value: "HAVE" },
];
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
.input-group {
  @include custom-padding-x();
  margin-top: $margin-default;
}
.input-title {
  @include custom-text-bold($font-size: 18px);
  text-align: left;
}
.input-content {
  @include custom-margin-input;
  display: flex;
  align-items: center;
  width: 100%;
}
.input-content input[type="text"] {
  @include custom-input-style;
  flex: 1; /* 남은 공간을 모두 차지하도록 설정 */
}

.input-description {
  @include custom-text($font-size: 12px, $font-color: $text-color-light);
}

.input-description p {
  display: block;
  @include custom-padding-y(4px);
  text-align: left;
}

.select-content {
  @include custom-margin-input;
}
</style>
