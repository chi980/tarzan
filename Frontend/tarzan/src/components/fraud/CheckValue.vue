<template>
  <div class="sub-container non-input-sub-container">
    <TopBarBack title="공인중개사 확인" @back="goSomewhere" />
    <div class="center-container">
      <form class="input-form" @submit.prevent="submitForm">
        <div class="input-group">
          <div class="select-container">
            <CustomSelectBox
              :options="seoulDistrictOptions"
              :parent-style="{
                backgroundColor: 'white',
                fontWeight: 400,
                justifyContent: `space-between`,
                border: '1px solid #e7e7e7',
              }" />
            <CustomSelectBox
              :options="seoulDistrictOptions"
              :parent-style="{
                backgroundColor: 'white',
                fontWeight: 400,
                justifyContent: `space-between`,
                border: '1px solid #e7e7e7',
              }" />
          </div>
        </div>

        <div class="input-group">
          <div class="input-content select-container">
            <div style="width: max-content; min-width: 100px">
              <CustomSelectBox
                :options="searchOption"
                :parent-style="{
                  backgroundColor: 'white',
                  fontWeight: 400,
                  justifyContent: `space-between`,
                  border: '1px solid #e7e7e7',
                }" />
            </div>
            <input type="type" placeholder="금액을 입력해주세요." />
          </div>
        </div>
      </form>
      <div class="content-indicator"></div>

      <div class="result-wrapper">
        <div class="result-bar">
          <p>결과</p>
          <p class="result-cnt">{{ resultCnt }}</p>
        </div>
        <hr />

        <ul>
          <li v-for="n in 2" :key="n">
            <div class="real-estitate-content">
              <div class="real-estitate-description">
                <p class="real-estitate-name">우리공인중개사사무소</p>
                <p>서울 강남구 논현로 71길 13 (우) 06248</p>
                <p>등록번호: 11620-2020-00067</p>
                <p>김정현, 02-522-4933</p>
              </div>
              <div class="real-estate-status active">영업중</div>
            </div>
            <div class="real-estitate-content">
              <div class="real-estitate-description">
                <p class="real-estitate-name">우리공인중개사사무소</p>
                <p>서울 강남구 논현로 71길 13 (우) 06248</p>
                <p>등록번호: 11620-2020-00067</p>
                <p>김정현, 02-522-4933</p>
              </div>
              <div class="real-estate-status unactive">폐업</div>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <div class="bottom-button-wrapper">
      <div>
        <p>직접 추가하기</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, Ref } from "vue";
import { useRouter } from "vue-router";
import { Option } from "@/data/options";
// import DropDown from "@/components/common/DropDown.vue";
import CustomSelectBox from "@/components/common/CustomSelectBox.vue";
import { seoulSiGunGu } from "@/data/seoulSiGunGu";
import TopBarBack from "../common/TopBarBack.vue";

const router = useRouter();

const seoulDistrictOptions = seoulSiGunGu.map((district) => ({
  idx: district.idx,
  name: district.name.split(" ")[1],
  value: district.value,
}));
const searchOption: Option[] = [
  { idx: 1, name: "날짜순", value: "name" },
  { idx: 2, name: "최신순", value: "name" },
  { idx: 3, name: "상호", value: "name" },
];

const resultCnt: Ref<number> = ref(0);

function goSomewhere() {
  router.push({ name: "Home" }); // 또는 router.go(-1) 도 가능
}
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
      @include custom-text($font-size: 14px);
      text-align: left;

      .input-title-mandatory {
        color: red;
      }
    }

    .input-content {
      display: flex;
      align-items: center;
      gap: $padding-small;

      input[type="text"] {
        @include custom-input-style;
        flex: 1;
      }
      input {
        @include custom-input-style;
        flex: 1;
      }

      .select-content {
        flex-grow: 1;
      }
    }
  }
}

.select-container {
  display: flex;
  flex-direction: row;
  gap: $padding-small;

  input {
    width: 30px;
  }
}

// content를 구분해주는 회색 긴 선
.content-indicator {
  margin: 0;
  margin-top: $margin-default;
  background-color: #ededed;
  height: 10px;
}

.result-wrapper {
  @include custom-padding-x;
  flex: 1;
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

  .result-bar {
    height: 48px;
    display: flex;
    flex-direction: row; /* 기본값이 row이지만 명시적으로 지정 */
    align-items: center; /* 수직 중앙 정렬 */
    justify-content: flex-start; /* 수평 왼쪽 정렬 */
    gap: 4px;

    p {
      @include custom-text;

      &:first-child {
        font-weight: 600; // weight가 아니라 font-weight
      }

      &.result-cnt {
        @include custom-text(
          $font-color: $primary-color-default,
          $font-size: 16px,
          $font-weight: 400
        );
      }
    }
  }
  hr {
    border: none;
    margin: 0;
    height: 1px;
    background-color: #ccc;
  }
}
// scoped
.real-estitate-content {
  @include custom-padding-y;
  display: flex;
  align-items: center;

  .real-estitate-description {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 4px;
    p {
      @include custom-text(
        $font-color: $text-color-light,
        $font-weight: 300,
        $font-size: 14px
      );
      text-align: left;

      &.real-estitate-name {
        @include custom-text;
        margin-bottom: 4px;
      }
    }
  }

  .real-estate-status {
    &.active {
      @include custom-text(
        $font-color: $primary-color-default,
        $font-size: 14px
      );
      background-color: #e0f9ed;
    }
    &.unactive {
      @include custom-text($font-size: 14px);
      background-color: #f7f7f7;
    }
    display: flex;
    height: fit-content;
    border-radius: 20px;
    padding: 10px;
  }
}
</style>
