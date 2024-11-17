<template>
  <div class="searchbar">
    <div class="input-icon-wrap">
      <font-awesome-icon :icon="['fas', 'magnifying-glass']" class="icon-search"/>
        <input
          v-model="searchQuery" 
          type="text"
          placeholder="찾고 싶은 글 제목을 입력해주세요." />
    </div>
  </div>
      
</template>
<script>
export default {
  
}
</script>
<style lang="scss" scoped>
  .searchbar {
    display: flex;
    padding-top: $margin-small;
    padding-bottom: $margin-default;
    @include custom-padding-x;
    box-sizing: border-box;
  }

  .searchbar .input-icon-wrap {
    display: flex;
    align-items: center;
    width: 100%;
    height: 48px;
    border-radius: 13px;
    background-color: $input-color;
    padding-right: $padding-default;
  }

  .searchbar .input-icon-wrap .icon-search {
    width: 16px;
    height: 16px;
    @include custom-margin-x;
    color: $input-placeholder-color;
  }

  .searchbar .input-icon-wrap input {
    width: 100%;
    appearance: none;
    border: none;
    outline: none;
    background: transparent;
    @include custom-text;
  }
</style>