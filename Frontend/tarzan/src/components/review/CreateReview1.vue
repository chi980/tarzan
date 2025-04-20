<template>
  <form class="input-form" @submit.prevent="submitForm">
    <!-- <div class="input-group photo-upload-container">
      <PhotoUpload />
      <p>
        캡쳐한 이미지, 본인이 촬용하지 않은 사진, 식별 불가능한 이미지를
        등록하는 경우 이미지 비노출 및 정상 후기로 인정되지 않습니다.
      </p>
    </div> -->
    <div class="input-group">
      <h2 class="input-title">
        만족도<span class="input-title-mandatory">*</span>
      </h2>
      <div class="input-content star-container">
        <StarRating
          v-model="reviewData.review_score"
          @update:score="updateReviewScore"
          :readonly="false" />
      </div>
    </div>

    <div class="input-group">
      <h2 class="input-title">
        관리비 및 생활 요금<span class="input-title-mandatory">*</span>
      </h2>
      <div class="input-content-wrapper">
        <div class="input-content select-container">
          <CustomSelectBox
            :options="rentalOptions"
            :parentStyle="{
              backgroundColor: 'white',
              fontWeight: 400,
              justifyContent: `space-between`,
              border: '1px solid #e7e7e7',
            }"
            v-model="reviewData.review_lease_type"
            @update:selected="handleLeaseType" />
          <input
            type="text"
            placeholder="월세를 입력해주세요."
            :class="{ disabled: isDisabled }"
            v-model="reviewData.review_rent"
            :disabled="isDisabled" />
        </div>
        <div class="input-content">
          <input
            type="text"
            placeholder="보증금을 입력해주세요."
            v-model="reviewData.review_deposit" />
        </div>
        <div class="input-content">
          <input
            type="text"
            placeholder="관리비를 입력해주세요."
            v-model="reviewData.review_management_fee" />
        </div>
      </div>
    </div>
    <div class="input-group">
      <h2 class="input-title">
        거주 년도<span class="input-title-mandatory">*</span>
      </h2>
      <div class="input-content">
        <input
          type="text"
          placeholder="거주 년도를 입력해주세요."
          v-model="reviewData.review_residence_period"
          maxlength="4"
          @input="
            (e) => {
              const onlyNums = e.target.value.replace(/[^0-9]/g, '');
              reviewData.review_residence_period = onlyNums.slice(0, 4);
            }
          " />
      </div>
    </div>

    <div class="input-group" id="floor">
      <h2 class="input-title">층수</h2>
      <div class="input-content">
        <input
          type="text"
          placeholder="층수를 입력해주세요."
          v-model="reviewData.review_floor"
          maxlength="3"
          @input="
            (e) => {
              const val = e.target.value;
              // 0 이상의 숫자만 허용
              const parsed = val.replace(/[^0-9]/g, ''); // 숫자만 허용
              reviewData.review_floor = parsed;
            }
          " />
      </div>
    </div>
    <!-- 

      <div class="input-group" id="waste">
        <h2 class="input-title">쓰레기 버리는 방법</h2>
        <div class="input-content">
          <input type="text" placeholder="쓰레기 버리는 방법을 입력해주세요." />
        </div>
      </div> -->
    <!-- </div> -->
  </form>
</template>

<script setup>
import { reactive, ref, defineModel, computed } from "vue";
import TopBarBack from "@/components/common/TopBarBack.vue";
import AddressCard from "./AddressCard.vue";
import PhotoUpload from "./PhotoUpload.vue";
import StarRating from "./StarRating.vue";
import Divider from "../common/Divider.vue";
import CustomSelectBox from "../common/CustomSelectBox.vue";
const reviewData = defineModel("reviewData");

// 임대 유형 선택
const rentalOptions = reactive([
  { idx: 0, name: "월세", value: "MONTHLY" },
  { idx: 1, name: "전세", value: "KEY_MONEY" },
]);
const handleLeaseType = (idx) => {
  const selectedOption = rentalOptions.find((option) => option.idx === idx);
  if (selectedOption.value == "MONTHLY") {
    reviewData.value.review_rent = null;
  }

  reviewData.value.review_lease_type = selectedOption.value;
};
const isDisabled = computed(
  () => reviewData.value.review_lease_type !== "MONTHLY"
);

// 별점 선택 
const rating = ref(0);
const updateReviewScore = (newScore) => {};
</script>

<style scoped lang="scss">
.input-form {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: $padding-big;

  .input-group {
    @include custom-padding-x();
    display: flex;
    flex-direction: column;
    gap: 12px;

    .input-title {
      @include custom-text($font-size: 14px);
      text-align: left;

      .input-title-mandatory {
        color: red;
      }
    }

    .input-content {
      display: flex;
      align-items: center;
      gap: $padding-small;

      input[type="text"] {
        @include custom-input-style;
        flex: 1;
      }
    }
  }
}
// scoped
.photo-upload-container {
  @include custom-text($font-size: 12px, $font-color: #969696);

  align-items: start;

  line-height: 1.5; /* 보통 1.5 ~ 1.8이 읽기 좋음 */
  text-align: left;
}

.star-container {
  display: flex;
  align-items: center;
  justify-content: center;
}

.select-container {
  display: flex;
  flex-direction: row;
  gap: $padding-small;

  input[type="text"] {
    width: 30px;
  }
}

.input-content-wrapper {
  display: flex;
  flex-direction: column;
  gap: $padding-small;
}
.disabled {
  background-color: #00000010 !important;
  cursor: not-allowed;
}
</style>
