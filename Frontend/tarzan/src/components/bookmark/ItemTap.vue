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
