<template>
  <div class="resultbar">
    <div class="resultbar-text">
      <span id="result-title">결과</span>
      <span id="result-number">1600</span>
    </div>
    <div class="select-sort">
      <CustomSelectBox
        :options="sortOptions"
      />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { Option } from "@/data/options";
import CustomSelectBox from "@/components/common/CustomSelectBox.vue";

const sortOptions: Option[] = [
  { idx: 1, name: "최신순", value: "latest" },
  { idx: 2, name: "조회수순", value: "views" },
  { idx: 3, name: "오래된순", value: "oldest" },
];

</script>

<style lang="scss" scoped>
  .resultbar {
    background-color: orange;
    display: flex;
    align-items: center;
    justify-content: space-between;
    @include custom-margin-x
  }

  .resultbar-text {
    background-color: yellow;
    display: flex;
    align-items: baseline;
  }

  #result-title {
    @include custom-text-bold;
  }

  #result-number{
    @include custom-text-bold($font-color:$primary-color-default, $font-size: 12px);
  }

  .select-sort {
    background-color: green;
  }
</style>