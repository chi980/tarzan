<template>
  <div class="post-list-container">
    <div class="post-items-container">
      <ResultBar />
      <PostItem
      v-for="post in posts"
      :key="post.id"
      :post="post"
      @click="goToPostDetail(post.id)"/>
    </div>
    <div class="load-more-container">
      <button id="load-more-btn">
        더보기
        <img
          :src="arrowDownSrc"
          alt="arrowDown"
          class="input-item-image"
        />
      </button>
    </div>
  </div>
</template>
<script>
import ResultBar from "@/components/community/ResultBar.vue";
import PostItem from './PostItem.vue';

export default {
  components: {
    PostItem,
    ResultBar,
  },
  data() {
    return {
      posts: [
        { id: 1, tag: '교통', title: 'First Post', content: 'This is the first post.', comments: '3'},
        { id: 2, tag: '정보', title: '오늘 중구 시청역더플라자호텔 버스정류장  앞에서 사고 났어요!', content: 'This is the second post. XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX', comments: '5'},
        // 나중엔 진짜 데이터로
      ],
    };
  },
  methods: {
    goToPostDetail(postId) {
      this.$router.push({ name: 'PostDetail', params: { id: postId } });
    },
  },
};
</script>
<style scoped lang="scss">
  .post-list-container {
    display: flex;
    flex-direction: column;
    flex-grow: 1; /* 부모 요소에 높이를 채우도록 설정 */
    justify-content: space-between;
  }

  .load-more-container {
    background-color: orange;
  }
  
  #load-more-btn {
    width: 100%;
    height: 100%;
    border-radius: 0;
    font-size: 14px;
  }

  .input-item-image {
  @include custom-icon-style;
  }
  
</style>
