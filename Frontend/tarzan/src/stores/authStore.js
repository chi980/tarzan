import { defineStore } from "pinia";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    accessToken: localStorage.getItem("accessToken") || "",
    refreshToken: localStorage.getItem("refreshToken") || "",
    role: localStorage.getItem("role") || "guest", // 기본값 추가
    gu: localStorage.getItem("gu") || "",
    nickname: localStorage.getItem("nickname") || "익명",
  }),
  actions: {
    setTokens(accessToken, refreshToken) {
      this.setAccessToken(accessToken); // this 사용
      if (refreshToken) {
        this.setRefreshToken(refreshToken); // this 사용
      }
    },
    setAccessToken(accessToken) {
      this.accessToken = accessToken;
      localStorage.setItem("accessToken", accessToken);
    },
    setRefreshToken(refreshToken) {
      this.refreshToken = refreshToken;
      localStorage.setItem("refreshToken", refreshToken);
    },
    setUserInfo(role, gu, nickname) {
      this.setRole(role);
      this.setGu(gu);
      this.setNickname(nickname);
    },
    setRole(role) {
      this.role = role;
      localStorage.setItem("role", role);
    },
    setGu(gu) {
      this.gu = gu;
      localStorage.setItem("gu", gu);
    },
    setNickname(nickname) {
      this.nickname = nickname;
      localStorage.setItem("nickname", nickname);
    },
    registerUser(refreshToken, role, gu, nickname) {
      this.setRefreshToken(refreshToken);
      this.setRole(role);
      this.setGu(gu);
      this.setNickname(nickname);
    },
    clearAuth() {
      this.accessToken = "";
      this.refreshToken = "";
      this.role = "guest"; // 기본값으로 초기화
      this.gu = "";
      this.nickname = "익명"; // 기본값으로 초기화
      // localStorage에서 JWT를 삭제
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
      localStorage.removeItem("role");
      localStorage.removeItem("gu");
      localStorage.removeItem("nickname");
    },
  },
  getters: {
    isAuthenticated: (state) => !!state.accessToken && state.accessToken !== "",
  },
});
