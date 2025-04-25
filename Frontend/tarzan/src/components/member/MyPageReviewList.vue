<script setup lang="ts">
import { ref } from "vue";

import { axiosInstance } from "@/plugins/axiosPlugin";
import List from "@/components/common/List.vue";
import ReviewItem from "@/components/review/ReviewItem.vue";

const params = ref({
  size: 4,
  sortBy: "최신순",
});

const fetchReviews = async (page, params) => {
  try {
    const response = await axiosInstance.get(`/v1/user/reviews`, {
      params: {
        ...params,
        page: page,
      },
    });

    if (response.data.success) {
      console.log("리뷰 목록:", response.data);
      return {
        items: response.data.data.list,
        isNext: response.data.data.isNext,
      };
    } else {
      console.error("Failed to fetch data:", response.data.message);
      return {
        items: [],
        isNext: false,
      };
    }
  } catch (error) {
    console.error("API request error:", error);
    return {
      items: [],
      isNext: false,
    };
  }
};
</script>

<template>
  <div class="my-page-review-list">
    <List :fetchItems="fetchReviews" :params="params">
      <template #item="{ item }">
        <ReviewItem :review="item" />
      </template>
    </List>
  </div>
</template>

<style scoped lang="scss">
.my-page-review-list {
  @include custom-margin;
}
</style>
