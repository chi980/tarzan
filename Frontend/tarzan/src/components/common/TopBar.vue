<template>
  <div class="top-bar">
    <div class="select-content">
      <CustomSelectBox
        :options="seoulDistrictOptions"
        :parentStyle="topBarStyle"
        @update:selected="handleDistrictSelect"
      />
    </div>
  </div>
</template>
<script lang="ts" setup>
import { ref } from "vue";
import { Option } from "@/data/options";
import { SelectStyle } from "@/data/selectStyle";
import CustomSelectBox from "@/components/common/CustomSelectBox.vue";

const seoulDistrictOptions: Option[] = [
  { idx: 1, name: "서울시 종로구", value: "JONGNO" },
  { idx: 2, name: "서울시 중구", value: "JUNG" },
  { idx: 3, name: "서울시 용산구", value: "YONGSAN" },
  { idx: 4, name: "서울시 성동구", value: "SEONGDONG" },
  { idx: 5, name: "서울시 광진구", value: "GWANGJIN" },
  { idx: 6, name: "서울시 동대문구", value: "DONGDAEMUN" },
  { idx: 7, name: "서울시 중랑구", value: "JUNGNANG" },
  { idx: 8, name: "서울시 성북구", value: "SEONGBUK" },
  { idx: 9, name: "서울시 강북구", value: "GANGBUK" },
  { idx: 10, name: "서울시 도봉구", value: "DOBONG" },
  { idx: 11, name: "서울시 노원구", value: "NOWON" },
  { idx: 12, name: "서울시 은평구", value: "EUNPYEONG" },
  { idx: 13, name: "서울시 서대문구", value: "SEODAEMUN" },
  { idx: 14, name: "서울시 마포구", value: "MAPO" },
  { idx: 15, name: "서울시 양천구", value: "YANGCHEON" },
  { idx: 16, name: "서울시 강서구", value: "GANGSEO" },
  { idx: 17, name: "서울시 구로구", value: "GURO" },
  { idx: 18, name: "서울시 금천구", value: "GEUMCHEON" },
  { idx: 19, name: "서울시 영등포구", value: "YEONGDEUNGPO" },
  { idx: 20, name: "서울시 동작구", value: "DONGJAK" },
  { idx: 21, name: "서울시 관악구", value: "GWANAK" },
  { idx: 22, name: "서울시 서초구", value: "SEOCHO" },
  { idx: 23, name: "서울시 강남구", value: "GANGNAM" },
  { idx: 24, name: "서울시 송파구", value: "SONGPA" },
  { idx: 25, name: "서울시 강동구", value: "GANGDONG" },
];

const selectedDistrict = ref<string | null>(null); // 지역구 이름 저장

// emit 정의
const emit = defineEmits<{
  (e: "update:selected", district: string): void;
}>();

const handleDistrictSelect = (idx: number) => {
  const selectedOption = seoulDistrictOptions.find(option => option.idx === idx + 1);
  selectedDistrict.value = selectedOption?.value || null; 
  // console.log(`선택된 옵션의 인덱스: ${idx}`);
  console.log(`탑바: ${selectedDistrict.value}`);
  emit("update:selected", selectedDistrict.value);
};

const topBarStyle: SelectStyle = {
  backgroundColor: "white",
  fontWeight: 700,
  justifyContent: "center",
};

</script>
<style lang="scss" scoped>
.top-bar {
  @include custom-text($font-weight: 800, $font-size: 16px);
  @include custom-bar-style(
    $height: $height-top-bar,
    $z-index: $z-index-top-bar
  );
  @include custom-none-select-basic;

  justify-content: center;
  align-items: center;
}

.select-content {
  @include custom-margin-x;
  width: 100%;
}
</style>
