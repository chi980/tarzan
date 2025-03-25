import { defineStore } from "pinia";

export const useReviewStore = defineStore("review", {
  state: () => ({
    reviewData: {
      review_house_id: 1, // 현재 임시로 하드코딩된 값
      review_img_url: "",
   
      review_residence_period: null,
      review_floor: null,
      review_score: null,

      review_lease_type: "",
      review_deposit: null,
      review_management_fee: null,
      review_advantage: "",
      review_advantage_tags: [],
      review_disadvantage: "",
      review_disadvantage_tags: [],
    },
  }),
  actions: {
    setReviewData(data) {
      this.reviewData = { ...this.reviewData, ...data };
    },
    resetReviewData() {
      this.reviewData = {
        review_house_id: 1, // 현재 임시로 하드코딩된 값
        review_img_url: "",
     
        review_residence_period: null,
        review_floor: null,
        review_score: null,
  
        review_lease_type: "",
        review_deposit: null,
        review_management_fee: null,
        review_advantage: "",
        review_advantage_tags: [],
        review_disadvantage: "",
        review_disadvantage_tags: [],
      };
    },
  },
});
