<script setup lang="ts">
import { defineProps, defineEmits } from "vue";
import HouseItem from "@/components/bookmark/HouseItem.vue";

// 부모로부터 상태 props로 받음
const props = defineProps<{
  house: Object;
  checked: boolean;
  idx: number;
}>();

// 부모에게 알림
const emit = defineEmits<{
  (e: "toggle-check", house: any): void;
}>();

// 체크박스나 HouseItem 클릭 시
const toggle = () => {
  emit("toggle-check", props.idx);
};
</script>

<template>
  <div class="bookmark-item-wrapper">
    <input type="checkbox" :checked="props.checked" @change="toggle" />
    <HouseItem :house="props.house" @navigate="toggle" />
  </div>
</template>
<style lang="scss" scoped>
.bookmark-item-wrapper {
  @include custom-padding-x;
  @include custom-padding-y($padding-small);
  display: flex;
  flex-direction: row;
  gap: $padding-default;

  align-items: center;
}
</style>
