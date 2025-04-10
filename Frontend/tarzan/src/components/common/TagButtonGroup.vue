<template>
  <div class="tag-button-container">
    <div
      v-for="button in buttons"
      :key="button.value"
      class="tag-button"
      :class="{ [activeClass]: isSelected(button.value) }"
      role="button"
      tabindex="0"
      @click="toggleSelection(button.value)">
      <slot :button="button">{{ button.label }}</slot>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";

const props = defineProps({
  selectedButton: String,
  selectedButtons: Array,
  buttons: Array,
  multiple: Boolean,
  activeClass: {
    type: String,
    default: "active", // 기본 css는 기존과 동일하게 유지
  },
});

const emit = defineEmits(["update:selectedButton", "update:selectedButtons"]);

const isSelected = (value) => {
  return props.multiple
    ? props.selectedButtons.includes(value)
    : props.selectedButton === value;
};

const toggleSelection = (value) => {
  if (props.multiple) {
    const updated = props.selectedButtons.includes(value)
      ? props.selectedButtons.filter((v) => v !== value)
      : [...props.selectedButtons, value];
    emit("update:selectedButtons", updated);
  } else {
    emit("update:selectedButton", value);
  }
};
</script>

<style scoped lang="scss">
// .tag-button-container {
//   display: flex;
//   flex-wrap: nowrap;
//   gap: 5px;

// }
.tag-button-container {
  display: flex;
  flex-wrap: nowrap;
  gap: 5px;
  overflow-x: auto;
  -webkit-overflow-scrolling: touch;

  &::-webkit-scrollbar {
    width: 0;
    height: 0;
    display: none;
  }

  scrollbar-width: none;
  -ms-overflow-style: none;
}

.tag-button {
  @include custom-text($font-size: 12px);
  background: white;
  border-radius: 20px;
  border: 1px solid #e5e5e5;
  white-space: nowrap;
  padding: 13px 14px;
  display: inline-block;
  cursor: pointer;
  &:focus {
    outline: none;
  }
}

.active {
  background-color: $primary-color-light;
  border: 1.2px solid $primary-color-default;
  color: $primary-color-default;
}
</style>
