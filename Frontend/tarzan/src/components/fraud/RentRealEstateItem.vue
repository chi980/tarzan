<script setup lang="ts">
import { defineProps } from "vue";
function convertToPyeong(areaInSquareMeters: number): string {
  const pyeong = areaInSquareMeters / 3.3;
  return `${pyeong.toFixed(1)}`;
}

function formatToKoreanCurrency(amountInManwon: number): string {
  const EOK = 10000;
  const eok = Math.floor(amountInManwon / EOK);
  const chonman = amountInManwon % EOK;

  let result = "";
  if (eok > 0) result += `${eok}억`;
  if (chonman > 0) result += ` ${chonman}천만`;

  return result.trim();
}

const props = defineProps<{
  data: {
    bfrRentFee: string;
    bfrSecurity: string;
    buildYear: number;
    buildingName: string;
    buildingUse: string;
    contractDate: string;
    contractDateRange: string;
    dong: string;
    floor: number;
    gu: string;
    rentArea: number;
    rentFee: string;
    rentType: "월세" | "전세";
    security: string;
    streetNumber: string;
  };
}>();
</script>

<template>
  <div class="sale-real-estate-item-container">
    <div class="desc-tag-button-contianer">
      <div>{{ props.data.contractDateRange }}</div>
      <div>{{ props.data.rentType }}</div>
    </div>
    <div class="building-content">
      <p id="building-name">{{ props.data.buildingName || "비공개" }}</p>
      <p id="building-address">
        서울특별시 {{ props.data.gu }} {{ props.data.dong }} ({{
          props.data.streetNumber
        }})
      </p>
      <p id="building-details">
        <span>{{ props.data.buildYear }}년식&nbsp;|&nbsp;</span>
        <span>{{ props.data.buildingUse }}&nbsp;|&nbsp;</span>
        <span>{{ props.data.floor }}층&nbsp;|&nbsp;</span>
        <span>{{ props.data.rentArea }}㎡&nbsp;|&nbsp;</span>
        <span>{{ convertToPyeong(props.data.rentArea) }}평</span>
      </p>
    </div>
    <p id="building-price">
      {{ props.data.security }}만원<span v-if="props.data.rentType == '월세'"
        >(월 {{ props.data.rentFee }}만원)</span
      >
    </p>
  </div>
</template>

<style scoped lang="scss">
.sale-real-estate-item-container {
  @include custom-padding;
  display: flex;
  flex-direction: column;
  gap: 12px;
  border-bottom: 1px solid #f8f8f8;

  .desc-tag-button-contianer {
    display: flex;
    flex-direction: row;
    gap: 4px;

    div {
      @include custom-padding(8px);
      @include custom-text($font-size: 10px, $font-color: #717277);
      background-color: #f2f3f9;
      border-radius: 10px;
    }
  }

  .building-content {
    display: flex;
    flex-direction: column;
    gap: 6px;
  }

  #building-name,
  #building-price {
    @include custom-text($font-size: 16px);
    text-align: left;
  }

  #building-address {
    @include custom-text($font-size: 12px, $font-color: $text-color-light);
    text-align: left;
  }

  #building-details {
    display: flex;
    flex-direction: row;
    gap: 0px;

    span {
      @include custom-text($font-size: 12px, $font-color: $text-color-light);
      text-align: left;
    }
  }
}
</style>
