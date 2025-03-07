<template>
  <div class="sub-container">
    <TopBarBack title="글쓰기" />
    <div class="input-container">
      <input class="input-item" type="text" placeholder="제목을 입력해주세요" v-model="post.title" />
      <CustomSelectBox :options="tagOptions" v-model:selected="selectedTag" />
      <textarea class="input-item" placeholder="내용을 입력해주세요" v-model="post.content" ></textarea>
    </div>

    <div v-if="message">{{ message }}</div>

    <div class="post-button">
      <button @click="goToBack" class="cancle-button">취소</button>
      <button @click="submit" class="create-button">글쓰기</button>
    </div>
  </div>
</template>


<script setup>
import { ref, computed } from 'vue';
import CustomSelectBox from '../common/CustomSelectBox.vue';
import TopBarBack from '../common/TopBarBack.vue';
import { useAuthStore } from "@/stores/authStore";
import { axiosInstance } from "@/plugins/axiosPlugin";
import { useRouter } from 'vue-router';

const router = useRouter();
const post = ref({
  title: '',
  content: '',
});
const selectedTag = ref(null); // 선택된 태그 객체
const message = ref('');

// 옵션 변수
const tagOptions = [
  { idx: 1, name: "교통", value: "TRANSPORT" },
  { idx: 2, name: "맛집", value: "TASTE" },
  { idx: 3, name: "생활팁", value: "LIFE" },
  { idx: 4, name: "질문", value: "QUESTION" },
  { idx: 5, name: "모임", value: "MEETING" },
  { idx: 6, name: "기타", value: "ETC" },
];

// authStore에서 사용자 정보 가져오기
const authStore = useAuthStore();
const userGu = computed(() => authStore.gu);

// API: 게시글 생성
const submit = async () => {
  try {
    const response = await axiosInstance.post('/v1/board', {
      board_title: post.value.title,
      board_content: post.value.content,
      board_tag: selectedTag.value, // selectedTag에서 선택된 태그값 가져오기
      board_gu: userGu.value,
    });

    console.log(userGu.value);

    if (response.data.success) {
      message.value = '게시글이 성공적으로 생성되었습니다!';
      console.log(response.data);
      router.push('/community');
    } else {
      console.error('Failed:', response.data.message);
      message.value = `Error: ${response.data.message}`; // 사용자에게 오류 메시지 표시
    }
  } catch (error) {
    console.error('Error creating post:', error);
    message.value = '게시글을 생성하는 데 실패했습니다.' + selectedTag.value; // 사용자에게 알림
  }
};

// 뒤로 가기 함수
const goToBack = () => {
  router.go(-1);
};
</script>


<style scoped lang="scss">
  .input-container {
    display: flex;
    flex-direction: column;
    flex-grow: 1;
    width: 100%;
    max-width: 600px; 
    padding: $padding-default;
    box-sizing: border-box;
    row-gap: $padding-default;
  }

  .input-container .input-item {
    width: 100%;
    box-sizing: border-box;
    background-color: #F2F2F2;
    border-radius: 14px;
    border: none;
    outline: none;
    @include custom-text($font-size: 14px, $font-color: $text-color-light);
  }

  .input-container input {
    @include custom-input-style;
  }

  .input-container textarea {
    height: 283px;
    padding-left: 16px;
    padding-top: 16px;
    @include custom-text;
  }

  .post-button {
    display: flex;
    justify-content: center;
    width: 100%;
    justify-content: space-between;
    padding: $margin-big $margin-default;
    box-sizing: border-box;
  }

  .cancle-button,
  .create-button {
    flex-basis: 160px;
    @include custom-text-bold($font-size: 14px)

  }
  
  .cancle-button:hover,
  .create-button:hover {
    background-color: black;
    color: white;
  }
</style>