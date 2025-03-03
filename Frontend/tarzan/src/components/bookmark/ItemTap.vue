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

// API: 체크리스트 API 호출
// const fetchCheckMoverList = async () => {
//   try {
//     const response = await axiosInstance.get(`/v1/checklist/mover?`);

//     if (response.data.success) {
//       console.log("이사 체크리스트 가져오기 성공!");
//       const data = response.data.data;
//       console.log("API 응답 데이터:", data);

//       Object.keys(checkListData).forEach((key) => {
//         if (data[key]) {
//           checkListData[key].splice( 
//             0, 
//             checkListData[key].length, // 기존 값 전부 제거
//             ...data[key].name_list.map((name, index) => ({
//               idx: data[key].id_list[index],
//               name,
//               value: data[key].value_list[index],
//             }))
//           );
//         }
//       });
//     } else {
//       console.error("체크리스트 데이터 없음", response.data.message);
//     }
//   } catch (error) {
//     console.error("API 요청 오류: ", error);
//   }
// };
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
