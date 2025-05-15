<template>
  <div
    ref="wrapperRef"
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
      :style="{
        transform: `translateX(${translateX}px)`,
        pointerEvents: translateX !== 0 ? 'none' : 'auto',
      }"
      @click="onClick">
      <slot></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, defineEmits, onMounted, onBeforeUnmount } from "vue";

const emit = defineEmits(["delete", "click"]);

const startX = ref(0);
const translateX = ref(0);
const isDragging = ref(false);
const moved = ref(false);
const preventClick = ref(false);
const threshold = 40;

const wrapperRef = ref<HTMLElement | null>(null);

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

  if (translateX.value < -threshold) {
    translateX.value = 0;
    return;
  }

  // 3) 정상 클릭 시 부모로 emit
  emit("click");
};

// 삭제 버튼 클릭
const deleteThis = () => {
  translateX.value = 0;
  emit("delete");
};
// 4) 전역 클릭 리스너: 다른 곳 클릭 시 닫기
const onDocumentClick = (e: MouseEvent) => {
  // 컴포넌트 내부 클릭은 무시
  if (wrapperRef.value?.contains(e.target as Node)) return;
  // 스와이프 열려있으면 닫기
  if (translateX.value < -threshold) {
    translateX.value = 0;
  }
};

onMounted(() => {
  document.addEventListener("click", onDocumentClick);
});

onBeforeUnmount(() => {
  document.removeEventListener("click", onDocumentClick);
});
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
