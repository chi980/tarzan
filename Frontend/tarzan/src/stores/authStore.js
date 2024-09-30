import { defineStore } from "pinia";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    accessToken: localStorage.getItem("accessToken") || null,
    refreshToken: localStorage.getItem("refreshToken") || null,
    role: null, // 사용자 역할
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
    loadTokensFromStorage() {
      // localStorage에서 JWT를 로드
      this.accessToken = localStorage.getItem("accessToken");
      this.refreshToken = localStorage.getItem("refreshToken");
    },
    clearAuth() {
      this.accessToken = null;
      this.refreshToken = null;
      this.role = null;
      // localStorage에서 JWT를 삭제
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
    },
  },
  getters: {
    isAuthenticated: (state) => !!state.accessToken,
  },
});
