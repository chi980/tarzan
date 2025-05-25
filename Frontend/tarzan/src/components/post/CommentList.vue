<template>
  <div class="comment-list-container">
    <div class="comment-items-container">
      <CommentItem
        v-for="comment in comments"
        :key="comment.comment_id"
        :comment="comment" />
      <NonContent v-if="comments.length === 0" :value="'댓글이 없습니다.'" />
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";
import CommentItem from "@/components/post/CommentItem.vue";
import NonContent from "@/components/common/NonContent.vue";

const props = defineProps({
  comments: {
    type: Array,
    required: true,
  },
});
</script>

<style scoped lang="scss">
.comment-list-container {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  justify-content: space-between;
  gap: 16px;
}

.comment-list-container .comment-items-container {
  display: flex;
  flex-direction: column;
}

#comment-more-button {
  width: 100%;
  height: 100%;
  border-radius: 0;
  font-size: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
}

#comment-more-icon {
  @include custom-icon-style;
}
</style>
