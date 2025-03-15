<template>
  <div class="house-item-container" @click="handleClick">
    <div class="house-content">
    <div class="house-meta">
      <h3 id="house_name">{{ house.house_name }}</h3>
      <p id="house_category">{{ house.house_category }}</p>
      </div>
      <p id="house_address">{{ house.house_address }}</p>
    </div>
  </div>
</template>

<script>

export default {
  props: {
    house: Object,  // 부모 컴포넌트로부터 house라는 객체를 props로 받음
  },
  methods: {
    handleClick() {
      this.$emit('click');
    },
  },
};
</script>

<style scoped lang="scss">
.house-item-container {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  // @include custom-margin-x;
  // @include custom-margin-y($margin-size: 12px);
  margin: 5px 0 20px 0;
  gap: 8px;
  
}


.house-item-container .house-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 8px;
  width: 100%;
  text-align: left;
}

#house_name {
  @include custom-text($font-size: 14px);
  white-space: nowrap;  /* 텍스트를 한 줄로 유지 */
  overflow: hidden;  /* 내용이 넘치면 숨김 */
  text-overflow: ellipsis;  /* 넘치는 텍스트를 말줄임표로 표시 */
}

#house_category {
  font-size: 12px;
  color: #9F9F9F;
  white-space: nowrap;  /* 텍스트를 한 줄로 유지 */
  overflow: hidden;  /* 내용이 넘치면 숨김 */
  text-overflow: ellipsis;  /* 넘치는 텍스트를 말줄임표로 표시 */
}

#house_address {
  font-size: 12px;
  color: #9F9F9F;
  width:100%;
  white-space: nowrap;  /* 텍스트를 한 줄로 유지 */
  overflow: hidden;  /* 내용이 넘치면 숨김 */
  text-overflow: ellipsis;  /* 넘치는 텍스트를 말줄임표로 표시 */
}

.house-item-container .house-meta {
  align-items: flex-end;
  display:flex;
  gap: 8px;
  font-size: 12px;
  color: #9F9F9F;
}
</style>