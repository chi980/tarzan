<template>
  <div class="sub-container non-input-sub-container">
    <div class="top-bar-wrapper">
      <TopBarBack title="공인중개사 확인" @back="goSomewhere" />
    </div>
    <div class="center-container">
      <form class="input-form" @submit.prevent="">
        <div class="input-group">
          <div class="select-container">
            <CustomSelectBox
              :options="seoulDistrictOptions"
              :parent-style="{
                backgroundColor: 'white',
                fontWeight: 400,
                justifyContent: `space-between`,
                border: '1px solid #e7e7e7',
              }"
              @update:selected="selectSiGunGu" />
          </div>
        </div>

        <div class="input-group" style="">
          <div class="input-content select-container">
            <div style="width: max-content; min-width: 100px">
              <CustomSelectBox
                :options="searchOption"
                :parent-style="{
                  backgroundColor: 'white',
                  fontWeight: 400,
                  justifyContent: `space-between`,
                  border: '1px solid #e7e7e7',
                }"
                @update:selected="selectOption" />
            </div>
            <input
              type="type"
              placeholder="검색어를 입력해주세요."
              v-model="searchData.search" />
          </div>
        </div>
        <div class="button-default-wrapper">
          <div class="button-default" @click="submitData">검색하기</div>
        </div>
      </form>
      <div class="content-indicator"></div>
      <div class="result-wrapper">
        <div v-if="isLoading">
          <SkeletonCard v-for="n in 3" :key="n" />
        </div>
        <div v-if="!isLoading">
          <transition-group name="fade" tag="div">
            <div v-for="(realEstate, index) in realEstates" :key="index">
              <div class="real-estate-container">
                <div
                  class="real-estate-status"
                  :class="{ ok: realEstate['status-code'] === '1' }">
                  {{ realEstate["status-name"] }}
                </div>
                <div class="real-estate-content">
                  <p class="real-estate-name">
                    {{ realEstate["real-estate-name"] }}
                  </p>
                  <p>{{ realEstate["real-estate-person-name"] }}</p>
                  <p class="real-estate-address">
                    {{ realEstate["road-name-address"] }}
                    <span v-if="realEstate['lot-number-address']">
                      ({{ realEstate["lot-number-address"] }})
                    </span>
                  </p>

                  <p>
                    {{ realEstate["establish-begin-date"] }} ~
                    {{ realEstate["establish-end-date"] }}
                  </p>
                  <p>
                    {{ realEstate["register-code"] }}/{{
                      realEstate["register-date"]
                    }}
                  </p>
                </div>
              </div>
            </div>
          </transition-group>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";

import { RealEstate } from "@/data/fraud";
import { Option } from "@/data/options";
import { seoulSiGunGu } from "@/data/seoulsigungu.js";

import { axiosInstance } from "@/plugins/axiosPlugin";

import CustomSelectBox from "@/components/common/CustomSelectBox.vue";
import SkeletonCard from "@/components/common/SkeletonCard.vue";
import TopBarBack from "@/components/common/TopBarBack.vue";

const router = useRouter();

const searchData = ref({
  gu: null,
  searchBy: null,
  search: null,
});

const seoulDistrictOptions = seoulSiGunGu.map((district) => ({
  idx: district.idx,
  name: district.name.split(" ")[1],
  value: district.value,
}));
const selectSiGunGu = (idx) => {
  searchData.value.gu = seoulDistrictOptions[idx].value;
};

const searchOption: Option[] = [
  { idx: 1, name: "중개업자명", value: "중개업자명" },
  { idx: 2, name: "사업자상호", value: "사업자상호" },
];
const selectOption = (idx) => {
  searchData.value.searchBy = searchOption[idx].value;
};

const realEstates = ref<RealEstate[] | null>(null);
const isLoading = ref(false);

const submitData = async () => {
  if (searchData.value.search === null) {
    alert("검색어를 입력해주세요.");
    return;
  }

  isLoading.value = true;
  try {
    const response = await axiosInstance.get("/fraud/real-estate", {
      params: {
        gu: searchData.value.gu,
        searchBy: searchData.value.searchBy,
        search: searchData.value.search,
        numOfRows: 100,
        pageNo: 1,
      },
    });
    if (response.data.data.list) {
      realEstates.value = response.data.data.list;
    } else {
      throw new Error("No data found");
    }
  } catch (error) {
    console.error("Error fetching data:", error);
    realEstates.value = [];
  } finally {
    isLoading.value = false;
  }
};

function goSomewhere() {
  router.push({ name: "Home" }); // 또는 router.go(-1) 도 가능
}
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

// scoped

.input-form {
  padding-top: $padding-default;
  gap: $padding-small !important;
}
.button-default-wrapper {
  @include custom-padding-x;
}
.button-default {
  @include custom-button-style;
  color: white !important;
}

.result-wrapper {
  flex: 1;
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

.real-estate-container {
  @include custom-padding;

  border-bottom: 1px solid #f8f8f8;

  display: flex;
  flex-direction: column;
  gap: 12px;

  .real-estate-status {
    @include custom-padding(8px);
    @include custom-text($font-size: 10px, $font-color: #717277);

    width: fit-content;

    background-color: #f2f3f9;
    border-radius: 10px;

    &.ok {
      background-color: $primary-color-light;
      color: $primary-color-default !important;
    }
  }
  .real-estate-name {
    @include custom-text($font-size: 16px);
  }
  .real-estate-content {
    display: flex;
    flex-direction: column;
    gap: 6px;
  }

  p {
    @include custom-text($font-size: 12px, $font-color: $text-color-light);
    text-align: left;
  }
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
