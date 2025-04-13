<template>
  <div class="checklist-item" @click="changeValue">
    <p>{{ props.checkListItem.name || props.checkListItem.label }}</p>
    <input type="checkbox" :checked="props.checkListItem.value" @change.stop />
  </div>
</template>

<script setup lang="ts">
import { defineProps, PropType, defineEmits } from "vue";
import type { Check } from "@/data/check";

const props = defineProps({
  checkListItem: {
    type: Object as PropType<Check>,
    required: true,
  },
});

const emit = defineEmits(["change"]);

const changeValue = () => {
  emit("change");
};
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
