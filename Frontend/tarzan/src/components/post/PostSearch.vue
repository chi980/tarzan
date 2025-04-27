<script setup lang="ts">
import { ref, defineEmits, watch } from "vue";
import { debounce } from "lodash";
import searchIconImg from "@/assets/icons/Magnifier.png";
import { axiosInstance } from "@/plugins/axiosPlugin";
import TopBarBack from "@/components/common/TopBarBack.vue";
import BottomDefaultButton from "@/components/common/BottomDefaultButton.vue";
import PostList from "@/components/post/PostList.vue";
import InfiniteScrollTrigger from "@/components/common/InfiniteScrollTrigger.vue"; // 추가

const emit = defineEmits(["close"]);

const searchQuery = ref("");
const searchResults = ref([]);
const page = ref(0);
const size = 8;
const isEnd = ref(false);
const loading = ref(false);

const searchPosts = async (isNewSearch = false) => {
  if (!searchQuery.value.trim()) return;
  if (loading.value) return;
  if (!isNewSearch && isEnd.value) return;

  loading.value = true;

  try {
    const { data } = await axiosInstance.get(`/v1/board/search`, {
      params: {
        size,
        page: page.value,
        sortBy: "최신순",
        gu: "JONGNO",
        search: searchQuery.value.trim(),
      },
    });

    if (data.success) {
      const newItems = data.data.list;
      if (isNewSearch) {
        searchResults.value = newItems;
      } else {
        searchResults.value.push(...newItems);
      }
      if (newItems.length < size) {
        isEnd.value = true;
      } else {
        page.value++;
      }
    } else {
      console.error("검색 실패:", data.message);
    }
  } catch (error) {
    console.error("검색 중 오류 발생:", error);
  } finally {
    loading.value = false;
  }
};

const debouncedSearch = debounce(() => {
  resetAndSearch();
}, 500);

const resetAndSearch = () => {
  page.value = 0;
  isEnd.value = false;
  searchResults.value = [];
  searchPosts(true);
};

const closeModal = () => {
  emit("close");
};

watch(searchQuery, debouncedSearch);
</script>

<template>
  <div class="modal-container" @click.self="closeModal">
    <div class="modal-wrapper">
      <div class="modal-title">
        <TopBarBack title="게시글 검색" @back="closeModal" />
      </div>

      <div class="search-container">
        <img :src="searchIconImg" alt="search icon" class="icon-search" />
        <input v-model="searchQuery" placeholder="검색어를 입력하세요" @keyup.enter="resetAndSearch" />
      </div>

      <div class="modal-content">
        <div v-if="searchResults.length === 0 && !loading">
          <p>검색 결과가 없습니다.</p>
        </div>
        <div v-else>
          <PostList :posts="searchResults" />
          <InfiniteScrollTrigger v-if="!isEnd" @trigger="() => searchPosts()" />
        </div>
      </div>

      <BottomDefaultButton label="검색" :onClick="resetAndSearch" />
    </div>
  </div>
</template>

<style scoped lang="scss">
.modal-wrapper {
  display: flex;
  flex-direction: column;
  height: 100%;
  .modal-content {
    flex-grow: 1;
    overflow-y: auto;
    @include custom-scrollbar-style;
    padding: 16px;
  }
}
.modal-container {
  @include custom-modal;
}
.search-container {
  @include custom-margin-x;
  @include custom-margin-y;
  @include custom-padding-x;
  display: flex;
  align-items: center;
  gap: $padding-default;
  height: 48px;
  border-radius: $border-radius-default;
  background: white;
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);
  img {
    @include custom-icon-style;
  }
  input {
    flex: 1;
    border: none;
    height: 100%;
  }
}
</style>
