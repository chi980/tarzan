<template>
  <div class="button-wrapper">
    <div class="button-default" @click="props.onClick">
      {{ props.label }}
    </div>
  </div>
</template>

<script setup lang="ts">
const props = defineProps<{
  label: string;
  onClick: () => void;
}>();
</script>

<style scoped lang="scss">
.button-wrapper {
  width: 100%;
  padding-bottom: 8px;
  display: flex;
  flex-direction: row;
}
.button-default {
  @include custom-margin-x;
  @include custom-button-style(
    $bg-color: $secondary-color-default,
    $font-color: white
  );
  flex: 1;
  color: white !important;
}
</style>
