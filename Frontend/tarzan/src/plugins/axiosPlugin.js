// src/plugins/axiosPlugin.js

// import axiosInstance from "@/axios/axiosInstance";

// export default {
//   install: (app) => {
//     app.config.globalProperties.$axios = axiosInstance;
//   },
// };
import axios from "axios";
import router from "@/router/index";
import { useAuthStore } from "@/stores/authStore";
const axiosInstance = axios.create({
  baseURL: "http://localhost:8080/api", // 백엔드 API의 기본 URL 설정
  timeout: 5000, // 요청 타임아웃 설정
  headers: {
    "Content-Type": "application/json",
  },
});

// 요청 인터셉터 설정
axiosInstance.interceptors.request.use(
  (config) => {
    const accessToken = localStorage.getItem("accessToken");
    const refreshToken = localStorage.getItem("refreshToken");

    if (accessToken) {
      config.headers["Authorization"] = `Bearer ${accessToken}`;
    }
    if (refreshToken) {
      config.headers["x-refresh-token"] = refreshToken;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

axiosInstance.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;
    const authStore = useAuthStore();

    // 401 오류 처리
    if (error.response && error.response.status === 401) {
      // Refresh token이 없는 경우 로그인 페이지로 리다이렉트
      router.push("/login");
      return Promise.reject(error);
    }

    return Promise.reject(error);
  }
);
export default {
  install: (app) => {
    // 전역 프로퍼티에 axios 인스턴스를 추가
    app.config.globalProperties.$axios = axiosInstance;
  },
};

export { axiosInstance };
