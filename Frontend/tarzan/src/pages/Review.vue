<template>
  <div class="sub-container">
    <TopBarBack title="실거주 후기" />
    <div class="center-container">
      <div class="center-container-content">
        <StarRating v-model="rating" />
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
      <div>
        <div class="center-container-content" id="review-photo-wrapper">
          <div v-for="n in 4" :key="n" class="box"></div>
        </div>

        <div class="center-container-content">
          <ResultBar resultTitle="전체 후기" :sortOptions="sortOptions" />

          <div class="reivew-content">
            <ReviewItem
              v-for="(review, index) in reviews"
              :key="index"
              :review="review" />
          </div>
        </div>

        <button>더보기</button>
      </div>
    </div>

    <div class="button-group">
      <div class="button-default" @click="buttonHandler">후기 작성하기</div>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted } from "vue";

// import { Review, Tag } from "@/data/review";

import { axiosInstance } from "@/plugins/axiosPlugin";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();
const houseIdx = Number(route.query.houseIdx);

onMounted(() => {
  if (!houseIdx) {
    // 쿼리 파라미터가 없으면 홈으로 리디렉트
    alert("잘못된 접근입니다.");
    router.replace("/");
  }

  // houseIdx가 있을 경우 처리
  console.log("houseIdx:", houseIdx);
});

import TopBarBack from "@/components/common/TopBarBack.vue";
import ResultBar from "@/components/common/ResultBar.vue";

import PhotoUpload from "@/components/review/PhotoUpload.vue";
import Divider from "@/components/common/Divider.vue";
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
// const sortOptions = ref(["최신순", "오래된순", "평점순"]);÷
const sortOptions = ref([
  {
    idx: 0,
    name: "최신순",
    value: "최신순",
  },
  {
    idx: 1,
    name: "오래된순",
    value: "오래된순",
  },
  {
    idx: 2,
    name: "평점순",
    value: "평점순",
  },
]);

const rating = ref(3); // 초기 별점 값

const reviews = ref([]); // 게시글 목록
// API : 리뷰 목록 호출
const fetchReviews = async () => {
  const queryParams = new URLSearchParams({
    houseIdx: 1,
    size: 3,
    page: 0,
    sortBy: "최신순",
  }).toString();

  try {
    const response = await axiosInstance.get(`/v1/reviews?${queryParams}`);

    if (response.data.success) {
      reviews.value = response.data.data.list;
      console.log(reviews.value);
    } else {
      console.error("Failed:", response.data.message);
      alert(`Error: ${response.data.message}`);
    }
  } catch (error) {
    console.error("Error fetching posts:", error);
    alert("후기를 불러오는 데 실패했습니다.");
  }
};

// 컴포넌트가 생성될 때 데이터를 불러옴
onMounted(() => {
  // fetchReviews();
});

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
.button-group {
  width: 100%;
  padding-bottom: 8px;
  display: flex;
  flex-direction: row;
}
.button-default {
  @include custom-margin-x;
  @include custom-button-style(
    $bg-color: $secondary-color-default,
    $font-color: white
  );
  flex: 1;
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
