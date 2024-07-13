<template>
  <div class="sub-container non-input-sub-container">
    <div style="width: 100%">
      <div class="top-bar-back">
        <p>점검하기</p>
      </div>
    </div>
    <div class="center-container left-top-container">
      <div class="tag-wrapper">
        <h2 class="tag-title">주방 및 세탁실</h2>
        <div class="tag-content">
          <div>식탁</div>
          <div>식탁</div>
          <div>식탁</div>
          <div>식탁</div>
        </div>
      </div>
      <div class="tag-wrapper">
        <h2 class="tag-title">거실</h2>
        <div class="tag-content">
          <div>식탁</div>
          <div>식탁</div>
          <div>식탁</div>
          <div>식탁</div>
        </div>
      </div>
      <div class="tag-wrapper">
        <h2 class="tag-title">방</h2>
        <div class="tag-content">
          <div>식탁</div>
          <div>식탁</div>
          <div>식탁</div>
          <div>식탁</div>
        </div>
      </div>
      <div class="tag-wrapper">
        <h2 class="tag-title">화장실</h2>
        <div class="tag-content">
          <div>식탁</div>
          <div>식탁</div>
          <div>식탁</div>
          <div>식탁</div>
        </div>
      </div>
      <div class="tag-wrapper">
        <h2 class="tag-title">보안 및 기타시설</h2>
        <div class="tag-content">
          <div>식탁</div>
          <div>식탁</div>
          <div>식탁</div>
          <div>식탁</div>
        </div>
      </div>
    </div>
    <div class="bottom-button-wrapper">
      <div>
        <p>다음으로</p>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup></script>
<style lang="scss" scoped>
// 공통
.top-bar-back {
  @include custom-bar-style(
    $height: $height-top-bar,
    $z-index: $z-index-top-bar
  );

  background-color: rgba(0, 0, 0, 0.1); /* 검은색 배경, 50% 투명도 */
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
.bottom-button-wrapper {
  @include custom-padding-x;

  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;

  div {
    @include custom-button-style($height: 54px, $font-color: white);
  }
}

.tag-wrapper {
  @include custom-padding-x;

  display: flex;
  flex-direction: column;
  gap: $padding-default;

  .tag-title {
    @include custom-text-bold($font-size: 18px);
    text-align: left;
  }

  .tag-content {
    background-color: aqua;
  }
}
.left-top-container {
  justify-content: flex-start;
  gap: $padding-big;
  padding-top: calc(#{$padding-default}* 2);
  padding-bottom: calc(54px + #{$margin-default} + #{$padding-default});
}
// scoped
</style>
