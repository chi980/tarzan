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
    // sortOptions: {
    //   type: Array,
    //   required: true,
    // }
  },
  data() {
    return {
      sortOptions: [
        {idx: 1, value: 'date', name: '날짜순' },
        { idx: 2, value: 'popularity', name: '인기순' },
        { idx: 3, value: 'rating', name: '평점순' },
      ],
    }
  },
}
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
  }

  :deep(.selected-item) {
    background-color: white;
  }
</style>