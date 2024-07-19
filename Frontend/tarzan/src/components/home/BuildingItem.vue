<template>
  <div class="building-item-container" @click="handleClick">
    <div class="building-content">
    <div class="building-meta">
      <h3 id="building-title">{{ building.title }}</h3>
      <p id="building-categoty">{{ building.categoty }}</p>
      </div>
      <p id="building-content">{{ building.content }}</p>
    </div>
  </div>
</template>

<script>

export default {
  props: {
    building: Object,  // 부모 컴포넌트로부터 building라는 객체를 props로 받음
  },
  methods: {
    handleClick() {
      this.$emit('click');
    },
  },
};
</script>

<style scoped lang="scss">
.building-item-container {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  @include custom-margin-x;
  @include custom-margin-y($margin-size: 12px);
  gap: 8px;
  
}


.building-item-container .building-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 8px;
  width: 100%;
  text-align: left;
}

#building-title {
  @include custom-text($font-size: 14px);
  white-space: nowrap;  /* 텍스트를 한 줄로 유지 */
  overflow: hidden;  /* 내용이 넘치면 숨김 */
  text-overflow: ellipsis;  /* 넘치는 텍스트를 말줄임표로 표시 */
}

#building-categoty {
  font-size: 12px;
  color: #9F9F9F;
  white-space: nowrap;  /* 텍스트를 한 줄로 유지 */
  overflow: hidden;  /* 내용이 넘치면 숨김 */
  text-overflow: ellipsis;  /* 넘치는 텍스트를 말줄임표로 표시 */
}

#building-content {
  font-size: 12px;
  color: #9F9F9F;
  width:100%;
  white-space: nowrap;  /* 텍스트를 한 줄로 유지 */
  overflow: hidden;  /* 내용이 넘치면 숨김 */
  text-overflow: ellipsis;  /* 넘치는 텍스트를 말줄임표로 표시 */
}

.building-item-container .building-meta {
  align-items: flex-end;
  display:flex;
  gap: 8px;
  font-size: 12px;
  color: #9F9F9F;
}
</style>
