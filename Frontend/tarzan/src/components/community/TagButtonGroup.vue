<template>
  <div class="tag-button-container">
    <TagButton
      class="tag=button"
      v-for="(TagButton, index) in buttons"
      :key="index"
      :label="TagButton.label"
      :isActive="selectedButton === index"
      @click="selectButton(index)"
    />
  </div>
</template>

<script>
import TagButton from './TagButton.vue';

export default {
  components: {
    TagButton,
  },
  data() {
    return {
      selectedButton: 0, // 기본으로 첫 번째 버튼 선택
      buttons: [
        { label: '전체' },
        { label: '교통' },
        { label: '맛집' },
        { label: '생활팁' },
        { label: '질문' },
        { label: '모임' },
        { label: '기타' },
      ]
    }
  },
  methods: {
    selectButton(index) {
      this.selectedButton = index;
    }
  }
}
</script>

<style scoped lang="scss">
.tag-button-container {
  display: flex;
  flex-wrap: nowrap;
  overflow-x: auto; /* 수평 스크롤 활성화 */
  gap: 5px;
  padding-left: $padding-small;
  padding-top: $padding-default;
  padding-bottom: $padding-small;
}
</style>
