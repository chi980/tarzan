<template>
  <div class="sub-container non-input-sub-container">
    <div class="top-bar-back">
      <p>주택소유자 확인</p>
    </div>
    <div class="center-container">
      <div class="accordion-wrapper">
        <BasicAccordion accordionTitle="임대인이 개인인 경우">
          <div>
            <p>눈 앞에 있는 사람..</p>
          </div>
        </BasicAccordion>
        <BasicAccordion accordionTitle="임대인이 법인인 경우">
          <div>
            <p>눈 앞에 있는 사람..</p>
          </div>
        </BasicAccordion>
        <BasicAccordion accordionTitle="임대인이 대리인인 경우">
          <div>
            <p>눈 앞에 있는 사람..</p>
          </div>
        </BasicAccordion>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import BasicAccordion from "@/components/common/BasicAccordion.vue";
</script>

<style lang="scss" scoped>
// 공통
.top-bar-back {
  @include custom-bar-style(
    $height: $height-top-bar,
    $z-index: $z-index-top-bar
  );
}

.center-container {
  position: relative;
  flex-grow: 1;
  width: 100%;

  display: flex;
  flex-direction: column;

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
.accordion-wrapper {
  @include custom-margin-y;
  display: flex;
  flex-direction: column;
  gap: $padding-small;

  p {
    background-color: aqua;
  }
}

// scoped
.center-container {
}
</style>
