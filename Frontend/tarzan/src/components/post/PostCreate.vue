<template>
  <div class="sub-container">
    <!-- header 넣기 -->
    <div class="center-container">
      <input class="input-title" ype="text" placeholder="제목을 입력해주세요"  />

      <div class="select-tag">
        <select>
          <option disabled selected>태그를 선택해주세요</option>
          <option value="transport">교통</option>
          <option value="transport">맛집</option>
          <option value="transport">생활팁</option>
          <option value="transport">질문</option>
          <option value="transport">모임</option>
        </select>
      </div>

      <textarea class="input-content" placeholder="내용을 입력해주세요"></textarea>
    </div>

    <div class="post-button">
      <button @click="goToBack" class="cancle-buttom">취소</button>
      <button @click="submit" class="create-buttom">글쓰기</button>
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


<style lang="">
  
</style>