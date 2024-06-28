<template>
  <div class="select-container">
    <select v-model="selectedOption" class="custom-select">
      <!-- <option disabled value="">Choose an option</option> -->
      <option v-for="option in options" :key="option.idx" :value="option.idx">
        {{ option.name }}
      </option>
    </select>
    <p v-if="selectedOption">
      Selected:
      {{ options.find((option) => option.id === selectedOption)?.name }}
    </p>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { Option } from "@/data/options";
import arrowDownSrc from "@/assets/icons/Arrows-chevron/Arrow-Down/Style=Outlined.svg";

// 부모로부터 받아온 options
const props = defineProps({
  options: {
    type: Array as () => Option[],
    required: true,
  },
});

// 선택된 옵션 상태
const selectedOption = ref<Option | null>(null);

// 선택된 옵션 변경 시 로직
const handleChange = () => {
  console.log("Selected option:", selectedOption.value);
};
</script>

<style scoped lang="scss">
/* 기본 스타일 제거 및 커스텀 스타일 적용 */
.select-container select {
  @include custom-text;
  @include custom-padding-x;
  @include custom-margin-input;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  width: 100%;
  height: 48px;
  border: none;
  border-radius: $border-radius-default;
  background-color: $input-color;
  /* 화살표 이미지 설정 */
  background-image: url("@/assets/icons/Arrows-chevron/Arrow-Down/Style=Outlined.svg");
  background-size: 18px;
  background-repeat: no-repeat;
  background-position: right 18px center; /* 이미지를 오른쪽 끝에 위치시킴 */
}
/* 활성화된 상태 */
.select-container select:focus {
  outline: none;
}

.select-container option {
  @include custom-text;
}
/* 스크롤바 전체 영역 */
::-webkit-scrollbar {
  width: 4px; /* 세로축 스크롤바 폭 너비 */
  height: 100%; /* 가로축 스크롤바 폭 너비 */
}
::-webkit-scrollbar-button {
  display: none;
}
/* 스크롤바 막대 제외 부분 */
::-webkit-scrollbar-track {
  background: none;
  margin: 8px;
}
/* 스크롤바 막대 */
::-webkit-scrollbar-thumb {
  border-radius: $border-radius-default;
  background: #d9d9d9;
}
</style>
