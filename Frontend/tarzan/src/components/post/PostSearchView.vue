<template>
  <LayoutWithBack title="검색하기">
    <div class="search-page">
      <input
        class="search-page__input"
        v-model="keyword"
        type="text"
        placeholder="검색어를 입력하세요"
      />

      <div class="search-page__results">
        <div
          v-for="item in filteredList"
          :key="item.id"
          class="search-result-item"
        >
          {{ item.title }}
        </div>
      </div>
    </div>
  </LayoutWithBack>
</template>

<script setup>
import LayoutWithBack from '@/layouts/LayoutWithBack.vue'
import { ref, computed } from 'vue'

const keyword = ref('')
const list = ref([
  { id: 1, title: 'Vue로 만든 게시판' },
  { id: 2, title: '타잔 프로젝트 후기' },
  { id: 3, title: '검색 기능 고민' }
])

const filteredList = computed(() =>
  list.value.filter((item) =>
    item.title.toLowerCase().includes(keyword.value.toLowerCase())
  )
)
</script>

<style scoped>
.search-page {
  padding: 16px;
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: white;
}

.search-page__input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
  margin-bottom: 16px;
}

.search-result-item {
  padding: 12px;
  border-bottom: 1px solid #eee;
}
</style>
