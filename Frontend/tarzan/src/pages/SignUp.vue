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
            v-model="address"
            placeholder="주소를 입력해주세요"
          />
        </div>
      </div>
      <button type="submit">제출</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
// import userImage from "@/assets/signup_user_default_img.png";
// import userInputImage from "@/assets/signup_user_img_input.png";

import { Option } from "@/data/options";
// import DropDown from "@/components/common/DropDown.vue";
import CustomSelectBox from "@/components/common/CustomSelectBox.vue";

import { seoulSiGunGu } from "@/data/seoulsigungu.js";

// 반응형 변수 정의
// const userDefaultSrc = ref(userImage);
// const userInputSrc = ref(userInputImage);

// const userImgInputOptions: Option[] = [
// { idx: 1, name: "갤러리 보기", value: "galary" },
// { idx: 2, name: "사진 촬영", value: "picture" },
// ];

// 부모 컴포넌트의 배열 데이터 정의
const seoulDistrictOptions: Option[] = seoulSiGunGu;

// 부모 컴포넌트의 배열 데이터 정의
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
// refreshToken을 받아와서 설정, role 역시 변경

import { getCurrentInstance } from "vue";
const { proxy } = getCurrentInstance();
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
      user_latitude: 37.12345,
      user_longitude: 127.12345,
    };
    console.log(formData);
    // 백엔드 API에 데이터 전송 (여기서는 axios를 사용한다고 가정)
    const response = await proxy.$axios.post("/v1/user", formData);
    console.log(response.data); // 성공적으로 전송되었을 때의 응답 처리
    const refreshToken = response.data.refresh_token;
    const role = response.data.user_role;

    authStore.registerUser(refreshToken, role, gu, user_nickname);
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
</style>
