<template>
  <div class="comment-input">
    <input 
    v-model="commentText"
    type="text" 
    placeholder="댓글을 입력해주세요." 
    />
    <button @click="createComment" class="create-button">
      <img src="@/assets/icons/Filter/comment-input-icon.png" alt="comment-input-icon">
    </button>
  </div>
</template>

<script setup>
import { ref, defineProps, defineEmits } from 'vue';
import { axiosInstance } from "@/plugins/axiosPlugin";

// Props 정의
const props = defineProps({
  boardIdx: {
    type: Number,
    required: true
  }
});

// 이벤트 정의
const emit = defineEmits(['commentSubmitted']);

// 상태 변수
const commentText = ref('');

// API: 댓글 생성
const createComment = async () => {
  if (commentText.value.trim() === '') {
    alert('댓글 내용을 입력하세요!');
    return;
  }

  try {
    const response = await axiosInstance.post('/v1/comments', {
      comment_board_idx: props.boardIdx,
      comment_content: commentText.value, 
    });
    console.log(props.boardIdx,commentText.value);

    if (response.data.success) {
      commentText.value = ''; // 입력란 초기화
      emit('commentSubmitted');
      console.log("댓글 등록 성공!");
    } else {
      console.error("API 실패:", response.data.message);
    }
  } catch (error) {
    console.error("댓글 생성 중 오류 발생:", error);
  }
};
</script>

<style lang="scss" scoped>
.comment-input {
  display: flex;
  align-items: center;
  width: 100%;
  position: absolute;
  bottom: 20px;
  z-index: 100;
}

.comment-input input {
  @include custom-margin-x($margin-small);
  @include custom-padding-x;
  flex-grow: 1;  
  height: 48px;
  appearance: none;
  border: none;
  outline: none;
  background-color: $input-color;
  border-radius: 13px;
  font-size: 16px;
  font-weight: 400;
  color: $text-color-default;
}

.comment-input button {
  background-color: $primary-color-default;
  @include custom-margin-x($margin-small);
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  // @include custom-shadow-style("");
}

.comment-input img {
  width: 20px;
}
</style>