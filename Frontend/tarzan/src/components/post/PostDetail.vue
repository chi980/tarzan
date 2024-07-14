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
        <PostComment
          v-for="(comment, index) in comments"
          :key="index"
          :comment="comment"
          :index="index"
          @delete-comment="deleteComment"
        />
      </div>
    </div>
    <BottomBar />
  </div>
</template>

<script>
import BottomBar from "@/components/common/BottomBar.vue";
import PostComment from "@/components/post/PostComment.vue";
import PostTopBar from "./PostTopBar.vue";

export default {
  components: {
    BottomBar,
    PostComment,
    PostTopBar,
  },
  data() {
    return {
      post: null,
      comments: [
        { writer: '톡기', content: '저는 주로 네일클로버 세탁방을 방문합니다.', time: '2024-07-01T08:00:00' },
        { writer: '화이트', content: '하얀 세탁소 추천이요', time: '2024-07-01T09:00:00' },
        { writer: '사자', content: '사자 세탁방도 좋아요.', time: '2024-07-01T10:00:00' },
      ],
    };
  },
  created() {
    const postId = this.$route.params.id;
    // 실제 프로젝트에서는 API를 통해 게시물 데이터를 가져옴
    this.post = {
      id: postId,
      tag: '질문',
      writer: '호랑이',
      title: `${postId} : 중구 신당동 주민분들!! 질문있습니다.`,
      content: `안녕하세요 새로 이사오게 되었습니다. 반갑습니다! 궁금한 것이 있어 이렇게 글을 씁니다.안녕하세요 새로 이사오게 되었습니다. 반갑습니다! 궁금한 것이 있어 이렇게 글을 씁니다.안녕하세요 새로 이사오게 되었습니다. 반갑습니다! 궁금한 것이 있어 이렇게 글을 씁니다.안녕하세요 새로 이사오게 되었습니다. 반갑습니다! 궁금한 것이 있어 이렇게 글을 씁니다.`,
      elapsedTime: '20시간 전',
    };
  },
  
};
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
    flex-grow: 1;
    padding-top: 20px;
    background-color: white;
  }
</style>