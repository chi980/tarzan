<template>
  <div class="review-item">
    <div class="header">
      <div class="column">
        <span id="nickname">{{ props.review.review_writer_nickname }}</span>
        <div class="row content">
          <StarRating v-model="props.review.review_score" :readonly="true" />
          <p>{{ formatDateWithoutTime(props.review.review_created_at) }}</p>
        </div>
        <!-- <div class="row">
          <div class="tag" v-if="props.review.review_residence_period">
            {{ props.review.review_residence_period }} 거주
          </div>
          <div class="tag" v-if="props.review.review_floo">
            {{ props.review.review_floo }} 층
          </div>
        </div> -->
      </div>
      <EditButton :options="options" />
    </div>

    <div class="row content" v-if="props.review.review_advantage">
      <img :src="advantageImgSrc" alt="장점" />
      <p>{{ props.review.review_advantage }}</p>
    </div>

    <div class="row content" v-if="props.review.review_disadvantage">
      <img :src="disadvantageImgSrc" alt="단점" />
      <p>{{ props.review.review_disadvantage }}</p>
    </div>

    <Modal v-model="show">
      <template #default="{ close }">
        <ReportComponent
          :reportTargetType="'REVIEW'"
          :reportTargetId="props.review.review_id"
          @close="close" />
      </template>
    </Modal>
  </div>
</template>

<script setup lang="ts">
import { ref, defineProps, defineEmits, computed } from "vue";
import advantageImgSrc from "@/assets/emoji/advantage.png";
import disadvantageImgSrc from "@/assets/emoji/disadvantage.png";
import EditButton from "@/components/common/EditButton.vue";

import { Review } from "@/data/review";
import { formatDateWithoutTime } from "@/utils/date";

import { axiosInstance } from "@/plugins/axiosPlugin";

import StarRating from "@/components/review/StarRating.vue";
import Modal from "@/components/common/Modal.vue";
import ReportComponent from "@/components/common/ReportComponent.vue";

const props = defineProps<{
  review: Review;
}>();
const emit = defineEmits<{
  (e: "delete"): void;
}>();

// 수정, 삭제, 신고 클릭 시 처리
const options = computed(() => {
  if (props.review.review_is_writer) {
    return [
      {
        name: "삭제하기",
        onClick: onDelete,
      },
    ];
  } else {
    return [
      {
        name: "신고하기",
        onClick: onReport,
      },
    ];
  }
});

const onDelete = () => {
  deleteReview();
  emit("delete");
};

const deleteReview = async () => {
  try {
    const response = await axiosInstance.delete(
      `/v1/reviews/${props.review.review_id}`
    );
    alert("리뷰 삭제 성공");
  } catch (error) {
    console.error("리뷰 삭제 실패", error);
  }
};

const show = ref(false);
const onReport = async () => {
  show.value = true;
};
</script>

<style scoped lang="scss">
.review-item {
  display: flex;
  flex-direction: column;
  gap: $padding-small;
  @include custom-padding-y($padding-small);
  .header {
    display: flex;
    flex-direction: row;
    justify-content: space-between;

    #nickname {
      @include custom-text($font-size: 14px);
      text-align: left;
    }

    img {
      @include custom-icon-style(16px);
    }
  }

  .row {
    display: flex;
    flex-direction: row;
    gap: 8px;

    align-items: flex-start;
  }

  .column {
    display: flex;
    flex-direction: column;
    gap: 8px;
  }

  .tag {
    @include custom-padding(8px);
    @include custom-text($font-size: 12px, $font-color: #717277);
    background-color: #f2f3f9;
    border-radius: 10px;
  }

  .content {
    @include custom-text($font-size: 12px, $font-color: $text-color-light);
    text-align: left;
    line-height: 1.2;
  }
  img {
    @include custom-icon-style(26px);
  }
}
:deep(.star svg) {
  width: 18px;
  height: 18px;
}
</style>
