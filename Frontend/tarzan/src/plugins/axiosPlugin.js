import axios from "axios";
import { useLoadingStore } from "@/stores/loadingStore";
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

// ✅ Pinia 로딩 스토어 가져오는 헬퍼
function startLoading() {
  try {
    useLoadingStore().start();
  } catch {}
}
function finishLoading() {
  try {
    useLoadingStore().finish();
  } catch {}
}

// ✅ 요청 인터셉터: 로딩 시작
axiosInstance.interceptors.request.use(
  (config) => {
    startLoading();
    return config;
  },
  (err) => {
    finishLoading();
    return Promise.reject(err);
  }
);

// ✅ 응답 인터셉터 설정 (401 처리 + 토큰 자동 갱신)
axiosInstance.interceptors.response.use(
  (response) => {
    finishLoading();
    return response;
  },
  (error) => {
    finishLoading();
    // 응답이 401 Unauthorized인 경우
    // 토큰 갱신 로직을 추가합니다.
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
        console.log("access token을 새로 발급받았습니다.");

        resolve(axios(error.config)); // 실패한 요청을 재시도
      })
      .catch((err) => {
        if (authStore.getEmail) console.error("발급받지 못했습니다.");

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

        // alert("재로그인합니다.");
        router.push({ path: "/login" });
      });
  });
}

export default {
  install: (app) => {
    // 전역 프로퍼티에 axios 인스턴스를 추가
    // Options API 에서 this.$axios 로 접근 가능
    app.config.globalProperties.$axios = axiosInstance;
  },
};

export { axiosInstance };
