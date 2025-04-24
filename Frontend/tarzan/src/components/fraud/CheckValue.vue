<template>
  <div class="sub-container non-input-sub-container">
    <div class="top-bar-wrapper">
      <TopBarBack title="시세 확인" @back="goSomewhere" />
    </div>
    <div class="center-container">
      <form class="input-form" @submit.prevent="handleSearch">
        <div class="input-group">
          <div class="select-container">
            <CustomSelectBox
              :options="seoulDistrictOptions"
              :parent-style="customStyle"
              @update:selected="updateSeoulSiGunGu" />
            <CustomSelectBox
              :options="dongOptions"
              :parent-style="customStyle"
              @update:selected="updateSeoulDong" />
          </div>
        </div>

        <div class="input-group">
          <div class="input-content select-container">
            <div style="width: max-content; min-width: 100px">
              <CustomSelectBox
                :options="searchOption"
                :parent-style="customStyle"
                @update:selected="updateSearchBy" />
            </div>
            <input
              type="text"
              placeholder="검색어를 입력해주세요."
              v-model="searchData.search" />
          </div>
        </div>
        <div class="button-default-wrapper">
          <div class="button-default" @click="handleSearch">검색하기</div>
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
import { ref, computed, watch } from "vue";
import { useRouter } from "vue-router";
import { axiosInstance } from "@/plugins/axiosPlugin";
import CustomSelectBox from "@/components/common/CustomSelectBox.vue";
import TabBar from "@/components/common/TabBar.vue";
import SaleRealEstateTap from "@/components/fraud/SaleRealEstateTap.vue";
import RentRealEstateTap from "@/components/fraud/RentRealEstateTap.vue";
import TopBarBack from "@/components/common/TopBarBack.vue";
import { seoulSiGunGu } from "@/data/seoulSiGunGu";
import seoulDongJson from "@/data/dong.json";
import { Option } from "@/data/options";

const router = useRouter();

const customStyle = {
  backgroundColor: "white",
  fontWeight: 400,
  justifyContent: `space-between`,
  border: "1px solid #e7e7e7",
};

// 1. 검색 데이터
const searchData = ref({
  gu: null,
  dong: null,
  searchBy: null,
  search: "",
});

// 2. 시군구 선택
const seoulDistrictIndex = ref(0);
const seoulDistrictOptions = seoulSiGunGu.map((district) => ({
  idx: district.idx,
  name: district.name.split(" ")[1],
  value: district.value,
}));
const updateSeoulSiGunGu = (idx: number) => {
  seoulDistrictIndex.value = idx;
  searchData.value.gu = seoulDistrictOptions[idx].value;
};

// 3. 동 옵션
const convertOptionArray = (dongJson) =>
  Object.keys(dongJson).map((key, index) => ({
    idx: index,
    name: dongJson[key].name,
    value: dongJson[key].value,
  }));
const dongOptions = ref(
  convertOptionArray(seoulDongJson[seoulDistrictOptions[0].name])
);
watch(seoulDistrictIndex, (newIdx) => {
  const guValue = seoulDistrictOptions[newIdx].name;
  dongOptions.value = convertOptionArray(seoulDongJson[guValue]);
});
const updateSeoulDong = (idx: number) => {
  searchData.value.dong = dongOptions.value[idx].value;
};

// 4. 검색 옵션
const searchOption: Option[] = [
  { idx: 1, name: "건물명", value: "건물명" },
  { idx: 2, name: "지번", value: "지번" },
];
const updateSearchBy = (idx: number) => {
  searchData.value.searchBy = searchOption[idx].value;
};

// 5. 탭 + 결과 데이터
const selectedTabIdx = ref(0);
const resultList = ref<any[]>([]);
const tabs = computed(() => [
  {
    name: "매매",
    component: SaleRealEstateTap,
    props: { params: { ...searchData.value, size: 100 } },
  },
  {
    name: "전월세",
    component: RentRealEstateTap,
    props: { params: { ...searchData.value, size: 100 } },
  },
]);

const selectTab = (idx: number) => {
  selectedTabIdx.value = idx;
};

// 6. 검색 요청
const handleSearch = async () => {
  const { gu, dong, searchBy, search } = searchData.value;
  // if (!gu || !dong || !searchBy || !search.trim()) {
  //   alert("모든 값을 입력해주세요.");
  //   return;
  // }

  const selectedTab = tabs[selectedTabIdx.value];

  try {
    const response = await axiosInstance.get(selectedTab.apiUrl, {
      params: {
        gu,
        dong,
        searchBy,
        search,
        numOfRows: 100,
        pageNo: 1,
      },
    });

    if (response.data.data.list) {
      resultList.value = response.data.data.list;
      console.log("검색 결과", resultList.value);
    } else {
      resultList.value = [];
      alert("검색 결과가 없습니다.");
    }
  } catch (e) {
    console.error("API 호출 실패", e);
  }
};
// 7. 라우팅
const goSomewhere = () => {
  router.push({ name: "Home" });
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

  input {
    width: 30px;
  }
}

// content를 구분해주는 회색 긴 선
.result-wrapper {
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

.select-container {
  gap: $padding-small;
}
</style>
