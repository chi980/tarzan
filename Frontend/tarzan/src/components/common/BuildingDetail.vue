<script setup lang="ts">
import { defineProps } from "vue";

// Building 타입 정의 (필요 시 따로 인터페이스 분리해도 좋아)
interface Building {
  building_name: string;
  building_category: string;
  building_address: string;
  building_latitude: number;
  building_longitude: number;
  building_type: string;
}
const { building } = defineProps<{ building: Building }>();
</script>

<template>
  <div v-if="building" class="building-detail-wrapper">
    <div class="building-detail-header">
      <p class="building-name">{{ building.building_name }}</p>
      <p class="building-category">{{ building.building_category }}</p>
    </div>
    <p>{{ building.building_address }}</p>
  </div>
</template>

<style scoped lang="scss">
.building-detail-wrapper {
  @include custom-padding;
  @include custom-text($font-color: $text-color-light, $font-size: 12px);
  width: 100%;

  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: $padding-small;

  .building-detail-header {
    display: flex;
    flex-direction: row;
    align-items: flex-end;
    gap: $padding-small;

    .building-name {
      @include custom-text(
        $font-size: 14px,
        $font-color: $text-color-default,
        $font-weight: 500
      );
    }
  }
}
</style>
