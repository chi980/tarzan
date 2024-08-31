<template>
  <div class="sub-container non-input-sub-container">
    <TopBarBack title="특약 사항 작성하기" />
    <div class="center-container">
      <div class="card-wrapper">
        <p>
          계약서에는 없지만, 집을 볼 때 구두로 약속했던 내용이 있다면 계약서에
          반드시 특약으로 적어놓으세요
        </p>
        <div class="card-content">
          <p class="card-content-title">예시 1)</p>
          <p>
            곰팡이를 없애고 새로 도배를 해주겠다.<br /><br />
            > 이에 대한 특약을 쓰세요.
          </p>
        </div>
        <div class="card-content">
          <p class="card-content-title">예시 2)</p>
          <p>
            이미 빚이 많은 집인데, 계약하고 나면 임차인보다 우선하던 빚들을
            없애겠다.
            <br /><br />
            > 나보다 선순위에 있는 근저당을 말소하기로 했다면 꼭 특약을
            써주세요! 그리고 특약조건을 이행하지 않으면 이 계약은 무효로 한다는
            내용까지 포함해주세요!
          </p>
        </div>
        <div class="card-content">
          <p class="card-content-title">예시 3)</p>
          <p>
            전세보증금반환보증 가입가능한 집이다.
            <br /><br />
            > 임차인의 귀책없이 보증에 가입할 수 없는 경우 계약을 취소하고
            보증금을 반환한다는 내용의 특약사항을 계약서에 명시해요!
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import TopBarBack from '../common/TopBarBack.vue';
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

  p {
    @include custom-text(
      $font-weight: 300,
      $font-color: $text-color-light,
      $font-size: 14px
    );
    text-align: left;
    line-height: 1.2;
  }

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
