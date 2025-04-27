<script setup lang="ts">
import { ref, computed, onMounted } from "vue";

import { axiosInstance } from "@/plugins/axiosPlugin";

import TopBarBack from "@/components/common/TopBarBack.vue";
import BookmarkItemWithCheck from "@/components/bookmark/BookmarkItemWithCheck.vue";
import List from "@/components/common/List.vue";
import BottomDefaultButton from "@/components/common/BottomDefaultButton.vue";
import CompareHouses from "@/components/bookmark/CompareHouses.vue";

const params = ref({
  size: 10,
  status: "ALL", // 나중에 필터링 추가
  sortBy: "최신순",
});

const fetchBookmarks = async (page: number, params: any) => {
  try {
    const response = await axiosInstance.get(`/v1/bookmark`, {
      params: {
        ...params,
        page: page,
      },
    });

    if (response.data.success) {
      return {
        items: response.data.data.list.map((item) => ({
          ...item,
          checked: false, // 체크 상태 초기화
        })),
        isNext: response.data.data.isNext,
      };
    } else {
      console.error("Failed to fetch data:", response.data.message);
      return {
        items: [],
        isNext: false,
      };
    }
  } catch (error) {
    console.error("API request error:", error);
    return {
      items: [],
      isNext: false,
    };
  }
};

const hideUI = ref(false);
const checkedList = ref<any[]>([]);

const handleClick = (bookmarks) => {
  checkedList.value = bookmarks
    .filter((item) => item.checked)
    .map((item) => item.bookmark_id);
};

const compareBookmarks = () => {
  const length = checkedList.value.length;
  if (length < 2 || length > 3) {
    alert(
      "비교할 북마크는 2개 또는 3개만 선택할 수 있습니다. 다시 선택해주세요."
    );
    return;
  }

  hideUI.value = true;
};
</script>

<template>
  <div class="sub-container">
    <div class="top-bar-wrapper">
      <TopBarBack :title="'비교하기'" />
    </div>

    <div class="center-container">
      <CompareHouses v-if="hideUI" :list="checkedList" />
      <div class="bookmark-item-list-wrapper" v-if="!hideUI">
        <List
          :fetchItems="fetchBookmarks"
          :params="params"
          :onClick="handleClick"
          :isItemChangable="true">
          <template #item="{ item, onClick }">
            <BookmarkItemWithCheck
              :bookmark="item"
              :checked="item.checked"
              @click="onClick" />
          </template>
        </List>
      </div>
    </div>
    <BottomDefaultButton
      :label="'비교하기'"
      :onClick="compareBookmarks"
      v-if="!hideUI" />
  </div>
</template>

<style scoped lang="scss">
.top-bar-wrapper {
  width: 100%;
}
.center-container {
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
</style>
