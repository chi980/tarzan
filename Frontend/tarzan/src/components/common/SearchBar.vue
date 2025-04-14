<template>
  <div class="searchbar" @click="goToSearch">
    <div class="input-icon-wrap">
      <img :src="searchIconImg" alt="search icon" class="icon-search" />
      <p>찾고 싶은 주소를 입력해주세요.</p>
    </div>
  </div>
</template>

<script setup>
import { ref, defineEmits } from 'vue';
import router from '@/router';
import searchIconImg from "@/assets/icons/Magnifier.png";

const props = defineProps({
  // 이동할 라우트 이름
  routeName: {
    type: String,
    default: 'Home',
  },
});

// 검색 페이지로 이동
const goToSearch = () => {
  console.log('라우트로 이동 시도:', props.routeName);
  try {
    router.push({
      name: props.routeName,
    });
    console.log('라우트로 이동', props.routeName);
  } catch (error) {
    console.error('라우트 이동 오류:', error);
  }
}
</script>

<style lang="scss" scoped>
.searchbar {
  @include custom-margin-x;
  @include custom-padding-y;
  display: flex;
  cursor: pointer;
  .input-icon-wrap {
    @include custom-padding-x;
    display: flex;
    gap: $padding-default;
    align-items: center;
    width: 100%;
    height: 48px;
    border-radius: 13px;
    background-color: white;
    padding-right: $padding-default;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    cursor: pointer;

    .icon-search {
      @include custom-icon-style;
      color: $input-placeholder-color;
    }

    p {
      @include custom-text($font-size: 14px, $font-color: $text-color-light);
    }
  }
};
</style>