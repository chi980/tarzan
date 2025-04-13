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
              v-model.number="bookmarkData.value.bookmark_deposit"
              type="number"
              placeholder="보증금을 입력해주세요." />
          </div>
          <div class="input-content select-container">
            <div style="width: max-content; min-width: 100px">
              <CustomSelectBox
                v-model="bookmarkData.value.bookmark_lease_type"
                :options="rentalOptions"
                :parent-style="{
                  backgroundColor: 'white',
                  fontWeight: 400,
                  justifyContent: `space-between`,
                  border: '1px solid #e7e7e7',
                }"
                @update:selected="handleSelectLeaseType" />
            </div>
            <input
              v-model.number="bookmarkData.value.bookmark_rent"
              :class="{ disabled: isDisabled }"
              type="number"
              placeholder="금액을 입력해주세요."
              :disabled="isDisabled" />
          </div>
          <div class="input-content">
            <input
              v-model.number="bookmarkData.value.bookmark_commission_fee"
              type="number"
              placeholder="중개수수료를 입력해주세요." />
          </div>
          <div class="input-content">
            <input
              v-model.number="bookmarkData.value.bookmark_management_fee"
              type="number"
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
              v-model="bookmarkData.value.bookmark_real_estate"
              type="text"
              placeholder="부동산/집주인명을 입력해주세요." />
          </div>
          <div class="input-content">
            <input
              v-model="bookmarkData.value.bookmark_real_estate_phone_number"
              type="text"
              placeholder="부동산/집주인 연락처를 입력해주세요." />
          </div>
        </div>
      </div>

      <!-- 반려동물 가능 여부 -->
      <!-- 반려동물 유무 선택 -->
      <div class="input-group">
        <h2 class="input-title">
          반려동물 가능 여부<span class="input-title-mandatory">*</span>
        </h2>
        <div class="option-group">
          <div
            class="option-group-item"
            v-for="(petOption, index) in petOptions"
            :key="petOption.idx"
            :class="{ active: petOption.isSelected }"
            @click="selectOption(petOptions, index, changePetData)">
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
              v-model="bookmarkData.value.bookmark_direction"
              :options="directionOptions"
              :parent-style="{
                backgroundColor: 'white',
                fontWeight: 400,
                justifyContent: `space-between`,
                border: '1px solid #e7e7e7',
              }"
              @update:selected="handleSelectDirection" />
          </div>
        </div>
      </div>

      <!-- 주차장 대수 -->
      <div class="input-group">
        <h2 class="input-title">주차장 대수</h2>
        <div class="input-content">
          <div class="select-content">
            <CustomSelectBox
              v-model="bookmarkData.value.bookmark_parking_cnt"
              :options="carOptions"
              :parent-style="{
                backgroundColor: 'white',
                fontWeight: 400,
                justifyContent: `space-between`,
                border: '1px solid #e7e7e7',
              }"
              @update:selected="handleSelectParkingCnt" />
          </div>
        </div>
      </div>

      <!-- 방수/욕실수 -->
      <div class="input-group">
        <h2 class="input-title">방수/욕실수</h2>
        <div class="input-content-wrapper">
          <div class="input-content">
            <input
              v-model.number="bookmarkData.value.bookmark_room_cnt"
              type="number"
              placeholder="방수를 입력해주세요." />
          </div>
          <div class="input-content">
            <input
              v-model.number="bookmarkData.value.bookmark_bath_room_cnt"
              type="number"
              placeholder="욕실수를 입력해주세요." />
          </div>
        </div>
      </div>

      <!-- 입주가능일 -->
      <div class="input-group">
        <h2 class="input-title">입주가능일</h2>
        <div class="input-content">
          <input
            v-model="bookmarkData.value.bookmark_available_move_in_date"
            type="date"
            placeholder="입주가능일을 입력해주세요." />
        </div>
      </div>

      <!-- 층수 -->
      <div class="input-group">
        <h2 class="input-title">층수</h2>
        <div class="input-content">
          <input
            v-model.number="bookmarkData.value.bookmark_floor"
            type="number"
            placeholder="층수를 입력해주세요." />
        </div>
      </div>
    </form>
  </div>
</template>

<script lang="ts" setup>
import { defineProps, ref, watch, reactive, defineModel, computed } from "vue";
import { Option } from "@/data/options";
import { axiosInstance } from "@/plugins/axiosPlugin";
import CustomSelectBox from "@/components/common/CustomSelectBox.vue";

const props = defineProps<{ bookmarkIdx: number }>();
const bookmarkData = defineModel<Object>("bookmarkData");
console.log(bookmarkData.value.value);

const selectOption = (
  options: Option[] | undefined,
  idx: number,
  changeData: Function
) => {
  if (!options || !Array.isArray(options)) {
    console.error("options가 배열이 아닙니다:", options);
    return;
  }

  options.forEach((option) => {
    option.isSelected = false;
  });

  if (idx >= 0 && idx < options.length) {
    options[idx].isSelected = true;
    changeData(options, idx);
  } else {
    console.warn("잘못된 인덱스:", idx);
  }
};

const isDisabled = computed(
  () => bookmarkData.value.value.bookmark_lease_type !== "MONTHLY"
);

const handleSelectLeaseType = (idx: number) => {
  bookmarkData.value.value.bookmark_lease_type = rentalOptions[idx].value;
  bookmarkData.value.value.bookmark_rent = null;
};
const handleSelectDirection = (idx: number) => {
  bookmarkData.value.value.bookmark_direction = directionOptions[idx].value;
};
const handleSelectParkingCnt = (idx: number) => {
  bookmarkData.value.value.bookmark_parking_lot_coverage =
    carOptions[idx].value;
};
const changePetData = (options, idx: number) => {
  bookmarkData.value.value.bookmark_can_animal = options[idx].value;
};

const houseData = reactive({
  bookmark_lease_type: "MONTHLY", // 전세 | 월세
  bookmark_rent: null, // 월세만 해당 항목 이용
  bookmark_deposit: null,
  bookmark_commission: null,
  bookmark_management_fee: null,
  bookmark_estate_name: "",
  bookmark_estate_phone_number: "",
  bookmark_can_animal: true, // true | false
  bookmark_parking_cnt: "0",
  bookmark_room_cnt: null,
  bookmark_bath_cnt: null,
  bookmark_available_date: "",
  bookmark_floor: null,
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

// 부모 컴포넌트의 배열 데이터 정의
const rentalOptions: Option[] = [
  { idx: 1, name: "월세", value: "MONTHLY" },
  { idx: 2, name: "전세", value: "JEONSE" },
  { idx: 3, name: "매매", value: "PROPERTY" },
];

const petOptions = ref<Option[]>([
  { idx: 1, name: "가능", value: true, isSelected: false },
  { idx: 2, name: "불가능", value: false, isSelected: false },
]);

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

  input {
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

.disabled {
  background-color: #00000010 !important;
  cursor: not-allowed;
}
</style>
