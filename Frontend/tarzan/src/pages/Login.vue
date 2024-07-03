<template>
  <div class="sub-container">
    <img :src="logoSrc" alt="Logo" id="logo" />

    <div class="custom-button-group">
      <div id="recommended-login-group" class="custom-button-item">
        <div class="custom-button-kakao">
          <img :src="kakaoLogoSrc" alt="kakaoLogo" class="button-item-image" />
          <p>카카오로 계속하기</p>
        </div>
        <img :src="LoginDescSrc" alt="loginDesc" id="recommended-login-desc" />
      </div>
      <div class="custom-button-item">
        <div class="custom-button-google">
          <img
            :src="googleLogoSrc"
            alt="googleLogo"
            class="button-item-image"
          />
          <p class="button-item-content">구글로 계속하기</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import logoImage from "@/assets/tarzan_logo.png";
import kakaoImage from "@/assets/icons/kakao_login_logo.png";
import googleImage from "@/assets/icons/google_login_logo.png";
import loginDescImage from "@/assets/login_desc.png";

export default {
  data() {
    return {
      logoSrc: logoImage,
      kakaoLogoSrc: kakaoImage,
      googleLogoSrc: googleImage,
      LoginDescSrc: loginDescImage,
    };
  },
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
#logo {
  @include custom-none-select-basic;
  height: 254px;
  width: 254px;
  margin-bottom: calc($margin-default * 3) px;
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

#recommended-login-group:hover #recommended-login-desc {
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

/**https://w-world.tistory.com/232 참고해보자 */
</style>
