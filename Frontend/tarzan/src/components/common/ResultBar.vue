<template>
  <div class="resultbar">
    <div class="resultbar-text">
      <span id="result-title">{{ resultTitle }}</span>
      <span id="result-number">1600</span>
    </div>
    <div class="post-sort">
      <CustomSelectBox 
      :options="sortOptions"
      @update:selected="handleSortSelectedIdx"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, defineEmits } from "vue";
import CustomSelectBox from "@/components/common/CustomSelectBox.vue";

// props 정의
const props = defineProps({
  resultTitle: {
    type: String,
    required: true,
  },
  sortOptions: {
    type: Array,
  },
});

// emits 정의
const emit = defineEmits(["updateSortBy"]);

// 선택된 인덱스 상태를 관리하는 ref
const selectedSortIdx = ref(null);

// 선택된 정렬 인덱스를 업데이트하는 함수
const handleSortSelectedIdx = (idx) => {
  selectedSortIdx.value = idx;
  console.log("Selected idx:", selectedSortIdx.value);
  emit("updateSortBy", idx); // 선택한 인덱스를 부모에게 전달

};
</script>

<style lang="scss" scoped>
  .resultbar {
    display: flex;
    align-items: center;
    // background-color: yellow;
  }

  .resultbar-text {
    display: flex;
    align-items: baseline;
    flex-grow: 1;
    gap: 5px;
  }

  #result-title {
    @include custom-text-bold;
  }

  #result-number{
    @include custom-text-bold($primary-color-default, 12px);
    margin: 10px
  }

  :deep(.selected-item) {
    background-color: white;
  }
</style>