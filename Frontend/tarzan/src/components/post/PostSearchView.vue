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
        class="result-bar"
        resultTitle="전체 게시물"
        :sortOptions="sortOptions"
        @updateSortBy="updateSortBy"
      />
      <TagButtonGroup
        class="tag-button-container"
        v-model:selectedButton="selectedButton"
        :buttons="tagOptions"
        :multiple="false"
      />
      <PostList class="post-list" :posts="filteredList" />
    </div>
  </LayoutWithBack>
</template>

<script setup>
import { ref, computed } from "vue";

import LayoutWithBack from "@/layouts/LayoutWithBack.vue";
import ResultBar from "../common/ResultBar.vue";
import TagButtonGroup from "../common/TagButtonGroup.vue";
import PostList from "./PostList.vue";

import searchIconImg from "@/assets/icons/Magnifier.png";

const sortOptions = ref([
  { idx: 0, value: "latest", name: "최신순" },
  { idx: 1, value: "views", name: "조회수순" },
  { idx: 2, value: "oldest", name: "오래된순" },
]);

const selectedButton = ref("ALL"); // 태그 버튼 그룹에서 선택된 버튼
const tagOptions = ref([
  { label: "전체", value: "ALL" },
  { label: "교통", value: "TRANSPORT" },
  { label: "맛집", value: "TASTE" },
  { label: "생활팁", value: "LIFE" },
  { label: "질문", value: "QUESTION" },
  { label: "모임", value: "MEETING" },
  { label: "기타", value: "ETC" },
]);

const filteredList = ref([
  {
    board_id: 1,
    board_tag: "생활팁",
    board_title: "Vue로 만든 게시판",
    board_content: "Vue 3로 간단한 게시판 만들기 프로젝트입니다.",
    board_created_at: "2024-04-01",
    board_read_count: 123,
    comments: 4,
  },
  {
    board_id: 2,
    board_tag: "모임",
    board_title: "타잔 프로젝트 후기",
    board_content: "타잔 프로젝트에서 Vue와 API 연동을 했어요!",
    board_created_at: "2024-04-03",
    board_read_count: 256,
    comments: 7,
  },
  {
    board_id: 3,
    board_tag: "질문",
    board_title: "검색 기능 고민",
    board_content: "게시판에 검색 기능을 어떻게 넣을지 고민 중입니다.",
    board_created_at: "2024-04-04",
    board_read_count: 78,
    comments: 2,
  },
]);
</script>

<style lang="scss" scoped>
.search-page {
  padding: 16px;
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: #ffffff;
}

.searchbar {
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

    .icon-search {
      @include custom-icon-style;
      color: $input-placeholder-color;
    }

    p {
      @include custom-text($font-size: 14px, $font-color: $text-color-light);
    }
  }
}

.result-bar {
  height: 48px;
  margin-top: 10px;
}

.tag-button-container {
  margin: 8px 0;
  // background-color: darkgoldenrod;
}

.post-list {
  flex-grow: 1;
  margin-top: 8px;
  // background-color: lightblue;
}
</style>
