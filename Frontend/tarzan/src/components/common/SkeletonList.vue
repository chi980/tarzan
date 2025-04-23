<template>
  <div class="skeleton-list">
    <SkeletonItem v-for="n in count" :key="n" />
  </div>
</template>

<script setup lang="ts">
import SkeletonItem from "@/components/common/SkeletonItem.vue";
defineProps<{
  count?: number;
}>();
</script>

<style scoped>
.skeleton-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}
</style>
