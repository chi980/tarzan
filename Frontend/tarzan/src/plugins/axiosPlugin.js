import axios from "axios";
import router from "@/router/index";
import { useAuthStore } from "@/stores/authStore";

const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL, // 백엔드 API의 기본 URL 설정
  withCredentials: true, // HttpOnly 쿠키 자동 포함
  timeout: 5000, // 요청 타임아웃 설정
  headers: {
    "Content-Type": "application/json",
  },
});

// ✅ 응답 인터셉터 설정 (401 처리 + 토큰 자동 갱신)
axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => {
    console.log("error발생!");
    if (error.response && error.response.status === 401) {
      return refreshTokenAndRetry(error);
    }
    return Promise.reject(error);
  }
);

function refreshTokenAndRetry(error) {
  const authStore = useAuthStore();
  const axiosNewInstance = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL, // 기본 URL 설정
    withCredentials: true,
    timeout: 5000,
  });

  return new Promise((resolve, reject) => {
    // if (!authStore.getEmail) {
    //   reject(new Error("email이 존재하지 않습니다."));
    //   return;
    // }

    axiosNewInstance
      .post("/auth/refresh")
      .then((res) => {
        console.log("access token을 새로 발급받았씁니다.");

        resolve(axios(error.config)); // 실패한 요청을 재시도
      })
      .catch((err) => {
        if (authStore.getEmail) console.error("발급받지 못했습니다");
        alert("재로그인합니다.");

        axiosNewInstance
          .post("/auth/logout", {
            email: authStore.getEmail,
          })
          .then(() => {
            authStore.logout();
            router.push({ path: "/login" });
            reject(err); // 에러 처리
          })
          .catch((logoutErr) => {
            reject(logoutErr); // 로그아웃 오류 처리
          });
      });
  });
}

export default {
  install: (app) => {
    // 전역 프로퍼티에 axios 인스턴스를 추가
    app.config.globalProperties.$axios = axiosInstance;
  },
};

export { axiosInstance };
