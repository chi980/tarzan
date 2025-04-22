<script setup lang="ts">
import { ref, watch } from "vue";

import RadarChart from "@/components/common/RadarChart.vue";

const props = defineProps<{
  house: any;
}>();
const chartData = ref(null);

chartData.value = {
  [props.house.house_id]: {
    houseName: props.house.value.house_name,
    indexes: [
      {
        label: "교통",
        value: props.house.value.house_indexes.house_index_transportation,
      },
      {
        label: "상업시설",
        value: props.house.value.house_indexes.house_index_shopping,
      },
      {
        label: "편의시설",
        value: props.house.value.house_indexes.house_index_amenity,
      },
      {
        label: "치안",
        value: props.house.value.house_indexes.house_index_security,
      },
      {
        label: "보건",
        value: props.house.value.house_indexes.house_index_clinic,
      },
    ],
  },
};
watch(
  () => props.house,
  (newHouse) => {
    if (!newHouse || !newHouse.house_indexes) return;
    console.log(newHouse.house_indexes);

    chartData.value = {
      [newHouse.house_id]: {
        houseName: newHouse.house_name,
        indexes: [
          {
            label: "교통",
            value: newHouse.house_indexes.house_index_transportation,
          },
          {
            label: "상업시설",
            value: newHouse.house_indexes.house_index_shopping,
          },
          {
            label: "편의시설",
            value: newHouse.house_indexes.house_index_amenity,
          },
          { label: "치안", value: newHouse.house_indexes.house_index_security },
          { label: "보건", value: newHouse.house_indexes.house_index_clinic },
        ],
      },
    };
  },
  { immediate: true }
);
</script>

<template>
  <div class="building-info-tap-wrapper">
    <div class="chart-wrapper">
      <RadarChart :chartData="chartData" v-if="chartData" />
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
