<script setup lang="ts">
import { ref, defineProps, defineEmits } from "vue";
import locationMarkImg from "@/assets/icons/location_mark.png";

const props = defineProps({ addresses: Array });
const emit = defineEmits(["selectAddress"]);

const selectAddress = (address: any) => {
  emit("selectAddress", address); // 부모 컴포넌트로 선택된 주소 전달
};

const onClick = (address) => {
  console.log("AddressSearchResult.vue: ", JSON.stringify(address));
  selectAddress(address); // 클릭된 address를 selectAddress로 전달
};
</script>

<template>
  <div
    v-for="(address, index) in addresses"
    :key="index"
    class="location-wrapper"
    @click="onClick(address)">
    <div class="location-degree">
      <img :src="locationMarkImg" alt="이미지" />
      <p class="distance">{{ address.distance }}</p>
    </div>
    <div class="location-address-wrapper">
      <p class="location-address-name">{{ address.place_name }}</p>
      <p class="location-address">{{ address.address_name }}</p>
    </div>
  </div>
</template>

<style scoped lang="scss">
.location-wrapper {
  @include custom-text;
  @include custom-padding-y($padding-default);
  @include custom-padding-x($padding-small);

  display: flex;
  flex-direction: row;
  gap: $padding-default;
  align-items: center; /* 아이템을 수직 중앙에 정렬 */

  .location-degree {
    img {
      @include custom-icon-style(24px);
    }
    p.distance {
      font-family: "Ownglyph_ParkDaHyun", sans-serif;
      font-size: 12px;
      color: #969696;
      min-width: 50px; /* 일정 너비 설정 */
      text-align: center; /* 텍스트 중앙 정렬 */
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis; /* 너무 긴 텍스트는 '...'으로 표시 */
    }
  }

  .location-address-wrapper {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: $padding-small;

    @include custom-text($font-size: 12px, $font-color: $text-color-light);
    text-align: left;

    .location-address-name {
      @include custom-text($font-size: 14px);
      span {
        @include custom-text($font-size: 12px, $font-color: $text-color-light);
        padding-left: $padding-small;
      }
    }
  }
}
</style>
