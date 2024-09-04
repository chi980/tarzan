<template>
  <div class="topbar">
    <img id="back-button"
         src="@/assets/icons/Filter/back-icon.png" 
         alt="back-button"
         @click="refreshPage">
    <div class="title">{{ title }}</div>
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: 'Topbar',
  props: {
    title: {
      type: String,
      required: true
    }
  },
  methods: {
    refreshPage() {
      window.location.reload(); // 현재 페이지 새로고침
    },
  }
};
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
