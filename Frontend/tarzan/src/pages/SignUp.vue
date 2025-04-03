<template>
  <div class="sub-container">
    <form class="input-form" @submit.prevent="submitForm">
      <!-- 닉네임 입력 -->
      <div class="input-group">
        <h2 class="input-title">
          닉네임<span class="input-title-mandatory">*</span>
        </h2>
        <div class="input-content-wrapper">
          <div class="input-content">
            <input
              type="text"
              placeholder="닉네임을 입력해주세요"
              v-model="nickname"
              @input="handleInput"
              required
              :style="{
                border:
                  nicknameValid === false
                    ? '1px solid red'
                    : '1px solid #e7e7e7',
              }" />
          </div>
          <div class="input-description">
            <p v-if="isChecking">✔ 닉네임 확인 중...</p>
            <p v-if="nicknameValid === true">✅ 사용 가능한 닉네임입니다!</p>
            <p v-if="nicknameValid === false" style="color: red">
              ❌ 사용 불가능한 닉네임입니다.
            </p>
            <p>
              <i class="bi bi-info-circle"> </i>
              닉네임은 영문, 숫자로 이루어져야 합니다.
            </p>
            <p>
              <i class="bi bi-info-circle"> </i>
              닉네임은 6글자 이상 10글자 이하여야 합니다.
            </p>
          </div>
        </div>
      </div>

      <!-- 사는 곳 선택 -->
      <div class="input-group">
        <h2 class="input-title">
          사는 곳<span class="input-title-mandatory">*</span>
        </h2>
        <div class="select-content">
          <CustomSelectBox
            :options="seoulDistrictOptions"
            :parentStyle="{
              backgroundColor: 'white',
              fontWeight: 400,
              justifyContent: `space-between`,
              border: '1px solid #e7e7e7',
            }"
            @update:selected="handleSeoulDistrictSelectedIdx" />
        </div>
      </div>

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

      <!-- 자차 유무 선택 -->
      <div class="input-group">
        <h2 class="input-title">
          자차 유무<span class="input-title-mandatory">*</span>
        </h2>
        <div class="option-group">
          <div
            class="option-group-item"
            v-for="(carOption, index) in carOptions"
            :key="carOption.idx"
            :class="{ active: carOption.isSelected }"
            @click="selectOption(carOptions, index)">
            {{ carOption.name }}
          </div>
        </div>
      </div>

      <!-- 학교/직장 주소 입력 -->
      <div class="input-group">
        <h2 class="input-title">학교/직장 주소</h2>
        <div class="input-content">
          <input
            type="text"
            placeholder="주소를 입력해주세요"
            readonly
            v-model="address"
            @click="openAddressSearch" />
        </div>
      </div>
    </form>

    <!-- 주소 검색 팝업 -->
    <AddressSearch
      v-if="isAddressSearchOpen"
      @close="setAddress" />
    
    <div style="width: 100%;position: absolute; bottom: 16px;display: flex; flex-direction: row;">
      <div class="button-default" @click="submitForm">제출하기</div>
    </div>
  </div>
</template>

<script setup lang="ts">
/**library load */
import { ref } from "vue";
import { debounce } from "lodash"; // lodash 라이브러리 사용

/**data, componenet, env load */
import { useAuthStore } from "@/stores/authStore";
import { axiosInstance } from "@/plugins/axiosPlugin";

import CustomSelectBox from "@/components/common/CustomSelectBox.vue";
import AddressSearch from "@/components/common/AddressSearch.vue";

import { Option } from "@/data/options";
import { seoulSiGunGu } from "@/data/seoulsigungu.js";

const authStore = useAuthStore();

/** form value */
const nickname = ref<string | null>(null);
const seoulDistrictOptions: Option[] = seoulSiGunGu;
const petOptions = ref<Option[]>([
  { idx: 1, name: "반려동물 없음", value: false, isSelected: false },
  { idx: 2, name: "반려동물 있음", value: true, isSelected: false },
]);
const carOptions = ref<Option[]>([
  { idx: 1, name: "차 없음", value: false, isSelected: false },
  { idx: 2, name: "차 있음", value: true, isSelected: false },
]);

/* nickname */
const isChecking = ref(false);
const nicknameValid = ref<boolean | null>(null);
const checkNicknameUnique = async (nickname: string) => {
  if (!nickname.trim()) {
    nicknameValid.value = null;
    return;
  }

  isChecking.value = true;
  try {
    const response = await axiosInstance.post(`/v1/user/check`, {
      nickname,
    });
    if (response.status === 200) {
      nicknameValid.value = true;
    }
  } catch (error) {
    if (error.response && error.response.status === 409) {
      nicknameValid.value = false;
    } else {
      console.error("닉네임 중복 체크 실패", error);
      nicknameValid.value = null;
    }
  } finally {
    isChecking.value = false;
  }
};

