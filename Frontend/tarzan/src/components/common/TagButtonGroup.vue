<template>
  <div class="tag-button-container">
    <div
      v-for="button in buttons"
      :key="button.value"
      class="tag-button"
      :class="{ [activeClass]: isSelected(button) }"
      role="button"
      tabindex="0"
      @click="readonly ? null : toggleSelection(button)"
    >
      <!-- @click="toggleSelection(button)" -->
      <slot :button="button">{{ button.label }}</slot>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";

const props = defineProps({
  selectedButton: Object, // 단일 선택일 때 선택된 버튼 객체
  selectedButtons: Array, // 다중 선택일 때 선택된 버튼 객체 배열
  buttons: Array, // 버튼 내용 목록
  multiple: Boolean, // 다중 선택 여부
  readonly: Boolean, // 읽기 전용 모드 여부

  // 활성화 클래스 (기본: "active")
  activeClass: {
    type: String,
    default: "active",
  },
});

const emit = defineEmits(["update:selectedButton", "update:selectedButtons"]);

// 버튼이 선택되었는지 여부 확인
const isSelected = (button) => {
  if (props.multiple) {
    return props.selectedButtons.some(
      (selectedButton) => selectedButton.value === button.value
    );
  } else {
    return props.selectedButton && props.selectedButton.value === button.value;
  }
};

// 버튼을 클릭했을 때 선택 상태를 토글(켜고 끄는) 하는 함수
const toggleSelection = (button) => {
  if (props.multiple) {
    // 다중 선택 모드
    const isAlreadySelected = props.selectedButtons.some(
      (selectedButton) => selectedButton.value === button.value
    );

    if (isAlreadySelected) {
      // 이미 선택된 버튼이면 제거
      const updated = props.selectedButtons.filter(
        (selectedButton) => selectedButton.value !== button.value
      );
      emit("update:selectedButtons", updated);
    } else {
      // 선택되지 않은 버튼이면 추가
      emit("update:selectedButtons", [...props.selectedButtons, button]);
    }
  } else {
    // 단일 선택 모드
    emit("update:selectedButton", button);
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
