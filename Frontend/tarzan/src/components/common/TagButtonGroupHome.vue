<template>
  <div class="tag-button-container">
    <TagButton
      class="tag-button"
      v-for="(button, index) in buttons"
      :key="button.value"
      :label="button.label"
      :isActive="selectedButton === index"
      @click="handleClick(button.value)"
    />
  </div>
</template>

<script>
import TagButton from "@/components/common/TagButton.vue";

export default {
  components: {
    TagButton,
  },
  props: {
    selectedType: String, // Home.vue에서 전달받는 선택된 타입
  },
  data() {
    return {
      selectedButton: null, // 선택된 버튼의 인덱스를 저장
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
  methods: {
    handleClick(value) {
      this.selectedButton = value;
      this.$emit("button-clicked", value); // 선택된 버튼의 value를 부모 컴포넌트에 전달
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
</style>
