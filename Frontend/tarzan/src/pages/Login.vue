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
.sub-container {
  position: absolute;
  width: 100%;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.custom-button-group {
  @include custom-padding;
}
.button-item-image {
  height: 24px;
  width: 24px;
  margin-right: 4px;
}

.button-item-content {
  @include custom-text();
}

.custom-button {
  @include custom-button-style();
}
#logo {
  height: 254px;
  width: 254px;
  margin-bottom: calc($margin-default * 3) px;
}
#recommended-login-group {
  width: 100%;
  height: 90px;
  position: relative;
}
.custom-button-kakao {
  @include custom-button-style($kakao-color);
  position: absolute;
  bottom: 0;
}

#recommended-login-desc {
  width: 56px;
  position: absolute;
  top: 0;
  right: 0;
}

.custom-button-google {
  @include custom-button-style($google-color);
}
/**https://w-world.tistory.com/232 참고해보자 */
</style>
