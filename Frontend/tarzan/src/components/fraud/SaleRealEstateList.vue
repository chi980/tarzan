<template>
  <div class="sale-list">
    <div v-if="data.length === 0" class="no-result">검색 결과가 없습니다.</div>
    <SaleRealEstateItem
      v-else
      v-for="(item, idx) in data"
      :key="idx"
      :data="item"
      :type="props.type" />
  </div>
</template>

<script setup lang="ts">
import { defineProps } from "vue";
import SaleRealEstateItem from "@/components/fraud/SaleRealEstateItem.vue";

const props = defineProps<{
  data: any[];
  type: string;
}>();
</script>

<style scoped lang="scss">
.sale-list {
  display: flex;
  flex-direction: column;
}

.no-result {
  text-align: center;
  color: #888;
  font-size: 14px;
  padding: 20px;
}
</style>
