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
      <!-- 
      <div class="search-container">
        <img :src="searchIconImg" alt="search icon" class="icon-search" />
        <input
          v-model="searchQuery"
          placeholder="검색어를 입력하세요"
          @keyup.enter="resetAndSearch" />
      </div> -->

      <div class="searchbar">
        <div class="input-icon-wrap">
          <img :src="searchIconImg" alt="search icon" class="icon-search" />
          <input
            v-model="searchQuery"
            @keyup.enter="resetAndSearch"
            type="text"
            placeholder="검색어를 입력해주세요."
            class="search-input" />
        </div>
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
    @include custom-padding-x;
    flex-grow: 1;
    overflow-y: auto;
    @include custom-scrollbar-style;
  }
}
.modal-container {
  @include custom-modal;
}
.searchbar {
  @include custom-margin-y;
}
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

    input {
      @include custom-text($font-size: 14px, $font-color: $text-color-light);
      background-color: white;
      border: none;
      flex: 1;
      padding: 0;
    }
  }
}
</style>
