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
      "/api": "http://localhost:8080",
    },
  },
});
