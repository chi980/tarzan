<!-- 입력:
    * options: Array
출력:
    * 선택된 idx -->

<template>
  <div class="select-wrapper" style="min-width: max-content">
    <div class="dropdown">
      <div
        :style="parentStyle"
        class="selected-item"
        v-if="selectedOption"
        @click="controllDropDown"
      >
        <span>{{ selectedOption.name }}</span>
        <img
          :src="arrowDownSrc"
          alt="arrowDown"
          class="input-item-image"
          :class="{ rotated: isRotated, rotate: true }"
        />
      </div>

      <div
        class="scrollable-container dropdown-content"
        :class="['dropdown-content', { show: isDropDownOpen }]"
        style="min-width: max-content"
        @click="controllDropDown"
      >
        <div class="scrollable-list">
          <ul>
            <li
              v-for="(option, index) in options"
              :key="option.idx"
              @click="selectOption(option, index)"
            >
              {{ option.name }}
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div
      :class="['dropdown-exterior', { show: isDropDownOpen }]"
      @click="controllDropDown"
    ></div>
  </div>
</template>

<script setup lang="ts">
// @ts-ignore
import { ref, onMounted, defineEmits } from "vue";

// @ts-ignore
import { Option } from "@/data/options";

// @ts-ignore
import { SelectStyle } from "@/data/selectStyle";

// @ts-ignore
import arrowDownSrc from "@/assets/icons/Arrows-chevron/Arrow-Down/Style=Outlined.svg";

// 부모로부터 받아온 options
const props = defineProps({
  options: {
    type: Array as () => Option[],
    required: true,
  },
  parentStyle: {
    type: Object as () => SelectStyle,
    default: () => ({
      backgroundColor: `$input-color`,
      fontWeight: 400,
      justifyContent: `space-between`,
    }),
  },
});

// 선택된 옵션 상태
const selectedOption = ref<Option | null>(null);
const selectedIdx = ref<number | null>(null);

// 팝업 상태 관리
const isDropDownOpen = ref(false);
// 화살표 상태 관리
const isRotated = ref(false);

// emit 정의
const emit = defineEmits<{
  (e: "update:selected", idx: number): void;
}>();

const controllDropDown = () => {
  isDropDownOpen.value = !isDropDownOpen.value;
  isRotated.value = !isRotated.value;
};

// 초기 선택 옵션 설정
onMounted(() => {
  if (props.options.length > 0) {
    selectedOption.value = props.options[0]; // options 배열의 첫 번째 항목을 선택된 옵션으로 설정
    selectedIdx.value = 0;
    emit("update:selected", 0); // 기본 선택된 인덱스를 부모에게 전달
  }
});

// 옵션 클릭 핸들러

// @ts-ignore
const selectOption = (option: Option, index: number) => {
  selectedOption.value = option;
  selectedIdx.value = index;
  emit("update:selected", index); // 선택한 옵션의 idx emit
};
</script>

<style lang="scss" scoped>
.select-wrapper {
  display: flex;
}
.selected-item {
  @include custom-text($font-size: 14px);
  @include custom-padding-x;
  // @include custom-margin-input;

  height: 48px;
  border-radius: $border-radius-default;
  background-color: $input-color;

  display: flex;
  gap: $padding-small;

  align-items: center; /* 수직 가운데 정렬 */
}
.selected-item span {
  margin-right: $input-margin-default;
  text-align: left;
  white-space: nowrap;
}
.input-item-image {
  @include custom-icon-style;
}
.rotate {
  transition: transform 0.5s ease;
}

.rotated {
  transform: rotate(180deg);
}

.scrollable-container {
  @include custom-popup-shadow;
  border-radius: $border-radius-default;
  background-color: white;
}

.scrollable-list {
  max-height: 138px;
  overflow-y: auto;
  border-radius: $border-radius-default;
  padding: 0;
  margin: 10px;
}
.scrollable-list ul {
  padding: 0;
  margin: 0;
}
.scrollable-list li {
  @include custom-text($font-size: 14px);
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

.dropdown {
  @include custom-none-select-basic;
  position: relative;
  display: inline-block;
  // width: 100%;
  flex: 1;
}
.dropdown-exterior {
  display: none;
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.1);
}
.dropdown-exterior.show {
  display: block;
  z-index: $z-index-dropdown-exterior; /* 층위 값을 설정 */
}
.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
}
.dropdown-content.show {
  display: block;
  margin-top: $margin-small;
  z-index: $z-index-dropdown;
  width: 100%;
}
</style>
