<template>
  <div v-if="posts.length" class="post-list-container">
    <PostItem
      v-for="post in posts"
      :key="post.board_id"
      :post="post"
      @click="goToPostDetail(post.board_id)" />
  </div>

  <div v-else class="post-list-container">
    <NonContent :value="'내용이 없습니다.'" />
  </div>
</template>

<script>
import PostItem from "@/components/post/PostItem.vue";
import NonContent from "@/components/common/NonContent.vue";

export default {
  components: {
    PostItem,
    NonContent,
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

  background-color: white;
}

.post-list-container > p {
  color: #888;
  font-size: 16px;
  font-weight: 500;
  margin-top: 20px;
}
</style>
