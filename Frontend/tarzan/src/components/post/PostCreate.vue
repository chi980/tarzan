<template>
  <div class="sub-container">
    <!-- header 넣기 -->
    <div class="header">
      <h3>글쓰기</h3>
    </div>
    <div class="input-container">
      <input class="input-item" ype="text" placeholder="제목을 입력해주세요"  />

      <select class="input-item">
        <option disabled selected>태그를 선택해주세요</option>
        <option value="transport">교통</option>
        <option value="transport">맛집</option>
        <option value="transport">생활팁</option>
        <option value="transport">질문</option>
        <option value="transport">모임</option>
      </select>

      <textarea class="input-item" placeholder="내용을 입력해주세요"></textarea>
    </div>

    <div class="post-button">
      <button @click="goToBack" class="cancle-button">취소</button>
      <button @click="submit" class="create-button">글쓰기</button>
    </div>
  </div>
</template>


<script>
export default {
  methods: {
    goToBack() {
      this.$router.go(-1);
    }
  }
}
</script>


<style scoped lang="scss">
// input
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

  .input-item {
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

  .input-container select {
    @include custom-input-style;
    appearance: none; // 스타일 제거
  }

  .input-container textarea {
    height: 283px;
    padding-left: 16px;
    padding-top: 16px;
    @include custom-text;
  }


// 글 제출/취소 버튼
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