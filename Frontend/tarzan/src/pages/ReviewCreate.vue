<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";

import { HouseOverview } from "@/data/house";
import { ReviewRequest } from "@/data/review";
import { LeaseType } from "@/data/review";

import TopBarBack from "@/components/common/TopBarBack.vue";
import AddressCard from "@/components/common/\bAddressCard.vue";
import Step1 from "@/components/review/CreateReview1.vue";
import Step2 from "@/components/review/CreateReview2.vue";
import BottomDefaultButton from "@/components/common/BottomDefaultButton.vue";

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

const topBarHandler = () => {
  if (step.value > 0) {
    step.value--;
    return;
  }
  router.back();
};

// 리뷰 작성 컴포넌트들
const steps = [Step1, Step2];
const step = ref(0);
// 초기 상태
const reviewData = ref<ReviewRequest>({
  review_house_id: 1,
  review_img_url: "https://example.com/images/house1.jpg",
  review_score: 4,
  review_lease_type: "MONTHLY" as LeaseType,
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
const prev = () => {
  if (step.value > 0) step.value--;
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
    <TopBarBack title="후기 작성" @back="topBarHandler" />
    <div class="center-container">
      <AddressCard :houseOverview="houseOverview" />

      <div class="create-review-tabs">
        <component :is="steps[step]" :data="reviewData" @update="updateData" />
      </div>
    </div>
    <BottomDefaultButton
      :label="step < steps.length - 1 ? '다음' : '제출'"
      :onClick="next" />
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

.create-review-tabs {
  flex: 1;
}
.create-review-tabs .create-review-tab {
  display: flex;
  flex-direction: column;
  gap: 16px;
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
