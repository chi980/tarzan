<template>
  <div class="tag-button-container">
    <button 
      v-for="button in buttons" 
      :key="button.value"
      class="tag-button"
      :class="{ active: multiple ? selectedButtons.includes(button.value) : selectedButton === button.value }"
      @click="handleClick(button.value)">
      <slot :button="button">{{ button.label }}</slot>
    </button>
  </div>
</template>

<script>
// import TagButton from "@/components/common/TagButton.vue";
import { ref, defineProps, defineEmits } from "vue";

export default {
  props: {
    selectedType: String, // Home.vue에서 전달받는 선택된 타입
  },
  data() {
    return {
      selectedButton: this.selectedType || null, // 초기값을 selectedType과 동기화
      buttons: [
        { label: "매물", value: "HOUSE" },
        { label: "주민센터", value: "CIVIC_CENTER" },
        { label: "체육관", value: "GYM" },
        { label: "공원", value: "PARK" },
        { label: "병원", value: "HOSPITAL" },
        { label: "약국", value: "PHARMACY" },
        { label: "의원", value: "MEDICAL_CLINIC" },
        { label: "CCTV", value: "CCTV" },
        { label: "경찰서", value: "POLICE" },
        { label: "편의점", value: "CONVENIENCE_STORE" },
        { label: "마트", value: "MART" },
        { label: "지하철", value: "SUBWAY" },
        { label: "버스정류장", value: "BUS" },
      ],
    };
  },
  watch: {
    selectedType(newVal) {
      this.selectedButton = newVal; // 부모에서 변경된 값 반영
    },
  },
  methods: {
    handleClick(value) {
      this.selectedButton = value;
      this.$emit("update:selectedType", value); // v-model을 위한 이벤트 발생
    },
  },
};

</script>

<style scoped lang="scss">
.tag-button-container {
  display: flex;
  flex-wrap: nowrap;
  padding: 20px;
  overflow-x: scroll;
  gap: 5px;

  /* 스크롤바 숨기기 */
  &::-webkit-scrollbar {
    display: none;
  }
}

.tag-button {
  background: white;
  border-radius: 20px;
  border: 1px solid #E5E5E5;
  font-size: 12px;
  font-weight: 600;
  height: 40px;
  white-space: nowrap;
  padding: 13px 14px;
  cursor: pointer;
  &:focus {
    outline: none;
  }
}
.tag-button.active {
  background-color: $primary-color-50;
  border: 1.2px solid $primary-color-default;
  color: $primary-color-default;
}
</style>
