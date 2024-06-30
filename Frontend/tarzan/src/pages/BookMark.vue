<template>
  <div class="sub-container">
    <TopBar></TopBar>
    <div class="center-container">
      <h1>ㅇ러낭리;</h1>
      <h1>중앙 센터입니다.</h1>
    </div>
    <BottomBar></BottomBar>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import TopBar from "@/components/common/TopBar.vue";
import BottomBar from "@/components/common/BottomBar.vue";
</script>

<style lang="scss" scoped>
.sub-container {
  justify-content: space-between;
}

.center-container {
  height: 100%;
  width: 100%;
  background-color: white;
}
</style>
