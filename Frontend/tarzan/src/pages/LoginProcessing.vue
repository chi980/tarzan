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
    const { gu, email, nickname, role } = route.query;

    if (!email) {
      console.error("로그인 정보가 없습니다.");
      router.replace("/login");
      return;
    }

    // 로그인 성공 시 데이터 저장
    authStore.setUser({ email, gu, nickname, role });

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
    router.replace("/login");
  }
});
</script>
