<script setup lang="ts">
import { ref } from "vue";

import { HouseOverview } from "@/data/house";

import AddressCard from "@/components/common/AddressCard.vue";

const houseOverview = ref<HouseOverview | null>({
  house_name: "서울역게스트원룸",
  house_address: "서울시 강남구 테헤란로 123",
});
</script>

<template>
  <div class="component">
    <h1>{{ message }}</h1>
  </div>
</template>

<style scoped lang="scss"></style>
