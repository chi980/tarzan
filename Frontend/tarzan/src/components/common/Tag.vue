<template>
  <div
    class="tag-wrapper"
    :class="{ checked: checkItem.value }"
    @click="toggleCheck"
  >
    <p>{{ checkItem.name }}</p>
  </div>
</template>

<script setup lang="ts">
import { defineProps, PropType, defineEmits } from "vue";
import { Check } from "@/data/check";

const props = defineProps({
  checkItem: {
    type: Object as PropType<Check>,
    required: true,
  },
});

const emit = defineEmits(["update:checkItem"]);

const toggleCheck = () => {
  emit("update:checkItem", {
    ...props.checkItem,
    value: !props.checkItem.value,
  });
};
</script>

<style lang="scss" scoped>
.tag-wrapper {
  @include custom-text($font-size: 14px, $font-weight: 500);
  display: flex;
  box-sizing: border-box;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding: 14px;
  border-radius: 24px;
  background-color: white;
  border: 1px solid #e5e5e5;

  &.checked {
    color: #32d583;
    background-color: #e0f9ed;
    border: 1px solid #32d583;
  }
}
</style>
