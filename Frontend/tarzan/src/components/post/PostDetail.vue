<template>
  <div class="sub-container">
    <PostTopBar
      v-if="post.board_is_writer !== undefined"
      :isAuthor="post.board_is_writer"
      :boardIdx="boardIdx"
      type="'post'"
      class="top-bar"
    />
    <div class="center-container">
      <!-- 게시글 상세 내용 -->
      <div class="post-detail-container">
        <div class="post-tag">
          <span>{{ post.board_tag }}</span>
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
          <span>{{ post.board_created_at }}</span>
        </div>
      </div>

      <!-- 댓글 영역 -->
      <div class="comment-container">
        <div class="comment-list">
          <CommentList :comments="comments" />
        </div>
        <CommentInput
          class="comment-input"
          :boardIdx="boardIdx"
          @commentSubmitted="onNewComment"
        />
        <div ref="commentTarget" class="loading-trigger"></div>
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

const route = useRoute();

// 게시글 상세 정보
const post = ref({}); // 게시글
const comments = ref([]); // 댓글 목록
const boardIdx = route.params.id; // 게시글 ID

// 댓글 추가 후 댓글 목록 갱신
const onNewComment = (newComment) => {
  comments.value.unshift(newComment); // 앞에 추가
};

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
const commentSize = 5;
const isLastPage = ref(false);

const fetchComments = async () => {
  if (isLastPage.value) return;

  const queryParams = new URLSearchParams({
    size: commentSize,
    page: commentPage.value,
    sortBy: "최신순",
    boardIdx: boardIdx,
  }).toString();

  try {
    const response = await axiosInstance.get(`/v1/comments?${queryParams}`);
    if (response.data.success) {
      // 댓글 목록을 기존 댓글 목록에 추가
      const list = response.data.data.list;
      comments.value.push(...list);
      commentPage.value++;
      isLastPage.value = list.length < commentSize; // 마지막 페이지인지 확인

      console.log("댓글 목록 성공:", comments.value);
    } else {
      console.error("댓글 목록 실패:", response.data.message);
      alert(`Error: ${response.data.message}`);
    }
  } catch (error) {
    console.error("댓글 목록 오류:", error);
    alert("댓글을 불러오는 중 오류가 발생했습니다.");
  }
};

const { target: commentTarget } = useInfiniteScroll(fetchComments);

onMounted(async () => {
  await Promise.all([fetchPostDetail(), fetchComments()]);
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
