<template>
  <div class="comment-container">
    <div class="comment-header">
        <span id="comment-writer">{{ comment.writer }}</span>
        <div class="comment-edit-container">
          <img id="comment-edit-icon" 
          src="@/assets/icons/Filter/comment-edit-icon.png" 
          alt="comment-edit-icon.png">
        </div>
    </div>
    <div class="comment-content">
      <p>{{ comment.content }}</p>
    </div>
    <div class="comment-time">
      <span>{{ comment.time }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Comment',
  props: {
    comment: {
      type: Object,
      required: true
    },
    index: {
      type: Number,
      required: true
    },
  },
  data() {
    return {
      edit: [
        { id: 1, name: "수정하기", value: "edit" },
        { id: 2, name: "삭제하기", value: "delete" }
      ]
    };
  }
}
</script>

<style scoped lang="scss">
.comment-container {
  display: flex;
  flex-direction: column;
  gap: 8px;
  text-align: left;
  // @include custom-padding-x($padding-size: $padding-default);
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
</style>