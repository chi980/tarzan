<template>
  <div class="post-list">
    <PostItem
      v-for="post in posts"
      :key="post.id"
      :post="post"
      @click="goToPostDetail(post.id)"
    />
  </div>
</template>

<script>
import PostItem from './PostItem.vue';

export default {
  components: {
    PostItem,
  },
  data() {
    return {
      posts: [
        { id: 1, tag: '교통', title: 'First Post', content: 'This is the first post.', comments: '3'},
        { id: 2, tag: '정보', title: 'Second Post', content: 'This is the second post.', comments: '5'},
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

<style scoped>
</style>
