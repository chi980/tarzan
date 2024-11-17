<template>
  <div class="sub-container">
    <PostTopBar 
      v-if="post.board_is_writer !== undefined" 
      :isAuthor="post.board_is_writer" 
      :boardIdx="boardIdx" 
    />
    <div class="center-container">
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
          <!-- 경과시간은 컴포넌트로 만들어야할 듯 -->
          <span>{{ post.board_created_at }}</span>
        </div>
      </div>
      <div class="comment-container">
        <div class="comment-list">
          <CommentList :post-id="post.id" /> 
        </div>
        <CommentInput :board-idx="post.id" /> 
      </div>
    </div>
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

// 컴포넌트 등록
const components = {
  PostTopBar,
  BottomBar,
  CommentList,
  CommentInput,
};

const route = useRoute(); 
const post = ref({}); 
const boardIdx = route.params.id; 

const fetchPostDetail = async () => {
  try {
    const response = await axiosInstance.get(`/v1/board/${boardIdx}`);
    console.log(response);
    
    if (response.data.success) {
      post.value = response.data.data; // ref로 선언된 posts에 값 할당
      console.log('게시물 상세 가져오기 성공');
      console.log(response.data.data);
    } else {
      console.error('Failed:', response.data.message);
    }
  } catch (error) {
    console.error('Error fetching posts:', error);
  }
};

// 컴포넌트가 마운트될 때 fetchPosts 호출
onMounted(fetchPostDetail); 
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