<template>
  <div class="sub-container non-input-sub-container">
    <div style="width: 100%">
      <div class="top-bar-back">
        <p>점검하기</p>
      </div>
    </div>
    <div class="center-container left-top-container">
      <div class="tag-wrapper">
        <h2 class="tag-title">주방 및 세탁실</h2>
        <div class="tag-content">
          <Tag
            v-for="(item, index) in checkItemsInKitchen"
            :key="index"
            :checkItem="item"
            @update:checkItem="
              updateCheckItem(checkItemsInKitchen, index, $event)
            "
          />
        </div>
      </div>
      <div class="tag-wrapper">
        <h2 class="tag-title">거실</h2>
        <div class="tag-content">
          <Tag
            v-for="(item, index) in checkItemsInLivingRoom"
            :key="index"
            :checkItem="item"
            @update:checkItem="
              updateCheckItem(checkItemsInLivingRoom, index, $event)
            "
          />
        </div>
      </div>
      <div class="tag-wrapper">
        <h2 class="tag-title">방</h2>
        <div class="tag-content">
          <Tag
            v-for="(item, index) in checkItemsInRoom"
            :key="index"
            :checkItem="item"
            @update:checkItem="updateCheckItem(checkItemsInRoom, index, $event)"
          />
        </div>
      </div>
      <div class="tag-wrapper">
        <h2 class="tag-title">화장실</h2>
        <div class="tag-content">
          <Tag
            v-for="(item, index) in checkItemsInBathRoom"
            :key="index"
            :checkItem="item"
            @update:checkItem="
              updateCheckItem(checkItemsInBathRoom, index, $event)
            "
          />
        </div>
      </div>
      <div class="tag-wrapper">
        <h2 class="tag-title">보안 및 기타시설</h2>
        <div class="tag-content">
          <Tag
            v-for="(item, index) in checkItemsInEtc"
            :key="index"
            :checkItem="item"
            @update:checkItem="updateCheckItem(checkItemsInEtc, index, $event)"
          />
        </div>
      </div>
    </div>
    <div class="bottom-button-wrapper">
      <div>
        <p>다음으로</p>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { Check } from "@/data/check.ts";
import Tag from "@/components/common/Tag.vue";

const checkItemsInKitchen = ref<Check[]>([
  { name: "식탁", value: false },
  { name: "인덕션/가스레인지", value: false },
  { name: "전자레인지", value: false },
  { name: "냉장고", value: false },
  { name: "세탁기", value: false },
  { name: "싱크대", value: false },
]);

const checkItemsInLivingRoom = ref<Check[]>([
  { name: "TV", value: false },
  { name: "천장/벽걸이에어컨", value: false },
  { name: "전자레인지", value: false },
]);

const checkItemsInRoom = ref<Check[]>([
  { name: "침대", value: false },
  { name: "옷장/북박이장", value: false },
]);

const checkItemsInBathRoom = ref<Check[]>([
  { name: "샤워부스", value: false },
  { name: "비데", value: false },
]);
const checkItemsInEtc = ref<Check[]>([
  { name: "경비원/사설경비", value: false },
  { name: "CCTV", value: false },
  { name: "무인택배함", value: false },
  { name: "현관보안", value: false },
  { name: "인터폰/비디오폰", value: false },
  { name: "카드키", value: false },
  { name: "화재경보기", value: false },
  { name: "베란다", value: false },
  { name: "엘레베이터", value: false },
  { name: "WIFI", value: false },
]);

const updateCheckItem = (array: Check[], index: number, updatedItem: Check) => {
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
