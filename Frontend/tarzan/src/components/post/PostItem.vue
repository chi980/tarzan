<template>
  <div class="post-item-container" @click="handleClick">
    <!-- <SmallTag /> -->
    <div class="post-tag">
      <span>{{ post.board_tag }}</span>
    </div>
    <div class="post-content">
      <h3 id="post-title">{{ post.board_title }}</h3>
      <p id="post-content">{{ post.board_content }}</p>
    </div>
    <div class="post-meta">
      <div class="time-views">
        <span id="time">20시간 전 · </span>
        <span id="views"> 조회 {{ post.board_read_count }}</span>
      </div>
      <div class="comment">
        <font-awesome-icon id="comment-icon" :icon="['far', 'comment']" />
        <span id="comment-count">{{ post.comments }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    post: Object,  // 부모 컴포넌트로부터 post라는 객체를 props로 받음
  },
  // methods: {
  //   handleClick() {
  //     this.$emit('click');
  //   },
  // },
};
</script>

<style scoped lang="scss">
.post-item-container {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  // @include custom-margin-x;
  // @include custom-margin-y($margin-size: 12px);
  gap: 8px;
  
}

.post-item-container .post-tag {
  background-color: #F2F3F9;
  @include custom-padding($margin-small);
  border-radius: 10px;
  font-size: 10px;
}

.post-item-container .post-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 8px;
  width: 100%;
  text-align: left;
}

#post-title {
  @include custom-text($font-size: 14px);
  width:100%;
  white-space: nowrap;  /* 텍스트를 한 줄로 유지 */
  overflow: hidden;  /* 내용이 넘치면 숨김 */
  text-overflow: ellipsis;  /* 넘치는 텍스트를 말줄임표로 표시 */
}

#post-content {
  font-size: 12px;
  color: #9F9F9F;
  width:100%;
  white-space: nowrap;  /* 텍스트를 한 줄로 유지 */
  overflow: hidden;  /* 내용이 넘치면 숨김 */
  text-overflow: ellipsis;  /* 넘치는 텍스트를 말줄임표로 표시 */
}

.post-item-container .post-meta {
  display:flex;
  width: 100%;
  justify-content: space-between;
  font-size: 12px;
  color: #9F9F9F;
}
</style>
