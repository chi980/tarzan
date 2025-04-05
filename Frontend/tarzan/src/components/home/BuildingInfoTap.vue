<script setup lang="ts">
import { ref } from "vue";
import { Index } from "@/data/house";

const props = defineProps<{
  indexes: Index[];
}>();
</script>

<template>
  <div class="component">
    <p>{{ indexes }}</p>
  </div>
</template>

<style scoped lang="scss"></style>
