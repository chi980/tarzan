<template>
  <div>
    <h1>커뮤니티 홈</h1>
  </div>
  <Search />
</template>

<script>
import Search from './community/Search.vue'

export default {
  components: {
    Search
  }
};
</script>

<style scoped lang="scss">
h1 {
  color: blue;
}
</style> 
