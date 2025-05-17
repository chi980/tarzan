<template>
  <div class="sub-container non-input-sub-container">
    <div class="top-bar-wrapper">
      <TopBarBack :title="''" @back="backToBookmark" />
    </div>
    <div class="center-container">
      <form class="input-form" @submit.prevent="">
        <div class="input-group">
          <h2 class="input-title">
            건물 종류<span class="input-title-mandatory">*</span>
          </h2>
          <div class="select-content">
            <CustomSelectBox
              :options="HouseCategoryOptions"
              :parentStyle="{
                backgroundColor: 'white',
                fontWeight: 400,
                justifyContent: `space-between`,
                border: '1px solid #e7e7e7',
              }"
              @update:selected="handleBuildingCategorySelected" />
          </div>
        </div>

        <div class="input-group">
          <h2 class="input-title">
            건물 이름<span class="input-title-mandatory">*</span>
          </h2>
          <div
            class="input-content"
            :class="{ error: !house_name && showError }">
            <input
              type="text"
              v-model="house_name"
              placeholder="건물명을 입력해주세요" />
          </div>
        </div>
        <div class="input-group">
          <h2 class="input-title">
            주소<span class="input-title-mandatory">*</span>
          </h2>
          <div class="input-content">
            <input
              type="text"
              v-model="house_address"
              placeholder="주소를 입력해주세요" />
          </div>
          <div class="input-description">
            <p>
              <i class="bi bi-info-circle"> </i>
              상세 주소를 추가 입력해주세요.
            </p>
          </div>
        </div>
      </form>
    </div>
    <BottomDefaultButton :label="'추가하기'" :onClick="handleAddHouseClick" />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { axiosInstance } from "@/plugins/axiosPlugin";

import TopBarBack from "@/components/common/TopBarBack.vue";
import CustomSelectBox from "@/components/common/CustomSelectBox.vue";
import BottomDefaultButton from "@/components/common/BottomDefaultButton.vue";

// const searchQuery = ref(""); 검색기능 사용시

const showError = ref(false); // 오류 메시지 표시 여부
const route = useRoute(); // useRoute: 현재 URL 정보
const router = useRouter(); // useRouter: 페이지 이동

const house_address = ref("");
const house_name = ref("");
const house_latitude = ref(null);
const house_longitude = ref(null);
const house_category = ref("");

const list = ref([]); // ✅ 추가한 집 목록을 저장

// 건물 종류 선택지를 배열로 정의
const HouseCategoryOptions = [
  { idx: 0, name: "아파트", value: "아파트" },
  { idx: 1, name: "오피스텔", value: "오피스텔" },
  { idx: 2, name: "빌라", value: "빌라" },
  { idx: 3, name: "주택", value: "주택" },
];

// 선택된 건물 종류의 인덱스를 저장할 변수
const selectedBuildingCategoryIdx = ref(null);

// 선택된 건물 종류를 처리하는 메서드
const handleBuildingCategorySelected = (idx: number) => {
  selectedBuildingCategoryIdx.value = idx;
  house_category.value = HouseCategoryOptions[idx].value; // house_category 값 업데이트
  console.log("선택된 건물 종류:", HouseCategoryOptions[idx].name);
};

onMounted(() => {
  house_address.value = (route.query.house_address as string) || "";
  house_name.value = (route.query.house_name as string) || "";
  house_latitude.value = route.query.house_latitude
    ? parseFloat(route.query.house_latitude as string)
    : null;
  house_longitude.value = route.query.house_longitude
    ? parseFloat(route.query.house_longitude as string)
    : null;
});

// "직접 추가하기" 버튼 클릭 시
const handleAddHouseClick = async () => {
  if (!house_name.value || !house_name.value.trim()) {
    // 🚨 빈 값과 공백 체크
    showError.value = true;
    return;
  }

  showError.value = false; // 정상 입력 시 에러 메시지 숨김

  const newHouse = {
    house_name: house_name.value.trim(),
    house_address: house_address.value,
    house_category: house_category.value,
    house_latitude: route.query.house_latitude,
    house_longitude: route.query.house_longitude,
  };

  try {
    const response = await axiosInstance.post("/v1/bookmark/user", newHouse);
    list.value.push(newHouse);

    router
      .push({ name: "BookMark", query: { list: JSON.stringify(list.value) } })
      .then(() => {
        window.location.reload();
      });

    console.log("Response:", response.data);
  } catch (error) {
    console.error("저장 실패:", error);
  }
};

onMounted(() => {
  house_address.value = (route.query.house_address as string) || "";
  house_name.value = (route.query.house_name as string) || "";
});

const backToBookmark = () => {
  router.push({ name: "BookMark" });
};
</script>

<style lang="scss" scoped>
// 공통
.top-bar-wrapper {
  width: 100%;
}

.sub-container {
  display: flex;
  flex-direction: column;
  width: 100%;
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
        @include custom-input-style;
        flex-grow: 1; /* 남은 공간을 모두 차지하도록 설정 */
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

// scoped
.center-container {
  justify-content: center;
}
</style>
