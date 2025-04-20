<template>
  <div class="chart-wrapper">
    <div>
      <Radar :data="data" :options="options" />
    </div>
    <div class="legend">
      <div
        v-for="item in data.datasets"
        :key="item.bookmarkId"
        class="legend-item">
        <div
          :style="{ backgroundColor: item.borderColor }"
          class="legend-color"></div>
        <div class="legend-text">
          <p>{{ item.label }}</p>
          <p class="legend-sub" v-if="item.created_at">
            {{ formatDate(item.created_at) }} 추가
          </p>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import * as ChartData from "@/data/chart";
import { formatDate } from "@/utils/date";
import { defineProps, ref, computed } from "vue";
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

// Chart.js 구성 요소 등록
ChartJS.register(
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend
);

const props = defineProps<{
  chartData: Record<number, ChartData.HouseIndexChart>;
}>();

const data = computed(() => {
  const labels = props.chartData[Object.keys(props.chartData)[0]].indexes.map(
    (i) => i.label
  );

  const datasets = Object.values(props.chartData).map((item, idx) => ({
    bookmarkId: item.bookmarkId,
    label: item.houseName,
    created_at: item.bookmarkCreatedAt,
    data: item.indexes.map((i) => i.value),
    ...ChartData.ChartColorOptionDefault[idx], // 색상 지정
  }));

  return { labels, datasets };
});

const options = {
  responsive: true,
  maintainAspectRatio: false,
  scales: {
    r: {
      grid: {
        circular: true,
      },
    },
  },
  plugins: {
    legend: {
      display: false,
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

    align-items: center;

    .legend-color {
      height: 10px;
      width: 10px;
      border-radius: 4px;
    }
    .legend-text {
      text-align: left;
    }

    .legend-sub {
      @include custom-text($font-size: 10px, $font-color: $text-color-light);
    }
  }
}
</style>
