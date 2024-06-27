<!-- 입력:
    * options: Array
출력:
    * 선택된 idx -->

<template>
  <div>
    <div class="selected-item" v-if="selectedOption" @click="controllPopUp">
      <p>서울시 {{ selectedOption.name }}</p>
    </div>
    <div v-if="isPopupOpen" class="scrollable-container">
      <div class="scrollable-list">
        <ul>
          <li
            v-for="option in options"
            :key="option.idx"
            @click="selectOption(option)"
          >
            서울시 {{ option.name }}
          </li>
        </ul>
      </div>
    </div>

    나날이
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { Option } from "@/data/options";

// 부모로부터 받아온 options
const props = defineProps({
  options: {
    type: Array as () => Option[],
    required: true,
  },
});

// 선택된 옵션 상태
const selectedOption = ref<Option | null>(null);

// 팝업 상태 관리
const isPopupOpen = ref(false);

const controllPopUp = () => {
  isPopupOpen.value = !isPopupOpen.value;
};

// 팝업 열기 핸들러
const openPopup = () => {
  isPopupOpen.value = true;
};

// 팝업 닫기 핸들러
const closePopup = () => {
  isPopupOpen.value = false;
};
// 초기 선택 옵션 설정
onMounted(() => {
  if (props.options.length > 0) {
    selectedOption.value = props.options[0]; // options 배열의 첫 번째 항목을 선택된 옵션으로 설정
  }
});

// 옵션 클릭 핸들러
const selectOption = (option: Option) => {
  selectedOption.value = option;
  closePopup();
};
</script>

<style lang="scss" scoped>
.selected-item {
  @include custom-text;
  @include custom-padding-x;
  @include custom-margin-input;

  height: 48px;
  border-radius: $border-radius-default;
  background-color: $input-color;

  display: flex;
  align-items: center; /* 수직 가운데 정렬 */
}

.scrollable-container {
  @include custom-padding-x($padding-small);
  @include custom-padding-y($padding-small);
  @include custom-popup-shadow;
  border-radius: $border-radius-default;
  background-color: white;
}
.scrollable-list {
  max-height: 138px;
  overflow-y: auto;
  // border: 1px solid #ccc;
  border-radius: $border-radius-default;
  padding: 0;
  margin: 0;
}
.scrollable-list ul {
  padding: 0;
  margin: 0;
}
.scrollable-list li:first-child {
  list-style-type: none;
}
.scrollable-list li {
  @include custom-text;
  // background-color: antiquewhite;
  list-style-type: none;
  border-radius: $border-radius-default;
  padding: $padding-default;
}

/* 스크롤바 전체 영역 */
::-webkit-scrollbar {
  width: 4px; /* 세로축 스크롤바 폭 너비 */
  height: 100%; /* 가로축 스크롤바 폭 너비 */
}
::-webkit-scrollbar-button {
  display: none;
}
/* 스크롤바 막대 제외 부분 */
::-webkit-scrollbar-track {
  background: none;
  margin: 8px;
}
/* 스크롤바 막대 */
::-webkit-scrollbar-thumb {
  border-radius: $border-radius-default;
  background: #d9d9d9;
}
</style>
