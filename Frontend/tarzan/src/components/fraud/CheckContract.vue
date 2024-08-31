<template>
  <div class="sub-container non-input-sub-container">
    <TopBarBack title="계약 내용 확인하기" />
    <div class="center-container">
      <div class="card-wrapper">
        <div class="card-title">
          <img :src="CheckIcon" />
          <p>임대차 계약시 확인사항</p>
        </div>
        <div class="card-content">
          <p class="card-content-title">1. 임대차 대상 부동산</p>
          <p>
            목적물 주소 및 임차 면적 등이<br />
            등기부등본 및 건축물대장과 일치하는지 확인
          </p>
        </div>
        <div class="card-content">
          <p class="card-content-title">2. 계약 당사자의 인적사항</p>
          <p>
            목임대인, 임차인, 공인중개사의 인적사항이 <br />
            신분증과 일치 여부 확인
          </p>
        </div>
        <div class="card-content">
          <p class="card-content-title">3. 보증금·월차임의 지급 방법</p>
          <p>보증금 및 월세 지급 계좌 및 지급 시기 확인</p>
        </div>
        <div class="card-content">
          <p class="card-content-title">4. 계약체결일</p>
          <p>실제 계약을 체결한 날짜 확인</p>
        </div>
        <div class="card-content">
          <p class="card-content-title">5. 기타 특약사항</p>
          <p>
            주택 상태 관련 특약(옵션 사항, 수리비 부담 등), 계약조건(근저당 말소
            등) 확인
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import CheckIcon from "@/assets/icons/Check/Check.svg";
import TopBarBack from "../common/TopBarBack.vue";
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
}
.card-wrapper {
  padding: $padding-default;
  display: flex;
  flex-direction: column;
  gap: $padding-small;

  .card-title {
    padding: $padding-default;
    display: flex;
    gap: $padding-small;
    background-color: #f8f8f8;
    align-items: center;
    border-radius: $border-radius-default;

    p {
      @include custom-text;
    }
  }

  .card-content {
    padding: $padding-default;
    display: flex;
    flex-direction: column;
    justify-content: flex-start; /* 수평 왼쪽 정렬 (기본값) */
    align-items: flex-start; /* 수직 왼쪽 정렬 */
    gap: $padding-small;
    border-radius: $border-radius-default;
    border: $border-color-unactive 1px solid;

    p {
      @include custom-text($font-weight: 300, $font-color: $text-color-light);
      text-align: left;
      line-height: 1.2;
      &.card-content-title {
        @include custom-text(
          $font-weight: 600,
          $font-color: $text-color-default
        );
      }
    }
  }
}
</style>
