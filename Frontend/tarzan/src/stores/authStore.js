import { defineStore } from "pinia";
import { Role } from "@/data/userRole";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    accessToken: localStorage.getItem("accessToken") || "",
    refreshToken: localStorage.getItem("refreshToken") || "",
    role: localStorage.getItem("role") || Role.ANONYMOUS, // 기본값 추가
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
      localStorage.setItem("role", role);
      localStorage.setItem("gu", gu);
      localStorage.setItem("nickname", nickname);
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
      this.role = Role.ANONYMOUS;
      this.gu = "";
      this.nickname = "익명";
      // localStorage에서 JWT를 삭제
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
      localStorage.removeItem("role");
      localStorage.removeItem("gu");
      localStorage.removeItem("nickname");
    },
  },
  getters: {
    isAuthenticated: (state) => {
      return !!state.accessToken && state.role != Role.ANONYMOUS;
    },
    isGuest: (state) => state.role == Role.GUEST,
    isAnonymous: (state) => state.role == Role.ANONYMOUS,
    print: (state) =>
      `accessToken: ${state.accessToken} \n refreshToken: ${state.refreshToken} \n role: ${state.role} \n gu: ${state.gu} \n nickname: ${state.nickname}`,
  },
});
