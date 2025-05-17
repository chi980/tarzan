<template>
  <Teleport to="#app">
    <div class="global-loading" v-if="isLoading">
      <div class="wave-loader">
        <div class="bar" />
        <div class="bar" />
        <div class="bar" />
        <div class="bar" />
        <div class="bar" />
      </div>
    </div>
  </Teleport>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useLoadingStore } from "@/stores/loadingStore";
const loading = useLoadingStore();
const isLoading = computed(() => loading.isLoading);
</script>

<style scoped lang="scss">
.global-loading {
  position: fixed;
  inset: 0;
  background: rgba(255, 255, 255, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
}
.wave-loader {
  display: flex;
  gap: 0.25rem;
  justify-content: center;
  align-items: flex-end;
}
.wave-loader .bar {
  width: 6px;
  height: 20px;
  background: $primary-color-default;
  animation: wave 1.2s infinite ease-in-out;
}
.wave-loader .bar:nth-child(2) {
  animation-delay: 0.1s;
}
.wave-loader .bar:nth-child(3) {
  animation-delay: 0.2s;
}
.wave-loader .bar:nth-child(4) {
  animation-delay: 0.3s;
}
.wave-loader .bar:nth-child(5) {
  animation-delay: 0.4s;
}

@keyframes wave {
  0%,
  40%,
  100% {
    transform: scaleY(0.4);
  }
  20% {
    transform: scaleY(1);
  }
}
</style>
