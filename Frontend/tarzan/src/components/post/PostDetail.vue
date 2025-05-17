<template>
  <div class="sub-container">
    <PostTopBar
      v-if="post.board_is_writer !== undefined"
      :isAuthor="post.board_is_writer"
      :boardIdx="boardIdx"
      type="'post'"
      class="top-bar" />
    <div class="center-container">
      <!-- 게시글 상세 내용 -->
      <div class="post-detail-container">
        <div class="post-tag">
          <span>{{ post.board_tag_kor }}</span>
        </div>
        <div class="post-writer">
          <span clas>{{ post.board_writer_nickname }}</span>
        </div>
        <div class="post-title">
          <h2>{{ post.board_title }}</h2>
        </div>
        <div class="post-content">
          <p>{{ post.board_content }}</p>
        </div>
        <div class="post-time">
          <!-- <span>{{ post.board_created_at }}</span> -->
          <span>{{ formatSmartTime(post.board_created_at) }} · </span>
        </div>
      </div>

      <!-- 댓글 영역 -->
      <div class="comment-container">
        <div class="comment-list">
          <CommentList :comments="comments" />
          <div ref="target" style="height: 1px"></div>
        </div>

        <CommentInput class="comment-input" :boardIdx="boardIdx" />
      </div>
    </div>

    <BottomBar class="bottom-bar" />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import { axiosInstance } from "@/plugins/axiosPlugin";

import BottomBar from "@/components/common/BottomBar.vue";
import PostTopBar from "./PostTopBar.vue";
import CommentInput from "./CommentInput.vue";
import CommentList from "./CommentList.vue";

import { useInfiniteScroll } from "@/composables/useInfiniteScroll";
import { formatSmartTime } from "@/utils/formatTime";

const route = useRoute();

// 게시글 상세 정보
const post = ref({}); // 게시글
const comments = ref([]); // 댓글 목록
const boardIdx = route.params.id; // 게시글 ID

// API: 게시글 상세 정보 가져오기
const fetchPostDetail = async () => {
  try {
    const response = await axiosInstance.get(`/v1/board/${boardIdx}`);
    if (response.data.success) {
      post.value = response.data.data;
      console.log("게시물 상세 성공");
    } else {
      console.error("게시물 상세 실패:", response.data.message);
    }
  } catch (error) {
    console.error("게시물 상세 오류:", error);
  }
};

// API: 해당 게시글의 댓글 목록 가져오기
const commentPage = ref(0);
const commentSize = 7;
const isLastPage = ref(false);
const isLoading = ref(false); // ✅ 추가

// const fetchComments = async () => {
//   if (isLastPage.value) return;

//   const queryParams = new URLSearchParams({
//     size: commentSize,
//     page: commentPage.value,
//     sortBy: "최신순",
//     boardIdx: boardIdx,
//   }).toString();

//   try {
//     const response = await axiosInstance.get(`/v1/comments?${queryParams}`);
//     if (response.data.success) {
//       // 댓글 목록을 기존 댓글 목록에 추가
//       const list = response.data.data.list;
//       comments.value.push(...list);
//       commentPage.value++;
//       isLastPage.value = list.length < commentSize; // 마지막 페이지인지 확인

//       console.log("댓글 목록 성공:", comments.value);
//     } else {
//       console.error("댓글 목록 실패:", response.data.message);
//       alert(`Error: ${response.data.message}`);
//     }
//   } catch (error) {
//     console.error("댓글 목록 오류:", error);
//     alert("댓글을 불러오는 중 오류가 발생했습니다.");
//   }
// };

// API: 게시글 댓글 가져오기(useInfiniteScroll 사용)
const fetchComments = async () => {
  if (isLoading.value || isLastPage.value) return; // ✅ 로딩 중이면 return

  isLoading.value = true; //

  const queryParams = new URLSearchParams({
    size: commentSize,
    page: commentPage.value,
    sortBy: "최신순",
    boardIdx: boardIdx,
  }).toString();

  try {
    const response = await axiosInstance.get(`/v1/comments?${queryParams}`);
    if (response.data.success) {
      const newComment = response.data.data.list;
      if (commentPage.value === 0) {
        // ✅ 첫 페이지는 덮어쓰기
        comments.value = newComment;
      } else {
        comments.value = [...comments.value, ...newComment];
      }

      // 다음 페이지로 넘기기
      commentPage.value++;

      // 받아온 데이터 개수가 commentSize보다 작으면 마지막 페이지
      if (newComment.length < commentSize) {
        isLastPage.value = true;
      }

      console.log("댓글 무한스크롤 성공:", comments.value);
    }
  } catch (error) {
    console.error("댓글 데이터 요청 중 오류 발생:", error.message);
  } finally {
    isLoading.value = false;
  }
};

// ✨ 여기!! fetchComments를 넘겨서 세팅
const { target, setupObserver } = useInfiniteScroll(fetchComments);

onMounted(async () => {
  await Promise.all([fetchPostDetail(), fetchComments()]);
  setupObserver();
});
</script>

<style lang="scss" scoped>
.sub-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.top-bar,
.bottom-bar {
  flex-shrink: 0;
}

.center-container {
  display: flex;
  flex-direction: column;
  background-color: #ededed;
  flex-grow: 1;
  width: 100%;
  height: 100%;
  gap: 10px;
  overflow-y: auto;
  @include custom-text();
}

.post-detail-container {
  background-color: white;
  text-align: left;
  display: flex;
  flex-direction: column;
  gap: 12px;
  @include custom-padding-x;
  @include custom-padding-y;
}

.center-container .post-detail-container .post-tag span {
  display: inline-block;
  background-color: #f2f3f9;
  @include custom-padding($margin-small);
  border-radius: 10px;
  font-size: 10px;
}

.center-container .post-detail-container .post-writer {
  color: #9f9f9f;
  font-size: 12px;
}

.center-container .post-detail-container .post-title {
  @include custom-text-bold($font-color: $text-color-default, $font-size: 19px);
}

.center-container .post-detail-container .post-content {
  line-height: 1.5;
  font-size: 13px;
}

.center-container .post-detail-container .post-time {
  font-size: 12px;
  color: #9f9f9f;
  margin-top: 12px;
}

.comment-container {
  // position: relative;
  flex-grow: 1;
  padding-top: 15px;
  padding-bottom: 80px;
  background-color: white;
  @include custom-padding-x;

  .comment-input {
    position: fixed;
    bottom: 80px;
    z-index: 10;
    left: 50%;
    transform: translateX(-50%);
    max-width: 355px;
  }
}
</style>
