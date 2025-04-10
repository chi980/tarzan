<template>
  <div class="searchbar">
    <div class="input-icon-wrap">
        <input
          v-model="searchQuery" 
          type="text"
          placeholder="찾고 싶은 글 제목을 입력해주세요."
          readonly
          @focus="goToSearch"
        />
    </div>
  </div>
</template>

<script setup>
import { ref, defineEmits } from 'vue';
import router from '@/router';

const searchQuery = ref('');
const emit = defineEmits();

// 게시물 검색 페이지로 이동
const goToSearch = () => {
  router.push({ name: 'PostSearch' }) // 라우터 이름은 아래에 정의
}
</script>

<style lang="scss" scoped>
  .searchbar {
    display: flex;
    padding-top: $margin-small;
    padding-bottom: $margin-default;
    @include custom-padding-x;
    box-sizing: border-box;
    cursor: pointer;
  }

  .searchbar .input-icon-wrap {
    display: flex;
    align-items: center;
    width: 100%;
    height: 48px;
    border-radius: 13px;
    background-color: $input-color;
    padding-right: $padding-default;
  }

  .searchbar .input-icon-wrap .icon-search {
    width: 16px;
    height: 16px;
    @include custom-margin-x;
    color: $input-placeholder-color;
  }

  .searchbar .input-icon-wrap input {
    width: 100%;
    appearance: none;
    border: none;
    outline: none;
    background: transparent;
    @include custom-text;
  }
</style>