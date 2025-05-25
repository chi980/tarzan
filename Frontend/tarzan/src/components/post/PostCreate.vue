<template>
  <div class="sub-container">
    <TopBarBack title="글쓰기" />
    <div class="input-container">
      <input
        class="input-item"
        type="text"
        placeholder="제목을 입력해주세요"
        v-model="post.title" />
      <div class="tag-select-wrapper">
        <CustomSelectBox
          :options="tagOptions"
          @update:selected="handleTagIdx" />
      </div>

      <textarea
        class="input-item"
        placeholder="내용을 입력해주세요"
        v-model="post.content">
      </textarea>
      <div class="description">
        <p>
          <i class="bi bi-info-circle"> </i>
          회원가입 시 입력한 지역구가 자동으로 설정됩니다.
        </p>
      </div>
    </div>
    <div v-if="message">{{ message }}</div>
    <div style="width: 100%">
      <div class="post-button">
        <button @click="goToBack" class="inactive">취소</button>
        <button @click="submit" class="active">글쓰기</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from "vue";
import router from "@/router";
import { axiosInstance } from "@/plugins/axiosPlugin";
import { useAuthStore } from "@/stores/authStore";

import CustomSelectBox from "@/components/common/CustomSelectBox.vue";
import TopBarBack from "@/components/common/TopBarBack.vue";
import DropDown from "@/components/common/DropDown.vue";

// 게시글 제목, 내용, 태그를 저장할 ref 변수
const post = ref({
  title: "",
  content: "",
  tag: "",
});
const selectedTagIndex = ref(null);
const message = ref("");

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
const handleTagIdx = (idx) => {
  selectedTagIndex.value = idx;
  post.value.tag = tagOptions.value[idx].value;
};

// authStore에서 사용자 지역구 정보 가져오기
const authStore = useAuthStore();
const userGu = authStore.user;
console.log("userGu", userGu);

// API: 게시글 생성
const submit = async () => {
  // 게시글 제목, 내용, 태그가 모두 입력되었는지 확인
  if (!post.value.title || !post.value.content || !selectedTagIndex.value) {
    message.value = "제목, 내용, 태그를 모두 입력해주세요.";
    return;
  }

  // 게시글 제목, 내용, 태그를 콘솔에 출력
  // console.log({
  //   board_title: post.value.title,
  //   board_content: post.value.content,
  //   board_tag: selectedTag.value?.value,
  //   board_gu: userGu.value,
  // });

  try {
    const response = await axiosInstance.post("/v1/board", {
      board_title: post.value.title,
      board_content: post.value.content,
      board_tag: post.value.tag,
      board_gu: "JONGNO", // userGu.value,
    });

    if (response.data.success) {
      message.value = "게시글이 성공적으로 생성되었습니다!";
      router.push("/community");
    } else {
      message.value = `Error: ${response.data.message}`;
    }
  } catch (error) {
    console.error("Error creating post:", error);
    message.value = "게시글을 생성하는 데 실패했습니다.";
  }
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

.input-item {
  width: 100%;
  box-sizing: border-box;
  background-color: #ffffff;
  border: 1px solid #e5e5e5;
  outline: none;
  // overflow: hidden;
  @include custom-input-style;
  @include custom-text($font-size: 14px, $font-color: $text-color-light);
}

:deep(.selected-item) {
  background-color: #ffffff;
  @include custom-input-style;
}

textarea {
  flex-grow: 1;
  height: 283px;
  padding-left: 16px;
  padding-top: 16px;
  @include custom-text;
}

.post-button {
  @include custom-margin-x;
  display: flex;
  flex-direction: row;
  gap: $padding-small;

  button {
    all: unset;
    flex: 1;
    @include custom-button-style(
      $bg-color: $secondary-color-default,
      $font-color: white
    );

    &.inactive {
      background-color: transparent !important;
      color: $text-color-light !important;
    }
  }
}

.description {
  p {
    @include custom-text($font-size: 12px, $font-color: $text-color-light);
    display: block;
    text-align: left;
    line-height: 1.2;
  }
}
</style>
