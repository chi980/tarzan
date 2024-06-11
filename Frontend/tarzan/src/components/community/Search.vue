<template>
  <div class="container">
    <font-awesome-icon :icon="['fas', 'magnifying-glass']" />    
    <input
      v-model="searchQuery"
      class="search-input"
      type="text"
      placeholder="찾고 싶은 글 제목을 입력해주세요." />
    
  </div>
</template>

<script>
export default {
  
}
</script>

<style scoped lang="scss">
  
</style>