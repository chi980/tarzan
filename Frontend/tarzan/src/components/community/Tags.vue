<template>
  <div class="tag-buttons">
    <button
      v-for="(tag, index) in tags"
      :key="index"
      :class="['tag-button', { 'active': activeTag === tag }]"
      @click="setActiveTag(tag)">
      {{ tag }}
    </button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tags: ['전체', '교통', '맛집', '생활팁', '질문', '모임'],
      activeTag: '전체'
    };
  },
  methods: {
    setActiveTag(tag) {
      this.activeTag = tag;
    }
  }
};
</script>

<style scoped>
.tag-buttons {
  display: flex;
  gap: 10px;
}

.tag-button {
  padding: 10px 20px;
  border: 1px solid #ddd;
  border-radius: 20px;
  background-color: white;
  cursor: pointer;
}

.tag-button.active {
  background-color: #e0f7e7;
  border-color: #00c851;
  color: #00c851;
}
</style>
