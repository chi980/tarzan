<script setup lang="ts">
import { ref } from "vue";
import { Review } from "@/data/house";
import ReviewItem from "@/components/review/ReviewItem.vue";
import arrowRightImgSrc from "@/assets/icons/Arrows-chevron/Arrow-Right/chevron-right.png";

const props = defineProps<{
  reviews: Review[];
}>();

const showMoreReviews = () => {
  console.log("더보기 클릭");
};
</script>

<template>
  <div class="review-tap-wrapper">
    <ReviewItem
      v-for="(review, index) in reviews"
      :key="index"
      :review="review" />
    <div
      class="more-button"
      v-if="reviews.length >= 3"
      @click="showMoreReviews">
      <p>더보기</p>
      <img :src="arrowRightImgSrc" alt=">" />
    </div>
  </div>
</template>

<style scoped lang="scss">
.review-tap-wrapper {
  @include custom-padding-y;
  display: flex;
  flex-direction: column;
  gap: 0;

  .more-button {
    @include custom-padding;
    @include custom-text($font-color: $text-color-light, $font-size: 14px);
    display: flex;
    justify-content: center;
    align-items: center;

    img {
      @include custom-icon-style(16px);
    }
  }
}
</style>
