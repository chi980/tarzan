<template>
  <div>
    <!-- 클릭 이벤트를 바인딩할 div -->
    <div @click="togglePopup" class="popup-trigger">클릭해서 팝업 열기</div>

    <!-- 팝업 컨텐츠 -->
    <div v-if="isPopupOpen" class="popup">
      <!-- 팝업 내용 -->
      <p>팝업 내용입니다.</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isPopupOpen: false,
    };
  },
  methods: {
    togglePopup() {
      this.isPopupOpen = !this.isPopupOpen;
    },
  },
};
</script>

<style scoped>
.popup-trigger {
  cursor: pointer;
}

.popup {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  margin: 0 16px; /* 좌우 여백 설정 */
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  padding: 10px;
  z-index: 1000; /* 다른 요소 위에 나타나도록 설정 */
}
</style>
