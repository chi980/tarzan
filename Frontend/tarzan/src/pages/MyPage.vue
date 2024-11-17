<template>
  <div class="sub-container">

    <div class="center-container">
      <div class="my-profile">
          <div class="profile-info">
              <span class="profile-id">abc1234</span>
              <span class="profile-address">서울시 중구 거주중</span>
          </div>
          <button class="profile-edit-button" @click="goToNextPage">수정</button>
      </div>

      <div class="my-content">
        <h2 class="title">내가 작성한 게시글</h2>
        <div class="result-bar">
          <ResultBar resultTitle="결과" />
          <div class="post">
            <PostList />
          </div>
        </div>
      </div>

      <div class="my-content">
        <h2 class="title">내가 작성한 댓글</h2>
        <div class="result-bar">
          <ResultBar resultTitle="결과" />
        </div>
        <div class="my-comment">
          <CommentList />
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
<script>
import BottomBar from '../components/common/BottomBar.vue';
import ResultBar from '../components/common/ResultBar.vue';
import CommentList from '../components/post/CommentList.vue';
import PostList from '../components/post/PostList.vue';
import ReviewList from '../components/review/ReviewList.vue';

export default {
  components : {
    BottomBar,
    ResultBar,
    PostList,
    CommentList,
    ReviewList,
  },
  methods: {
    goToNextPage() {
      this.$router.push({ name: 'EditProfile' });
    }
  },
}
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