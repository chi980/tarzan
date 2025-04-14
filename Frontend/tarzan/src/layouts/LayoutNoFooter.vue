<template>
  <div class="layout">
    <TopBar />
    <main class="layout__content">
      <slot />
    </main>
  </div>
</template>

<script setup>
import TopBar from '@/components/common/TopBar.vue'
</script>

<style scoped>
.layout {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.layout__content {
  flex: 1;
  overflow-y: auto;
}
</style>
