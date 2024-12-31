<template>
  <div class="sub-container">

    <div class="center-container">
      <div class="my-profile">
          <div class="profile-info">
              <span class="profile-id">{{ userNickname }}</span>
              <span class="profile-address">{{ userGu }} 거주중</span>
          </div>
          <button class="profile-edit-button" @click="goToEditProfile">수정</button>
      </div>

      <div class="my-content">
        <h2 class="title">내가 작성한 게시글</h2>
        <div class="result-bar">
          <ResultBar 
            resultTitle="결과" 
            :sortOptions="sortOptions"
            @updateSortBy="updateSortBy"
          />
          <div class="post">
            <PostList :posts="posts" />
          </div>
        </div>
      </div>

      <div class="my-content">
        <h2 class="title">내가 작성한 댓글</h2>
        <div class="result-bar">
          <ResultBar 
            resultTitle="결과" 
            :sortOptions="sortOptions"
          />
        </div>
        <div class="my-comment">
          <CommentList :comments="comments" /> 
        </div>
      </div>

      <div class="my-content">
        <h2 class="title">내가 작성한 후기</h2>
        <div class="result-bar">
          <ResultBar resultTitle="결과" />
        </div>
        <div class="my-review">
          <ReviewList />
        </div>
      </div>
    </div>

    <BottomBar />
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter } from 'vue-router';
import { axiosInstance } from "@/plugins/axiosPlugin";
import { useAuthStore } from "@/stores/authStore";

import BottomBar from '../components/common/BottomBar.vue';
import ResultBar from '../components/common/ResultBar.vue';
import CommentList from '../components/post/CommentList.vue';
import PostList from '../components/post/PostList.vue';
import ReviewList from '../components/review/ReviewList.vue';

// Vue Router 사용
const router = useRouter();

// authStore에서 사용자 정보 가져오기
const authStore = useAuthStore();
const userNickname = computed(() => authStore.nickname);
const userGu = computed(() => authStore.gu);

// 정렬 기준
// const sortBy = ref('최신순');             
// const sortOptions = ref([
//   { idx: 0, value: "latest", name: "최신순" },
//   { idx: 1, value: "views", name: "조회수순" },
//   { idx: 2, value: "oldest", name: "오래된순" },
// ]);

// 정렬 기준 변경 시 목록 업데이트
// const updateSortBy = (selectedIndex) => {
//   const selectedOption = sortOptions.value.find(
//     (option) => option.idx === selectedIndex
//   );
//   if (selectedOption) {
//     sortBy.value = selectedOption.name;
//     fetchUserPosts();
//     console.log("현재 정렬 기준:", sortBy.value);
//   }
// };

const posts = ref([ ]);   // 게시물 목록
const comments = ref([ ]); // 댓글 목록

// API: 사용자의 게시글 데이터 불러오기
const fetchUserPosts = async () => {
  const queryParams = new URLSearchParams({
    size: 5,
    page: 0,
    sortBy: '최신순',
  }).toString();

  try {
    const response = await axiosInstance.get(`/v1/user/board?${queryParams}`);

    if (response.data.success) {
      console.log("사용자 게시글 목록 가져오기 성공!");
      posts.value = response.data.data.list;
    } else {
      console.error("API 실패:", response.data.message);

    }
  } catch (error) {
    console.error("게시글 데이터 요청 중 오류 발생:", error);
  }
};

// API: 사용자의 댓글 데이터 불러오기
const fetchUserComments = async () => {
  const queryParams = new URLSearchParams({
    size: 5,
    page: 0,
    sortBy: '최신순',
  }).toString();

  try {
    const response = await axiosInstance.get(`/v1/user/comments?${queryParams}`);

    if (response.data.success) {
      console.log("사용자 댓글 목록 가져오기 성공!");
      comments.value = response.data.data.list;
    } else {
      console.error("API 실패:", response.data.message);

    }
  } catch (error) {
    console.error("댓글 데이터 요청 중 오류 발생:", error);
  }
};

onMounted(async () => {
  await fetchUserPosts();
  await fetchUserComments();
});

// 수정 페이지로 이동
const goToEditProfile = () => {
  router.push({ name: 'EditProfile' }); // 라우트 이름으로 이동
};
</script>

<style lang="scss">
  .sub-container {
    display: flex;
    flex-direction: column;
    background-color: #EDEDED;
  }

  .center-container {
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    width: 100%;
    box-sizing: border-box;
    gap: 10px;
    overflow-y: auto;
    @include custom-scrollbar-style;
  }

  .my-profile {
    background-color: white;
    display: flex;
    @include custom-padding-x;
    @include custom-padding-y(30px);
    box-sizing: border-box;
  }

  .my-profile .profile-info {
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    text-align: left;
    gap: 8px;
  }

  .my-profile .profile-info .profile-id {
    @include custom-text-bold(14px);
  }

  .my-profile .profile-info .profile-address {
    font-size: 12px;
    color: #707070;
  }

  .my-profile .profile-edit-button {
    background-color: white; 
    border: 1px solid #dcdcdc;
    border-radius: 20px; 
    font-size: 12px; 
    transition: background-color 0.3s;
  }

  .my-profile .profile-edit-button:active {
    background-color: #e0e0e0; /* 클릭했을 때 배경색 변화 */
  }

  .my-content {
    background-color: white;
    flex-grow: 1;
    @include custom-padding-x;
    @include custom-padding-y(24px);
    box-sizing: border-box;
  }

  .my-content .title {
    @include custom-text-bold($font-size: 18px);
    text-align: left;
  }

  .my-content .result-bar {
    padding-top: 10px;
  }
</style>