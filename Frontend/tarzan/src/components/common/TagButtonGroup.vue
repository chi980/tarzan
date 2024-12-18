<template>
  <div class="tag-button-container">
    <TagButton
      class="tag-button"
      v-for="button in buttons"
      :key="button.value"  
      :label="button.label"
      :isActive="selectedButton === button.value"
      @click="selectButton(button.value)"
    />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import TagButton from '@/components/common/TagButton.vue';

const buttons = [
  { label: '전체', value: 'ALL' },
  { label: '교통', value: 'TRANSPORT' },
  { label: '맛집', value: 'TASTE' },
  { label: '생활팁', value: 'LIFE' },
  { label: '질문', value: 'QUESTION' },
  { label: '모임', value: 'MEETING' },
  { label: '기타', value: 'ETC' },
];

// Community.vue와 태그 데이터 양방향 바인딩
const props = defineProps({ selectedButton: String })
const emit = defineEmits(['update:selectedButton']);

// 태그 버튼 클릭 시 해당 버튼 값으로 업데이트
const selectButton = (value) => {
  emit('update:selectedButton', value); 
};
</script>

<style scoped lang="scss">
.tag-button-container {
  display: flex;
  flex-wrap: nowrap;
  overflow-x: auto; 
  gap: 5px;
}
</style>
