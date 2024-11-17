<template>
  <div class="checklist-item">
    <p>{{ props.checkListItem.name }}</p>
    <input type="checkbox" v-model="props.checkListItem.value" />
  </div>
</template>

<script setup lang="ts">
// @ts-ignore
import { defineProps, PropType } from "vue";
import { Check } from "@/data/check";
const props = defineProps({
  checkListItem: {
    type: Object as PropType<Check>,
    required: true,
  },
});
</script>

<style lang="scss" scoped>
.checklist-item {
  @include custom-padding;
  display: flex;
  flex-direction: row;
  justify-content: space-between;

  background-color: white;
  p {
    @include custom-text($font-size: 14px);
  }

  input[type="checkbox"] {
    // background-color: aqua;
  }
}
</style>
