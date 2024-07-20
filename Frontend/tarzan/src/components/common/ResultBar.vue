<template>
  <div class="resultbar">
    <div class="resultbar-text">
      <span id="result-title">{{ resultTitle }}</span>
      <span id="result-number">1600</span>
    </div>
    <div class="post-sort">
      <CustomSelectBox :options="sortOptions" />
    </div>
  </div>
</template>

<script>
import CustomSelectBox from "@/components/common/CustomSelectBox.vue";

export default {
  components: {
    CustomSelectBox,
  },
  props: {
    resultTitle: {
      type: String,
      required: true,
    },
    // Community.vue에서 전달받은 sortOptions prop을 정의
    sortOptions: {
      type: Array,
      required: true,
    }
  },
}
</script>

<style lang="scss" scoped>
  .resultbar {
    display: flex;
    align-items: center;
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
  }

  :deep(.selected-item) {
    background-color: white;
  }
</style>