<template>
  <div class="comment-list-container">
    <div class="comment-items-container">
      <CommentItem
        v-for="comment in comments"
        :key="comment.comment_id"
        :comment="comment"
      />
    </div>

    <div class="comment-more-container">
      <button id="comment-more-button" @click="loadMoreComments">
        <span>더보기</span>
        <img 
          id="comment-more-icon"
          src="@/assets/icons/Arrows-chevron/Arrow-Down/Arrow-Down.png"
          alt="arrowDown"
        />
      </button>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';
import CommentItem from '@/components/post/CommentItem.vue';

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
    gap: 15px;
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