<template>
  <div class="sub-container non-input-sub-container">
    <div class="top-bar-wrapper">
      <TopBarBack title="시세 확인" @back="goSomewhere" />
    </div>
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
        <div class="button-default-wrapper">
          <div class="button-default">검색하기</div>
        </div>
      </form>

      <div class="result-wrapper">
        <TabBar
          :tabs="tabs"
          :selectedTabIdx="selectedTabIdx"
          @update:selectedTabIdx="selectTab" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { Option } from "@/data/options";
// import DropDown from "@/components/common/DropDown.vue";
import CustomSelectBox from "@/components/common/CustomSelectBox.vue";
import { seoulSiGunGu } from "@/data/seoulSiGunGu";
import TabBar from "@/components/common/TabBar.vue";
import TopBarBack from "@/components/common/TopBarBack.vue";

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

function goSomewhere() {
  router.push({ name: "Home" }); // 또는 router.go(-1) 도 가능
}

const tabs = [
  {
    name: "매매",
    title: "매매",
    component: "CheckRealEstateBroker",
    props: {
      url: "매매 url",
      type: "매매",
    },
  },
  {
    name: "전월세",
    title: "전월세",
    component: "CheckRealEstateBroker",
    props: {
      url: "전월세 url",
      type: "전월세",
    },
  },
];
const selectedTabIdx = ref(0);
const selectTab = (index: number) => {
  selectedTabIdx.value = index;
};
</script>

<style lang="scss" scoped>
// 공통

.top-bar-wrapper {
  width: 100%;
}
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
}

//scoped
.button-default-wrapper {
  @include custom-padding-x;
}
.button-default {
  @include custom-button-style;
}
.input-form {
  gap: $padding-small !important;
  padding-top: $padding-default;
}
.result-wrapper {
  background-color: red;
}
</style>
