<script setup lang="ts">
import { ref, computed, onMounted } from "vue";

import { axiosInstance } from "@/plugins/axiosPlugin";

import NonContent from "@/components/common/NonContent.vue";
import TopBarBack from "@/components/common/TopBarBack.vue";
import BookmarkItem from "@/components/bookmark/BookmarkItem.vue";
import BottomDefaultButton from "@/components/common/BottomDefaultButton.vue";
import CompareHouses from "@/components/bookmark/CompareHouses.vue";

const isLoading = ref(true);
onMounted(async () => {
  try {
    const response = await axiosInstance.get(`/v1/bookmark`, {
      params: {
        size: 3,
        page: 0,
        sortBy: "최신순",
        status: "ALL",
      },
    });
    const houseList = response.data.data.list;
    console.log(houseList);
    if (houseList.length) {
      // checked 추가해서 list에 저장
      list.value = houseList.map((item: any) => ({
        checked: false,
        house: {
          bookmarkIdx: item.bookmark_id,
          house_name: item.bookmark_house_name,
          house_address: item.bookmark_house_address,
          house_category: item.bookmark_house_category,
          create_at: item.bookmark_created_at,
        },
      }));
    }
  } catch (error) {
    console.error("북마크 불러오기 실패:", error);
  } finally {
    isLoading.value = false;
  }
});
const list = ref<
  {
    checked: boolean;
    house: {
      bookmarkIdx: number;
      house_name: string;
      house_address: string;
      house_category: string;
      created_at: string;
    };
  }[]
>([]);

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
const isNonContent = computed(
  () => !isLoading.value && list.value.length === 0
);
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
      <Transition name="fade">
        <NonContent
          :value="'점검 완료한 집이 없습니다.'"
          v-if="isNonContent"></NonContent>
      </Transition>
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
}
</style>
