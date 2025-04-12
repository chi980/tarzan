<template>
  <div
    class="swipe-wrapper"
    @mousedown="onMouseDown"
    @mousemove="onMouseMove"
    @mouseup="onMouseUp"
    @mouseleave="onMouseUp"
    @touchstart="onTouchStart"
    @touchmove="onTouchMove"
    @touchend="onTouchEnd">
    <div class="delete-button" @click.stop="deleteThis">🗑</div>
    <div
      class="item-content"
      :style="{ transform: `translateX(${translateX}px)` }"
      @click="onClick">
      <slot></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, defineEmits } from "vue";

const emit = defineEmits(["delete", "click"]); // 클릭 이벤트도 emit할 수 있도록 추가

const startX = ref(0);
const translateX = ref(0);
const isDragging = ref(false);
const threshold = 40;
const moved = ref(false); // 클릭인지 드래그인지 구분

// PC
const onMouseDown = (e: MouseEvent) => {
  isDragging.value = true;
  startX.value = e.clientX;
  moved.value = false;
};

const onMouseMove = (e: MouseEvent) => {
  if (!isDragging.value) return;
  const deltaX = e.clientX - startX.value;
  if (deltaX < 0) {
    moved.value = true;
    translateX.value = deltaX;
  }
};

const onMouseUp = () => {
  if (!isDragging.value) return;
  isDragging.value = false;
  if (!moved.value) return;
  translateX.value = Math.abs(translateX.value) > threshold ? -80 : 0;
};

// Mobile
const onTouchStart = (e: TouchEvent) => {
  startX.value = e.touches[0].clientX;
  isDragging.value = true;
  moved.value = false;
};

const onTouchMove = (e: TouchEvent) => {
  if (!isDragging.value) return;
  const deltaX = e.touches[0].clientX - startX.value;
  if (deltaX < 0) {
    moved.value = true;
    translateX.value = deltaX;
  }
};

const onTouchEnd = () => {
  if (!isDragging.value) return;
  isDragging.value = false;
  if (!moved.value) return;
  translateX.value = Math.abs(translateX.value) > threshold ? -80 : 0;
};

// 클릭 이벤트 (드래그가 아닌 경우에만 emit)
const onClick = () => {
  if (!moved.value) {
    emit("click");
  }
};

// 삭제 버튼 클릭
const deleteThis = () => {
  translateX.value = 0;
  emit("delete");
};
</script>

<style scoped>
.swipe-wrapper {
  position: relative;
  overflow: hidden;
  user-select: none;
}

.delete-button {
  position: absolute;
  right: 0;
  top: 0;
  bottom: 0;
  width: 80px;
  background-color: red;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1;
}

.item-content {
  position: relative;
  background: white;
  transition: transform 0.2s ease;
  z-index: 2;
  cursor: grab;
}
</style>
