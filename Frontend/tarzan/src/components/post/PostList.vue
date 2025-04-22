<template>
  <div v-if="posts.length" class="post-list-container">
    <PostItem
      v-for="post in posts"
      :key="post.board_id"
      :post="post"
      @click="goToPostDetail(post.board_id)"
    />
  </div>

  <div v-else class="post-list-container">
    <p>아직 게시물이 없습니다.</p>
  </div>
</template>

<script>
import PostItem from "@/components/post/PostItem.vue";

export default {
  components: {
    PostItem,
  },
  props: {
    posts: {
      type: Array,
      required: true,
    },
  },
  methods: {
    goToPostDetail(postId) {
      this.$router.push({ name: "PostDetail", params: { id: postId } });
    },
  },
};
</script>

<style scoped lang="scss">
.post-list-container {
  display: flex;
  flex-direction: column;
  gap: 16px;

  background-color: aliceblue;
}

.post-list-container > p {
  color: #888;
  font-size: 16px;
  font-weight: 500;
  margin-top: 20px;
}
</style>
