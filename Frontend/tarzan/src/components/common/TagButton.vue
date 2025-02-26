<!-- <template>
  <div class="tag-button-container">
    <button 
      v-for="button in buttons" 
      :key="button.value"
      class="tag-button"
      :class="{ active: selectedButton === button.value }"
      @click="selectButton(button.value)">
      <slot :button="button">{{ button.label }}</slot>
    </button>
  </div>
</template>

<script setup>
const props = defineProps({ 
  selectedButton: String,
  buttons: Array
});
const emit = defineEmits(['update:selectedButton']);

const selectButton = (value) => {
  emit('update:selectedButton', value);
};
</script> -->
<template>
  <div class="tag-button-container">
    <button 
      v-for="button in buttons" 
      :key="button.value"
      class="tag-button"
      :class="{ active: multiple ? selectedButtons.includes(button.value) : selectedButton === button.value }"
      @click="toggleSelection(button.value)">
      <slot :button="button">{{ button.label }}</slot>
    </button>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";

const props = defineProps({ 
  selectedButton: String, // 단일 선택용 prop
  selectedButtons: Array, // 다중 선택용 prop
  buttons: Array,
  multiple: Boolean, // 단일 선택 vs 다중 선택 모드
});

const emit = defineEmits(["update:selectedButton", "update:selectedButtons"]);

// 선택된 버튼 토글 (단일/다중 선택 모드에 따라 다르게 동작)
const toggleSelection = (value) => {
  if (props.multiple) {
    // 다중 선택 모드
    const updatedSelection = props.selectedButtons.includes(value)
      ? props.selectedButtons.filter(v => v !== value) // 선택 해제
      : [...props.selectedButtons, value]; // 선택 추가
    emit("update:selectedButtons", updatedSelection);
  } else {
    // 단일 선택 모드
    emit("update:selectedButton", value);
  }
};
</script>


<style scoped lang="scss">
.tag-button-container {
  display: flex;
  flex-wrap: nowrap;
  overflow-x: auto;
  gap: 5px;
}

.tag-button {
  background: white;
  border-radius: 20px;
  border: 1px solid #E5E5E5;
  font-size: 12px;
  font-weight: 600;
  white-space: nowrap;
  padding: 13px 14px;
  cursor: pointer;
  &:focus {
    outline: none;
  }
}

.tag-button.active {
  background-color: $primary-color-50;
  border: 1.2px solid $primary-color-default;
  color: $primary-color-default;
}
</style>
