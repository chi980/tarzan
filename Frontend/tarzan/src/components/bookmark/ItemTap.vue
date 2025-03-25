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
import { onMounted } from "vue";
import ComplexAccordion from "@/components/common/ComplexAccordion.vue";
import {
  homeAppliances,
  fabrics,
  bathroomSupplies,
  ingredients,
  kitchenUtensils,
  householdGoods,
} from "@/data/bookmark/itemTab";

// 메인 아코디언 열고 닫기
const toggleAccordion = (contents: { value: CheckList[] }) => {
  contents.value.forEach((controlledCheckList: CheckList) => {
    controlledCheckList.canSee = false;
    controlledCheckList.isRotated = false;
  });
};

// 서브 아코디언 열고 닫기
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

const fetchCheckItemList = async () => {
  try {
    const response = await axiosInstance.get(`/v1/checklist/item`);

    if (response.data?.success && response.data?.data) {
      console.log("아이템 체크리스트 가져오기 성공!");
      const data = response.data.data;
      console.log(data);

      // 🏠 "이사 전 필수품" (before_move) -> homeAppliances[0].contents에 삽입
      homeAppliances.value[0].contents =
        data.home_appliances_before_move.id_list.map(
          (id: number, index: number) => ({
            idx: id,
            name: data.home_appliances_before_move.name_list[index],
            value: data.home_appliances_before_move.value_list[index],
          })
        );
      // 🏠 "이사 후 사도 되는 물품" (after_move) -> homeAppliances[1].contents에 삽입
      homeAppliances.value[1].contents =
        data.home_appliances_after_move.id_list.map(
          (id: number, index: number) => ({
            idx: id,
            name: data.home_appliances_after_move.name_list[index],
            value: data.home_appliances_after_move.value_list[index],
          })
        );

      // 🛋 가구ㆍ패브릭 (fabrics)
      fabrics.value[0].contents = data.furniture_fabric_before_move.id_list.map(
        (id: number, index: number) => ({
          idx: id,
          name: data.furniture_fabric_before_move.name_list[index],
          value: data.furniture_fabric_before_move.value_list[index],
        })
      );
      fabrics.value[1].contents = data.furniture_fabric_after_move.id_list.map(
        (id: number, index: number) => ({
          idx: id,
          name: data.furniture_fabric_after_move.name_list[index],
          value: data.furniture_fabric_after_move.value_list[index],
        })
      );

      // 🚿 욕실 용품 (bathroomSupplies)
      bathroomSupplies.value[0].contents =
        data.bathroom_before_move.id_list.map((id: number, index: number) => ({
          idx: id,
          name: data.bathroom_before_move.name_list[index],
          value: data.bathroom_before_move.value_list[index],
        }));
      bathroomSupplies.value[1].contents = data.bathroom_after_move.id_list.map(
        (id: number, index: number) => ({
          idx: id,
          name: data.bathroom_after_move.name_list[index],
          value: data.bathroom_after_move.value_list[index],
        })
      );

      // 🍽 주방 용품 (kitchenUtensils)
      kitchenUtensils.value[0].contents = data.kitchen_before_move.id_list.map(
        (id: number, index: number) => ({
          idx: id,
          name: data.kitchen_before_move.name_list[index],
          value: data.kitchen_before_move.value_list[index],
        })
      );
      kitchenUtensils.value[1].contents = data.kitchen_after_move.id_list.map(
        (id: number, index: number) => ({
          idx: id,
          name: data.kitchen_after_move.name_list[index],
          value: data.kitchen_after_move.value_list[index],
        })
      );

      // 🍲 필수 식재료 (ingredients)
      ingredients.value[0].contents = data.food_before_move.id_list.map(
        (id: number, index: number) => ({
          idx: id,
          name: data.food_before_move.name_list[index],
          value: data.food_before_move.value_list[index],
        })
      );
      ingredients.value[1].contents = data.food_after_move.id_list.map(
        (id: number, index: number) => ({
          idx: id,
          name: data.food_after_move.name_list[index],
          value: data.food_after_move.value_list[index],
        })
      );

      // 🏠 생활 용품 (householdGoods)
      householdGoods.value[0].contents = data.household_before_move.id_list.map(
        (id: number, index: number) => ({
          idx: id,
          name: data.household_before_move.name_list[index],
          value: data.household_before_move.value_list[index],
        })
      );
      householdGoods.value[1].contents = data.household_after_move.id_list.map(
        (id: number, index: number) => ({
          idx: id,
          name: data.household_after_move.name_list[index],
          value: data.household_after_move.value_list[index],
        })
      );
    } else {
      console.error("아이템리스트 데이터 없음", response.data?.message);
    }
  } catch (error) {
    console.error("API 요청 오류: ", error);
  }
};

onMounted(() => {
  fetchCheckItemList();
});
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
