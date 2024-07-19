<template>
  <div class="address-card">
    <h1 id="title">주소</h1>
    <div class="address-container">
      <span id="building-name">한성원룸고시텔</span>
      <span id="building-address">서울 중구 서소문로 11길 8 한성빌딩 4층 (서소문동)</span>
    </div>
  </div>
</template>
<script>
export default {
  
}
</script>
<style lang="scss" scoped>
.address-card {
  background-color: $input-color;
  display: flex;
  flex-direction: column;
  gap: 12px;
  border-radius: 13px;
  text-align: left;
  @include custom-padding;
  box-sizing: border-box;
  width: 100%;
}

.address-card #title {
  @include custom-text-bold;
}

.address-container {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.address-container #building-name {
  font-size: 14px;
  font-weight: 500;
}

.address-container #building-address {
  font-size: 12px;
  font-weight: 500;
  color: #969696;
}

</style>