<template>
  <div>
    <h1>tmp입니다.</h1>
    <div @click="closeDropdownOutside">
      sdfsdjfhklsdkjshkfd
      <SelectBox :options="options" :isDropDownOpen="isDropDownOpen" />
      <h1>dfsjkdlf;</h1>
    </div>
    <div class="select-box">
      <select v-model="selectedOption">
        <option disabled value="">Choose an option</option>
        <option v-for="option in options" :key="option.idx" :value="option.idx">
          {{ option.name }}
        </option>
      </select>
      <p v-if="selectedOption">
        Selected:
        {{ options.find((option) => option.id === selectedOption)?.name }}
      </p>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref } from "vue";
import { Option } from "@/data/options";
import SelectBox from "@/components/common/SelectBox.vue";
const options = [
  { idx: 1, name: "Option 1" },
  { idx: 2, name: "Option 2" },
  { idx: 3, name: "Option 3" },
];

const isDropDownOpen = ref(false);

const closeDropdownOutside = () => {
  isDropDownOpen.value = false; // 외부 클릭 시 드롭다운 닫기
  alert("그 외의 곳을 클릭했슴다.");
};

// 선택된 옵션을 관리할 ref
const selectedOption = ref("");

// 선택된 옵션 변경 시 로직
const handleChange = () => {
  console.log("Selected option:", selectedOption.value);
};
</script>

<style scoped lang="scss">
select {
  display: inline-block;
  width: 100%;
}
</style>
