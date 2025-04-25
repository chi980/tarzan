<!--
  - v-model로 별점 바인딩 (Number)
  - :readonly="true" 옵션으로 읽기 전용 가능
  - 최대 5점까지 평가 가능
  - 사용 예시:
      <StarRating v-model="rating" />
      <StarRating v-model="rating" :readonly="true" />

  Emits:
    - update:modelValue (별 클릭 시 값 변경)
-->
<template>
  <div class="sub-container">
    <TopBarBack title="실거주 후기" />
    <div class="center-container">
      <div class="center-container-content">
        <StarRating v-model="rating" :readonly="true" />
        <div class="rating-description">
          <span id="average">4.0</span>
          <span id="count">(45)</span>
        </div>
        <div class="tag">
          <TagButtonGroup
            v-model:selectedButton="selectedButton"
            :buttons="buttons">
            <template v-slot:default="{ button }">
              <span>{{ button.label }}</span>
            </template>
          </TagButtonGroup>
        </div>
      </div>
      <!-- <div class="center-container-content" id="review-photo-wrapper">
          <div v-for="n in 4" :key="n" class="box"></div>
        </div> -->

      <div class="center-container-content">
        <ResultBar resultTitle="전체 후기" :sortOptions="sortOptions" />
        <List :fetchItems="fetchReviews" :params="params">
          <template #item="{ item }">
            <ReviewItem :review="item" />
          </template>
        </List>
      </div>
    </div>

    <BottomDefaultButton :label="'후기 작성하기'" :onClick="buttonHandler" />
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted } from "vue";

import { axiosInstance } from "@/plugins/axiosPlugin";
import { useRoute, useRouter } from "vue-router";

import BottomDefaultButton from "@/components/common/BottomDefaultButton.vue";
import List from "@/components/common/List.vue";

const route = useRoute();
const router = useRouter();
const houseIdx = Number(route.query.houseIdx);

onMounted(() => {
  if (!houseIdx) {
    // 쿼리 파라미터가 없으면 홈으로 리디렉트
    alert("잘못된 접근입니다.");
    router.replace("/");
  }
});

import TopBarBack from "@/components/common/TopBarBack.vue";
import ResultBar from "@/components/common/ResultBar.vue";

import PhotoUpload from "@/components/review/PhotoUpload.vue";
import ReviewItem from "@/components/review/ReviewItem.vue";
import StarRating from "@/components/review/StarRating.vue";
import TagButtonGroup from "@/components/common/TagButtonGroup.vue";

// 태그 버튼
const buttons = ref([
  { label: "🐛 벌레", value: "BUG" },
  { label: "🧹 건물 관리", value: "BUILDING_MANAGEMENT" },
  { label: "🚘 주차", value: "PARKING" },
  { label: "🔇 방음", value: "SOUND_PROOF" },
  { label: "🚨 치안 3", value: "SECURITY" },
  { label: "👮🏻 경비실", value: "SECURITY_GUARD" },
  { label: "🌳 산책로", value: "TRAIL" },
  { label: "🪟 환기", value: "VENTILATION" },
  { label: "🤫 조용한 동네", value: "QUITE" },
  { label: "🥶 단열", value: "CLEAN" },
  { label: "🐶 반려동물 키우기", value: "PREVENT_HEAT" },
  { label: "🛗 엘레베이터", value: "ELEVATOR" },
  { label: "🦠 곰팡이", value: "MOLD" },
  { label: "👟 평지", value: "FLAT" },
]);
const selectedButton = ref("ALL");

// 정렬 옵션
const sortOptions = ref([
  {
    idx: 0,
    name: "최신순",
    value: "최신순",
  },
  {
    idx: 1,
    name: "호평순",
    value: "호평순",
  },
  {
    idx: 2,
    name: "혹평순",
    value: "혹평순",
  },
]);

const rating = ref(3); // 초기 별점 값

// 리뷰 목록
const params = ref({
  houseIdx: houseIdx,
  size: 10,
  sortBy: selectedButton.value,
});

// API : 리뷰 목록 호출
const fetchReviews = async (page, params) => {
  console.log("params:", params);
  try {
    const response = await axiosInstance.get(`/v1/reviews`, {
      params: {
        ...params,
        page: page,
      },
    });

    if (response.data.success) {
      console.log("리뷰 목록:", response.data);
      return response.data.data.list;
    } else {
      console.error("Failed to fetch data:", response.data.message);
      return [];
    }
  } catch (error) {
    console.error("API request error:", error);
    return [];
  }
};

const buttonHandler = () => {
  router.push(`/review/create?houseIdx=${houseIdx}`);
};
</script>

<style lang="scss" scoped>
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
.center-container {
  @include custom-padding-y;
  width: 100%;
  flex: 1;

  display: flex;
  flex-direction: column;

  .center-container-content {
    @include custom-margin-y;
    @include custom-margin-x;

    display: flex;
    flex-direction: column;
    gap: $padding-default;
  }
}

.rating-description {
  display: flex;
  flex-direction: row;
  gap: $padding-small;
  justify-content: center; /* 가로(수평) 가운데 정렬 */
  align-items: center; /* 세로(수직) 가운데 정렬 */
  @include custom-text($font-size: 16px, $font-color: #b9bcbe);

  #average {
    @include custom-text(
      $font-size: 16px,
      $font-color: #f24822,
      $font-weight: 700
    );
  }
}

:deep(.tag-button-container) {
  flex-wrap: wrap;
  justify-content: center;
}

#review-photo-wrapper {
  display: flex;
  flex-direction: row;
  gap: $padding-small;

  .box {
    width: 70px;
    height: 70px;

    border-radius: $border-radius-default;
    background-color: #efefef;
  }
}
</style>
