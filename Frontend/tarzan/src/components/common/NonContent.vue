<template>
  <div class="non-content">
    <p>
      <i class="bi bi-info-circle"> </i>
      {{ props.value }}
    </p>
  </div>
</template>

<script setup lang="ts">
import { defineProps } from "vue";
const props = defineProps({
  value: {
    type: String,
    required: true,
  },
});
</script>

<style lang="scss" scoped>
.non-content {
  display: flex;
  justify-content: center; /* 가로 중앙 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
  border-top: 1px solid #d9d9d9;
  min-height: 140px;

  p {
    @include custom-text-description(
      $font-color: $text-color-light,
      $font-size: 12px
    );
  }
}
</style>
