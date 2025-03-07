// store/reviewStore.js
import { defineStore } from 'pinia';

export const useReviewStore = defineStore('review', {
  state: () => ({
    reviewData: {
      address: "",
      images: [],
      satisfaction: 0,
      rentType: "",
      rentFee: null,
      deposit: null,
      managementFee: null,
      residenceYear: "",
      floor: null,
      wasteDisposal: "",
      advantages: [],
      disadvantages: [],
      advantageDetail: "",
      disadvantageDetail: "",
    }
  }),
  actions: {
    updateReviewData(newData) {
      this.reviewData = { ...this.reviewData, ...newData };
    },
    resetReviewData() {
      this.reviewData = {
        address: "",
        images: [],
        satisfaction: 0,
        rentType: "",
        rentFee: null,
        deposit: null,
        managementFee: null,
        residenceYear: "",
        floor: null,
        wasteDisposal: "",
        advantages: [],
        disadvantages: [],
        advantageDetail: "",
        disadvantageDetail: "",
      };
    }
  }
});
