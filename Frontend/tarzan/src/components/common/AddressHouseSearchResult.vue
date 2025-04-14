<script setup lang="ts">
import { defineProps, defineEmits } from "vue";
import locationMarkImg from "@/assets/icons/location_mark.png";

const props = defineProps({
  addresses: Array, // address 리스트
});
const emit = defineEmits(["selectAddress"]);

const selectAddress = (address: any) => {
  emit("selectAddress", address);
};

const onClick = (address) => {
  selectAddress(address);
};
</script>

<template>
  <div
    v-for="(address, index) in addresses"
    :key="index"
    class="address-wrapper"
    @click="onClick(address)">
    <div class="address-top">{{ address.mainAddress }}</div>

    <div class="address-middle">
      <img :src="locationMarkImg" alt="위치아이콘" />
      <p class="distance">{{ address.distance }}</p>
      <div class="tag">{{ address.type === '지번' ? '도로명' : '지번' }}</div>
      <p class="sub-address">{{ address.subAddress }}</p>
    </div>

    <div v-if="address.buildingName" class="address-bottom">
      {{ address.buildingName }}
    </div>
  </div>
</template>

<style scoped lang="scss">
.address-wrapper {
  @include custom-padding-y($padding-default);
  @include custom-padding-x($padding-small);
  display: flex;
  flex-direction: column;
  gap: $padding-small;
  cursor: pointer;

  .address-top {
    font-size: 14px;
    font-weight: 500;
  }

  .address-middle {
    display: flex;
    align-items: center;
    gap: $padding-small;

    img {
      @include custom-icon-style(16px);
    }

    .distance {
      font-size: 12px;
      color: #969696;
    }

    .tag {
      font-size: 12px;
      color: #969696;
      padding: 0 6px;
      border-radius: 4px;
      border: 1px solid #ccc;
    }

    .sub-address {
      font-size: 12px;
      color: #969696;
    }
  }

  .address-bottom {
    font-size: 13px;
    color: #6c6c6c;
  }
}
</style>
