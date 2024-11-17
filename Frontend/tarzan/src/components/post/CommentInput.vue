<template>
  <div class="comment-input">
    <input type="text" placeholder="댓글을 입력해주세요." v-model="comment_content" />
    <button @click="submit" class="create-button">
      <img src="@/assets/icons/Filter/comment-input-icon.png" alt="comment-input-icon">
    </button>
  </div>
</template>

<script setup>
import { axiosInstance } from "@/plugins/axiosPlugin";
import { ref, defineProps } from 'vue';

// Props 정의
const props = defineProps({
  boardIdx: {
    type: Number,
    required: true
  }
});

// 상태 변수
const comment_board_idx = ref(1); // 아직 게시물이 없어서 id 없음, 임시로 지정 -> api 연결 불가
const comment_content = ref('');
const message = ref('');

// API: 댓글 생성
const submit = async () => {
  try {
    const response = await axiosInstance.post('/v1/comments', {
      comment_board_idx: comment_board_idx.value,
      comment_content: comment_content.value,
    });
    console.log(comment_board_idx.value, comment_content.value);

    if (response.data.success) {
      message.value = '댓글이 성공적으로 생성되었습니다!';
      console.log(response.data);
    } else {
      console.error('Failed:', response.data.message);
      message.value = `Error: ${response.data.message}`; // 사용자에게 오류 메시지 표시
    }
  } catch (error) {
    console.error('Error creating post:', error);
    message.value = '댓글을 생성하는 데 실패했습니다.' + comment_content.value; // 사용자에게 알림
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