<script setup lang="ts">
import { ref, computed } from "vue";

import { Index } from "@/data/house";
import { ChartDataOption } from "@/data/chart";

import RadarChart from "@/components/common/RadarChart.vue";

const props = defineProps<{
  indexes: Index[];
}>();
const chartData = computed<ChartDataOption[]>(() => [
  {
    label: "현재 건물",
    data: [
      props.indexes.house_index_amenity,
      props.indexes.house_index_clinic,
      props.indexes.house_index_security,
      props.indexes.house_index_shopping,
      props.indexes.house_index_transportation,
    ],
  },
]);
</script>

<template>
  <div class="building-info-tap-wrapper">
    <div class="chart-wrapper">
      <RadarChart :chartData="chartData" />
    </div>
  </div>
</template>

<style scoped lang="scss">
.building-info-tap-wrapper {
  @include custom-padding-y;
  display: flex;
  flex-direction: column;
  gap: $padding-default;

  .chart-wrapper {
    display: flex;
    justify-content: center;
    align-items: center;
  }
}
</style>
