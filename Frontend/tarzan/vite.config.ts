import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
// import styleImport from 'vite-plugin-style-import';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue(),
    // styleImport({
    //   preprocess: true, // SCSS 파일을 처리하도록 설정
    // }),],
  ]
})
