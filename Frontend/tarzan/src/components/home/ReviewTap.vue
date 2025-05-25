<script setup lang="ts">
import { ref, watch } from "vue";
import { useRouter } from "vue-router";

import { Review } from "@/data/review";
import ReviewItem from "@/components/review/ReviewItem.vue";
import NonContent from "@/components/common/NonContent.vue";
import arrowRightImgSrc from "@/assets/icons/Arrows-chevron/Arrow-Right/chevron-right.png";

const router = useRouter();

const props = defineProps<{
  houseId: number;
  reviews: Review[];
}>();

// ✅ props.reviews를 복사해서 내부 상태로 관리
const localReviews = ref<Review[]>([...props.reviews]);

// ✅ props.reviews가 바뀌면 localReviews도 반영
watch(
  () => props.reviews,
  (newReviews) => {
    localReviews.value = [...newReviews];
  },
  { deep: true, immediate: true }
);

const showMoreReviews = () => {
  router.push({
    name: "Review",
    query: {
      houseIdx: props.houseId,
    },
  });
};

const goToReviewCreatePage = () => {
  router.push({
    name: "ReviewCreate",
    query: {
      houseIdx: props.houseId,
    },
  });
};

const handleDelete = (review: Review) => {
  const key = "review_id";
  localReviews.value = localReviews.value.filter(
    (i) => i[key] !== localReviews[key]
  );
};
</script>

<template>
  <div class="review-tap-wrapper">
    <div v-if="localReviews.length > 0">
      <div class="review-item-wrapper">
        <ReviewItem
          v-for="(review, index) in localReviews"
          :key="index"
          :review="review"
          @delete="handleDelete(review)" />
      </div>
      <div class="more-button" @click="showMoreReviews">
        <p>더보기</p>
        <img :src="arrowRightImgSrc" alt=">" />
      </div>
    </div>
    <div v-if="!localReviews || localReviews.length == 0">
      <NonContent :value="'리뷰가 없습니다.'">
        <p class="non-content-sub-desc" @click="goToReviewCreatePage">
          리뷰를 추가할까요?
        </p>
      </NonContent>
    </div>
  </div>
</template>

<style scoped lang="scss">
.non-content-sub-desc {
  @include custom-text($font-size: 12px);
  line-height: 100%;
  text-decoration-line: underline;
}
// scoped
.review-tap-wrapper {
  @include custom-padding-y;
  display: flex;
  flex-direction: column;
  gap: 0;

  .review-item-wrapper {
    @include custom-padding-x;
    display: flex;
    flex-direction: column;
    gap: $padding-default;
  }

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
