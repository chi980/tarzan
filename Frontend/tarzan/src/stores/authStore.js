import axios from "axios";
import { defineStore } from "pinia";
import { Role } from "@/data/userRole";
import { axiosInstance } from "@/plugins/axiosPlugin";
import router from "@/router";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    accessToken: localStorage.getItem("accessToken") || null,
    user: JSON.parse(localStorage.getItem("user")) || null,
  }),
  actions: {
    setAccessToken(token) {
      this.accessToken = token;
      localStorage.setItem("accessToken", token); // 로컬 스토리지에 저장
    },
    setUser(user) {
      this.user = user;
      localStorage.setItem("user", JSON.stringify(user)); // 로컬 스토리지에 저장
    },
    login(token, user) {
      this.setAccessToken(token);
      this.setUser(user);
    },
    logout() {
      this.accessToken = null;
      localStorage.removeItem("accessToken"); // 로컬 스토리지에서 삭제
      this.user = null;
      localStorage.removeItem("user"); // 로컬 스토리지에서 삭제

      router.push({ name: "Login" });
    },
  },
  getters: {
    getAccessToken: (state) => state.accessToken,
    getUser: (state) => {
      return state.user ? JSON.stringify(state.user) : "없음"; // user를 JSON 문자열로 변환하여 반환
    },
    getEmail: (state) => (!state.user ? null : state.user.email),
    getGu: (state) => {
      return state.user.gu;
    },
  },
});
