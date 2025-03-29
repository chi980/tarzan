<!-- TagButtonGroup 사용법:
 
 Props 설명:
 - buttons: 버튼으로 표시할 데이터를 배열로 전달
   예: [{ label: '표시할 이름', value: '고유값' }, ...]
 
 - multiple: 다중선택(true), 단일선택(false)을 지정하는 옵션
 
 v-model 설명:
 - 다중 선택일 때는 `selectedButtons` (배열)를 연결
 - 단일 선택일 경우 v-model:selectedButton 으로 연결 -->
<template>
  <div class="tag-button-container">
    <div
      v-for="button in buttons"
      :key="button.value"
      class="tag-button"
      :class="{
        active: multiple
          ? selectedButtons.includes(button.value)
          : selectedButton === button.value,
      }"
      role="button"
      tabindex="0"
      @click="toggleSelection(button.value)"
      @keydown.enter="toggleSelection(button.value)"
      @keydown.space.prevent="toggleSelection(button.value)">
      <slot :button="button">{{ button.label }}</slot>
    </div>
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
      ? props.selectedButtons.filter((v) => v !== value) // 선택 해제
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
  gap: 5px;
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

.tag-button.active {
  background-color: $primary-color-light;
  border: 1.2px solid $primary-color-default;
  color: $primary-color-default;
}
</style>
