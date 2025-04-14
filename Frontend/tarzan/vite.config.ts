import { defineConfig, loadEnv } from "vite";
import path from "path";
import vue from "@vitejs/plugin-vue";
// defineConfig에서 mode에 맞게 환경 변수 로드
export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd()); // 개발/배포 환경에 맞는 .env 파일 로드
  console.log("API Base URL:", env.VITE_API_BASE_URL); // 확인용

  return {
    plugins: [vue()],
    resolve: {
      alias: {
        "@": path.resolve(__dirname, "./src"),
      },
    },

    css: {
      preprocessorOptions: {
        scss: {
          additionalData: `
        @import "@/assets/scss/_variables.scss";
        @import "@/assets/scss/_mixins.scss";
        @import "@/assets/scss/main.scss";
      `,
        },
      },
    },
    server: {
      hmr: false,
      host: "0.0.0.0", // iphone에서 접근 가능하도록 설정
      proxy: {
        "/api": env.VITE_API_BASE_URL, // 로드한 환경 변수를 서버 프록시에 적용
      },
    },
  };
});
// export default defineConfig({
//   plugins: [vue()],
//   resolve: {
//     alias: {
//       "@": path.resolve(__dirname, "./src"), // 추가
//     },
//   },

//   // SCSS 전역 사용
//   css: {
//     preprocessorOptions: {
//       scss: {
//         additionalData: `
//         @import "@/assets/scss/_variables.scss";
//         @import "@/assets/scss/_mixins.scss";
//         @import "@/assets/scss/main.scss";
//       `,
//       },
//     },
//   },
//   server: {
//     proxy: {
//       "/api": env.VITE_API_BASE_URL, // env에서 불러온 API URL 사용
//     },
//   },
// });
