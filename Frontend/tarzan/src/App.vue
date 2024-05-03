<template>
  <div id="main-container">
    <button @click="googleSnsLogin">Google 로그인</button>
    <button @click="kakaoSnsLogin">Kakao 로그인</button>

    <button @click="axiosClick">axios</button>
    <button @click="reissue">reisuue Token</button>
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
export default {
  methods: {
    googleSnsLogin() {
      location.href = "http://localhost:8080/oauth2/authorization/google";
    },
    kakaoSnsLogin() {
      location.href = "http://localhost:8080/oauth2/authorization/kakao";
    },

    axiosClick() {
      axios
        .get("/api", { withCredentials: true })
        .then((res) => {
          alert(JSON.stringify(res.data));
        })
        .catch((error) => alert(error));
    },

    reissue() {
      axios
        .post("/api/oauth")
        .then((res) => {
          alert(JSON.stringify(res.data));
        })
        .catch((error) => alert(error));
    },
  },
  data() {
    return {};
  },
};
</script>
