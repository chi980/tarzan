import { defineConfig } from "vite";
import path from "path";
import vue from "@vitejs/plugin-vue";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"), // 추가
    },
  },

  // SCSS 전역 사용
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
    proxy: {
      "/api": "http://localhost:8080", // 백엔드 API 서버로 프록시 설정
    },
  },

  // 빌드 관련 설정 추가
  build: {
    outDir: path.resolve(__dirname, "dist"), // 빌드 결과물이 생성될 경로
    assetsDir: "assets", // 빌드 시 자산 파일 경로 지정 (예: 이미지, 폰트 등)
    rollupOptions: {
      input: path.resolve(__dirname, "index.html"), // 커스텀 엔트리 파일 지정
    },
    chunkSizeWarningLimit: 500, // 빌드 시 덩치가 큰 청크가 있을 경우 경고를 방지
  },

  // 생산 모드에서의 최적화 옵션
  optimizeDeps: {
    include: [
      // 필요할 경우 특정 패키지를 강제로 포함시킬 수 있습니다
    ],
  },
});
