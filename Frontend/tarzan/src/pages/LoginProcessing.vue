<template>
  <div>로그인 처리 중...</div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores/authStore";
import { onMounted } from "vue";

const authStore = useAuthStore();
const router = useRouter();

onMounted(() => {
  // 현재 URL에서 쿼리 파라미터 추출
  const urlParams = new URLSearchParams(window.location.search);
  const accessToken = urlParams.get("access_token");
  const refreshToken = urlParams.get("refresh_token");

  if (accessToken && refreshToken) {
    // Pinia 스토어에 토큰 저장
    authStore.setTokens(accessToken, refreshToken);
    alert(`accessToken=${accessToken}, refreshToken=${refreshToken}`);
    alert(accessToken == refreshToken);
    // 이후 메인 페이지로 리다이렉트
    router.push("/"); // Vue Router를 사용할 때는 router.push 사용
  } else if (accessToken) {
    authStore.setAccessToken(accessToken);
    alert("첫 로그인 당사자!!" + accessToken);
    router.push("/signup");
  } else {
    // 토큰이 없을 경우 로그인 실패 처리
    console.error("Authentication failed. No tokens found.");
    alert("Authentication failed. No tokens found.");
    router.push("/login");
  }
});
</script>
