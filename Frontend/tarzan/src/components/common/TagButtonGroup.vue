<template>
  <div class="tag-button-container">
    <div
      v-for="button in buttons"
      :key="button.value"
      class="tag-button"
      :class="{ [activeClass]: isSelected(button) }"
      role="button"
      tabindex="0"
      @click="readonly ? null : toggleSelection(button)">
      <!-- @click="toggleSelection(button)" -->
      <slot :button="button">{{ button.label }}</slot>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";

const props = defineProps({
  selectedButton: [String, Number], // 단일 선택일 때 선택된 버튼
  selectedButtons: Array, // 다중 선택일 때 선택된 버튼 배열
  buttons: Array, // 버튼 내용 목록
  multiple: Boolean, // 다중 선택 여부
  readonly: Boolean, // 읽기 전용 모드 여부

  // 활성화 클래스 (기본: "active")
  activeClass: {
    type: String,
    default: "active",
  },
});

const emit = defineEmits([
  "update:selectedButton",
  "update:selectedButtons",
  "tag-click",
]);

// 버튼 선택 여부 체크 함수
const isSelected = (button) => {
  if (props.multiple) {
    return props.selectedButtons.includes(button.value);
  } else {
    return props.selectedButton === button.value;
  }
};

// 버튼을 클릭했을 때 선택 상태를 토글(켜고 끄는) 하는 함수
const toggleSelection = (button) => {
  if (props.readonly) return;

  if (props.multiple) {
    const isAlreadySelected = props.selectedButtons.includes(button.value);

    if (isAlreadySelected) {
      const updated = props.selectedButtons.filter((v) => v !== button.value);
      emit("update:selectedButtons", updated);
    } else {
      emit("update:selectedButtons", [...props.selectedButtons, button.value]);
    }
  } else {
    emit("update:selectedButton", button.value);
    emit("tag-click", button); // 👈 클릭되었다는 이벤트를 부모에 알림
  }
};
</script>

<style scoped lang="scss">
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

// 선택된 버튼의 스타일
// 기존의 active 클래스를 사용하여 스타일을 적용합니다.
// 필요에 따라 기존 스타일을 수정하거나 추가할 수 있습니다.
.active {
  background-color: $primary-color-light;
  border: 1.2px solid $primary-color-default;
  color: $primary-color-default;
}
</style>
