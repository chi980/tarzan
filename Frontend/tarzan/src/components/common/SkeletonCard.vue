<!-- components/fraud/SkeletonCard.vue -->
<template>
  <div class="real-estate-container skeleton-card">
    <div class="status skeleton-box" style="width: 80px; height: 16px"></div>
    <div class="real-estate-content">
      <div class="skeleton-box" style="width: 120px; height: 20px"></div>
      <div class="skeleton-box" style="width: 100px; height: 14px"></div>
      <div class="skeleton-box" style="width: 160px; height: 14px"></div>
      <div class="skeleton-box" style="width: 140px; height: 14px"></div>
      <div class="skeleton-box" style="width: 180px; height: 14px"></div>
    </div>
  </div>
</template>

<style scoped>
.skeleton-card {
  animation: pulse 1.5s infinite ease-in-out;
}
.skeleton-box {
  background: #f0f0f0;
  border-radius: 6px;
  margin-bottom: 8px;
}
@keyframes pulse {
  0% {
    background-color: #f2f2f2;
  }
  50% {
    background-color: #e6e6e6;
  }
  100% {
    background-color: #f2f2f2;
  }
}
</style>
