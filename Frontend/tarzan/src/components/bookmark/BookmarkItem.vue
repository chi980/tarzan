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
  console.log(props.checked);
  emit("toggle-check", props.idx);
};
</script>

<template>
  <div>
    <input type="checkbox" :checked="props.checked" @change="toggle" />
    <HouseItem :house="props.house" @navigate="toggle" />
  </div>
</template>
<style lang="scss" scoped></style>
