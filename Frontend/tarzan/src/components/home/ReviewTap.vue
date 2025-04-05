<script setup lang="ts">
import { ref } from "vue";
import { Review } from "@/data/house";

const props = defineProps<{
  reviews: Review[];
}>();
</script>

<template>
  <div class="component">
    <h1>{{ props.reviews }}</h1>
  </div>
</template>

<style scoped lang="scss">
.component {
  background-color: yellow;
  h1 {
    color: blue;
  }
}
</style>
