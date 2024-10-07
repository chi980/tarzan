<template>
  <div class="post-list-container">
    <div v-if="posts.length" class="post-items-container">
      <PostItem
      v-for="post in posts"
      :key="post.id"
      :post="post"
      @click="goToPostDetail(post.id)"/>
    </div>
    <div v-else class="post-items-container">
      <p>No posts available.</p>
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
import PostItem from '@/components/post/PostItem.vue';

export default {
  components: {
    PostItem,
  },
  props: {
    posts: {
      type: Array,
      required: true
    }
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
    flex-grow: 1; 
    justify-content: space-between;
    gap: 16px;
  }

  .post-list-container .post-items-container {
    display: flex;
    flex-direction: column;
    gap: 15px;
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
