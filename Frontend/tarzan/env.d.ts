/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_API_BASE_URL: string; // VITE_API_BASE_URL 타입 정의
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
