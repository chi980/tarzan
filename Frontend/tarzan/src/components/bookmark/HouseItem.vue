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

<script setup lang="ts">
import { defineProps, defineEmits } from "vue";

const props = defineProps({
  house: Object, // House object passed from parent
});

const emit = defineEmits(["navigate"]);

const handleClick = () => {
  const house = props.house; // Get the house object from props
  if (house && house.bookmarkIdx) {
    // navigateToCheckCostPage 호출 등
    emit("navigate", house); // Emit the 'navigate' event with the house object
  } else {
    console.error("bookmarkIdx가 없습니다.");
  }
};
</script>

<style scoped lang="scss">
.house-item-container {
  @include custom-padding-y($padding-small);
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 8px;
  background-color: aqua;
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
  white-space: nowrap; /* 텍스트를 한 줄로 유지 */
  overflow: hidden; /* 내용이 넘치면 숨김 */
  text-overflow: ellipsis; /* 넘치는 텍스트를 말줄임표로 표시 */
}

#house_category {
  font-size: 12px;
  color: #9f9f9f;
  white-space: nowrap; /* 텍스트를 한 줄로 유지 */
  overflow: hidden; /* 내용이 넘치면 숨김 */
  text-overflow: ellipsis; /* 넘치는 텍스트를 말줄임표로 표시 */
}

#house_address {
  font-size: 12px;
  color: #9f9f9f;
  width: 100%;
  white-space: nowrap; /* 텍스트를 한 줄로 유지 */
  overflow: hidden; /* 내용이 넘치면 숨김 */
  text-overflow: ellipsis; /* 넘치는 텍스트를 말줄임표로 표시 */
}

.house-item-container .house-meta {
  align-items: flex-end;
  display: flex;
  gap: 8px;
  font-size: 12px;
  color: #9f9f9f;
}
</style>
