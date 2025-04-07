<script setup lang="ts">
import { onprop } from "vue";
import { HouseOverview } from "@/data/house";

import { defineProps } from "vue";

const props = defineProps<{
  houseOverview: HouseOverview;
}>();
</script>

<template>
  <div class="house-over-view-card">
    <p class="card-title">주소</p>
    <p class="house-name">{{ houseOverview?.house_name }}</p>
    <p class="house-address">{{ houseOverview?.house_address }}</p>
  </div>
</template>

<style scoped lang="scss">
.house-over-view-card {
  @include custom-text($font-size: 12px, $font-color: #969696);
  @include custom-margin-x;
  @include custom-padding;
  display: flex;
  flex-direction: column;
  gap: $padding-small;
  align-items: start;
  justify-content: center;

  border-radius: 16px;

  background-color: #f8f8f8;

  .card-title {
    @include custom-text($font-size: 16px, $font-weight: 700);
  }

  .house-name {
    @include custom-text($font-size: 14px);
  }
}
</style>
