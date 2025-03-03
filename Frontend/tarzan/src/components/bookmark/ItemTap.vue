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
import { ref, onMounted } from "vue";

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

// // API: 체크리스트 API 호출
// const fetchCheckItemList = async () => {
//   try {
//     const response = await axiosInstance.get(`/v1/checklist/item?`);

//     if (response.data.success) {
//       console.log("아이템 체크리스트 가져오기 성공!");
//       const data = response.data.data;
//       console.log("API 응답 데이터:", data);
  
//     } else {
//       console.error("아이템리스트 데이터 없음", response.data.message);

//     }
//   } catch (error) {
//     console.error("API 요청 오류: ", error);
//   }
// };


// const homeAppliances = ref([]);
// const fabrics = ref([]);
// const bathroomSupplies = ref([]);
// const ingredients = ref([]);
// const kitchenUtensils = ref([]);
// const householdGoods = ref([]);

// const transformData = (beforeData: any, afterData: any) => {
//   const combinedList = [];

//   if (beforeData) {
//     beforeData.id_list.forEach((id: number, index: number) => {
//       combinedList.push({
//         id,
//         name: beforeData.name_list[index],
//         checked: beforeData.value_list[index],
//       });
//     });
//   }

//   if (afterData) {
//     afterData.id_list.forEach((id: number, index: number) => {
//       combinedList.push({
//         id,
//         name: afterData.name_list[index],
//         checked: afterData.value_list[index],
//       });
//     });
//   }

//   return combinedList;
// };

// // API 호출 후 데이터 변환
// const fetchCheckItemList = async () => {
//   try {
//     const response = await axiosInstance.get(`/v1/checklist/item?`);
//     if (response.data.success) {
//       console.log("아이템 체크리스트 가져오기 성공!");
//       const data = response.data.data;

//       homeAppliances.value = transformData(
//         data.home_appliances_before_move,
//         data.home_appliances_after_move
//       );

//       fabrics.value = transformData(
//         data.furniture_fabric_before_move,
//         data.furniture_fabric_after_move
//       );

//       bathroomSupplies.value = transformData(
//         data.bathroom_before_move,
//         data.bathroom_after_move
//       );

//       ingredients.value = transformData(
//         data.food_before_move,
//         data.food_after_move
//       );

//       kitchenUtensils.value = transformData(
//         data.kitchen_before_move,
//         data.kitchen_after_move
//       );

//       householdGoods.value = transformData(
//         data.household_before_move,
//         data.household_after_move
//       );

//     } else {
//       console.error("아이템리스트 데이터 없음", response.data.message);
//     }
//   } catch (error) {
//     console.error("API 요청 오류: ", error);
//   }
// };

// onMounted(() => {
//   fetchCheckItemList();
// });


// onMounted(() => {
//   fetchCheckItemList();
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
