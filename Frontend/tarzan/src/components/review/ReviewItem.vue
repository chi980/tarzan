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
      <div class="dropdown" @click="toggleDropdown">
        <img :src="menuButtonImgSrc" alt="..." />
        <div v-if="showMenu" class="dropdown-content">
          <div v-if="props.review.review_is_writer">
            <!-- <div @click="onEdit">수정</div> -->
            <div @click="onDelete">삭제</div>
          </div>
          <div v-else>
            <div @click="onReport">신고</div>
          </div>
        </div>
      </div>
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
      <ReportComponent
        :reportTargetType="'REVIEW'"
        :reportTargetId="props.review.review_id" />
    </Modal>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, defineProps, defineEmits } from "vue";
import advantageImgSrc from "@/assets/emoji/advantage.png";
import disadvantageImgSrc from "@/assets/emoji/disadvantage.png";
import menuButtonImgSrc from "@/assets/icons/menu.png";

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

const showMenu = ref(false);

// 드롭다운 열고 닫는 함수
const toggleDropdown = (event: Event) => {
  showMenu.value = !showMenu.value;
  event.stopPropagation(); // 클릭 이벤트 전파 방지
};

// 외부 클릭 시 드롭다운 닫기
const closeDropdown = () => {
  showMenu.value = false;
};

// 수정, 삭제, 신고 클릭 시 처리
const onEdit = () => {
  closeDropdown();
};

const onDelete = () => {
  closeDropdown();

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
  closeDropdown();
  show.value = true;

  try {
    const response = await axiosInstance.post(
      `/v1/reviews/${props.review.review_id}/report`
    );
    console.log("리뷰 신고 성공", response.data);
  } catch (error) {
    console.error("리뷰 신고 실패", error);
  }
};

// 외부 클릭 시 드롭다운을 닫도록 이벤트 리스너 추가
onMounted(() => {
  document.addEventListener("click", closeDropdown);
});

onUnmounted(() => {
  document.removeEventListener("click", closeDropdown);
});
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

.dropdown {
  position: relative;

  .dropdown-content {
    @include custom-padding(16px);
    @include custom-text($font-size: 12px);
    position: absolute;
    right: 0;

    width: max-content;

    border-radius: 10px;
    background-color: white;
    box-shadow: 0px -1px 10px rgba(0, 0, 0, 0.05);

    div {
      display: flex;
      flex-direction: column;
      gap: $padding-default;
    }
  }
}
</style>
