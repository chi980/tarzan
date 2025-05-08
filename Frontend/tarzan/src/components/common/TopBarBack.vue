<!-- 
1. 기본 동작(router.back)만 사용하고 싶을 때
<TopBarBack title="페이지 제목" />

2. 커스텀 뒤로가기 동작을 사용하고 싶을 때
<TopBarBack 
  title="페이지 제목" 
  :useCustomBack="true" 
  @back="myCustomBackFunction" 
/> 
-->

<template>
  <div class="topbar">
    <img
      id="back-button"
      src="@/assets/icons/Filter/back-icon.png"
      alt="back-button"
      @click="onBackClick" />
    <div class="title">{{ title }}</div>
    <slot></slot>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";
import router from "@/router";

const emit = defineEmits(["back"]);

const props = defineProps({
  title: {
    type: String,
    required: true,
  },
  useCustomBack: {
    type: Boolean,
    default: false,
  },
});

function onBackClick() {
  emit("back");

  // 커스텀 뒤로가기가 설정되지 않은 경우에만 router.back() 실행
  if (!props.useCustomBack) {
    router.back();
  }
}
</script>

<style lang="scss" scoped>
.topbar {
  background-color: white;
  display: flex;
  align-items: center;
  width: 100%;
  height: 60px;
  @include custom-text($font-weight: 800, $font-size: 16px);
  @include custom-padding-x;
  box-sizing: border-box;
}

.title {
  @include custom-text($font-size: 15px);
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  font-weight: bold;
}

#back-button {
  width: 16px;
}
</style>
