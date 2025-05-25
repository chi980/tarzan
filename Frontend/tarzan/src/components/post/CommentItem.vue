<template>
  <div class="comment-container">
    <div class="comment-header">
      <span id="comment-writer">{{ comment.comment_writer_nickname }}</span>
      <EditButton :options="options" />
    </div>
    <div class="comment-content">
      <p>{{ comment.comment_content }}</p>
    </div>
    <div class="comment-time">
      <!-- <span>{{ comment.comment_created_at }}</span> -->
      <span>{{ formatSmartTime(comment.comment_created_at) }}</span>
    </div>

    <Modal v-model="show">
      <template #default="{ close }">
        <ReportComponent
          :reportTargetType="'COMMENT'"
          :reportTargetId="props.comment.comment_id"
          @close="close" />
      </template>
    </Modal>
  </div>
</template>

<script setup>
import { ref, computed, defineProps, defineEmits } from "vue";
import EditButton from "@/components/common/EditButton.vue";
import Modal from "@/components/common/Modal.vue";
import ReportComponent from "@/components/common/ReportComponent.vue";
import { formatSmartTime } from "@/utils/formatTime";
import { axiosInstance } from "@/plugins/axiosPlugin";

const props = defineProps({
  comment: {
    type: Object,
    required: true,
  },
});

const emit = defineEmits(["delete"]);

// 수정, 삭제, 신고 클릭 시 처리
const options = computed(() => {
  if (props.comment.comment_is_writer) {
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
  deleteComment();
  emit("delete");
};
const deleteComment = async () => {
  try {
    const response = await axiosInstance.delete(
      `/v1/comments/${props.comment.comment_id}` // 댓글 삭제 API 호출
    );
    if (response.data.success) {
      alert("댓글이 삭제되었습니다.");
    } else {
      new Error("댓글 삭제 실패: " + response.data.message);
    }
  } catch (error) {
    console.error("댓글 삭제 오류:", error.message);
    alert("댓글 삭제에 실패했습니다.");
  }
};
const show = ref(false);
const onReport = async () => {
  show.value = true;
};
</script>

<style scoped lang="scss">
.comment-container {
  @include custom-padding-y($padding-small);
  display: flex;
  flex-direction: column;
  gap: 8px;
  text-align: left;
}

.comment-container .comment-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

#comment-writer {
  @include custom-text($font-size: 14px);
}

.comment-container .comment-content {
  @include custom-text($font-size: 13px);
}

.comment-container .comment-time {
  @include custom-text($font-size: 12px, $font-color: $text-color-light);
}
</style>