const handleInput = debounce(() => {
  // 영문과 숫자만 허용
  const nicknamePattern = /^[a-zA-Z0-9]+$/;
  if (!nicknamePattern.test(nickname.value)) {
    nicknameValid.value = false;
    return;
  }

  if (nickname.value.trim().length >= 6) {
    checkNicknameUnique(nickname.value);
  } else {
    nicknameValid.value = null; // 닉네임 길이가 짧으면 체크하지 않음
  }
}, 500);

/** sigungu select */
const selectedSeoulSiGunGuIdx = ref<number | null>(null);
const handleSeoulDistrictSelectedIdx = (idx: number) => {
  selectedSeoulSiGunGuIdx.value = idx;
  console.log("Selected idx:", selectedSeoulSiGunGuIdx.value);
};

/** petOption, carOption check */
const selectOption = (options: Option[] | undefined, idx: number) => {
  if (!options || !Array.isArray(options)) {
    console.error("options가 배열이 아닙니다:", options);
    return;
  }

  options.forEach((option) => {
    option.isSelected = false;
  });

  if (idx >= 0 && idx < options.length) {
    options[idx].isSelected = true;
  } else {
    console.warn("잘못된 인덱스:", idx);
  }
};

/** address */
const address = ref<string | null>(null);
const isAddressSearchOpen = ref<boolean>(false);
const openAddressSearch = () => {
  isAddressSearchOpen.value = true;
};
const closeAddressSearch = (selectedAddress: string) => {
  address.value = selectedAddress;
  isAddressSearchOpen.value = false;
};


// 부모로부터 전달받은 주소를 저장하는 메소드
const setAddress = (selectedAddress) => {
  console.log(selectedAddress);  // selectedAddress가 무엇인지 확인
  isAddressSearchOpen.value = false;  // 모달 닫기
  if (selectedAddress && selectedAddress.place_name) {
    address.value = `${selectedAddress.place_name} - ${selectedAddress.road_address_name || selectedAddress.address_name}`;
  } else {
    console.error('선택된 주소에 place_name이 없습니다:', selectedAddress);
  }
};


/** submit form */
const submitForm = async () => {
  try {
    /** data 가져오기 */
    const user_nickname = nickname.value == null ? "d" : nickname.value;
    const gu =
      seoulDistrictOptions[
        selectedSeoulSiGunGuIdx.value == null
          ? 0
          : selectedSeoulSiGunGuIdx.value
      ].value;
    const selectedPetIdx = petOptions.value.findIndex(
      (option) => option.isSelected
    );
    const selectedCarIdx = carOptions.value.findIndex(
      (option) => option.isSelected
    );

    /** data valid한지 확인 */
    if (nicknameValid.value == false) {
      alert("닉네임 중복을 확인해주세요");
      return;
    }

    if (gu == null) {
      alert("사는 곳을 선택해주세요");
      return;
    }
    if (selectedPetIdx == -1) {
      alert("반려동물 유무를 선택해주세요");
      return;
    }

    if (selectedCarIdx == -1) {
      alert("자차 유무를 선택해주세요");
      return;
    }

    const formData = {
      user_image_url: "https://example.com/image.jpg",
      user_nickname,
      user_gu: gu,
      user_have_animal: petOptions[selectedPetIdx].value,
      user_have_car: carOptions[selectedCarIdx].value,
      user_job_address: address.value == null ? "d" : address.value,
      user_latitude: 37.5665,
      user_longitude: 126.978,
    };

    const response = await axiosInstance
      .post("/v1/user", formData)
      .then((response) => {
        console.log(response.data);
      });
    const role = response.data.user_role;
    authStore.setRole(role);
  } catch (error) {
    console.error("회원가입 중 오류 발생", error);
  }
};
</script>

<style lang="scss" scoped>
.custom-container {
  display: flex;
  flex-direction: column; /* 기본값 */
  align-items: center;
  justify-content: center;
  width: 100%;
}
#custom-buttom-img {
  @include custom-none-select-basic;
  height: 94px;
  width: 82px;
  margin: 0 auto;
  position: relative;
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
.sub-container {
  position: relative;
  display: flex;
  flex-direction: column;
  gap: $padding-default;
}

.button-default {
  @include custom-button-style(
    $bg-color: $secondary-color-default,
    $font-color: white
  );
  @include custom-margin-x;
  width: 100%;
}
</style>