import { defineStore } from "pinia";

export const useReviewStore = defineStore("review", {
  state: () => ({
    reviewData: {
      buildIdx: 1,  // 건물 ID (예제에서는 기본값 1)
      review_score: 3,  // 만족도 점수
      review_lease_type: "",  // 전세 | 월세
      review_rent: null,  // 월세
      review_deposit: null,  // 보증금
      review_management_fee: null,  // 관리비
      review_residence_period: null,  // 거주 기간 (년)
      review_floor: null,  // 층수
      review_advantage: "",  // 장점
      review_advantage_tag: [],  // 장점 태그
      review_disadvantage: "",  // 단점
      review_disadvantage_tag: []  // 단점 태그
    },
  }),
  actions: {
    setReviewData(data) {
      this.reviewData = { ...this.reviewData, ...data };
    },
    resetReviewData() {
      this.reviewData = {
        buildIdx: 1,
        review_score: 3,
        review_lease_type: "",
        review_rent: null,
        review_deposit: null,
        review_management_fee: null,
        review_residence_period: null,
        review_floor: null,
        review_advantage: "",
        review_advantage_tag: [],
        review_disadvantage: "",
        review_disadvantage_tag: []
      };
    },
  },
});
