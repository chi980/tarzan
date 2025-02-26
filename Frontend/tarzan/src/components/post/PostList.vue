<template>
  <div v-if="posts.length" class="post-list-container">
    <PostItem
    v-for="post in posts"
    :key="post.board_id"
    :post="post"
    @click="goToPostDetail(post.board_id)"/>
  </div>
  
  <div v-else class="post-list-container">
    <p>게시물이 없습니다.</p>
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
    overflow-y: auto;
    gap: 16px;
  }
</style>
