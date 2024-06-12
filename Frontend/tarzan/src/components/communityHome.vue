<template>
  <div>
    <h1>커뮤니티 홈</h1>
  </div>
  <Search />
  <Tags />
  <ResultBar />
  <PostItem />
</template>

<script>
import Search from './community/Search.vue'
import Tags  from './community/Tags.vue'
import ResultBar from './community/ResultBar.vue'
import PostItem from './community/PostItem.vue'

export default {
  components: {
    Search,
    Tags,
    ResultBar,
    PostItem,
  }
};
</script>

<style scoped lang="scss">
h1 {
  color: blue;
}
</style> 
