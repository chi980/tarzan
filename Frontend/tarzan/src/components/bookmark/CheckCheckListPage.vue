<template>
  <div class="arrordion-container">
    <BasicAccordion accordionTitle="수도와 배수">
      <CheckListItem
        v-for="(item, index) in checkListAboutWater"
        :key="index"
        :checkListItem="item"
        @change="updateCheckItem(checkListAboutWater, index)" />
    </BasicAccordion>
    <BasicAccordion accordionTitle="창문">
      <CheckListItem
        v-for="(item, index) in checkListAboutWindow"
        :key="index"
        :checkListItem="item"
        @change="updateCheckItem(checkListAboutWindow, index)" />
    </BasicAccordion>
    <BasicAccordion accordionTitle="화장실">
      <CheckListItem
        v-for="(item, index) in checkListAboutBathroom"
        :key="index"
        :checkListItem="item"
        @change="updateCheckItem(checkListAboutBathroom, index)" />
    </BasicAccordion>
    <BasicAccordion accordionTitle="주변환경">
      <CheckListItem
        v-for="(item, index) in checkListAboutSurroundings"
        :key="index"
        :checkListItem="item"
        @change="updateCheckItem(checkListAboutSurroundings, index)" />
    </BasicAccordion>
    <BasicAccordion accordionTitle="기본옵션">
      <CheckListItem
        v-for="(item, index) in checkListAboutOption"
        :key="index"
        :checkListItem="item"
        @change="updateCheckItem(checkListAboutOption, index)" />
    </BasicAccordion>
    <BasicAccordion accordionTitle="디테일">
      <CheckListItem
        v-for="(item, index) in checkListAboutDetail"
        :key="index"
        :checkListItem="item"
        @change="updateCheckItem(checkListAboutDetail, index)" />
    </BasicAccordion>
    <BasicAccordion accordionTitle="보안">
      <CheckListItem
        v-for="(item, index) in checkListAboutSecurity"
        :key="index"
        :checkListItem="item"
        @change="updateCheckItem(checkListAboutSecurity, index)" />
    </BasicAccordion>
    <BasicAccordion accordionTitle="기타사항">
      <CheckListItem
        v-for="(item, index) in checkListAboutEtc"
        :key="index"
        :checkListItem="item"
        @change="updateCheckItem(checkListAboutEtc, index)" />
    </BasicAccordion>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, defineModel } from "vue";
import { Check } from "@/data/check";

import BasicAccordion from "@/components/common/BasicAccordion.vue";
import CheckListItem from "@/components/common/CheckListItem.vue";

const props = defineProps<{ bookmarkIdx: number }>();
const bookmarkData = defineModel<Object>("bookmarkData");

const checkListAboutWater = ref(null);
const checkListAboutWindow = ref(null);
const checkListAboutBathroom = ref(null);
const checkListAboutSurroundings = ref(null);
const checkListAboutOption = ref(null);
const checkListAboutDetail = ref(null);
const checkListAboutSecurity = ref(null);
const checkListAboutEtc = ref(null);
const checkLists = ref([
  { category: "수도와 배수", list: checkListAboutWater },
  { category: "창문", list: checkListAboutWindow },
  { category: "화장실", list: checkListAboutBathroom },
  { category: "주변환경", list: checkListAboutSurroundings },
  { category: "기본옵션", list: checkListAboutOption },
  { category: "디테일", list: checkListAboutDetail },
  { category: "보안", list: checkListAboutSecurity },
  { category: "기타사항", list: checkListAboutEtc },
]);
// ✅ 체크리스트 업데이트 함수
const updateCheckItem = (array: Check[], index: number) => {
  console.log(array[index].value);
  array[index].value = !array[index].value;
};
onMounted(() => {
  checkListAboutWater.value =
    bookmarkData.value.value.bookmark_checklist.CHECK_WATER;
  checkListAboutWindow.value =
    bookmarkData.value.value.bookmark_checklist.CHECK_WINDOW;
  checkListAboutBathroom.value =
    bookmarkData.value.value.bookmark_checklist.CHECK_BATHROOM;
  checkListAboutSurroundings.value =
    bookmarkData.value.value.bookmark_checklist.CHECK_SURROUNDINGS;
  checkListAboutOption.value =
    bookmarkData.value.value.bookmark_checklist.CHECK_OPTION;
  checkListAboutDetail.value =
    bookmarkData.value.value.bookmark_checklist.CHECK_DETAIL;
  checkListAboutSecurity.value =
    bookmarkData.value.value.bookmark_checklist.CHECK_SECURITY;
  checkListAboutEtc.value =
    bookmarkData.value.value.bookmark_checklist.CHECK_ETC;
});
</script>

<style lang="scss" scoped>
// 공통
// scoped
.arrordion-container {
  display: flex;
  flex-direction: column;
  gap: $padding-small;

  @include custom-padding-y;
}
</style>
