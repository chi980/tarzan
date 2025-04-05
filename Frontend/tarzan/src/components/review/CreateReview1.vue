<template>
  <div>
    <div class="photo-upload-container">
      <PhotoUpload />
      <p>
        캡쳐한 이미지, 본인이 촬용하지 않은 사진, 식별 불가능한 이미지를
        등록하는 경우 이미지 비노출 및 정상 후기로 인정되지 않습니다.
      </p>
    </div>

    <Divider />

    <div class="star-rating-container">
      <h2 class="input-title">만족도</h2>
      <div class="star">
        <StarRating
          v-model="reviewStore.reviewData.review_score"
          @update:score="updateReviewScore"
          :readonly="false" />
      </div>
    </div>

    <Divider />

    <div class="input-container">
      <div class="input-group" id="living-expense">
        <h2 class="input-title">관리비 및 생활 요금</h2>
        <div class="input-content">
          <div id="rent-type">
            <CustomSelectBox
              :options="rentalOptions"
              v-model:selected="reviewStore.reviewData.review_lease_type"
              @update:selected="updateLeaseType" />
            <input
              type="text"
              placeholder="월세를 입력해주세요."
              v-model="reviewStore.reviewData.review_rent" />
          </div>
          <input
            type="text"
            placeholder="보증금을 입력해주세요."
            v-model="reviewStore.reviewData.review_deposit" />
          <input
            type="text"
            placeholder="관리비를 입력해주세요."
            v-model="reviewStore.reviewData.review_management_fee" />
        </div>
      </div>

      <div class="input-group" id="residence-year">
        <h2 class="input-title">거주 년도</h2>
        <div class="input-content">
          <input
            type="text"
            placeholder="거주 년도를 입력해주세요."
            v-model="reviewStore.reviewData.review_residence_period" />
        </div>
      </div>

      <div class="input-group" id="floor">
        <h2 class="input-title">층수</h2>
        <div class="input-content">
          <input
            type="text"
            placeholder="층수를 입력해주세요."
            v-model="reviewStore.reviewData.review_floor" />
        </div>
      </div>

      <div class="input-group" id="waste">
        <h2 class="input-title">쓰레기 버리는 방법</h2>
        <div class="input-content">
          <input type="text" placeholder="쓰레기 버리는 방법을 입력해주세요." />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, watchEffect } from "vue";
import { useRouter } from "vue-router";
import { useReviewStore } from "@/stores/reviewStore";
import TopBarBack from "@/components/common/TopBarBack.vue";
import AddressCard from "./AddressCard.vue";
import PhotoUpload from "./PhotoUpload.vue";
import StarRating from "./StarRating.vue";
import Divider from "../common/Divider.vue";
import CustomSelectBox from "../common/CustomSelectBox.vue";

const router = useRouter();
const reviewStore = useReviewStore();

const rating = ref(0);

const rentalOptions = reactive([
  { idx: 0, name: "월세", value: "MONTHLY" },
  { idx: 1, name: "전세", value: "KEY_MONEY" },
]);

const goToNextPage = () => {
  console.log(
    "🚀 CreateReview1 - 다음 페이지 이동 직전 데이터:",
    reviewStore.reviewData
  );
  router.push({ name: "CreateReview2" });
};

// 선택된 임대 유형 업데이트
const updateLeaseType = (idx) => {
  const selectedOption = rentalOptions.find((option) => option.idx === idx);
  if (selectedOption) {
    reviewStore.reviewData.review_lease_type = selectedOption.value;
  }
};

// 별점 선택 시 review_score 업데이트
const updateReviewScore = (newScore) => {
  reviewStore.reviewData.review_score = newScore;
  console.log("현재 선택된 리뷰 점수:", reviewStore.reviewData.review_score);
};
</script>

<style scoped lang="scss">
.photo-upload-container {
  display: flex;
  flex-direction: column;
  gap: 26px;
}

.photo-upload-container p {
  font-size: 12px;
  font-weight: 500;
  color: #969696;
  text-align: left;
}

.star-rating-container .star {
  margin-top: 10px;
  margin-bottom: 10px;
}

.input-container {
  display: flex;
  flex-direction: column;
  gap: 32px;
}

.input-container .input-group {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.center-container .input-title {
  @include custom-text-bold;
  text-align: left;
}

.input-container .input-group .input-content {
  display: flex;
  flex-direction: column;
  gap: $padding-small;
}

.input-container .input-group .input-content input {
  @include custom-input-style;
}

.input-container #living-expense .input-content #rent-type {
  display: flex;
  gap: $padding-small;
}
</style>
