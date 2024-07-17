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
    <div class="post-more-container">
      <button id="post-more-button">
        <span>더보기</span>
        <img 
          id="post-more-icon"
          src="@/assets/icons/Arrows-chevron/Arrow-Down/Arrow-Down.png"
          alt="arrowDown"
        />
      </button>
    </div>
  </div>
</template>
<script>
import ResultBar from "@/components/common/ResultBar.vue";
import PostItem from '@/components/post/PostItem.vue';

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
  
  #post-more-button {
    width: 100%;
    height: 100%;
    border-radius: 0;
    font-size: 14px;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  #post-more-icon {
  @include custom-icon-style;
  }

</style>
