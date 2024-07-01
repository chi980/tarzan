<template>
  <div class="sub-container">
    <TopBar></TopBar>
    <div class="center-container">

      <div class="searchbar">
        <font-awesome-icon :icon="['fas', 'magnifying-glass']" />
        <input
          v-model="searchQuery"
          class="search-input"
          type="text"
          placeholder="찾고 싶은 글 제목을 입력해주세요." />
      </div>

      <div class="tag-buttons">
        <button
          v-for="(tag, index) in tags"
          :key="index"
          :class="['tag-button', { 'active': activeTag === tag }]"
          @click="setActiveTag(tag)">
          {{ tag }}
        </button>
      </div>

      <div class="resultbar">
        <span class="result-text">결과</span>
        <span class="result-number">{{ result }}</span>
        <div class="sort-dropdown">
          <select v-model="selectedSort" @change="onSortChange">
            <option value="latest">최신순</option>
            <option value="views">조회수순</option>
            <option value="likes">좋아요순</option>
          </select>
        </div>
      </div>

    </div>

    <BottomBar></BottomBar>
  </div>
</template>

<script>
import TopBar from "@/components/common/TopBar.vue";
import BottomBar from "@/components/common/BottomBar.vue";

export default {
  components: {
    TopBar,
    BottomBar,
  },
  data() {
    return {
      tags: ['전체', '교통', '맛집', '생활팁', '질문', '모임'],
      activeTag: '전체',
      result: '1600',
      selectedSort: 'latest',
    };
  },
  methods: {
    setActiveTag(tag) {
      this.activeTag = tag;
    }
  },
}
</script>


<style lang="">
  
</style>