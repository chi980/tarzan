<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";

import { HouseOverview } from "@/data/house";
import { ReviewRequest } from "@/data/review";
import { LeaseType } from "@/data/review";

import TopBarBack from "@/components/common/TopBarBack.vue";
import Step1 from "@/components/review/CreateReview1.vue";
import Step2 from "@/components/review/CreateReview2.vue";

const route = useRoute();
const router = useRouter();

const houseIdx = Number(route.query.houseIdx);
const houseOverview = ref<HouseOverview | null>({
  house_name: "서울역게스트원룸",
  house_address: "서울시 강남구 테헤란로 123",
});
onMounted(() => {
  if (!houseIdx) {
    // 쿼리 파라미터가 없으면 홈으로 리디렉트
    alert("잘못된 접근입니다.");
    router.replace("/");
  }

  // houseIdx가 있을 경우 처리
  console.log("houseIdx:", houseIdx);
});

// 리뷰 작성 컴포넌트들
const steps = [Step1, Step2];
const step = ref(0);
// 초기 상태
const reviewData = ref<ReviewRequest>({
  review_house_id: 1,
  review_img_url: "https://example.com/images/house1.jpg",
  review_score: 4,
  review_lease_type: "MOnTHLY" as LeaseType,
  review_rent: 0,
  review_deposit: 20000,
  review_management_fee: 10,
  review_residence_period: 24,
  review_floor: 3,
  review_advantage: "역세권이라 교통이 편리하고, 환기가 잘 됨",
  review_advantage_tags: ["VENTILATION", "TRAIL", "QUITE"],
  review_disadvantage: "벌레가 자주 나옴",
  review_disadvantage_tags: ["BUG"],
});

const updateData = (partial: Partial<ReviewRequest>) => {
  Object.assign(reviewData.value, partial);
};

const next = () => {
  if (step.value < steps.length - 1) step.value++;
  else submit();
};

const submit = () => {
  console.log("제출할 데이터:", reviewData.value);
};
</script>

<template>
  <div class="sub-container">
    <TopBarBack title="후기 작성" @back="$router.go(-1)" />
    <div class="center-container">
      <div class="house-over-view-card">
        <p class="card-title">주소</p>
        <p class="house-name">{{ houseOverview?.house_name }}</p>
        <p class="house-address">{{ houseOverview?.house_address }}</p>
      </div>

      <div class="create-review-tabs">
        <component :is="steps[step]" :data="reviewData" @update="updateData" />
      </div>
    </div>
    <div
      style="
        width: 100%;
        padding-bottom: 16px;
        display: flex;
        flex-direction: row;
      ">
      <div class="button-default" @click="next">
        {{ step < steps.length - 1 ? "다음" : "제출" }}
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.center-container {
  overflow-x: hidden;
  overflow-y: auto;
  /* 스크롤바 전체 영역 */
  &::-webkit-scrollbar {
    width: 4px; /* 세로축 스크롤바 폭 너비 */
    height: 100%; /* 가로축 스크롤바 폭 너비 */
  }
  &::-webkit-scrollbar-button {
    display: none;
  }
  /* 스크롤바 막대 제외 부분 */
  &::-webkit-scrollbar-track {
    background: transparent;
  }
  /* 스크롤바 막대 */
  &::-webkit-scrollbar-thumb {
    border-radius: calc($border-radius-default * 2);
    background: #f2f2f2;
  }
}
// scoped
.sub-container {
  display: flex;
  flex-direction: column;

  align-items: center;
  justify-content: flex-start;
}

.house-over-view-card {
  @include custom-text($font-size: 12px, $font-color: #969696);
  @include custom-margin-x;
  @include custom-padding;
  display: flex;
  flex-direction: column;
  gap: $padding-small;
  align-items: start;
  justify-content: center;

  border-radius: 16px;

  background-color: #f8f8f8;

  .card-title {
    @include custom-text($font-size: 16px, $font-weight: 700);
  }

  .house-name {
    @include custom-text($font-size: 14px);
  }
}

.create-review-tabs {
  flex: 1;
  background-color: aqua;
}
.create-review-tabs .create-review-tab {
  display: flex;
  flex-direction: column;
  gap: 16px;
  width: 100%;
}

.button-default {
  @include custom-button-style(
    $bg-color: $secondary-color-default,
    $font-color: white
  );
  @include custom-margin-x;
  width: 100%;
}

.center-container {
  @include custom-margin-y;
  width: 100%;
  flex: 1;

  display: flex;
  flex-direction: column;
  gap: $padding-default;
}
</style>
