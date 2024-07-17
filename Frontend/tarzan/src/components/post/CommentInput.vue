<template>
  <div class="comment-input">
    <input type="text" placeholder="댓글을 입력해주세요." />
    <button>
      <img src="@/assets/icons/Filter/comment-input-icon.png" alt="comment-input-icon">
    </button>
  </div>
</template>
<script>
export default {
  
}
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