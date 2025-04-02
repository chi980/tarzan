<template>
  <div class="sub-container">
    <p id="logo_desc_text">정글같은 서울 도심 속 터전 찾기</p>
    <p id="logo_text">TARZAN</p>

    <img :src="logoImage" alt="Logo" id="logo" />

    <div class="custom-button-group">
      <div id="recommended-login-group" class="custom-button-item">
        <div class="custom-button-kakao" @click="clickKakaoBtn">
          <img :src="kakaoImage" alt="kakaoLogo" class="button-item-image" />
          <p>카카오로 계속하기</p>
        </div>
        <img
          :src="loginDescImage"
          alt="loginDesc"
          id="recommended-login-desc" />
      </div>
      <div class="custom-button-item">
        <div class="custom-button-google" @click="clickGoogleBtn">
          <img :src="googleImage" alt="googleLogo" class="button-item-image" />
          <p class="button-item-content">구글로 계속하기</p>
        </div>
      </div>

      <div class="custom-button-item">
        <div class="custom-button-naver" @click="clickNaverBtn">
          <img :src="naverImage" alt="naverLogo" class="button-item-image" />
          <p class="button-item-content">네이버로 계속하기</p>
        </div>
        "
      </div>
    </div>

    <!-- <button @click="clickBtn">상태 확인</button>
    <button @click="clickLogOutBtn">로그아웃</button>
    <button @click="checkBack">확인하기</button> -->
  </div>
</template>

<script setup>
import logoImage from "@/assets/etc/map.png";
import kakaoImage from "@/assets/icons/kakao_login_logo.png";
import googleImage from "@/assets/icons/google_login_logo.png";
import naverImage from "@/assets/icons/naver_login_logo.png";
import loginDescImage from "@/assets/login_desc.png";

const clickKakaoBtn = () => {
  const kakaoLoginUrl = import.meta.env.VITE_API_KAKAO_URL;
  location.href = kakaoLoginUrl;
};
const clickGoogleBtn = () => {
  const googleLoginUrl = import.meta.env.VITE_API_GOOGLE_URL;
  location.href = googleLoginUrl;
};

// const clickNaverBtn = () => {
//   const naverLoginUrl = import.meta.env.VITE_API_NAVER_URL;

import { useAuthStore } from "@/stores/authStore";
const clickBtn = () => {
  const authStore = useAuthStore();
  console.log(authStore.getUser);
};

const clickLogOutBtn = () => {};
import { axiosInstance } from "@/plugins/axiosPlugin";
const checkBack = async () => {
  alert("확인");

  try {
    const response = await axiosInstance.get("/check");

    console.log("API 호출 성공:", response);
  } catch (error) {
    // alert("API 호출 실패: " + error);
    console.log(error);
  }
};
</script>

<style lang="scss">
@keyframes shake {
  0% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-5px); /* 중간에서 위로 5px 이동 */
  }
  100% {
    transform: translateY(0);
  }
}
// logo
#logo_desc_text {
  font-family: "Ownglyph_ParkDaHyun", sans-serif;
  font-size: 13px;
  color: #848484;
  margin-bottom: 10px;
}

#logo_text {
  font-family: "Ownglyph_ParkDaHyun", sans-serif;
  font-size: 48px;
  color: #000000;
  margin-bottom: 30px;
}

#logo {
  @include custom-none-select-basic;
  height: 180px;
  width: 180px;
  margin-bottom: 40px;
}

.custom-button-group {
  display: flex;
  padding: 0;
  width: 100%;
  flex-direction: column; /* 세로 방향으로 요소 배치 */
}

// 추천하는 sns login
#recommended-login-group {
  height: 100px;
  position: relative;
  display: flex;
}

#recommended-login-desc {
  width: 56px;
  position: absolute;
  top: 0;
  right: 0;
}

#recommended-login-desc {
  animation: shake 0.8s infinite alternate ease-in-out;
}

.custom-button-item {
  @include custom-margin-x;
  display: block;
}

.custom-button-kakao {
  @include custom-button-style($bg-color: $kakao-color, $height: 56px);
  margin-top: auto;
}

// 그 외 sns login
.custom-button-google {
  @include custom-button-style($bg-color: $google-color, $height: 56px);
}

.custom-button-naver {
  color: white;
  @include custom-button-style(
    $bg-color: $naver-color,
    $height: 56px,
    $font-color: white
  );
}
/**https://w-world.tistory.com/232 참고해보자 */
</style>
