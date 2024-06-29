<template>
  <div id="dropdown-container">
    <div
      :class="['dropdown-exterior', { show: isDropDownOpen }]"
      @click="toggleDropdown"
    ></div>
    <slot :toggleDropdown="toggleDropdown"><div>보기</div></slot>
    <div :class="['dropdown-content', { show: isDropDownOpen }]">
      <ul>
        <li
          v-for="option in options"
          :key="option.idx"
          @click="selectOption(option)"
        >
          {{ option.name }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineProps, ref } from "vue";
import { Option } from "@/data/options";

// 부모로부터 받아온 options
const props = defineProps({
  options: {
    type: Array as () => Option[],
    required: true,
  },
});

// 팝업 상태 관리
const isDropDownOpen = ref(false);

const toggleDropdown = () => {
  isDropDownOpen.value = !isDropDownOpen.value;
  console.log("Dropdown toggled! Current state:", isDropDownOpen.value);
};
const selectOption = (option: Option) => {
  console.log(option.value);
};
</script>

<style lang="scss" scoped>
#dropdown-container {
  width: 100%;
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
  z-index: 1; /* 층위 값을 설정 */
}
.dropdown-content {
  @include custom-popup-shadow;
  @include custom-padding-x;
  @include custom-padding-y;

  margin-top: $margin-small;
  display: none;
  position: absolute;
  border-radius: $border-radius-default;
  background-color: white;
  left: 50%;
  transform: translateX(-50%);
}
.dropdown-content.show {
  display: block;
  z-index: 1000;
}

.dropdown-content > ul > li {
  @include custom-none-select-basic;
  @include custom-text;
  display: grid;
  min-width: 120px;
  min-height: 24px;
  place-items: center;
  border-radius: calc($border-radius-default/2);
  margin-bottom: $margin-default;
}
.dropdown-content > ul > li:last-child {
  margin-bottom: 0;
}
</style>
