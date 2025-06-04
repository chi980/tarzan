<template>
  <div class="sub-container non-input-sub-container">
    <div style="width: 100%">
      <TopBarBack title="점검하기" />
    </div>
    <div class="center-container left-top-container">
      <div class="tag-wrapper">
        <h2 class="tag-title">주방 및 세탁실 옵션</h2>
        <div class="tag-content">
          <Tag
            v-for="(item, index) in checkItemsInKitchen"
            :key="index"
            :checkItem="item"
            @update:checkItem="
              updateCheckItem(checkItemsInKitchen, index, $event)
            " />
        </div>
      </div>
      <div class="tag-wrapper">
        <h2 class="tag-title">거실 옵션</h2>
        <div class="tag-content">
          <Tag
            v-for="(item, index) in checkItemsInLivingRoom"
            :key="index"
            :checkItem="item"
            @update:checkItem="
              updateCheckItem(checkItemsInLivingRoom, index, $event)
            " />
        </div>
      </div>
      <div class="tag-wrapper">
        <h2 class="tag-title">방 옵션</h2>
        <div class="tag-content">
          <Tag
            v-for="(item, index) in checkItemsInRoom"
            :key="index"
            :checkItem="item"
            @update:checkItem="
              updateCheckItem(checkItemsInRoom, index, $event)
            " />
        </div>
      </div>
      <div class="tag-wrapper">
        <h2 class="tag-title">화장실 옵션</h2>
        <div class="tag-content">
          <Tag
            v-for="(item, index) in checkItemsInBathRoom"
            :key="index"
            :checkItem="item"
            @update:checkItem="
              updateCheckItem(checkItemsInBathRoom, index, $event)
            " />
        </div>
      </div>
      <div class="tag-wrapper">
        <h2 class="tag-title">보안 및 기타시설</h2>
        <div class="tag-content">
          <Tag
            v-for="(item, index) in checkItemsInEtc"
            :key="index"
            :checkItem="item"
            @update:checkItem="
              updateCheckItem(checkItemsInEtc, index, $event)
            " />
        </div>
      </div>
    </div>
    <div class="bottom-button-wrapper">
      <div @click="handleNextClick">
        <p>다음으로</p>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, watchEffect } from "vue";
import { Check } from "@/data/check";
import { useRoute, useRouter } from "vue-router";
import { axiosInstance } from "@/plugins/axiosPlugin";

import Tag from "@/components/common/Tag.vue";
import TopBarBack from "../common/TopBarBack.vue";

const route = useRoute();
const router = useRouter();

const bookmarkIdx = ref<number | null>(null);

watchEffect(() => {
  if (route.params.bookmarkIdx) {
    bookmarkIdx.value = Number(route.params.bookmarkIdx);
    console.log("bookmarkIdx from route:", bookmarkIdx.value); // bookmarkIdx 값 확인
  }
});

// ✅ '다음으로' 버튼 클릭 시 실행되는 함수
const handleNextClick = async () => {
  try {
    await updateCheckOptions();
    router.push({ name: "CheckCheckListPage" });
  } catch (error) {
    console.error("Error updating check options:", error);
  }
};

// ✅ 체크된 옵션을 서버에 저장하는 PUT 요청 함수
const updateCheckOptions = async () => {
  console.log("bookmarkIdx:", bookmarkIdx.value, typeof bookmarkIdx.value);

  // 🔹 bookmarkIdx 유효성 검사
  if (!bookmarkIdx.value || isNaN(bookmarkIdx.value)) {
    console.error("Invalid bookmarkIdx:", bookmarkIdx.value);
    return;
  }

  const updatedCheckItems = {
    kitchen: checkItemsInKitchen.value,
    livingRoom: checkItemsInLivingRoom.value,
    room: checkItemsInRoom.value,
    bathRoom: checkItemsInBathRoom.value,
    etc: checkItemsInEtc.value,
  };

  console.log("PUT 요청 데이터:", JSON.stringify(updatedCheckItems, null, 2)); // 데이터 확인

  try {
    const response = await axiosInstance.put(
      `/v1/bookmark/${bookmarkIdx.value}`,
      updatedCheckItems
    );
    console.log("Successfully updated:", response.data);
  } catch (error) {
    console.error("Error updating check options:", error);
    throw error;
  }
};

const checkItemsInKitchen = ref<any[]>([
  { idx: 0, name: "식탁", value: "식탁" },
  { idx: 1, name: "인덕션/가스레인지", value: "인덕션/가스레인지" },
  { idx: 2, name: "전자레인지", value: "전자레인지" },
  { idx: 3, name: "냉장고", value: "냉장고" },
  { idx: 4, name: "세탁기", value: "세탁기" },
  { idx: 5, name: "싱크대", value: "싱크대" },
]);

const checkItemsInLivingRoom = ref<any[]>([
  { idx: 0, name: "TV", value: "TV" },
  { idx: 1, name: "천장/벽걸이에어컨", value: "천장/벽걸이에어컨" },
  { idx: 2, name: "전자레인지", value: "전자레인지" },
]);

const checkItemsInRoom = ref<any[]>([
  { idx: 0, name: "침대", value: "침대" },
  { idx: 1, name: "옷장/북박이장", value: "옷장/북박이장" },
]);

const checkItemsInBathRoom = ref<any[]>([
  { idx: 0, name: "샤워부스", value: "샤워부스" },
  { idx: 1, name: "비데", value: "비데" },
]);

const checkItemsInEtc = ref<any[]>([
  { idx: 0, name: "경비원/사설경비", value: "경비원/사설경비" },
  { idx: 1, name: "CCTV", value: "CCTV" },
  { idx: 2, name: "무인택배함", value: "무인택배함" },
  { idx: 3, name: "현관보안", value: "현관보안" },
  { idx: 4, name: "인터폰/비디오폰", value: "인터폰/비디오폰" },
  { idx: 5, name: "카드키", value: "카드키" },
  { idx: 6, name: "화재경보기", value: "화재경보기" },
  { idx: 7, name: "베란다", value: "베란다" },
  { idx: 8, name: "엘레베이터", value: "엘레베이터" },
  { idx: 9, name: "WIFI", value: "WIFI" },
]);

const updateCheckItem = (array: any[], index: number, updatedItem: any) => {
  array.splice(index, 1, updatedItem);
  console.log(array[index]);
};
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

.tag-wrapper {
  @include custom-padding-x;

  display: flex;
  flex-direction: column;
  gap: 18px;

  .tag-title {
    @include custom-text-bold($font-size: 18px);
    text-align: left;
  }
  .tag-content {
    display: flex;
    flex-wrap: wrap;
    gap: $padding-small;
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
