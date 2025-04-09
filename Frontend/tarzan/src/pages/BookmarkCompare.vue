<script setup lang="ts">
import { ref, computed } from "vue";
import TopBarBack from "@/components/common/TopBarBack.vue";
import BookmarkItem from "@/components/bookmark/BookmarkItem.vue";
import BottomDefaultButton from "@/components/common/BottomDefaultButton.vue";
import CompareHouses from "@/components/bookmark/CompareHouses.vue";

const list = ref([]);
list.value = [
  {
    checked: false,
    house: {
      bookmarkIdx: 1,
      house_name: "집 이름 1",
      house_address: "주소 1",
      house_category: "카테고리 1",
      hoconstuse_review_score: 4.5,
      house_latitude: 37.5665,
      house_longitude: 126.978,
      created_at: "2025.03.16 10:00:00",
    },
  },
  {
    checked: false,
    house: {
      bookmarkIdx: 2,
      house_name: "집 이름 1",
      house_address: "주소 1",
      house_category: "카테고리 1",
      hoconstuse_review_score: 4.5,
      house_latitude: 37.5665,
      house_longitude: 126.978,
      created_at: "2025.03.16 10:00:00",
    },
  },
];

// 체크 상태 바꿔주는 함수
const toggleCheck = (idx: number) => {
  list.value[idx].checked = !list.value[idx].checked;
};

const hideUI = ref(false);
const compareBookmarks = () => {
  const length = list.value.filter((item) => item.checked).length;
  if (length < 2) return;
  hideUI.value = true;
};
</script>

<template>
  <div class="sub-container">
    <TopBarBack :title="'비교하기'" />

    <CompareHouses v-if="hideUI" />
    <div class="center-container">
      <div class="bookmark-item-list-wrapper" v-if="!hideUI">
        <BookmarkItem
          v-for="(bookmark, index) in list"
          :key="index"
          :house="bookmark.house"
          :checked="bookmark.checked"
          :idx="index"
          @toggle-check="toggleCheck" />
      </div>
    </div>
    <BottomDefaultButton
      :label="'비교하기'"
      :onClick="compareBookmarks"
      v-if="!hideUI" />
  </div>
</template>

<style scoped lang="scss">
:deep(.center-container) {
  position: relative;
  flex-grow: 1;
  width: 100%;

  display: flex;
  flex-direction: column;

  background-color: white;

  overflow-y: auto;
  /* 스크롤바 전체 영역 */
  &::-webkit-scrollbar {
    width: 4px; /* 세로축 스크롤바 폭 너비 */
    height: 100%; /* 가로축 스크롤바 폭 너비 */
  }
  &::-webkit-scrollbar-button {
    display: none;
  }
  /* 스크롤바 막대 제외 부분 */
  &::-webkit-scrollbar-track {
    background: transparent;
  }
  /* 스크롤바 막대 */
  &::-webkit-scrollbar-thumb {
    border-radius: calc($border-radius-default * 2);
    background: #f2f2f2;
  }
}

// scoped
.bookmark-item-list-wrapper {
  @include custom-margin-y;
  display: flex;
  flex-direction: column;
  gap: $padding-default;
}
</style>
