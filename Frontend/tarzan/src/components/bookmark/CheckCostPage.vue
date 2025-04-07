<template>
  <div>
    <form class="input-form">
      <!-- 가격 -->
      <div class="input-group">
        <h2 class="input-title">
          가격<span class="input-title-mandatory">*</span>
        </h2>
        <div class="input-content-wrapper">
          <div class="input-content">
            <input
              v-model="houseData.bookmark_deposit"
              type="text"
              placeholder="보증금을 입력해주세요." />
          </div>
          <div class="input-content select-container">
            <div style="width: max-content; min-width: 100px">
              <CustomSelectBox
                v-model="houseData.bookmark_lease_type"
                :options="rentalOptions"
                :parent-style="{
                  backgroundColor: 'white',
                  fontWeight: 400,
                  justifyContent: `space-between`,
                  border: '1px solid #e7e7e7',
                }" />
            </div>
            <input
              v-model="houseData.bookmark_rent"
              type="text"
              placeholder="금액을 입력해주세요." />
          </div>
          <div class="input-content">
            <input
              v-model="houseData.bookmark_commission"
              type="text"
              placeholder="중개수수료를 입력해주세요." />
          </div>
          <div class="input-content">
            <input
              v-model="houseData.bookmark_management_fee"
              type="text"
              placeholder="관리비를 입력해주세요." />
          </div>
        </div>
      </div>

      <!-- 부동산/집주인 -->
      <div class="input-group">
        <h2 class="input-title">부동산/집주인</h2>
        <div class="input-content-wrapper">
          <div class="input-content">
            <input
              v-model="houseData.bookmark_estate_name"
              type="text"
              placeholder="부동산/집주인명을 입력해주세요." />
          </div>
          <div class="input-content">
            <input
              v-model="houseData.bookmark_estate_phone_number"
              type="text"
              placeholder="부동산/집주인 핸드폰 번호를 입력해주세요." />
          </div>
        </div>
      </div>

      <!-- 반려동물 가능 여부 -->
      <!-- 반려동물 유무 선택 -->
      <div class="input-group">
        <h2 class="input-title">
          반려동물 유무<span class="input-title-mandatory">*</span>
        </h2>
        <div class="option-group">
          <div
            class="option-group-item"
            v-for="(petOption, index) in petOptions"
            :key="petOption.idx"
            :class="{ active: petOption.isSelected }"
            @click="selectOption(petOptions, index)">
            {{ petOption.name }}
          </div>
        </div>
      </div>

      <!-- 방향 -->
      <div class="input-group">
        <h2 class="input-title">방향</h2>
        <div class="input-content">
          <div class="select-content">
            <CustomSelectBox
              v-model="houseData.bookmark_direction"
              :options="directionOptions"
              :parent-style="{
                backgroundColor: 'white',
                fontWeight: 400,
                justifyContent: `space-between`,
                border: '1px solid #e7e7e7',
              }" />
          </div>
        </div>
      </div>

      <!-- 주차장 대수 -->
      <div class="input-group">
        <h2 class="input-title">주차장 대수</h2>
        <div class="input-content">
          <div class="select-content">
            <CustomSelectBox
              v-model="houseData.bookmark_parking_cnt"
              :options="carOptions"
              :parent-style="{
                backgroundColor: 'white',
                fontWeight: 400,
                justifyContent: `space-between`,
                border: '1px solid #e7e7e7',
              }" />
          </div>
        </div>
      </div>

      <!-- 방수/욕실수 -->
      <div class="input-group">
        <h2 class="input-title">방수/욕실수</h2>
        <div class="input-content-wrapper">
          <div class="input-content">
            <input
              v-model="houseData.bookmark_room_cnt"
              type="text"
              placeholder="방수를 입력해주세요." />
          </div>
          <div class="input-content">
            <input
              v-model="houseData.bookmark_bath_cnt"
              type="text"
              placeholder="욕실수를 입력해주세요." />
          </div>
        </div>
      </div>

      <!-- 입주가능일 -->
      <div class="input-group">
        <h2 class="input-title">입주가능일</h2>
        <div class="input-content">
          <input
            v-model="houseData.bookmark_available_date"
            type="date"
            placeholder="입주가능일을 입력해주세요." />
        </div>
      </div>

      <!-- 층수 -->
      <div class="input-group">
        <h2 class="input-title">층수</h2>
        <div class="input-content">
          <input
            v-model="houseData.bookmark_floor"
            type="text"
            placeholder="층수를 입력해주세요." />
        </div>
      </div>
    </form>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { Option } from "@/data/options";
