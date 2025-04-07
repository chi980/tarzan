<script setup lang="ts">
import { ref } from "vue";

import { HouseOverview } from "@/data/house";

import TopBarBack from "@/components/common/TopBarBack.vue";
import AddressCard from "@/components/common/AddressCard.vue";
import BottomDefaultButton from "@/components/common/BottomDefaultButton.vue";

const houseOverview = ref<HouseOverview | null>({
  house_name: "서울역게스트원룸",
  house_address: "서울시 강남구 테헤란로 123",
});
const buttonLabel = ref("다음으로");

const handleClick = () => {
  console.log("click");
};
</script>

<template>
  <div class="sub-container">
    <TopBarBack :title="'점검하기'" />
    <div class="center-container"></div>
    <BottomDefaultButton :onClick="handleClick" :label="buttonLabel" />
  </div>
</template>

<style scoped lang="scss">
.center-container {
  flex: 1;
  width: 100%;
  background-color: aqua;
}

//scoped
.center-container {
  @include custom-margin-y;
}
</style>
