<template>
  <div class="post-item-container" @click="handleClick">
    <div class="post-tag">
      <span>{{ post.board_tag_kor }}</span>
    </div>
    <div class="post-content">
      <h3 id="post-title">{{ post.board_title }}</h3>
      <p id="post-content">{{ post.board_content }}</p>
    </div>
    <div class="post-meta">
      <div class="time-views">
        <span id="time">{{ formatSmartTime(post.board_created_at) }} · </span>
        <span id="views"> 조회 {{ post.board_read_count }}</span>
      </div>
      <div class="comment">
        <!-- <font-awesome-icon id="comment-icon" :icon="['far', 'comment']" /> -->
        <span id="comment-count">{{ post.comments }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from "vue";
import { formatSmartTime } from "@/utils/formatTime";

const props = defineProps({
  post: {
    type: Object,
  },
});
</script>

<style scoped lang="scss">
.post-item-container {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  // @include custom-margin-x;
  @include custom-padding-y($padding-default);
  gap: 8px;
}

.post-item-container .post-tag {
  background-color: #f2f3f9;
  @include custom-padding($margin-small);
  border-radius: 10px;
  @include custom-text($font-size: 10px, $font-color: $text-color-default);
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
  width: 100%;
  white-space: nowrap; /* 텍스트를 한 줄로 유지 */
  overflow: hidden; /* 내용이 넘치면 숨김 */
  text-overflow: ellipsis; /* 넘치는 텍스트를 말줄임표로 표시 */
}

#post-content {
  font-size: 12px;
  color: #9f9f9f;
  width: 100%;
  white-space: nowrap; /* 텍스트를 한 줄로 유지 */
  overflow: hidden; /* 내용이 넘치면 숨김 */
  text-overflow: ellipsis; /* 넘치는 텍스트를 말줄임표로 표시 */
}

.post-item-container .post-meta {
  display: flex;
  width: 100%;
  justify-content: space-between;
  font-size: 12px;
  color: #9f9f9f;
}
</style>
