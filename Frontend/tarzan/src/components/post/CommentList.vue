<template>
  <div class="comment-list-container">
    <div class="comment-items-container">
      <CommentItem
          v-for="(comment, index) in comments"
          :key="index"
          :comment="comment"
          :index="index"
          @delete-comment="deleteComment"
        />
    </div>

    <div class="comment-more-container">
      <button id="comment-more-button">
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
<script>
import CommentItem from '@/components/post/CommentItem.vue';
import { Comment } from 'vue';

export default {
  components: {
    CommentItem,
  },
  data() {
    return {
      comments: [
        { writer: '톡기', content: '저는 주로 네일클로버 세탁방을 방문합니다.', time: '2024-07-01T08:00:00' },
        { writer: '화이트', content: '하얀 세탁소 추천이요', time: '2024-07-01T09:00:00' },
        { writer: '사자', content: '사자 세탁방도 좋아요.', time: '2024-07-01T10:00:00' },
      ],
    };
  },
};
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