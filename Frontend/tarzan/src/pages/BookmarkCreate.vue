<script setup lang="ts">
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";

import TopBarBack from "@/components/common/TopBarBack.vue";
import Step1 from "@/components/bookmark/CheckCostPage.vue";
import Step2 from "@/components/bookmark/CheckOptionPage.vue";
import Step3 from "@/components/bookmark/CheckCheckListPage.vue";
import BottomDefaultButton from "@/components/common/BottomDefaultButton.vue";

const route = useRoute();
const router = useRouter();

const topBarHandler = () => {
  if (step.value > 0) {
    prev();
    return;
  }
  router.back();
};

// 리뷰 작성 컴포넌트들
const steps = [Step1, Step2, Step3];
const step = ref(0);
const prev = () => {
  if (step.value > 0) step.value--;
};
const next = () => {
  if (step.value < steps.length - 1) step.value++;
  else submit();
};

const submit = () => {
  console.log("제출할 데이터:");
};
</script>

<template>
  <div class="sub-container">
    <TopBarBack :title="'점검하기'" @back="topBarHandler" />
    <div class="center-container">
      <div class="create-bookmark-tabs">
        <component :is="steps[step]" />
      </div>
    </div>
    <BottomDefaultButton
      :label="step < steps.length - 1 ? '다음' : '제출'"
      :onClick="next" />
  </div>
</template>

<style scoped lang="scss">
.center-container {
  flex: 1;
  width: 100%;
}
.center-container {
  overflow-x: hidden;
  overflow-y: auto;
  /* 스크롤바 전체 영역 */
  &::-webkit-scrollbar {
    width: 4px; /* 세로축 스크롤바 폭 너비 */
    height: 100%; /* 가로축 스크롤바 폭 너비 */
  }
  &::-webkit-scrollbar-button {
    display: none;
  }
  /* 스크롤바 막대 제외 부분 */
  &::-webkit-scrollbar-track {
    background: transparent;
  }
  /* 스크롤바 막대 */
  &::-webkit-scrollbar-thumb {
    border-radius: calc($border-radius-default * 2);
    background: #f2f2f2;
  }
}
.padding-container {
  @include custom-padding-x;
}

//scoped
.center-container {
  @include custom-margin-y;
}

.create-bookmark-tabs {
  background-color: aqua;
}
</style>
