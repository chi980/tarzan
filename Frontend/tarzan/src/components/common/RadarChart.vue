<template>
  <div class="chart-wrapper">
    <div>
      <Radar :data="data" :options="options" />
    </div>
    <div class="legend">
      <div v-for="item in data.datasets" :key="item.label" class="legend-item">
        <div :style="{ backgroundColor: item.borderColor }"></div>
        <p>{{ item.label }}</p>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { defineProps, ref } from "vue";
import { Radar } from "vue-chartjs";
import {
  Chart as ChartJS,
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend,
} from "chart.js";
import * as ChartData from "@/data/chart.ts";
import { ChartColorOptionDefault } from "../../data/chart";

// 차트에 필요한 Chart.js 구성 요소 등록
ChartJS.register(
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend
);

// Props 정의
const props = defineProps<{
  chartData: ChartData.ChartDataOption[];
}>();

console.log(props.chartData);
// 차트 데이터 및 옵션 정의
const data = ref({
  labels: ["교통", "상업시설", "편의시설", "치안", "보건"],
  datasets: [
    {
      ...props.chartData[0],
      ...ChartData.ChartColorOptionDefault[0],
    },
    {
      ...props.chartData[1],
      ...ChartData.ChartColorOptionDefault[1],
    },
  ],
});

const options = {
  responsive: true,
  maintainAspectRatio: false,
  scales: {
    r: {
      grid: {
        circular: true, // 배경을 원형으로 설정
      },
    },
  },
  plugins: {
    legend: {
      display: false, // 이 줄을 추가하여 legend를 숨깁니다
    },
  },
};
</script>

<style lang="scss" scoped>
//scoped
.chart-wrapper {
  @include custom-margin-y($margin-size: $margin-small);
  display: flex;
  flex-direction: column;
}

.legend {
  @include custom-text($font-size: 12px);
  display: flex;
  flex-direction: column;
  gap: $padding-small;
  .legend-item {
    display: flex;
    flex-direction: row;
    gap: $padding-extra-small;
    div {
      height: 10px;
      width: 10px;
      border-radius: 4px;
    }
  }
}
</style>
