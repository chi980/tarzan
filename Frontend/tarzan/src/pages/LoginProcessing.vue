<template>
  <div>로그인 처리 중...</div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores/authStore";
import { onMounted } from "vue";
import { Role } from "@/data/userRole.ts"; // @/는 절대 경로 설정 시 가능, 아니면 ../constants/role

const authStore = useAuthStore();
const router = useRouter();

onMounted(() => {
  // 현재 URL에서 쿼리 파라미터 추출
  const urlParams = new URLSearchParams(window.location.search);
  const accessToken = urlParams.get("access_token");
  const refreshToken = urlParams.get("refresh_token");

  const role = urlParams.get("role");
  const gu = urlParams.get("gu");
  const nickname = urlParams.get("nickname");

  if (role == Role.USER) {
    // Pinia 스토어에 토큰 저장
    authStore.setTokens(accessToken, refreshToken);
    authStore.setUserInfo(role, gu, nickname);
    // 이후 메인 페이지로 리다이렉트
    router.push("/");
  } else if (role == Role.GUEST) {
    authStore.setTokens(accessToken, refreshToken);
    authStore.setRole(role);
    router.push("/signup");
  } else {
    // 토큰이 없을 경우 로그인 실패 처리
    console.error("Authentication failed. No tokens found.");
    alert("Authentication failed. No tokens found.");
    router.push("/login");
  }
});
</script>
