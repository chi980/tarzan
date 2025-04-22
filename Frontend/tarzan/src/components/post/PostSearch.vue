<script setup lang="ts">
import { ref, defineEmits, watch } from "vue";
import { debounce } from "lodash";
import searchIconImg from "@/assets/icons/Magnifier.png";
import { axiosInstance } from "@/plugins/axiosPlugin";
import TopBarBack from "@/components/common/TopBarBack.vue";
import BottomDefaultButton from "@/components/common/BottomDefaultButton.vue";
import PostList from "@/components/post/PostList.vue";

const emit = defineEmits(["close"]);

const searchQuery = ref("");
const searchResults = ref([]);

const searchPosts = async () => {
  if (!searchQuery.value.trim()) return;
  searchResults.value = [];

  try {
    const { data } = await axiosInstance.get(`/v1/board/search`, {
      params: {
        size: 5,
        page: 0,
        sortBy: "최신순",
        gu: "JONGNO",
        search: searchQuery.value.trim(),
      },
    });

    // console.log("search 응답 전체:", JSON.stringify(data, null, 2));  // 데이터 출력 추가

    if (data.success) {
      searchResults.value = data.data.list;
    } else {
      console.error("검색 실패:", data.message);
    }
  } catch (error) {
    console.error("검색 중 오류 발생:", error);
  }
};

const debouncedSearch = debounce(searchPosts, 500);

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
        <input v-model="searchQuery" placeholder="검색어를 입력하세요" @keyup.enter="searchPosts" />
      </div>

      <div class="modal-content">
        <div v-if="searchResults.length === 0">
          <p>검색 결과가 없습니다.</p>
        </div>
        <PostList v-else :posts="searchResults" />
      </div>

      <BottomDefaultButton label="검색" :onClick="searchPosts" />
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
