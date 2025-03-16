<template>
  <div class="sub-container non-input-sub-container">
    <div style="width: 100%">
      <TopBarBack title="추가하기" />
    </div>
    <div class="center-container left-top-container">
      <form class="input-form">
        <!-- 컴포넌트 화 하기 -->
        <div class="input-group">
          <h2 class="input-title">가격</h2>
          <div class="input-content-wrapper">
            <div class="input-content">
              <input v-model="houseData.bookmark_deposit" type="text" placeholder="보증금을 입력해주세요." />
            </div>
            <div class="input-content">
              <input v-if="houseData.bookmark_rent === 'MONTHLY'" v-model="houseData.monthly_rent" type="text" placeholder="월세를 입력해주세요." />
              <input v-if="houseData.bookmark_rent === 'JEONSE'" v-model="houseData.jeonse_price" type="text" placeholder="전세를 입력해주세요." />
              <input v-if="houseData.bookmark_rent === 'PROPERTY'" v-model="houseData.property_price" type="text" placeholder="매매가를 입력해주세요." />
            </div>
            <div class="input-content">
              <div class="select-content" style="width: max-content; min-width: 100px">
                <CustomSelectBox v-model="houseData.bookmark_rent" :options="rentalOptions" />
              </div>
            </div>
            <div class="input-content">
              <input type="text" placeholder="중개수수료를 입력해주세요." />
            </div>
            <div class="input-content">
              <input type="text" placeholder="관리비를 입력해주세요." />
            </div>
          </div>
        </div>

        <div class="input-group">
          <h2 class="input-title">부동산/집주인</h2>
          <div class="input-content-wrapper">
            <div class="input-content">
              <input v-model="houseData.bookmark_realtor" type="text" placeholder="부동산/집주인명을 입력해주세요." />
            </div>
            <div class="input-content">
              <input v-model="houseData.bookmark_realtor_phone" type="text" placeholder="부동산/집주인 핸드폰 번호를 입력해주세요." />
            </div>
          </div>
        </div>

        <div class="input-group">
          <h2 class="input-title">반려동물 가능 여부</h2>
          <div class="input-content">
            <div class="select-content">
              <CustomSelectBox v-model="houseData.pet_allowance" :options="petOptions" />
            </div>
          </div>
        </div>

        <div class="input-group">
          <h2 class="input-title">방향</h2>
          <div class="input-content">
            <div class="select-content">
              <CustomSelectBox :options="directionOptions" />
            </div>
          </div>
        </div>

        <div class="input-group">
          <h2 class="input-title">주차장 대수</h2>
          <div class="input-content">
            <div class="select-content">
              <CustomSelectBox :options="carOptions" />
            </div>
          </div>
        </div>

        <div class="input-group">
          <h2 class="input-title">방수/욕실수</h2>
          <div class="input-content-wrapper">
            <div class="input-content">
              <input v-model="houseData.room_count" type="text" placeholder="방수를 입력해주세요." />
            </div>
            <div class="input-content">
              <input v-model="houseData.bathroom_count" type="text" placeholder="욕실수를 입력해주세요." />
            </div>
          </div>
        </div>

        <div class="input-group">
          <h2 class="input-title">입주가능일</h2>
          <div class="input-content">
            <input v-model="houseData.move_in_date" type="date" placeholder="입주가능일을 입력해주세요." />
          </div>
        </div>

        <div class="input-group">
          <h2 class="input-title">층수</h2>
          <div class="input-content">
            <input v-model="houseData.floor" type="text" placeholder="층수를 입력해주세요." />
          </div>
        </div>
      </form>
    </div>
    <div class="bottom-button-wrapper">
      <div>
        <p @click="updateHouseData">수정하기</p>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { Option } from "@/data/options";
import { axiosInstance } from "@/plugins/axiosPlugin";
import CustomSelectBox from "@/components/common/CustomSelectBox.vue";
import TopBarBack from "../common/TopBarBack.vue";

const route = useRoute();
const bookmarkIdx = route.params.bookmarkIdx;

const houseData = ref({
  bookmark_deposit: '',
  bookmark_rent: '',
  bookmark_realtor: '',
  bookmark_realtor_phone: '',
  pet_allowance: '',
  direction: '',
  parking_lot: '',
  room_count: '',
  bathroom_count: '',
  move_in_date: '',
  floor: ''
});

onMounted(async () => {
  try {
    const response = await axiosInstance.get(`/v1/bookmark/${bookmarkIdx}`);
    if (response.data.success) {
      const fetchedData = response.data.data;
      houseData.value = {
        bookmark_deposit: fetchedData.bookmark_deposit || '',
        bookmark_rent: fetchedData.bookmark_rent || '',
        bookmark_realtor: fetchedData.bookmark_realtor || '',
        bookmark_realtor_phone: fetchedData.bookmark_realtor_phone || '',
        pet_allowance: fetchedData.pet_allowance || '',
        direction: fetchedData.direction || '',
        parking_lot: fetchedData.parking_lot || '',
        room_count: fetchedData.room_count || '',
        bathroom_count: fetchedData.bathroom_count || '',
        move_in_date: fetchedData.move_in_date || '',
        floor: fetchedData.floor || '',
      };
    } else {
      console.error('Failed to fetch house data:', response.data.message);
    }
  } catch (error) {
    console.error('API request error:', error);
  }
});

const updateHouseData = async () => {
  console.log('수정하기 버튼 클릭됨');
  try {
    const response = await axiosInstance.put(`/v1/bookmark/${bookmarkIdx}`, houseData.value);
    if (response.data.success) {
      console.log('House data updated successfully:', response.data);
    } else {
      console.error('Failed to update house data:', response.data.message);
    }
  } catch (error) {
    console.error('PUT 요청 중 에러 발생:', error);
  }
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
.top-bar-back {
  @include custom-bar-style(
    $height: $height-top-bar,
    $z-index: $z-index-top-bar
  );

  background-color: rgba(0, 0, 0, 0.1); /* 검은색 배경, 50% 투명도 */
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

      input {
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
.left-top-container {
  justify-content: flex-start;
  gap: $padding-big;
  padding-top: calc(#{$padding-default}* 2);
  padding-bottom: calc(54px + #{$margin-default} + #{$padding-default});
}
// scoped
</style>
