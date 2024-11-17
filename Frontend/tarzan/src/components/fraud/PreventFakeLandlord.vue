<template>
  <div class="sub-container non-input-sub-container">
    <div class="top-bar-back">
      <p>주택소유자 확인</p>
    </div>
    <div class="center-container">
      <div class="accordion-wrapper">
        <BasicAccordion accordionTitle="임대인이 개인인 경우">
          <div class="homeowner-content-wrapper">
            <div class="homeowner-content">
              <p class="homeowner-content-title">
                눈 앞에 있는 사람과 계약서에 도장 찍는 임대인과 등기부 등본에
                적힌 주택의 소유자가 동일 인물인가?
              </p>
              <p>
                눈 앞에 있는 사람과 계약서에 도장 찍는 임대인과 등기부 등본에
                적힌 주택의 소유자가 동일 인물인가?
              </p>
            </div>
            <div class="homeowner-content">
              <p class="homeowner-content-title">
                주택의 소유자가 2명 이상이면 소유자 전원과 계약을 맺기를 추천
              </p>
              <p>
                민법 265조에 따라 주택 지분의 과반을 소유한 사람과 계약하 면
                계약은 성립되지만 전세보증금반환보증에 가입하기 위해서 는 소유자
                전원과 전세계약을 체결해야 해요! 임대인이 여러 명일 경우에는
                전세계약을 종료할 때도 소유자 전원에게 종료 의사를 밝혀야 하기
                때문에 될 수 있다면 권리 관계가 깨끗한 집을 선택하는 것을
                추천해요~
              </p>
            </div>
            <div class="homeowner-content">
              <p class="homeowner-content-title">
                임대인에게 법적인 ‘행위능력’이 있는지 확인
              </p>
              <p>
                행위능력이 없는 자, 즉 제한능력자의 법률행위는 제대로 된 효력을
                가지지 못하기 때문이에요. 대표적인 예로 미성년자, 피성년후견인,
                피한정후견인 등이 있어요~
              </p>
            </div>
          </div>
        </BasicAccordion>
        <BasicAccordion accordionTitle="임대인이 법인인 경우">
          <div class="homeowner-content-wrapper">
            <div class="homeowner-content">
              <p class="homeowner-content-title">
                등기부등본을 확인하여 해당 법인이 이 집을 임대할 자격이 있는지만
                잘 확인
              </p>
            </div>
            <div class="homeowner-content">
              <p class="homeowner-content-title">
                1. 법인 등기부등본상의 대표와 계약을 체결할 때
              </p>
              <p>법인 대표자의 신분증과 법인 인감증명서가 필요해요!</p>
            </div>
            <div class="homeowner-content">
              <p class="homeowner-content-title">
                2. 법인 대표자가 아닌 사람과 계약할 때
              </p>
              <p>위임장 서류들을 꼭 받은 뒤 계약을 해야해요!</p>
            </div>
          </div>
        </BasicAccordion>
        <BasicAccordion accordionTitle="임대인이 대리인인 경우">
          <div class="homeowner-content-wrapper">
            <div class="homeowner-content">
              <p class="homeowner-content-title" style="line-height: 1.4">
                대리인과 계약 맺는 경우 <br />위임장 확인 사항
              </p>
            </div>
            <div class="homeowner-content">
              <p class="homeowner-content-title">
                1.  위임인과 수임인의 인적사항, 대상 주택, 위임의 범위 등이
                기재되어 있어야
              </p>
              <p>
                만약 대리인이 여기에 적혀진 위임 범위를 벗어나 계약한다면 계약
                자체가 무효가 되어 대항력을 잃게 될 수 있으니 주의해야 해요.
              </p>
            </div>
            <div class="homeowner-content">
              <p class="homeowner-content-title">
                2.  위임장에 찍힌 인감과 인감증명서의 인감이 동일한지
              </p>
              <p>
                만약, 인감이 아닌 서명이 적힌 위임장이라면, 본인서명사실확인서가
                필요해요. 혹은 아예 공증, 즉 공적으로 증명된 위임장을 통해서도
                위임 사실을 확인할 수 있어요.
              </p>
            </div>
            <div class="homeowner-content">
              <p class="homeowner-content-title">
                3.  임대인과의 통화를 통해 계약 내용을 확인
              </p>
              <p>
                임대인과의 통화를 통해 계약 내용을 확인해야 해요! 이 내용은
                녹음해두면 추후 분쟁이 발생할 때 활용할 수 있어요.
              </p>
            </div>
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
}

// scoped
.homeowner-content-wrapper {
  padding: $padding-default;
  display: flex;
  flex-direction: column;
  gap: $padding-big;

  .homeowner-content {
    display: flex;
    flex-direction: column;
    justify-content: flex-start; /* 수평 왼쪽 정렬 (기본값) */
    align-items: flex-start; /* 수직 왼쪽 정렬 */
    gap: $padding-small;

    p {
      @include custom-text($font-weight: 300, $font-color: $text-color-light);
      text-align: left;
      line-height: 1.2;
      &.homeowner-content-title {
        @include custom-text(
          $font-weight: 600,
          $font-color: $text-color-default
        );
      }
    }
  }
}
</style>
