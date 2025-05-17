import { defineStore } from "pinia";

export const useLoadingStore = defineStore("loading", {
  state: () => ({
    count: 0 as number,
  }),
  getters: {
    isLoading: (state) => state.count > 0,
  },
  actions: {
    start() {
      this.count++;
    },
    finish() {
      // 안전하게 0 밑으로 내려가지 않도록
      if (this.count > 0) this.count--;
    },
  },
});
