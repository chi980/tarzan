// home2.vue

<template>
  <div @click="closeDropdownOutside">
    sdfsdjfhklsdkjshkfd
    <SelectBox :options="options" :isDropDownOpen="isDropDownOpen" />
    <h1>dfsjkdlf;</h1>
  </div>
</template>
<script setup lang="ts">
import { ref } from "vue";
import { Option } from "@/data/options";
import SelectBox from "@/components/common/CustomSelectBox.vue";
const options = [
  { idx: 1, name: "Option 1" },
  { idx: 2, name: "Option 2" },
  { idx: 3, name: "Option 3" },
];

const isDropDownOpen = ref(false);

const closeDropdownOutside = () => {
  isDropDownOpen.value = false; // 외부 클릭 시 드롭다운 닫기
};
</script>

<style scoped></style>
