<script setup lang="ts">
import { ref, defineProps, defineEmits, computed } from "vue";
import { axiosInstance } from "@/plugins/axiosPlugin";

const props = defineProps<{
  reportTargetType: "REVIEW" | "POST" | "COMMENT";
  reportTargetId: number;
}>();
const emit = defineEmits<{
  (e: "close"): void;
}>();

interface ReasonOption {
  label: string;
  value: string;
}
const reportReason: ReasonOption[] = [
  { label: "스팸·광고성 게시물", value: "SPAM_AD" },
  { label: "욕설·비방·혐오 표현", value: "OFFENSIVE" },
  { label: "사칭·사기 행위", value: "SCAM" },
  { label: "기타", value: "ETC" },
];

const selectedReason = ref<string | null>(null);
const otherReason = ref<string>("");

// 제출 가능 여부 계산
const canSubmit = computed(() => {
  if (!selectedReason.value) return false;
  return selectedReason.value === "ETC"
    ? otherReason.value.trim().length > 0
    : true;
});

const onCancel = () => {
  emit("close");
};

const onSubmit = async () => {
  if (!canSubmit.value) return;

  const payload: Record<string, any> = {
    reportTargetType: props.reportTargetType,
    reportTargetId: props.reportTargetId,
    reportReasonType: selectedReason.value,
  };
  if (selectedReason.value === "ETC") {
    payload.reportReasonContent = otherReason.value.trim();
  }

  try {
    await axiosInstance.post("/v1/report", {
      "report-target-type": props.reportTargetType,
      "report-target-id": props.reportTargetId,
      "report-reason-type": selectedReason.value!,
      "report-reason-content":
        selectedReason.value === "ETC" ? otherReason.value : undefined,
    });
    alert("신고가 정상 접수되었습니다.");
    emit("close");
  } catch (err: any) {
    console.error("신고 제출 실패", err);
    alert(
      err.response?.data?.message ||
        "신고 제출 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요."
    );
  }
};
</script>

<template>
  <div class="column report-content-wrapper">
    <div
      class="row reason-content-wrapper"
      v-for="opt in reportReason"
      :key="opt.value">
      <label class="radio-label">
        <input
          type="radio"
          name="reportReason"
          :value="opt.value"
          v-model="selectedReason" />
        <span>{{ opt.label }}</span>
      </label>
    </div>

    <!-- 기타일 때만 보이는 텍스트 입력 -->
    <div class="input-content" v-if="selectedReason === 'ETC'">
      <input
        type="text"
        v-model="otherReason"
        placeholder="신고 사유를 입력하세요" />
    </div>

    <div class="row button-wrapper">
      <button type="button" class="btn cancel" @click="onCancel">취소</button>
      <button
        type="button"
        class="btn submit"
        :disabled="!canSubmit"
        @click="onSubmit">
        신고
      </button>
    </div>
  </div>
</template>

<style scoped lang="scss">
.column {
  display: flex;
  flex-direction: column;
  gap: $padding-small;
}
.row {
  display: flex;
  gap: $padding-small;
}

.report-content-wrapper {
  @include custom-padding;
}

.reason-content-wrapper {
  align-items: center;
  @include custom-text($font-size: 14px);
}
.radio-label {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  cursor: pointer;
}

.input-content {
  margin-top: $padding-small;
}
input[type="text"] {
  @include custom-input-style;
}

.button-wrapper {
  justify-content: space-between;
  margin-top: $padding-small;

  .btn {
    flex: 1;
    @include custom-button-style($font-color: white, $bottom: 0);
    &.cancel {
      background-color: white;
      p,
      & {
        color: $text-color-light;
      }
    }
    &.submit {
      margin-left: $padding-small;
      &:disabled {
        opacity: 0.5;
        cursor: not-allowed;
      }
    }
  }
}
</style>
