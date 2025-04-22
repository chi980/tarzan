<template>
  <div class="sale-list">
    <div v-if="data.length === 0" class="no-result">검색 결과가 없습니다.</div>
    <div v-else v-for="(item, idx) in data" :key="idx" class="sale-item">
      <div class="sale-info">
        <div>{{ item.buildingName }}</div>
        <div>계약일: {{ item.contractDate }}</div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  data: any[];
}>();
</script>

<style scoped lang="scss">
.sale-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
  padding: 16px;
}

.no-result {
  text-align: center;
  color: #888;
  font-size: 14px;
  padding: 20px;
}

.sale-item {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 12px;
  background-color: #fff;

  .sale-title {
    font-weight: 600;
    font-size: 16px;
    margin-bottom: 8px;
  }

  .sale-info {
    font-size: 14px;
    color: #444;
    display: flex;
    flex-direction: column;
    gap: 4px;
  }
}
</style>
