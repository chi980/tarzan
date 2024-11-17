<template>
  <div id="main-container">
    <router-view />
  </div>
</template>

<style scoped>
div#main-container {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  /* background-color: aliceblue; */

  position: relative;
}
</style>

<script>
export default {
  name: "App", // 컴포넌트 이름을 추가
};
</script>
