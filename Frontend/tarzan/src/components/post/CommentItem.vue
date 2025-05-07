<template>
  <div class="comment-container">
    <div class="comment-header">
      <span id="comment-writer">{{ comment.comment_writer_nickname }}</span>
      <EditButton
        :isAuthor="comment.comment_is_writer"
        :targetId="comment.comment_id"
        :type="'comment'"
      />
    </div>
    <div class="comment-content">
      <p>{{ comment.comment_content }}</p>
    </div>
    <div class="comment-time">
      <!-- <span>{{ comment.comment_created_at }}</span> -->
      <span>{{ formatSmartTime(comment.comment_created_at) }}</span>
    </div>
  </div>
</template>

<script setup>
import EditButton from "./EditButton.vue";
import { formatSmartTime } from "@/utils/formatTime";

const props = defineProps({
  comment: {
    type: Object,
    required: true,
  },
});
</script>

<style scoped lang="scss">
.comment-container {
  display: flex;
  flex-direction: column;
  gap: 8px;
  text-align: left;
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
  color: #9f9f9f;
  padding-top: 5px;
}
</style>
