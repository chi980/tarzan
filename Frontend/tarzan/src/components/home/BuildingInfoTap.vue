<script setup lang="ts">
import { ref } from "vue";
import { Index } from "@/data/house";

const props = defineProps<{
  indexes: Index[];
}>();
</script>

<template>
  <div class="component">
    <h1>{{ indexes }}</h1>
  </div>
</template>

<style scoped lang="scss">
.component {
  background-color: greenyellow;
  h1 {
    color: blue;
  }
}
</style>
