<template>
  <div
    class="description-container"
    :style="{ backgroundColor: props.backgroundColor }"
  >
    <img :src="props.descriptionImgSrc" alt="img" />
    <div class="description-content">
      <p v-html="props.descriptionTitle"></p>
      <p v-html="props.descriptionContent"></p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineProps } from "vue";

// 부모로부터 받아온 options
const props = defineProps({
  descriptionImgSrc: {
    default: "default-image-path.jpg",
  },
  descriptionTitle: {
    type: String,
    default: "기본 제목입니다",
  },
  descriptionContent: {
    type: String,
    default: "기본 내용입니다",
  },
  backgroundColor: {
    type: String,
    default: "#F2F2F2",
  },
});
// 이미지 경로 계산
</script>

<style lang="scss" scoped>
.description-container {
  @include custom-none-select-basic;
  display: flex;
  justify-content: center; /* 가로축 중앙 정렬 */
  align-items: center; /* 세로축 중앙 정렬 */
  gap: $margin-default;

  height: 150px;
}
.description-container > img {
  height: 80px;
  width: 80px;
  object-fit: contain;
  object-position: center;
}
.description-content {
  @include custom-text-description;
  display: flex;
  flex-direction: column;
  gap: calc($padding-small/2);
  justify-content: center; /* 가로축 중앙 정렬 */
  align-items: flex-start; /* 세로축 중앙 정렬 */
  text-align: left; /* 왼쪽 정렬 */
}
.description-content > p:first-child {
  @include custom-text-bold($font-size: 18px);
  line-height: 1.3em;
}
.description-content > p:last-child {
  @include custom-text($font-size: 12px, $font-color: $text-color-light);
}
</style>
