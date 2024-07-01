<template>
  <div class="post-item" @click="handleClick">
    <div class="post-categry">
      <span class="category">{{ post.tag }}</span>
    </div>

    <div class="post-content">
      <h3 class="title">{{ post.title }}</h3>
      <p class="content">{{ post.content }}</p>
    </div>

    <div class="post-comment">
      <font-awesome-icon :icon="['far', 'comment']" />
      <span class="comment-count">{{ post.comments }}</span>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    post: Object,  // 부모 컴포넌트로부터 post라는 객체를 props로 받음
  },
  methods: {
    handleClick() {
      this.$emit('click');
    },
  },
};
</script>

<style scoped>
.post-item {
  /* 스타일 정의 */
  cursor: pointer;
}
</style>
