<template>
  <div class="sub-container">
    <PostTopBar />
    <div class="center-container">
      <div class="post-detail-container">
        <div class="post-tag">
          <span>{{ post.tag }}</span>
        </div>
        <div class="post-writer">
          <span clas>{{ post.writer }}</span>
        </div>
        <div class="post-title">
          <h2>{{ post.title }}</h2>
        </div>
        <div class="post-content">
          <p>{{ post.content }}</p>   
        </div>
        <div class="post-time">
          <!-- 경과시간은 컴포넌트로 만들어야할 듯 -->
          <span>{{ post.elapsedTime }}</span>
        </div>
      </div>
      <div class="comment-container">
        <div class="comment-list">
          <CommentList :post-id="post.id" /> <!-- CommentList에 post.id 전달 -->
        </div>
        <CommentInput :board-idx="post.id" /> <!-- CommentInput에 post.id 전달 -->
      </div>
    </div>
    <BottomBar />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router'; // Vue Router에서 route 정보 가져오기
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

// route 정보 가져오기
const route = useRoute();

// 상태 변수 정의
const post = ref({});

// onMounted로 컴포넌트가 마운트될 때 post 데이터 설정
onMounted(() => {
  const postId = route.params.id;
  post.value = {
    id: postId,
    tag: '질문',
    writer: '호랑이',
    title: `${postId} : 중구 신당동 주민분들!! 질문있습니다.`,
    content: `안녕하세요 새로 이사오게 되었습니다. 반갑습니다! 궁금한 것이 있어 이렇게 글을 씁니다.안녕하세요 새로 이사오게 되었습니다. 반갑습니다! 궁금한 것이 있어 이렇게 글을 씁니다.안녕하세요 새로 이사오게 되었습니다. 반갑습니다! 궁금한 것이 있어 이렇게 글을 씁니다.안녕하세요 새로 이사오게 되었습니다. 반갑습니다! 궁금한 것이 있어 이렇게 글을 씁니다.`,
    elapsedTime: '20시간 전',
  };
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