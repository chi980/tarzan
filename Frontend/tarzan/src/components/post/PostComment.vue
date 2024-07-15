<template>
  <div class="comment-container">
    <div class="comment-header">
        <span id="comment-writer">{{ comment.writer }}</span>
        <img id="comment-edit-icon" 
          src="@/assets/icons/Filter/comment-edit-icon.png" 
          alt="comment-edit-icon.png">
    </div>
    <div class="comment-content">
      <p>{{ comment.content }}</p>
    </div>
    <div class="comment-time">
      <span>{{ comment.time }}</span>
    </div>
  </div>
  <hr class="custom-hr">
</template>
<script>
export default {
  name: 'Comment',
  props: {
    comment: Object,
    index: Number,
  },
}
</script>
<style scoped lang="scss">
.comment-container {
  display: flex;
  flex-direction: column;
  gap: 8px;
  text-align: left;
  @include custom-padding-x($padding-size: $padding-default);
  @include custom-padding-y($padding-size: 3px);
}

.comment-container .comment-header {
  // background-color: yellow;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

#comment-writer {
  @include custom-text-bold($font-color: $text-color-default, $font-size: 12px);
}

#comment-edit-icon {
  width: 24px;
}

.comment-container .comment-content {
  font-size: 13px;
}

.comment-container .comment-time {
  font-size: 12px;
  color: #9F9F9F;
  padding-top: 5px;
}

.custom-hr {
  border: none;
  border-top: 1px solid #EDEDED; /* 선의 색상을 지정합니다 */
  width: 90%;
}
</style>