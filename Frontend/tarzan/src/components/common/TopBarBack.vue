<template>
  <div class="topbar">
    <img
      id="back-button"
      src="@/assets/icons/Filter/back-icon.png"
      alt="back-button"
      @click="onBackClick"
    />
    <div class="title">{{ title }}</div>
    <slot></slot>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

const emit = defineEmits(['back']);

const props = defineProps({
  title: {
    type: String,
    required: true,
  },
});

function onBackClick() {
  emit('back');
}

// function goBack() {
//   router.go(-1);
// }
</script>

<style lang="scss" scoped>
.topbar {
  background-color: yellowgreen;
  display: flex;
  align-items: center;
  width: 100%;
  height: 60px;
  @include custom-text($font-weight: 800, $font-size: 16px);
  @include custom-padding-x;
  box-sizing: border-box;
}

.title {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  font-size: 18px;
  font-weight: bold;
}

#back-button {
  width: 16px;
}
</style>
