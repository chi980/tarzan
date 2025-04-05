<template>
  <div class="review-item">
    <div class="writer-container">
      <!-- <img :src="review.review_img_url" alt="후기 이미지" class="writer-img" v-if="review.review_img_url">
      <img src="@/assets/icons/Filter/writer-icon.png" alt="기본 이미지" class="writer-img" v-else> -->
      <img
        :src="review.review_img_url || defaultImage"
        alt="후기 이미지"
        class="review-image" />
      <div class="writer-info">
        <span class="username">{{ review.review_writer_nickname }}</span>
        <span class="residence-period">{{
          review.review_residence_period
        }}</span>
        <StarRating v-model="rating" :readonly="true" />
      </div>
    </div>

    <div class="review-content-container">
      <div class="review-content">
        <h4>장점</h4>
        <p>{{ review.review_advantage }}</p>
      </div>
      <div class="review-content">
        <h4>단점</h4>
        <p>{{ review.review_disadvantage }}</p>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, defineProps } from "vue";
import defaultImage from "@/assets/icons/Filter/writer-icon.png";
import StarRating from "./StarRating.vue";

const pros = defineProps({
  review: {
    type: Object,
    require: true,
  },
});

const rating = ref(3); // 초기 별점 값
</script>
<style lang="scss" scoped>
.review-item {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.writer-container {
  display: flex;
  gap: 16px;
}

.writer-container img {
  width: 40px;
  height: 40px;
}

.writer-container .writer-info {
  display: flex;
  flex-direction: column;
  text-align: left;
  gap: 4px;
}

.writer-container .writer-info .username {
  font-size: 14px;
  font-weight: 500;
}

.writer-container .writer-info .residence-period {
  font-size: 12px;
}

.review-content-container {
  background-color: #f8f8f8;
  border-radius: 16px;
  display: flex;
  flex-direction: column;
  gap: 24px;
  @include custom-padding;
}

.review-content-container .review-content {
  display: flex;
  gap: 12px;
  text-align: left;
  align-items: baseline;
}

.review-content-container .review-content h4 {
  font-size: 12px;
  white-space: nowrap;
}

.review-content-container .review-content p {
  font-size: 12px;
  color: #969696;
  line-height: 1.5;
}
</style>
