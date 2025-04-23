<template>
  <div ref="trigger" style="height: 1px" />
</template>

<script setup lang="ts">
import { onMounted, onUnmounted, ref } from "vue";

const emit = defineEmits(["trigger"]);

const trigger = ref<HTMLElement | null>(null);
// 화면에 이 요소가 들어오면 trigger 이벤트 발생
let observer: IntersectionObserver;

onMounted(() => {
  observer = new IntersectionObserver(
    ([entry]) => {
      if (entry.isIntersecting) {
        emit("trigger");
      }
    },
    {
      rootMargin: "100px", // 살짝 여유있게
    }
  );

  if (trigger.value) {
    observer.observe(trigger.value);
  }
});

onUnmounted(() => {
  if (trigger.value) {
    observer.unobserve(trigger.value);
  }
});
</script>
