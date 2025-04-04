<template>
  <div class="sub-container">
    <TopBarBack title="글쓰기" @back="goToBack"/>
    <div class="input-container">
      <input 
        class="input-item" 
        type="text" 
        placeholder="제목을 입력해주세요" 
        v-model="post.title" />
        <CustomSelectBox 
          :options="tagOptions" 
          v-model:selected="selectedTagIndex" 
        />
      <textarea 
        class="input-item" 
        placeholder="내용을 입력해주세요" 
        v-model="post.content" >
      </textarea>
    </div>

    <div v-if="message">{{ message }}</div>

    <div class="post-button">
      <button @click="goToBack" class="cancle-button">취소</button>
      <button @click="submit" class="create-button">글쓰기</button>
    </div>
  </div>
</template>


<script setup>
import { ref, computed, watch } from 'vue';
import { useRouter } from 'vue-router';
import { axiosInstance } from "@/plugins/axiosPlugin";
import { useAuthStore } from "@/stores/authStore";

import CustomSelectBox from '@/components/common/CustomSelectBox.vue';
import TopBarBack from '../common/TopBarBack.vue';

// 게시글 제목, 내용, 태그를 저장할 ref 변수
const post = ref({
  title: '',
  content: '',
});
const selectedTagIndex = ref(null);
const message = ref('');

// 태그 옵션 데이터
const tagOptions = ref([
  { idx: 1, name: "교통", value: "TRANSPORT" },
  { idx: 2, name: "맛집", value: "TASTE" },
  { idx: 3, name: "생활팁", value: "LIFE" },
  { idx: 4, name: "질문", value: "QUESTION" },
  { idx: 5, name: "모임", value: "MEETING" },
  { idx: 6, name: "기타", value: "ETC" },
]);

// 선택된 태그 객체 계산 속성 추가
const selectedTag = computed(() => {
  if (selectedTagIndex.value !== null) {
    return tagOptions.value[selectedTagIndex.value];
  }
  return null;
});

// authStore에서 사용자 지역구 정보 가져오기
const authStore = useAuthStore();

const userGu = computed(() => authStore.getGu);
console.log(userGu);

// API: 게시글 생성
const submit = async () => {
  try {
    const response = await axiosInstance.post('/v1/board', {
      board_title: post.value.title,      // 게시글 제목
      board_content: post.value.content,  // 게시글 내용
      board_tag: selectedTag.value.value, // 게시글 태그
      board_gu: userGu.value,             // 사용자 지역구 정보
    });

    if (response.data.success) {
      message.value = '게시글이 성공적으로 생성되었습니다!';
      console.log(response.data);
      router.push('/community');
    } else {
      console.error('Failed:', response.data.message);
      message.value = `Error: ${response.data.message}`;
    }
  } catch (error) {
    console.error('Error creating post:', error);
    message.value = '게시글을 생성하는 데 실패했습니다.' + selectedTag.value;
  }
};

const router = useRouter();

// 뒤로가기 버튼 클릭 시 호출되는 함수
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