<template>
  <div class="sub-container">
    <TopBar class="top-bar" @update:selected="updateDistrict" />

    <div class="center-container">
      <SearchBar routeName="PostSearch" />

      <DescriptionComponent
        class="description-component"
        descriptionImgSrc="/src/assets/etc/Saly-25.png"
        descriptionTitle="동네주민과<br/>얘기해보세요!"
        descriptionContent="여러 정보를 얻어보세요<br/>모임도 참여할 수 있어요!"
        backgroundColor="#FFF7D9"
      />

      <div class="tag-button-container-wrapper">
        <TagButtonGroup
          v-model:selectedButton="selectedButton"
          :buttons="tagOptions"
          :multiple="false"
        >
        </TagButtonGroup>
      </div>

      <div class="result-bar-container">
        <ResultBar
          resultTitle="전체 게시물"
          :sortOptions="sortOptions"
          @updateSortBy="updateSortBy"
        />
      </div>

      <PostList :posts="posts" />

      <!-- 감시할 요소 -->
      <div ref="target" class="loading-trigger">무한스크롤</div>

      <!-- <div class="write-button" @click="goToPostCreate">
        <img
          id="post-write-icon"
          src="@/assets/icons/Filter/post-write-icon.png"
        />
        글쓰기
      </div> -->
      <div class="write-post-button" @click="goToPostCreate">
        <img :src="writeIconImg" alt="refresh icon" />
        <p>글쓰기</p>
      </div>
    </div>

    <BottomBar class="bottom-bar" />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, watch } from "vue";
import router from "@/router";
import { useRouter } from "vue-router";
import { axiosInstance } from "@/plugins/axiosPlugin";
import { useInfiniteScroll } from "@/composables/useInfiniteScroll.js";

import TopBar from "@/components/common/TopBar.vue";
import BottomBar from "@/components/common/BottomBar.vue";
import SearchBar from "@/components/common/SearchBar.vue";
import DescriptionComponent from "@/components/common/Description.vue";
import ResultBar from "@/components/common/ResultBar.vue";
import TagButtonGroup from "@/components/common/TagButtonGroup.vue";
import PostList from "@/components/post/PostList.vue";

import writeIconImg from "@/assets/write_post.png";

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

const page = ref(1);
const posts = ref([]); // 게시물 목록
const sortBy = ref("최신순"); // 정렬 기준
const selectedButton = ref("전체"); // 태그
const selectedDistrict = ref("JONGNO"); // 지역구

// 정렬 기준(정렬 기준, 태그, 지역구) 변화 감지
watch([sortBy, selectedButton, selectedDistrict], () => {
  fetchPosts();
});

// 정렬 기준 변경 시 목록 업데이트
const updateSortBy = (selectedIndex) => {
  const selectedOption = sortOptions.value.find(
    (option) => option.idx === selectedIndex
  );

  if (selectedOption && sortBy.value !== selectedOption.value) {
    sortBy.value = selectedOption.name;
  }
};

// // 지역구 변경 시 목록 업데이트
const updateDistrict = (district) => {
  selectedDistrict.value = district; // 변경되면 자동으로 fetchPosts() 호출됨
};

// API: 게시글 데이터 불러오기
const fetchPosts = async () => {
  const queryParams = new URLSearchParams({
    size: 5,
    page: 1,
    sortBy: sortBy.value,
    tag: selectedButton.value,
    gu: selectedDistrict.value,
  }).toString();

  try {
    const response = await axiosInstance.get(`/v1/board?${queryParams}`);

    if (response.data.success) {
      console.log("게시글 목록 가져오기 성공!");
      posts.value = response.data.data.list;
    } else {
      console.error("API 실패:", response.data.message);
    }
  } catch (error) {
    console.error("게시글 데이터 요청 중 오류 발생:", error);
  }
};

// // API: 게시글 데이터 불러오기
// const fetchPosts = async () => {

// const queryParams = new URLSearchParams({
//   size: 5,
//   page: page.value,
//   sortBy: sortBy.value,
//   tag: selectedButton.value,
//   gu: selectedDistrict.value,
// }).toString();
// try {
//   const response = await axiosInstance.get(`/v1/board?${queryParams}`);
//   console.log("게시글 데이터 불러오기 실행")

//   if (response.data.success && response.data.data.list.length) {
//       posts.value.push(...response.data.data.list); // 기존 데이터에 추가
//       page.value++;
//       console.log("게시글 데이터 불러오기 성공")
//       console.log(response.data);
//     }
// } catch (error) {
//   console.error("게시글 데이터 요청 중 오류 발생:", error.message);
// }
// };

// // IntersectionObserver 사용
// const { target } = useInfiniteScroll(fetchPosts);

// watch([sortBy, selectedButton, selectedDistrict], () => {
//   posts.value = [];
//   page.value = 1;
//   fetchPosts();
// });

// 글쓰기 페이지로 이동
const goToPostCreate = () => {
  router.push({ name: "PostCreate" });
};

onMounted(fetchPosts);
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
