<template>
  <div class="sub-container non-input-sub-container">
    <div class="top-bar-back">
      <p>추가하기</p>
    </div>
    <div class="center-container">
      <form class="input-form">
        <!-- 컴포넌트 화 하기 -->
        <div class="input-group">
          <h2 class="input-title">주소</h2>
          <div class="input-content">
            <input type="text" placeholder="주소를 입력해주세요" />
          </div>
        </div>
        <div class="input-group">
          <h2 class="input-title">건물명</h2>
          <div class="input-content">
            <input type="text" placeholder="건물명을 입력해주세요" />
          </div>
          <div class="input-description">
            <p>
              <i class="bi bi-info-circle"> </i>
              입력된 내용은 지도가 아니라 북마크에 추가됩니다.
            </p>
          </div>
        </div>
      </form>
      <div class="bottom-button-wrapper">
        <div>
          <p>직접 추가하기</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style lang="scss" scoped>
// 공통
.top-bar-back {
  @include custom-bar-style(
    $height: $height-top-bar,
    $z-index: $z-index-top-bar
  );

  background-color: aqua;
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

  .bottom-button-wrapper {
    @include custom-padding-x;

    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;

    div {
      @include custom-button-style($height: 54px, $font-color: white);
      // background-color: aquamarine;
    }
  }
}

.input-group:first-child {
  margin-top: 0px;
}

.input-group {
  @include custom-padding-x();
  margin-top: $margin-big;

  .input-title {
    @include custom-text-bold($font-size: 18px);
    text-align: left;
  }
  .input-content {
    @include custom-margin-input;
    display: flex;
    align-items: center;
    gap: $padding-default;

    input[type="text"] {
      @include custom-input-style;
      flex: 1; /* 남은 공간을 모두 차지하도록 설정 */
    }
  }
  .input-description {
    @include custom-text($font-size: 12px, $font-color: $text-color-light);

    p {
      display: block;
      @include custom-padding-y(4px);
      text-align: left;
    }
  }
}
// scoped
.center-container {
  justify-content: center;
}
</style>
