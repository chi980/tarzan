<template>
  <div class="layout">
    <TopBarBack 
      title="검색하기"
      @back="goBack"
    />
    <main class="layout__content">
      <slot />
    </main>
  </div>
</template>

<script setup>
import TopBarBack from '@/components/common/TopBarBack.vue';
import router from '@/router';

const goBack = () => {
  router.back() // 또는 router.go(-1)
}
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
