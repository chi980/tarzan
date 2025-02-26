<template>
  <div class="sub-container">
    <TopBar @update:selected="updateDistrict" /> 
    
    <div class="center-container">
      <SearchBar 
        v-model:searchQuery="searchQuery" 
        @search="searchPosts" />
      <DescriptionComponent
          descriptionImgSrc="/src/assets/etc/Saly-25.png"
          descriptionTitle="동네주민과<br/>얘기해보세요!"
          descriptionContent="여러 정보를 얻어보세요<br/>모임도 참여할 수 있어요!"
          backgroundColor="#FFF7D9"/>

      <div class="tag-button-container">
        <TagButtonGroup v-model:selectedButton="selectedButton" :buttons="buttons" :multiple="false">
          <template v-slot:default="{ button }">
            <span>{{ button.label }}</span>
          </template>
        </TagButtonGroup>
      </div>


      <div class="result-bar-container">
        <ResultBar 
          resultTitle="결과" 
          :sortOptions="sortOptions"
          @updateSortBy="updateSortBy"
        />
      </div>

      <div class="post-llist-container">
        <PostList :posts="posts" />
      </div>

      <div class="write-button" @click="goToPostCreate">
        <img id="post-write-icon" src="@/assets/icons/Filter/post-write-icon.png" />
        글쓰기
      </div>
    </div>
    
    <BottomBar />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import { axiosInstance } from "@/plugins/axiosPlugin";

import { useInfiniteScroll } from "@/Composable/useInfiniteScroll.js";

import TopBar from "@/components/common/TopBar.vue";
import BottomBar from "@/components/common/BottomBar.vue";
import SearchBar from "@/components/common/SearchBar.vue";
import DescriptionComponent from "@/components/common/Description.vue";
import ResultBar from "@/components/common/ResultBar.vue";
import TagButtonGroup from "@/components/common/TagButtonGroup.vue";
import PostList from "@/components/post/PostList.vue";

// 태그 버튼
const buttons = ref([
  { label: '전체', value: 'ALL' },
  { label: '교통', value: 'TRANSPORT' },
  { label: '맛집', value: 'TASTE' },
  { label: '생활팁', value: 'LIFE' },
  { label: '질문', value: 'QUESTION' },
  { label: '모임', value: 'MEETING' },
  { label: '기타', value: 'ETC' },
]);

// 상태 관리
const router = useRouter();
const sortOptions = ref([
  { idx: 0, value: "latest", name: "최신순" },
  { idx: 1, value: "views", name: "조회수순" },
  { idx: 2, value: "oldest", name: "오래된순" },
]);

const posts = ref([ ]);                 // 게시물 목록
const sortBy = ref('최신순');             // 정렬 기준
const selectedButton = ref('ALL');      // 태그
const selectedDistrict = ref('JONGNO'); // 지역구
const searchQuery = ref(''); // 검색어 상태

// 글쓰기 페이지로 이동
const goToPostCreate = () => {
  router.push({ name: "PostCreate" });
};

// 정렬 기준 변경 시 목록 업데이트
const updateSortBy = (selectedIndex) => {
  const selectedOption = sortOptions.value.find(
    (option) => option.idx === selectedIndex
  );
  if (selectedOption) {
    sortBy.value = selectedOption.name;
    fetchPosts();
    console.log("현재 정렬 기준:", sortBy.value);
  }
};

// 지역구 변경 시 목록 업데이트
const updateDistrict = (district) => {
  selectedDistrict.value = district;
  fetchPosts();
}

// API: 게시글 데이터 불러오기
const fetchPosts = async () => {
  const queryParams = new URLSearchParams({
    size: 5,
    page: 0,
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

// API: 게시글 검색
const searchPosts = async (query) => {  
  const queryParams = new URLSearchParams({
    size: 5,
    page: 0,
    sortBy: sortBy.value,
    tag: selectedButton.value,
    gu: selectedDistrict.value,
    search: query,
  }).toString();

  try {
    const response = await axiosInstance.get(`/v1/board?${queryParams}`);

    if (response.data.success) {
      console.log("검색 결과 가져오기 성공!");
      posts.value = response.data.data.list;
      posts.value.forEach((post, index) => {
      console.log(`게시글 ${index + 1}:`, post);
      });
    } else {
      console.error("검색 API 실패:", response.data.message);
    }
  } catch (error) {
    console.error("검색 API 요청 중 오류 발생:", error);
  }
};

onMounted(fetchPosts);
</script>


<style lang="scss" scoped>
  .sub-container {
    justify-content: space-between;
  }

  .center-container {
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100%;
  }

  .tag-button-container {
    margin: 8px;
  }

  .result-bar-container {
    @include custom-margin-x;
  }

  .post-llist-container {
    @include custom-margin-x;
  }

  .center-container .write-button {
    @include custom-text($font-color: white, $font-weight: 800, $font-size: 14px);
    @include custom-none-select-basic;
    position: absolute;
    bottom: calc(#{$height-bottom-bar} + #{$padding-default});
    right: $padding-default;
    z-index: $z-index-button;

    display: flex;
    justify-content: center;
    align-items: center; 
    padding: 12px 14px 12px 12px;
    gap: $padding-small;

    border-radius: 20px;
    background-color: $primary-color-400;

    box-shadow: 0px 0px 10px rgba(166, 166, 166, 0.3);
  }

  #post-write-icon {
      @include custom-icon-style;
    }
</style>