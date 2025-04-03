<template>
  <div id="main-container">
    <router-view />
  </div>
</template>

<style scoped>
div#main-container {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  /* background-color: aliceblue; */

  position: relative;
}
</style>

<script>
import { useAuthStore } from "@/stores/authStore"; // authStore import
import { axiosInstance } from "@/plugins/axiosPlugin";
import { Role } from "@/data/userRole"; // Role enum import

export default {
  name: "App", // 컴포넌트 이름을 추가
  data() {
    return {
      userStore: useAuthStore(),
    };
  },
  mounted() {
    // if (this.userStore.userLoaded) return; // ✅ 이미 불러왔다면 실행 X
    // this.fetchUserInfo();
  },
  methods: {
    async fetchUserInfo() {
      try {
        const res = await axiosInstance.get("/v1/user/simple");
        const userInfo = res.data.data;
        this.userStore.setUser(userInfo); // ✅ 사용자 정보 저장
      } catch (error) {
        console.error("사용자 정보를 불러오지 못했습니다:", error);
        this.userStore.clearUser(); // ✅ 로그인 실패 시 초기화
      }
    },
  },
};
</script>
