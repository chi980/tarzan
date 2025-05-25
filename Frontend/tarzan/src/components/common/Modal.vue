<!-- src/components/Modal.vue -->
<template>
  <Teleport to="#modal-root">
    <transition name="fade">
      <div v-if="modelValue" class="backdrop" @click.self="close">
        <div class="modal-content">
          <slot :close="close" />
        </div>
      </div>
    </transition>
  </Teleport>
</template>

<script setup lang="ts">
import { defineProps, defineEmits } from "vue";

const props = defineProps<{ modelValue: boolean }>();

// Generic 으로 이벤트명과 페이로드 타입을 모두 선언
const emit = defineEmits<{
  (e: "update:modelValue", value: boolean): void;
}>();

const close = () => {
  emit("update:modelValue", false);
};
</script>

<style scoped lang="scss">
.backdrop {
  position: absolute;
  inset: 0;
  background: rgba(0, 0, 0, 0.5);
  z-index: $z-index-modal;

  display: flex;
  justify-content: center;
  align-items: end;
}
.modal-content {
  @include custom-margin-x;
  margin-bottom: $padding-default;
  width: 100%;
  background: white;
  border-radius: $border-radius-default;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