import { axiosInstance } from "@/plugins/axiosPlugin";
import CustomSelectBox from "@/components/common/CustomSelectBox.vue";
import TopBarBack from "../common/TopBarBack.vue";

const route = useRoute();
const router = useRouter();

const bookmarkIdx = route.params.bookmarkIdx;

const houseData = ref({
  bookmark_lease_type: "MONTHLY", // 전세 | 월세
  bookmark_rent: "", // 월세만 해당 항목 이용
  bookmark_deposit: "",
  bookmark_commission: "",
  bookmark_management_fee: "",
  bookmark_estate_name: "",
  bookmark_estate_phone_number: "",
  bookmark_can_animal: "FALSE", // true | false
  bookmark_parking_cnt: "0",
  bookmark_room_cnt: "",
  bookmark_bath_cnt: "",
  bookmark_available_date: "",
  bookmark_floor: "",
  bookmark_direction: "SOUTH", // EAST | WEST | SOUTH | NORTH | UNKNOWN
});

const updateHouseData = async () => {
  try {
    console.log("Request Data:", JSON.stringify(houseData.value, null, 2));
    const response = await axiosInstance.put(
      `/v1/bookmark/${bookmarkIdx}`,
      houseData.value
    );

    if (response.data.success) {
      console.log("House data updated successfully:", response.data);
      houseData.value = response.data.data;
    } else {
      console.error("Failed to update house data:", response.data.message);
    }
  } catch (error) {
    console.error("API 요청 중 에러 발생:", error);
  }
};

const goToCheckOptionPage = () => {
  router.push({ name: "CheckOptionPage" }); // 'CheckOptionPage'로 네임 기반 라우팅
};

const handleClick = () => {
  updateHouseData();
  goToCheckOptionPage();
};

// 부모 컴포넌트의 배열 데이터 정의
const rentalOptions: Option[] = [
  { idx: 1, name: "월세", value: "MONTHLY" },
  { idx: 2, name: "전세", value: "JEONSE" },
  { idx: 3, name: "매매", value: "PROPERTY" },
];

const petOptions: Option[] = [
  { idx: 1, name: "모름", value: "NULL" },
  { idx: 2, name: "가능", value: "TRUE" },
  { idx: 3, name: "불가능", value: "FALSE" },
];

const carOptions: Option[] = [
  { idx: 1, name: "모름", value: "NULL" },
  { idx: 2, name: "1", value: "1" },
  { idx: 3, name: "2", value: "2" },
  { idx: 4, name: "3", value: "3" },
  { idx: 5, name: "4", value: "4" },
  { idx: 6, name: "5", value: "5" },
  { idx: 7, name: "6", value: "6" },
  { idx: 8, name: "7", value: "7" },
  { idx: 9, name: "8", value: "8" },
  { idx: 10, name: "9", value: "9" },
  { idx: 11, name: "10+", value: "10+" },
];

const directionOptions: Option[] = [
  { idx: 1, name: "모름", value: "NULL" },
  { idx: 2, name: "동", value: "EARTH" },
  { idx: 3, name: "서", value: "WEST" },
  { idx: 4, name: "남", value: "SOUTH" },
  { idx: 5, name: "북", value: "NORTH" },
];
</script>

<style lang="scss" scoped>
// 공통
// input-form(최종)
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

  input[type="text"] {
    width: 30px;
  }
}

.option-group {
  display: flex;
  gap: $margin-small;
  .option-group-item {
    @include custom-text($font-size: 14px, $font-color: $text-color-light);
    flex: 1;
    height: 48px;
    cursor: pointer;
    border: 1px solid $border-color-input;
    border-radius: 13px;

    display: flex;
    justify-content: center; /* 수평 중앙 정렬 */
    align-items: center; /* 수직 중앙 정렬 */

    transition: background-color 0.3s ease, color 0.3s ease; /* 부드러운 전환 효과 추가 */

    &.active {
      background-color: $primary-color-light; /* active일 때 배경색 변경 */
      color: $primary-color-default; /* active일 때 글자색 변경 */
      border: 1px solid $primary-color-default; /* active일 때 테두리 색 변경 */
    }
  }
}

// scoped
.input-form {
  @include custom-padding-y;
}

.input-content-wrapper {
  display: flex;
  flex-direction: column;
  gap: $padding-small;
}
</style>
