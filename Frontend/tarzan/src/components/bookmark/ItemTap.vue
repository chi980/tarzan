<template>
  <div class="tab-content">
    <ComplexAccordion
      accordionTitle="가전"
      :accordionContents="homeAppliances"
      @toggleAccordion="toggleAccordionHomeAppliances"
      @toggleSubAccordion="toggleSubAccordionHomeAppliances"
    />
    <ComplexAccordion
      accordionTitle="가구ㆍ패브릭"
      :accordionContents="fabrics"
      @toggleAccordion="toggleAccordionFabrics"
      @toggleSubAccordion="toggleSubAccordionFabrics"
    />
    <ComplexAccordion
      accordionTitle="욕실 용품"
      :accordionContents="bathroomSupplies"
      @toggleAccordion="toggleAccordionBathroomSupplies"
      @toggleSubAccordion="toggleSubAccordionBathroomSupplies"
    />
    <ComplexAccordion
      accordionTitle="필수 식재료"
      :accordionContents="ingredients"
      @toggleAccordion="toggleAccordionIngredients"
      @toggleSubAccordion="toggleSubAccordionIngredients"
    />
    <ComplexAccordion
      accordionTitle="주방 용품"
      :accordionContents="kitchenUtensils"
      @toggleAccordion="toggleAccordionKitchenUtensils"
      @toggleSubAccordion="toggleSubAccordionKitchenUtensils"
    />
    <ComplexAccordion
      accordionTitle="생활 용품"
      :accordionContents="householdGoods"
      @toggleAccordion="toggleAccordionHouseholdGoods"
      @toggleSubAccordion="toggleSubAccordionHouseholdGoods"
    />
  </div>
</template>

<script setup lang="ts">
import { CheckList } from "@/data/check";
import { axiosInstance } from "@/plugins/axiosPlugin";

import {
  homeAppliances,
  fabrics,
  bathroomSupplies,
  ingredients,
  kitchenUtensils,
  householdGoods,
} from "@/data/bookmark/itemTab";
import ComplexAccordion from "@/components/common/ComplexAccordion.vue";

const toggleAccordion = (contents: { value: CheckList[] }) => {
  contents.value.forEach((controlledCheckList: CheckList) => {
    controlledCheckList.canSee = false;
    controlledCheckList.isRotated = false;
  });
};

const toggleSubAccordion = (contents: CheckList[], idx: number) => {
  const controlledCheckList = contents.find((item) => item.idx === idx); // contents.values 대신 contents 사용
  if (controlledCheckList) {
    controlledCheckList.canSee = !controlledCheckList.canSee;
    controlledCheckList.isRotated = !controlledCheckList.isRotated;
  }
};

const toggleAccordionHomeAppliances = () => {
  toggleAccordion(homeAppliances);
};
const toggleSubAccordionHomeAppliances = (idx: number) => {
  toggleSubAccordion(homeAppliances.value, idx);
};
const toggleAccordionFabrics = () => {
  toggleAccordion(fabrics);
};
const toggleSubAccordionFabrics = (idx: number) => {
  toggleSubAccordion(fabrics.value, idx);
};
const toggleAccordionBathroomSupplies = () => {
  toggleAccordion(bathroomSupplies);
};
const toggleSubAccordionBathroomSupplies = (idx: number) => {
  toggleSubAccordion(bathroomSupplies.value, idx);
};
const toggleAccordionIngredients = () => {
  toggleAccordion(ingredients);
};
const toggleSubAccordionIngredients = (idx: number) => {
  toggleSubAccordion(ingredients.value, idx);
};
const toggleAccordionKitchenUtensils = () => {
  toggleAccordion(kitchenUtensils);
};
const toggleSubAccordionKitchenUtensils = (idx: number) => {
  toggleSubAccordion(kitchenUtensils.value, idx);
};
const toggleAccordionHouseholdGoods = () => {
  toggleAccordion(householdGoods);
};
const toggleSubAccordionHouseholdGoods = (idx: number) => {
  toggleSubAccordion(householdGoods.value, idx);
};

// import { ref, onMounted } from 'vue';
// const comments = ref([]);

// // API: 사용자의 댓글 데이터 불러오기
// const fetchUserComments = async () => {
//   const queryParams = new URLSearchParams({
//     size: 5,
//     page: 0,
//     sortBy: '최신순',
//   }).toString();

//   try {
//     const response = await axiosInstance.get(`/v1/user/comments?${queryParams}`);

//     if (response.data.success) {
//       console.log("사용자 댓글 목록 가져오기 성공!");
//       comments.value = response.data.data.list;
//     } else {
//       console.error("API 실패:", response.data.message);

//     }
//   } catch (error) {
//     console.error("댓글 데이터 요청 중 오류 발생:", error);
//   }
// };

// onMounted(async () => {
//   await fetchUserComments();
// });
</script>

<style lang="scss" scoped>
// 공통

.tab-content {
  margin-top: 0;
}
// scoped
.tab-content {
  display: flex;
  flex-direction: column;
  gap: $padding-small;
}
</style>
