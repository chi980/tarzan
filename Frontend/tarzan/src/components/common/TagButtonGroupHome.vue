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
import TagButton from '@/components/common/TagButton.vue';

export default {
  components: {
    TagButton,
  },
  data() {
    return {
      selectedButton: 0, // 기본으로 첫 번째 버튼 선택
      buttons: [
        { label: '원룸' },
        { label: '오피스텔' },
        { label: '아파트' },
        { label: '병원' },
        { label: '편의시설' },
        { label: '상업시설' },
        { label: '버스정류장' },
        { label: '지하철' },
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
  padding: 20px;
  overflow-x: scroll; /* 수평 스크롤 기능 활성화 */
  gap: 5px;

  /* 스크롤바 숨기기 */
  &::-webkit-scrollbar {
    display: none; /* WebKit 브라우저에서 스크롤바를 숨깁니다 */
  }
}
</style>
