import axios from "axios";
import { defineStore } from "pinia";
import { Role } from "@/data/userRole";
import { axiosInstance } from "@/plugins/axiosPlugin";
import router from "@/router";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    user: JSON.parse(localStorage.getItem("user")) || null, // 새로고침 시 로컬스토리지에서 불러옴
    userLoaded: false, // ✅ 한 번만 실행되도록 관리
  }),
  actions: {
    setUser(userData) {
      this.user = userData;
      localStorage.setItem("user", JSON.stringify(userData)); // localStorage에 저장
      this.userLoaded = true; // ✅ 데이터가 로드됨을 표시
    },
    logout() {
      this.user = null;
      localStorage.removeItem("user"); // 로그아웃 시 삭제
      this.userLoaded = false; // ✅ 로그아웃 시 다시 요청 가능하도록 설정

      router.push({ name: "Login" });
    },
  },
  getters: {
    isLoggedIn: (state) => !!state.user, // ✅ 로그인 여부 쉽게 확인 가능
    getUser: (state) => {
      return state.user ? JSON.stringify(state.user) : "없음"; // user를 JSON 문자열로 변환하여 반환
    },
    getRole: (state) => (!state.user ? null : state.user.role),
    getEmail: (state) => (!state.user ? null : state.user.email),
    getGu: (state) => (!state.user ? null : state.user.gu),
    getNickname: (state) => (!state.user ? null : state.user.nickname),
  },
});
