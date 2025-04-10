<template>
  <LayoutWithBack title="검색하기">
    <div class="search-page">
      <div class="searchbar" @click="openAddressSearch">
        <div class="input-icon-wrap">
          <img :src="searchIconImg" alt="search icon" class="icon-search" />
          <p>찾고 싶은 주소를 입력해주세요.</p>
        </div>
      </div>

      <ResultBar
        resultTitle="전체 게시물"
        :sortOptions="sortOptions"
        @updateSortBy="updateSortBy"
      />

      <div class="tag-button-container-wrapper">
        <TagButtonGroup
          v-model:selectedButton="selectedButton"
          :buttons="tagOptions"
          :multiple="false" 
        />
      </div>

      <PostList :posts="filteredList" />
    </div>

  </LayoutWithBack>
</template>

<script setup>
import LayoutWithBack from '@/layouts/LayoutWithBack.vue'
import searchIconImg from "@/assets/icons/Magnifier.png";
import { ref, computed } from 'vue'
import ResultBar from '../common/ResultBar.vue';
import TagButtonGroup from '../common/TagButtonGroup.vue';
import PostList from './PostList.vue';

const selectedButton = ref('ALL');      // 태그 버튼 그룹에서 선택된 버튼
const tagOptions = ref([
  { label: '전체', value: 'ALL' },
  { label: '교통', value: 'TRANSPORT' },
  { label: '맛집', value: 'TASTE' },
  { label: '생활팁', value: 'LIFE' },
  { label: '질문', value: 'QUESTION' },
  { label: '모임', value: 'MEETING' },
  { label: '기타', value: 'ETC' },
]);

// const sortOptions = ref([
//   { idx: 0, value: "latest", name: "최신순" },
//   { idx: 1, value: "views", name: "조회수순" },
//   { idx: 2, value: "oldest", name: "오래된순" },
// ]);

const keyword = ref('')
const list = ref([
  { id: 1, title: 'Vue로 만든 게시판' },
  { id: 2, title: '타잔 프로젝트 후기' },
  { id: 3, title: '검색 기능 고민' }
])

const filteredList = computed(() =>
  list.value.filter((item) =>
    item.title.toLowerCase().includes(keyword.value.toLowerCase())
  )
)
</script>

<style lang="scss" scoped>
.search-page {
  padding: 16px;
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: white;
}

/** search bar style */
.searchbar {
    @include custom-margin-x;
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
      z-index: 5; /* Higher than overlay */
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

.tag-button-container-wrapper {
  @include custom-padding-x;
}
</style>
