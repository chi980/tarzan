<template>
  <div class="sub-container non-input-sub-container">
    <AddHouseBar class="add-house-bar" />
    <div class="center-container">
      <form class="input-form">
        <div class="input-group">
          <h2 class="input-title">건물 종류</h2>
            <CustomSelectBox
              :options="HouseCategoryOptions"
              @update:selected="handleBuildingCategorySelected"
            />
        </div>
        <div class="input-group">
          <h2 class="input-title">건물명</h2>
          <div class="input-content" :class="{'error': !house_name && showError}">
            <input
              type="text"
              v-model="house_name"
              placeholder="건물명을 입력해주세요"
            />
          </div>
        <div v-if="!house_name || !house_name.trim() || showError" class="input-description error-message">
          <p>
            <i class="bi bi-info-circle"></i>
            건물 명은 필수 정보입니다.
          </p>
        </div>
        </div>
        <div class="input-group">
          <h2 class="input-title">주소</h2>
          <div class="input-content">
            <input type="text" v-model="house_address" placeholder="주소를 입력해주세요" />
          </div>
          <div class="input-description">
            <p>
              <i class="bi bi-info-circle"> </i>
              상세 주소를 추가 입력해주세요.
            </p>
          </div>
        </div>
      </form>
      <div class="bottom-button-wrapper">
        <div @click="handleAddHouseClick">
          <p>직접 추가하기</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from 'vue-router';
import { axiosInstance } from "@/plugins/axiosPlugin";
import AddHouseBar from "@/components/bookmark/AddHouseBar.vue";
import CustomSelectBox from '@/components/common/CustomSelectBox.vue';

// const searchQuery = ref(""); 검색기능 사용시

const showError = ref(false);  // 오류 메시지 표시 여부
const route = useRoute();      // useRoute: 현재 URL 정보
const router = useRouter();    // useRouter: 페이지 이동

const house_address = ref("");
const house_name = ref("");
const house_latitude = ref(null);
const house_longitude = ref(null);
const house_category = ref("");

const list = ref([]); // ✅ 추가한 집 목록을 저장

// 건물 종류 선택지를 배열로 정의
const HouseCategoryOptions = [
  { idx: 0, name: '아파트', value: '아파트' },
  { idx: 1, name: '오피스텔', value: '오피스텔' },
  { idx: 2, name: '빌라', value: '빌라' },
  { idx: 3, name: '주택', value: '주택' }
];

// 선택된 건물 종류의 인덱스를 저장할 변수
const selectedBuildingCategoryIdx = ref(null);

// 선택된 건물 종류를 처리하는 메서드
const handleBuildingCategorySelected = (idx: number) => {
  selectedBuildingCategoryIdx.value = idx;
  house_category.value = HouseCategoryOptions[idx].value;  // house_category 값 업데이트
  console.log('선택된 건물 종류:', HouseCategoryOptions[idx].name);
};


// "직접 추가하기" 버튼 클릭 시
const handleAddHouseClick = async () => {
  if (!house_name.value || !house_name.value.trim()) {  // 🚨 빈 값과 공백 체크
    showError.value = true;
    return;
  }

  showError.value = false;  // 정상 입력 시 에러 메시지 숨김

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
    router.push({ name: "BookMark", query: { list: JSON.stringify(list.value) } });
    console.log("Response:", response.data);
  } catch (error) {
    console.error("저장 실패:", error);
  }
};





onMounted(() => {
  house_address.value = route.query.house_address as string || '';
  house_name.value = route.query.house_name as string || '';
});
</script>

<style lang="scss" scoped>
// 공통
.top-bar-back {
  @include custom-bar-style(
    $height: $height-top-bar,
    $z-index: $z-index-top-bar
  );

  background-color: aqua;
}

.sub-container {
  display: flex;
  flex-direction: column;
  width: 100%;
}

.center-container {
  flex-grow: 1;
  width: 100%;
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  margin-top: 20px;
}

.input-group:first-child {
  margin-top: 0;
}

.input-group {
  @include custom-padding-x;
  margin-top: $margin-big;

  .input-title {
    text-align: left;
    padding-bottom: 10px;
    font-weight: bold;
  }

  .input-content {
    display: flex;
    align-items: center;
    gap: $padding-default;

    input[type="text"] {
      @include custom-input-style;
      flex: 1;
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

  .error {
    border: 1px solid red;  /* 빨간색 테두리 */
    border-radius: 14px;  /* 테두리의 radius 조정 */
  }

  .error-message {
    color: red;  /* 빨간색 텍스트 */
  }
}

.bottom-button-wrapper {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  @include custom-padding-x;

  div {
    @include custom-button-style($height: 54px, $font-color: white);
  }
}
</style>
