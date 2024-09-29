import { defineStore } from "pinia";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    accessToken: localStorage.getItem("accessToken") || null,
    refreshToken: localStorage.getItem("refreshToken") || null,
    role: localStorage.getItem("role") || null, // 사용자 역할
    gu: localStorage.getItem("gu") || null,
    nickname: localStorage.getItem("nickname") || null,
  }),
  actions: {
    setTokens(accessToken, refreshToken) {
      setAccessToken(accessToken);
      if (refreshToken) {
        setRefreshToken(refreshToken);
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
      setRole(role);
      setGu(gu);
      setNickname(nickname);
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
    loadTokensFromStorage() {
      // localStorage에서 JWT를 로드
      this.accessToken = localStorage.getItem("accessToken");
      this.refreshToken = localStorage.getItem("refreshToken");
    },
    clearAuth() {
      this.accessToken = null;
      this.refreshToken = null;
      this.role = null;
      this.gu = null;
      this.nickname = null;
      // localStorage에서 JWT를 삭제
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
      localStorage.removeItem("gu");
      localStorage.removeItem("nickname");
    },
  },
  getters: {
    isAuthenticated: (state) => !!state.accessToken,
  },
});
