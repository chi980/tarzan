<template>
  <div class="sub-container">
    <PostTopBar 
      v-if="post.board_is_writer !== undefined" 
      :isAuthor="post.board_is_writer" 
      :boardIdx="boardIdx" 
      type="'post'"
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
          <CommentList
            :comments="comments"
          /> 
        </div>
        <CommentInput 
          :boardIdx="boardIdx" 
          @commentSubmitted="fetchComments" />
      </div>
    </div>

    <!-- 하단 네비게이션 -->
    <BottomBar />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router'; 
import { axiosInstance } from "@/plugins/axiosPlugin";
import BottomBar from "@/components/common/BottomBar.vue";
import PostTopBar from "./PostTopBar.vue";
import CommentInput from "./CommentInput.vue";
import CommentList from "./CommentList.vue";

// 상태 변수
const route = useRoute(); 
const post = ref({}); // 게시글 정보
const comments = ref([]); // 댓글 목록
const boardIdx = route.params.id; // 게시글 ID 

console.log("PostDetail의 게시물 번호 : " + boardIdx + typeof boardIdx);

// API: 게시글 상세 정보 가져오기
const fetchPostDetail = async () => {
  try {
    const response = await axiosInstance.get(`/v1/board/${boardIdx}`);
    if (response.data.success) {
      post.value = response.data.data;
      console.log('게시물 상세 성공');
    } else {
      console.error('게시물 상세 실패:', response.data.message);
    }
  } catch (error) {
    console.error('게시물 상세 오류:', error);
  }
};

// API: 해당 게시글의 댓글 목록 가져오기
const fetchComments = async () => {
  const queryParams = new URLSearchParams({
    size: 7,
    page: 0,
    sortBy: '최신순', 
    boardIdx: boardIdx,
  }).toString();

  try {
    const response = await axiosInstance.get(`/v1/comments?${queryParams}`); // 경로 수정
    if (response.data.success) {
      comments.value = response.data.data.list;
      console.log('댓글 목록 성공:', comments.value);
    } else {
      console.error('댓글 목록 실패:', response.data.message);
      alert(`Error: ${response.data.message}`);
    }
  } catch (error) {
    console.error('댓글 목록 오류:', error);
    alert('댓글을 불러오는 중 오류가 발생했습니다.');
  }
};

// 함수 호출
onMounted(async () => {
  await Promise.all([fetchPostDetail(), fetchComments()]);
});
</script>

<style lang="scss" scoped>
  .sub-container {
    display: flex;
    justify-content: space-between;
  }

  .center-container{
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    background-color: #EDEDED;
    height: 100%;
    width: 100%;
    gap: 10px;
  }

  .post-detail-container{
    background-color: white;
    text-align:left;
    display:flex;
    flex-direction: column;
    gap: 12px;
    @include custom-padding-x;
    @include custom-padding-y;
    // flex-basis: 230px;
  }

  .center-container .post-detail-container .post-tag span{
    display: inline-block;
    background-color: #F2F3F9;
    @include custom-padding($margin-small);
    border-radius: 10px;
    font-size: 10px;
  }

  .center-container .post-detail-container .post-writer {
    color: #9F9F9F;
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
    color: #9F9F9F;
    margin-top: 12px;
  }

  .comment-container {
    position: relative;
    flex-grow: 1;
    padding-top: 15px;
    background-color: white;
  }

  .comment-container .comment-list {
    @include custom-padding-x;
  }
</style>