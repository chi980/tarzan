<script setup lang="ts">
import { ref } from "vue";
import { axiosInstance } from "@/plugins/axiosPlugin";
import List from "@/components/common/List.vue";
import CommentItem from "@/components/post/CommentItem.vue";

const params = ref({
  size: 4,
  sortBy: "최신순",
});

const fetchComments = async (page, params) => {
  try {
    const response = await axiosInstance.get(`/v1/user/comments`, {
      params: {
        ...params,
        page: page,
      },
    });

    if (response.data.success) {
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

const handleDeleteComment = async (item) => {
  return Promise.resolve();
};
</script>

<template>
  <div class="my-page-comment-list">
    <List
      :fetchItems="fetchComments"
      :params="params"
      :onDelete="handleDeleteComment"
      :item-key="'comment_id'">
      <template #item="{ item, index, onDelete }">
        <CommentItem
          :comment="item"
          @delete="
            () => {
              onDelete(item, index);
            }
          " />
      </template>
    </List>
  </div>
</template>

<style scoped lang="scss">
.my-page-comment-list {
  @include custom-margin-x;
}
</style>
