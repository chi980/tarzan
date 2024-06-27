<template>
  <div class="sub-container">
    <div id="custom-buttom-img">
      <img :src="userDefaultSrc" alt="user-img" id="user-default-img" />
      <img
        :src="userInputSrc"
        alt="user-img-input-button"
        id="user-img-input"
      />
    </div>
    <form>
      <!-- 컴포넌트 화 하기 -->
      <div class="input-group">
        <div class="input-content">
          <h2 class="input-title">닉네임</h2>
          <input type="text" />
          <div id="check-duplicate-btn">중복 확인</div>
        </div>
        <br />
        <div class="input-content">
          <i class="bi bi-info-circle"
            >닉네임은 영문, 숫자로 이루어져야 합니다.
          </i>
        </div>
        <br />
        <div class="input-content">
          <i class="bi bi-info-circle"
            >닉네임은 6글자 이상 10글자 이하여야 합니다.
          </i>
        </div>
      </div>
      <!-- component 화 하기 // 들어가야 할것: title, options-->
      <div class="input-group">
        <div class="input-content">
          <h2 class="input-title">사는 곳</h2>
          <!--  select 박스 -->

          <SelectBox :options="seoulDistrictOptions" />
        </div>
      </div>

      <!-- component 화 하기 // 들어가야 할 것: title, 간단한 설명 -->
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import userImage from "@/assets/signup_user_default_img.png";
import userInputImage from "@/assets/signup_user_img_input.png";

import { Option } from "@/data/options";
import SelectBox from "@/components/common/SelectBox.vue";

// 반응형 변수 정의
const userDefaultSrc = ref(userImage);
const userInputSrc = ref(userInputImage);

// 부모 컴포넌트의 배열 데이터 정의
const seoulDistrictOptions: Option[] = [
  { idx: 1, name: "종로구", value: "JONGNO" },
  { idx: 2, name: "중구", value: "JUNG" },
  { idx: 3, name: "용산구", value: "YONGSAN" },
  { idx: 4, name: "성동구", value: "SEONGDONG" },
  { idx: 5, name: "광진구", value: "GWANGJIN" },
  { idx: 6, name: "동대문구", value: "DONGDAEMUN" },
  { idx: 7, name: "중랑구", value: "JUNGNANG" },
  { idx: 8, name: "성북구", value: "SEONGBUK" },
  { idx: 9, name: "강북구", value: "GANGBUK" },
  { idx: 10, name: "도봉구", value: "DOBONG" },
  { idx: 11, name: "노원구", value: "NOWON" },
  { idx: 12, name: "은평구", value: "EUNPYEONG" },
  { idx: 13, name: "서대문구", value: "SEODAEMUN" },
  { idx: 14, name: "마포구", value: "MAPO" },
  { idx: 15, name: "양천구", value: "YANGCHEON" },
  { idx: 16, name: "강서구", value: "GANGSEO" },
  { idx: 17, name: "구로구", value: "GURO" },
  { idx: 18, name: "금천구", value: "GEUMCHEON" },
  { idx: 19, name: "영등포구", value: "YEONGDEUNGPO" },
  { idx: 20, name: "동작구", value: "DONGJAK" },
  { idx: 21, name: "관악구", value: "GWANAK" },
  { idx: 22, name: "서초구", value: "SEOCHO" },
  { idx: 23, name: "강남구", value: "GANGNAM" },
  { idx: 24, name: "송파구", value: "SONGPA" },
  { idx: 25, name: "강동구", value: "GANGDONG" },
];
</script>

<style lang="scss">
#custom-buttom-img {
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
  display: inline;
  width: 72px;
  height: 40px;
  background-color: $primary-color-75;
}
.input-group {
  @include custom-padding-x;
}
.input-title {
  @include custom-text($font-size: 18px);
  text-align: left;
}
.input-content {
}
</style>
