<template>
  <div class="tab-container">
    <div class="tab-title">
    <TopBarBackHome title="추가하기" />
    </div>
  </div>
</template>

<script setup lang="ts">
import TopBarBackHome from "@/components/common/TopBarBackHome.vue"


</script>

<style lang="scss" scoped>
.tab-container {
  width: 100%;
  background: white;
  flex: 1;
}
.tab-title {
  @include custom-text($font-weight: 800, $font-size: 16px);
  display: flex;
  flex-direction: column;
  justify-content: center; /* 가로축 중앙 정렬 */
  align-items: center; /* 세로축 중앙 정렬 */
  min-width: 50px;
  height: 48px;
  margin: 8px;
}

#back-button {
  width: 16px;
}
</style>


