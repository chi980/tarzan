<template>
  <div class="skeleton-item">
    <div class="skeleton-thumbnail" />
    <div class="skeleton-content">
      <div class="skeleton-line short" />
      <div class="skeleton-line" />
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.skeleton-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 12px;
  border: 1px solid #eee;
  border-radius: 8px;
}

.skeleton-thumbnail {
  width: 60px;
  height: 60px;
  background-color: #ddd;
  border-radius: 8px;
}

.skeleton-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.skeleton-line {
  height: 14px;
  background-color: #ddd;
  border-radius: 4px;
  width: 100%;
  animation: pulse 1.5s infinite ease-in-out;
}

.skeleton-line.short {
  width: 60%;
}

@keyframes pulse {
  0%,
  100% {
    opacity: 1;
  }
  50% {
    opacity: 0.5;
  }
}
</style>
