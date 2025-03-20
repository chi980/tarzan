<template>
  <div>로그인 처리 중...</div>
</template>

<script setup>
import { onMounted } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores/authStore"; // authStore import
import { Role } from "@/data/userRole"; // Role enum import

const router = useRouter();

onMounted(() => {
  try {
    // 현재 URL에서 쿼리 파라미터 추출
    const urlParams = new URLSearchParams(window.location.search);
    const accessToken = urlParams.get("access_token");
    const user = Object.fromEntries(
      [...urlParams].filter(([key]) => key !== "access_token")
    );

    console.log(accessToken);
    console.log(user);
    // 필수 값 체크
    if (!accessToken || !user.role) throw new Error("응답이 올바르지 않음");

    const role = user.role;

    // 로그인 성공 시 데이터 저장
    const authStore = useAuthStore();
    authStore.setAccessToken(accessToken);
    authStore.setUser(user);

    // 역할에 따른 페이지 이동
    if (role === Role.USER) {
      router.push({ name: "Home" });
    } else if (role === Role.GUEST) {
      router.push({ name: "SignUp" });
    } else {
      throw new Error("역할값이 유효하지 않음");
    }
  } catch (error) {
    console.error("로그인 중 오류 발생: ", error);
    alert(error.message || "로그인 실패!");
    router.push({ name: "Login" });
  }
});
</script>
