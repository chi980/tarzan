<script setup lang="ts">
import { ref } from "vue";

import { useRouter } from "vue-router";

import { Review } from "@/data/house";
import ReviewItem from "@/components/review/ReviewItem.vue";
import NonContent from "@/components/common/NonContent.vue";
import arrowRightImgSrc from "@/assets/icons/Arrows-chevron/Arrow-Right/chevron-right.png";

const router = useRouter();

const props = defineProps<{
  houseId: number;
  reviews: Review[];
}>();

const showMoreReviews = () => {
  console.log("houseId" + props.houseId);
  router.push({
    name: "Review",
    query: {
      houseIdx: props.houseId,
    },
  });
};
</script>

<template>
  <div class="review-tap-wrapper">
    <div v-if="props.reviews > 0">
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
    <div v-if="!props.reviews || props.reviews == 0">
      <NonContent :value="'리뷰가 없습니다.'" />
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
