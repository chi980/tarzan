<template>
  <div class="sub-container">
    <div class="header">
      <h3>글쓰기</h3>
    </div>
    <div class="input-container">
      <input class="input-item" type="text" placeholder="제목을 입력해주세요"  />
      
      <CustomSelectBox :options="tagOptions" />

      <textarea class="input-item" placeholder="내용을 입력해주세요"></textarea>
    </div>

    <div class="post-button">
      <button @click="goToBack" class="cancle-button">취소</button>
      <button @click="submit" class="create-button">글쓰기</button>
    </div>
  </div>
</template>


<script>
import CustomSelectBox from '../common/CustomSelectBox.vue';

export default {
  components: {
    CustomSelectBox,
  },
  methods: {
    goToBack() {
      this.$router.go(-1);
    }
  },
  data() {
    return {
      tagOptions: [
      { idx: 1, name: "교통", value: "trans" },
      { idx: 2, name: "맛집", value: "food" },
      { idx: 3, name: "생활팁", value: "tip" },
      { idx: 4, name: "질문", value: "question" },
      { idx: 5, name: "모임", value: "?" },
      { idx: 6, name: "기타", value: "etc" },
      ]
    }
  }
}
</script>


<style scoped lang="scss">
  .input-container {
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100%;
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