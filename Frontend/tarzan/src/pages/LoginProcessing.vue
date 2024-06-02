<script>
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores/authStore.js";
import { mapState, mapActions } from "pinia";
export default {
  data() {
    return {
      accessToken: "",
      refreshToken: "",
    };
  },
  created() {
    const router = useRouter();

    // 현재 라우터에서 쿼리 파라미터 읽기
    this.accessToken = router.currentRoute.value.query.accessToken;
    this.refreshToken = router.currentRoute.value.query.refreshToken;

    if (!this.accessToken || !this.refreshToken) {
      // alert 후
      alert("로그인에 실패했습니다!");
      // 로그인 화면으로 다시 리다이렉트
      router.replace("/");
    }

    // console.log("토큰 정보");
    // console.log(`access-token: ${this.accessToken}`);
    // console.log(`refresh-token: ${this.refreshToken}`);

    this.setToken();

    alert("로그인에 성공해습니다!");
    router.replace("/");
  },
  computed: {
    ...mapState(useAuthStore, [
      "accessToken",
      "refreshToken",
      "getAccessToken",
      "getRefreshToken",
    ]),
  },
  methods: {
    ...mapActions(useAuthStore, ["setAccessToken", "setRefreshToken"]),
    setToken() {
      this.setAccessToken(this.accessToken);
      this.setRefreshToken(this.refreshToken);
    },
  },
};
</script>
<template>
  <div>
    <p>Processing login...</p>
  </div>
</template>
<style scoped></style>
