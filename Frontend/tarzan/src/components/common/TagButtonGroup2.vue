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
      selectedButton: 0,
      buttons: [
        { label: '❌ 없음' },
        { label: '🐛 벌레' },
        { label: '🧹 건물 관리' },
        { label: '🚘 주차' },
        { label: '🔇 방음' },
        { label: '🚨 치안' },
        { label: '👮🏻 경비실' },
        { label: '🌳 산책로' },
        { label: '🪟 환기' },
        { label: '🤫 조용한 동네' },
        { label: '🥶 단열' },
        { label: '🐶 반려동물 키우기' },
        { label: '🛗 엘레베이터' },
        { label: '🦠 곰팡이' },
        { label: '👟 평지' },
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
  gap: 5px;
}
</style>
