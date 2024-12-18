<template>
  <div class="searchbar">
    <div class="input-icon-wrap">
      <font-awesome-icon :icon="['fas', 'magnifying-glass']" class="icon-search"/>
        <input
          v-model="searchQuery" 
          type="text"
          placeholder="찾고 싶은 글 제목을 입력해주세요."
          @keyup.enter="onSearch" />
    </div>
  </div>
</template>

<script setup>
import { ref, defineEmits } from 'vue';

const searchQuery = ref('');
const emit = defineEmits();

// 검색 버튼이나 Enter 키 입력 시 searchPosts 함수 호출
const onSearch = () => {
  console.log('검색어:', searchQuery.value);
  emit('search', searchQuery.value); 
};
</script>

<style lang="scss" scoped>
  .searchbar {
    display: flex;
    padding-top: $margin-small;
    padding-bottom: $margin-default;
    @include custom-padding-x;
    box-sizing: border-box;
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