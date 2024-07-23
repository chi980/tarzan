<template>
  <div class="sub-container">
    <div class="center-container">
      <div class="my-profile">
        <span id="my-id">abc1234</span>
        <span id="my-address">서울시 중구 거주중</span>
        <button id="edit-button">수정</button>
      </div>
      <div class="my-content">
        <div class="result-bar">
          <ResultBar />
        </div>
        <h2 class="title">내가 작성한 게시글</h2>
      </div>
      <div class="my-content">
        <div class="result-bar">
          <ResultBar />
        </div>
        <h2 class="title">내가 작성한 댓글</h2>
      </div>
      <div class="my-contnet">
        <div class="result-bar">
          <ResultBar />
        </div>
        <h2 class="title">내가 작성한 후기</h2>
      </div>
    </div>
    <BottomBar />
  </div>
</template>
<script>
import BottomBar from '../components/common/BottomBar.vue';
import ResultBar from '../components/common/ResultBar.vue';

export default {
  components : {
    BottomBar,
    ResultBar,
  }
}
</script>
<style lang="scss">
  .sub-container {
    display: flex;
    flex-direction: column;
  }

  .center-container {
    flex-grow: 1;
  }
</style>