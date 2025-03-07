<template>
  <div class="bar bottom-bar">
    <div
      v-for="item in items"
      :key="item.idx"
      class="bar-item"
      @click="navigateTo(item.url)"
    >
      <img :src="item.icon" alt="Icon" />
      <span>{{ item.title }}</span>
    </div>
  </div>
</template>

<script lang="ts" setup>
// @ts-ignore
import { ref } from "vue";
// @ts-ignore
import { useRouter } from "vue-router";
// @ts-ignore
import homeIcon from "@/assets/icons/bottombar/home.svg";
// @ts-ignore
import checklistIcon from "@/assets/icons/bottombar/checklist.svg";

// @ts-ignore
import preventionIcon from "@/assets/icons/bottombar/prevention.svg";
import communityIcon from "@/assets/icons/bottombar/community.svg";
import mypageIcon from "@/assets/icons/bottombar/mypage.svg";

interface BarItem {
  idx: number;
  icon: string;
  title: string;
  url: string; // back
}

const items = ref<BarItem[]>([
  { idx: 1, icon: homeIcon, title: "홈", url: "/" },
  { idx: 2, icon: checklistIcon, title: "체크리스트", url: "/bookmark" },
  { idx: 3, icon: preventionIcon, title: "사기 예방", url: "/fraud" },
  { idx: 4, icon: communityIcon, title: "커뮤니티", url: "/community" },
  { idx: 5, icon: mypageIcon, title: "마이페이지", url: "/mypage" },
]);

const router = useRouter(); // Vue Router 훅 사용

function navigateTo(url: string) {
  router.push(url); // 해당 URL로 이동
}
</script>

<style lang="scss" scoped>
.bottom-bar {
  @include custom-text($font-weight: 400, $font-size: 12px);
  @include custom-bar-style(
    $height: $height-bottom-bar,
    $z-index: $z-index-bottom-bar
  );
  @include custom-none-select-basic;
  background-color: inherit;
}
.bar-item > img {
  @include custom-icon-style;
}
.bar-item {
  margin-top: 10px;
  height: 48px;
  min-width: 48px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-right: 10px;
  border-radius: calc($border-radius-default/2);
  background-color: inherit;
}
.bar-item:last-child {
  margin-right: 0;
}
.bar-item > span {
  @include custom-margin-x(4px);
  margin-top: 6px;
}
</style>
