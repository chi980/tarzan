<template>
  <div class="sub-container non-input-sub-container">
    <div class="top-bar-back">
      <p>공인중개소 확인</p>
    </div>
    <div class="center-container">
      <form class="input-form">
        <!-- 컴포넌트 화 하기 -->
        <div class="input-group">
          <h2 class="input-title">검색조건</h2>
          <div class="input-content-wrapper">
            <div class="input-content">
              <div style="flex: 1">
                <CustomSelectBox :options="seoulDistrictOptions" />
              </div>
              <div style="flex: 1">
                <CustomSelectBox :options="seoulDistrictOptions" />
              </div>
            </div>
          </div>
          <div class="input-content-wrapper">
            <div class="input-content">
              <div>
                <CustomSelectBox :options="searchOption" />
              </div>
              <div style="flex: 1; display: flex">
                <input
                  type="text"
                  style="width: 100%; box-sizing: border-box"
                  placeholder="df"
                />
              </div>
            </div>
          </div>
        </div>
      </form>

      <div class="content-indicator"></div>

      <div class="bottom-button-wrapper">
        <div>
          <p>직접 추가하기</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { Option } from "@/data/options";
import DropDown from "@/components/common/DropDown.vue";
import CustomSelectBox from "@/components/common/CustomSelectBox.vue";

const seoulDistrictOptions: Option[] = [
  { idx: 0, name: "서울시 종로구", value: "JONGNO" },
  { idx: 1, name: "서울시 종로구", value: "JONGNO" },
  { idx: 2, name: "서울시 중구", value: "JUNG" },
  { idx: 3, name: "서울시 용산구", value: "YONGSAN" },
  { idx: 4, name: "서울시 성동구", value: "SEONGDONG" },
  { idx: 5, name: "서울시 광진구", value: "GWANGJIN" },
  { idx: 6, name: "서울시 동대문구", value: "DONGDAEMUN" },
  { idx: 7, name: "서울시 중랑구", value: "JUNGNANG" },
  { idx: 8, name: "서울시 성북구", value: "SEONGBUK" },
  { idx: 9, name: "서울시 강북구", value: "GANGBUK" },
  { idx: 10, name: "서울시 도봉구", value: "DOBONG" },
  { idx: 11, name: "서울시 노원구", value: "NOWON" },
  { idx: 12, name: "서울시 은평구", value: "EUNPYEONG" },
  { idx: 13, name: "서울시 서대문구", value: "SEODAEMUN" },
  { idx: 14, name: "서울시 마포구", value: "MAPO" },
  { idx: 15, name: "서울시 양천구", value: "YANGCHEON" },
  { idx: 16, name: "서울시 강서구", value: "GANGSEO" },
  { idx: 17, name: "서울시 구로구", value: "GURO" },
  { idx: 18, name: "서울시 금천구", value: "GEUMCHEON" },
  { idx: 19, name: "서울시 영등포구", value: "YEONGDEUNGPO" },
  { idx: 20, name: "서울시 동작구", value: "DONGJAK" },
  { idx: 21, name: "서울시 관악구", value: "GWANAK" },
  { idx: 22, name: "서울시 서초구", value: "SEOCHO" },
  { idx: 23, name: "서울시 강남구", value: "GANGNAM" },
  { idx: 24, name: "서울시 송파구", value: "SONGPA" },
  { idx: 25, name: "서울시 강동구", value: "GANGDONG" },
];

const searchOption: Option[] = [
  { idx: 1, name: "날짜순", value: "name" },
  { idx: 2, name: "최신순", value: "name" },
  { idx: 3, name: "상호", value: "name" },
];
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
.accordion-wrapper {
  @include custom-margin-y;
  display: flex;
  flex-direction: column;
  gap: $padding-small;
}
.input-form {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: $padding-big;
  .input-group {
    @include custom-padding-x();
    display: flex;
    flex-direction: column;
    gap: 12px;

    .input-title {
      @include custom-text-bold($font-size: 18px);
      text-align: left;
    }

    .input-content-wrapper {
      display: flex;
      flex-direction: column;
      gap: $padding-small;

      .input-content {
        margin: 0;
      }
    }

    .input-content {
      display: flex;
      align-items: center;
      gap: $padding-small;

      input[type="text"] {
        @include custom-input-style-basic;
        padding: 14px;
        box-sizing: border-box;
        flex: 1; /* 남은 공간을 모두 차지하도록 설정 */
      }

      .select-content {
        flex-grow: 1;
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
}

// content를 구분해주는 회색 긴 선
.content-indicator {
  @include custom-margin-y;
  background-color: #ededed;
  height: 10px;
}
// scoped
.input-form {
  margin-top: $margin-default;
}
</style>
