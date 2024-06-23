<template>
  <div class="sub-container">
    <img :src="logoSrc" alt="Logo" id="logo" />
    <div class="custom-button-group">
      <div id="recommended-login-group">
        <div class="custom-button-kakao">
          <img :src="kakaoLogoSrc" alt="kakaoLogo" class="button-item-image" />
          <p class="button-item-content">카카오로 계속하기</p>
        </div>
        <div>
          <img
            :src="LoginDescSrc"
            alt="loginDesc"
            id="recommended-login-desc"
          />
        </div>
      </div>
      <div class="custom-button-google">
        <img :src="googleLogoSrc" alt="googleLogo" class="button-item-image" />
        <p class="button-item-content">구글로 계속하기</p>
      </div>
    </div>
  </div>
</template>

<script>
import logoImage from "@/assets/tarzan_logo.png";
import kakaoImage from "@/assets/kakao_login_logo.png";
import googleImage from "@/assets/google_login_logo.png";
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
// logo
#logo {
  height: 254px;
  width: 254px;
  margin-bottom: calc($margin-default * 3) px;
}

// 추천하는 sns login
#recommended-login-group {
  width: 100%;
  height: 90px;
  position: relative;
}

#recommended-login-desc {
  width: 56px;
  position: absolute;
  top: 0;
  right: 0;
}

.custom-button-kakao {
  @include custom-button-style($kakao-color);
  position: absolute;
  bottom: 0;
}

// 그 외 sns login
.custom-button-google {
  @include custom-button-style($google-color);
}

/**https://w-world.tistory.com/232 참고해보자 */
</style>
