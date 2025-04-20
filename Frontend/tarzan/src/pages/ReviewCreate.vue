<script setup lang="ts">
import { ref, onMounted, reactive, watch } from "vue";
import { useRoute, useRouter } from "vue-router";

import { HouseOverview } from "@/data/house";
import { ReviewRequest } from "@/data/review";
import { LeaseType } from "@/data/review";

import { axiosInstance } from "@/plugins/axiosPlugin";
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
    prev();
    return;
  }
  router.back();
};

// 리뷰 작성 컴포넌트들
const steps = [Step1, Step2];
const step = ref(0);
// 초기 상태
const reviewData = reactive<ReviewRequest>({
  review_house_id: houseIdx,
  review_img_url: null,
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
watch(
  () => reviewData,
  (newValue) => {
    console.log("reviewData:", newValue);
  },
  { deep: true }
);

const prev = () => {
  if (step.value > 0) step.value--;
};
const next = () => {
  if (step.value < steps.length - 1) step.value++;
  else submit();
};
const submit = async () => {
  try {
    if (
      reviewData.review_score === null ||
      reviewData.review_score === undefined
    ) {
      alert("만족도를 입력해주세요.");
      return;
    }

    const submitData = {
      review_house_id: reviewData.review_house_id,
      review_img_url: reviewData.review_img_url,
      review_score: reviewData.review_score,
      review_lease_type: reviewData.review_lease_type,
      review_rent: reviewData.review_rent,
      review_deposit: reviewData.review_deposit,
      review_management_fee: reviewData.review_management_fee,
      review_residence_period: reviewData.review_residence_period,
      review_floor: reviewData.review_floor,
      review_advantage: reviewData.review_advantage,
      review_advantage_tags: reviewData.review_advantage_tags,
      review_disadvantage: reviewData.review_disadvantage,
      review_disadvantage_tags: reviewData.review_disadvantage_tags,
    };

    const response = await axiosInstance.post("/v1/reviews", submitData);

    // 성공 시 처리
    console.log("리뷰 작성 성공", response.data);
    alert("리뷰가 성공적으로 등록되었습니다.");

    router.replace("/review?houseIdx=" + houseIdx);
  } catch (error: any) {
    if (error.response) {
      // 서버에서 오류 응답이 있을 경우
      if (error.response.status === 400) {
        // Bad Request (유효성 검사 오류)
        alert(`유효성 검사 오류: ${error.response.data.message}`);
      } else if (error.response.status === 500) {
        // Internal Server Error
        alert("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
      } else {
        // 그 외 오류 상태 처리
        alert(`알 수 없는 오류가 발생했습니다. 코드: ${error.response.status}`);
      }
    } else if (error.request) {
      // 요청은 보냈으나 응답을 받지 못한 경우
      alert("서버 응답을 받을 수 없습니다. 네트워크 상태를 확인해주세요.");
    } else {
      // 그 외 오류
      alert(`오류 발생: ${error.message}`);
    }
  }
};
</script>

<template>
  <div class="sub-container">
    <TopBarBack title="후기 작성" @back="topBarHandler" />
    <div class="center-container">
      <div class="padding-container">
        <AddressCard :houseOverview="houseOverview" />
      </div>

      <div class="create-review-tabs" v-if="reviewData">
        <component :is="steps[step]" v-model:reviewData="reviewData" />
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
.padding-container {
  @include custom-padding-x;
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
