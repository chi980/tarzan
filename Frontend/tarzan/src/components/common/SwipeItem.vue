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

const emit = defineEmits(["delete", "click"]);

const startX = ref(0);
const translateX = ref(0);
const isDragging = ref(false);
const moved = ref(false);
const preventClick = ref(false);
const threshold = 40;

// PC 이벤트
const onMouseDown = (e: MouseEvent) => {
  isDragging.value = true;
  startX.value = e.clientX;
  moved.value = false;
  preventClick.value = false;
};

const onMouseMove = (e: MouseEvent) => {
  if (!isDragging.value) return;
  const deltaX = e.clientX - startX.value;
  if (Math.abs(deltaX) > 5) {
    moved.value = true;
    preventClick.value = true;
  }
  translateX.value = deltaX;
};

const onMouseUp = () => {
  if (!isDragging.value) return;
  isDragging.value = false;
  if (!moved.value) return;

  translateX.value = translateX.value < -threshold ? -80 : 0;
};

// 모바일 터치 이벤트
const onTouchStart = (e: TouchEvent) => {
  isDragging.value = true;
  startX.value = e.touches[0].clientX;
  moved.value = false;
  preventClick.value = false;
};

const onTouchMove = (e: TouchEvent) => {
  if (!isDragging.value) return;
  const deltaX = e.touches[0].clientX - startX.value;
  if (Math.abs(deltaX) > 5) {
    moved.value = true;
    preventClick.value = true;
  }
  translateX.value = deltaX;
};

const onTouchEnd = () => {
  if (!isDragging.value) return;
  isDragging.value = false;
  if (!moved.value) return;

  translateX.value = translateX.value < -threshold ? -80 : 0;
};

// 클릭 이벤트: 드래그가 없고, 삭제 상태가 아닌 경우만 emit
const onClick = () => {
  if (preventClick.value) return;

  if (translateX.value === 0) {
    emit("click");
  } else {
    // 이미 스와이프되어 있던 상태라면 복귀
    translateX.value = 0;
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
  transition: transform 0.05s ease;
  z-index: 2;
  cursor: grab;
}
</style>
