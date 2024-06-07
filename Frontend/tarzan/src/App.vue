<template>
  <div id="main-container">
    <!-- <button @click="googleSnsLogin">Google 로그인</button>
    <button @click="kakaoSnsLogin">Kakao 로그인</button>

    <button @click="axiosClick">axios</button>
    <button @click="reissue">reisuue Token</button>

    <button @click="checkHeader">header확인</button> -->

    <router-view />
    <!-- 추가 -->
  </div>
</template>

<style scoped>
div#main-container {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  background-color: aliceblue;
}
</style>
<script>
import axios from "axios";
import { useAuthStore } from "@/stores/authStore.js";
export default {
  methods: {
    googleSnsLogin() {
      location.href =
        "http://localhost:8080/oauth2/authorization/google?redirect_uri=http://localhost:5173/login";
      console.log("Request headers:", response.headers);
      accessToken = response.headers;
    },
    kakaoSnsLogin() {
      location.href = "http://localhost:8080/oauth2/authorization/kakao";
      console.log("Request headers:", response.headers);
    },

    axiosClick() {
      console.log("Request headers:", response.headers);
      axios
        .get("/api", { withCredentials: true })
        .then((res) => {
          alert(JSON.stringify(res.data));
        })
        .catch((error) => alert(error));
    },

    reissue() {
      alert("재 발행!");
      alert(useAuthStore().getRefreshToken);
      axios
        .post("/api/oauth/token/refresh", {
          refreshTokenString: useAuthStore().getRefreshToken,
        })
        .then((res) => {
          alert("토큰 재발급 성공");
          console.log(res.data);
          // useAuthStore.setToken
          // next();
        })
        .catch((error) => {
          alert(error);
        });
    },
    checkHeader() {
      // 현재 페이지의 모든 헤더를 출력
      console.log("Headers:", window.location.href, document.headers);

      // 특정 헤더의 값을 확인 (예: Authorization)
      console.log(
        "Authorization Header:",
        window.location.href,
        document.headers.get("Authorization")
      );

      // 모든 쿠키 확인
      console.log("All Cookies:", document.cookie);

      // 특정 쿠키 이름으로 쿠키 값 확인 (예: Authorization)
      function getCookie(name) {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop().split(";").shift();
      }

      console.log("Authorization Cookie:", getCookie("Authorization"));
    },
  },
  data() {
    return {};
  },
};
</script>
