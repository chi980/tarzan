<script setup lang="ts">
import { ref } from "vue";

const message = ref("Hello Vue!");
</script>

<template>
  <div class="component">
    <h1>{{ message }}</h1>
  </div>
</template>

<style scoped lang="scss">
.component {
  h1 {
    color: blue;
  }
}
</style>
