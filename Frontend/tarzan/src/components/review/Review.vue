<template>
  <div class="sub-container">
    <TopBarBack title="실거주 후기" />
    <div class="center-container">
      <div class="rating-contaier">
        <StarRating v-model="rating" />
        <div class="number">
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

      <div class="review-container">
        <div class="review-group" id="photo0">
          <ResultBar resultTitle="사진 후기" />
          <div class="reivew-content">
            <PhotoUpload />
          </div>
        </div>

        <Divider />

        <div class="review-group" id="residence">
          <ResultBar resultTitle="실거주 후기" :sortOptions="sortOptions" />
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
  </div>
</template>
<script setup>
import { ref, onMounted } from "vue";

import { Review, Tag } from "@/data/review";

import { axiosInstance } from "@/plugins/axiosPlugin";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();

onMounted(() => {
  const houseIdx = Number(route.query.houseIdx);

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

import PhotoUpload from "./PhotoUpload.vue";
import Divider from "../common/Divider.vue";
import ReviewItem from "./ReviewItem.vue";
import StarRating from "./StarRating.vue";
import TagButtonGroup from "../common/TagButtonGroup.vue";

// 태그 버튼
const buttons = ref([
  { label: "전체", value: "ALL" },
  { label: "으앙", value: "TRANSPORT" },
  { label: "맛집", value: "TASTE" },
  { label: "생활팁", value: "LIFE" },
  { label: "질문", value: "QUESTION" },
  { label: "모임", value: "MEETING" },
  { label: "기타", value: "ETC" },
]);
const selectedButton = ref("ALL");
const sortOptions = ref(["최신순", "오래된순", "평점순"]);

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
</script>

<style lang="scss" scoped>
.sub-container {
  display: flex;
  flex-direction: column;
}

.center-container {
  background-color: #ededed;
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  width: 100%;
  gap: 10px;
  overflow-y: auto;
  @include custom-scrollbar-style;
}

.rating-contaier {
  background-color: white;
  display: flex;
  flex-direction: column;
  gap: 24px;
  box-sizing: border-box;
  @include custom-padding-x;
  @include custom-padding-y(24px);
}

.rating-contaier .number {
  display: flex;
  justify-content: center;
  gap: 3px;
}

.rating-contaier .number #average {
  @include custom-text-bold(red);
}

.rating-contaier .number #count {
  font-size: 16px;
  color: $secondary-color-200;
}

// :deep(.star svg) {
//   width: 36px;
// }

:deep(.tag-button-container) {
  flex-wrap: wrap;
  justify-content: center;
}

.review-container {
  background-color: white;
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  gap: 24px;
  padding-top: 24px;
  box-sizing: border-box;
  @include custom-padding-x;
}

.review-container .review-group {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.review-container .review-group .reivew-content {
  display: flex;
  flex-direction: column;
  gap: 16px;
  margin-top: 16px;
}
</style>
