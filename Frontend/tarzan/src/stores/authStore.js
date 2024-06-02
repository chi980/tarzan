import { defineStore } from "pinia";
/**
 * @ 1st parameter: name
 * @ 2nd parameter: option related with store
 */
export const useAuthStore = defineStore("authStore", {
  state: () => ({
    accessToken: null,
    refreshToken: null,
  }),
  getters: {
    isAuthenticated: (state) => !!state.accessToken,
    isRefreshToken: (state) => !!state.refreshToken,
    getAccessToken: (state) => {
      return state.accessToken;
    },
    getRefreshToken: (state) => {
      return state.refreshToken;
    },
  },
  // 항상 일반 함수 사용!
  actions: {
    setAccessToken(newAccessToken) {
      console.log(`newAccessToken: ${newAccessToken}`);
      this.accessToken = newAccessToken;
    },
    setRefreshToken(newRefreshToken) {
      console.log(`newRefreshToken: ${newRefreshToken}`);
      this.refreshToken = newRefreshToken;
    },
    setToken(newAccessToken, newRefreshToken) {
      console.log(`token 저장 완료`);
      this.accessToken = newAccessToken;
      this.refreshToken = newRefreshToken;
    },
  },
});
