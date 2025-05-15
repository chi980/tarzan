<template>
  <div class="sub-container">
    <TopBar class="top-bar" @update:selected="updateDistrict" />

    <div class="center-container">
      <SearchPostBar @click="openSearchModal" />

      <DescriptionComponent
        class="description-component"
        descriptionImgSrc="/assets/etc/Saly-25.png"
        descriptionTitle="동네주민과<br/>얘기해보세요!"
        descriptionContent="여러 정보를 얻어보세요<br/>모임도 참여할 수 있어요!"
        backgroundColor="#FFF7D9" />

      <div class="tag-button-container-wrapper">
        <TagButtonGroup
          v-model:selectedButton="selectedButton"
          :buttons="tagOptions"
          :multiple="false"
          :readonly="false">
        </TagButtonGroup>
      </div>

      <div class="result-bar-container">
        <ResultBar
          resultTitle="전체 게시물"
          :sortOptions="sortOptions"
          @updateSortBy="updateSortBy" />
      </div>

      <PostList :posts="posts" />
      <div ref="target" style="height: 1px"></div>

      <div class="write-post-button" @click="goToPostCreate">
        <img :src="writeIconImg" alt="refresh icon" />
        <p>글쓰기</p>
      </div>
    </div>

    <PostSearch v-if="isPostSearchModalOpen" @close="closeSearchModal" />

    <BottomBar class="bottom-bar" />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, watch, onUnmounted } from "vue";
import router from "@/router";
import { useRouter } from "vue-router";
import { axiosInstance } from "@/plugins/axiosPlugin";
import { useInfiniteScroll } from "@/composables/useInfiniteScroll.js";

import TopBar from "@/components/common/TopBar.vue";
import BottomBar from "@/components/common/BottomBar.vue";
import SearchPostBar from "@/components/common/SearchPostBar.vue";
import DescriptionComponent from "@/components/common/Description.vue";
import ResultBar from "@/components/common/ResultBar.vue";
import TagButtonGroup from "@/components/common/TagButtonGroup.vue";
import PostList from "@/components/post/PostList.vue";
import PostSearch from "@/components/post/PostSearch.vue";

import writeIconImg from "@/assets/write_post.png";

const isPostSearchModalOpen = ref(false);

const openSearchModal = () => {
  isPostSearchModalOpen.value = true;
};

const closeSearchModal = () => {
  isPostSearchModalOpen.value = false;
};

const tagOptions = ref([
  { label: "전체", value: "ALL" },
  { label: "교통", value: "TRANSPORT" },
  { label: "맛집", value: "TASTE" },
  { label: "생활팁", value: "LIFE" },
  { label: "질문", value: "QUESTION" },
  { label: "모임", value: "MEETING" },
  { label: "기타", value: "ETC" },
]);

const sortOptions = ref([
  { idx: 0, value: "latest", name: "최신순" },
  { idx: 1, value: "views", name: "조회수순" },
  { idx: 2, value: "oldest", name: "오래된순" },
]);

const page = ref(0);
const posts = ref([]); // 게시물 목록
const sortBy = ref("최신순"); // 정렬 기준
const selectedButton = ref(tagOptions.value[0]);
const selectedDistrict = ref("JONGNO"); // 지역구

// 정렬 기준 변경
const updateSortBy = (selectedIndex) => {
  const selectedOption = sortOptions.value.find(
    (option) => option.idx === selectedIndex
  );
  if (selectedOption && sortBy.value !== selectedOption.value) {
    sortBy.value = selectedOption.name;
  }
};

// 지역구 변경
const updateDistrict = (district) => {
  selectedDistrict.value = district; // 변경되면 자동으로 fetchPosts() 호출됨
};

// 📌 useInfiniteScroll 사용
const fetchPosts = async () => {
  const queryParams = new URLSearchParams({
    size: 5,
    page: page.value,
    sortBy: sortBy.value,
    tag: selectedButton.value.value,
    gu: selectedDistrict.value,
  }).toString();

  try {
    const response = await axiosInstance.get(`/v1/board?${queryParams}`);
    if (response.data.success) {
      const newPosts = response.data.data.list;
      if (page.value === 0) {
        posts.value = newPosts;
      } else {
        posts.value = [...posts.value, ...newPosts];
      }
      page.value++;
      console.log("게시글 데이터 불러오기 성공");
    }
  } catch (error) {
    console.error("게시글 데이터 요청 중 오류 발생:", error.message);
  }
};

// ✨ 여기!! fetchPosts를 넘겨서 세팅
const { target, setupObserver } = useInfiniteScroll(fetchPosts);

// watch - 정렬, 태그, 지역구 변경되면 초기화
watch([sortBy, selectedButton, selectedDistrict], async () => {
  posts.value = [];
  page.value = 1;
  await fetchPosts();
});

// 마운트될 때 fetch + 옵저버 시작
onMounted(async () => {
  await fetchPosts();
  setupObserver();
});

// // 글쓰기 페이지로 이동
const goToPostCreate = () => {
  router.push({ name: "PostCreate" });
};
</script>

<style lang="scss" scoped>
.sub-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.top-bar,
.bottom-bar,
.description-component {
  flex-shrink: 0;
}

.center-container {
  display: flex;
  flex-direction: column;
  width: 100%;
  flex-grow: 1;
  overflow-y: auto;
}

.search-input {
  width: 100%;
  padding: 12px;
  border-radius: 8px;
  border: 1px solid #ddd;
}

.tag-button-container-wrapper {
  @include custom-padding-x;
  @include custom-padding-y;
}

.result-bar-container {
  @include custom-margin-x;
}

.post-list-container {
  // background-color: aqua;
  @include custom-margin-x;
  flex-grow: 1;
}

.write-post-button {
  position: fixed;
  bottom: 70px;
  left: 50%; // 💡 가로 중앙 기준
  transform: translateX(-50%); // 💡 정확히 가운데 정렬
  z-index: 5;

  width: fit-content;
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 12px;
  margin-bottom: $margin-small;
  background-color: white;
  border-radius: 30px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.25);
  cursor: pointer;

  img {
    @include custom-icon-style(12px);
    color: $input-placeholder-color;
  }

  p {
    @include custom-text($font-size: 12px);
  }
}
</style>
