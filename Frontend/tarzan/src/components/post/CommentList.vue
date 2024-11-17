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

<script setup>
import { ref, onMounted } from 'vue';
import { axiosInstance } from "@/plugins/axiosPlugin";
import CommentItem from '@/components/post/CommentItem.vue';

const comments = ref([
  { writer: '톡기', content: '저는 주로 네일클로버 세탁방을 방문합니다.', time: '2024-07-01T08:00:00' },
  { writer: '화이트', content: '하얀 세탁소 추천이요', time: '2024-07-01T09:00:00' },
  { writer: '사자', content: '사자 세탁방도 좋아요.', time: '2024-07-01T10:00:00' },
]); 

const fetchComments = async () => {
  const queryParams = new URLSearchParams({
    size: 5,
    page: 1,
    sortBy: '최신순', // 정렬 기준
    boardIdx: '10', // 게시글 ID
  }).toString();

  try {
    const response = await axiosInstance.get(`/v1/comments?${queryParams}`); // 경로 수정
    if (response.data.success) {
      comments.value = response.data.data.list;
      console.log('댓글 목록 가져오기 성공:', comments.value);
    } else {
      console.error('댓글 가져오기 실패:', response.data.message);
      alert(`Error: ${response.data.message}`);
    }
  } catch (error) {
    console.error('Error fetching comments:', error);
    alert('댓글을 불러오는 중 오류가 발생했습니다.');
  }
};

onMounted(fetchComments);
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