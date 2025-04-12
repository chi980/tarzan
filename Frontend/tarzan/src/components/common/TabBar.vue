<template>
  <div class="tab-container">
    <div class="tab-titles">
      <div
        v-for="(tab, index) in tabs"
        :key="index"
        @click="selectTab(index)"
        :class="['tab-title', { active: selectedTabIdx === index }]">
        <p>{{ tab.name }}</p>
        <Transition name="indicator">
          <div class="tab-indicator" v-if="selectedTabIdx === index"></div>
        </Transition>
      </div>
    </div>
    <!-- 선택된 탭의 컨텐츠 -->
    <div class=".tab-content-wrapper">
      <component
        :is="currentTabComponent"
        v-bind="tabs[selectedTabIdx]?.props ?? {}" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, defineEmits } from "vue";
import { Tab } from "@/data/tabs";
// 부모로부터 받아온 options
const props = defineProps({
  tabs: {
    type: Array as () => Tab[],
    required: true,
  },
  selectedTabIdx: Number,
});

// 상태 변수
// const selectedTabIdx = ref<number>(0); // 초기값: 첫 탭

// 선택된 탭의 컴포넌트 계산
const currentTabComponent = computed(() => {
  return props.tabs[props.selectedTabIdx]?.component;
});

// 선택된 탭을 변경하는 메소드
const selectTab = (index: number) => {
  // selectedTabIdx.value = index;
  // console.log(props.tabs[index]);

  emit("update:selectedTabIdx", index);
};

const emit = defineEmits<{
  (e: "update:selectedTabIdx", value: number): void;
}>();
</script>

<style lang="scss" scoped>
.tab-container {
  width: 100%;
  flex: 1;
  display: flex;
  flex-direction: column;
}
.tab-titles {
  @include custom-padding-x;
  display: flex;
  flex-direction: row;
  gap: $padding-default;
}
.tab-title {
  @include custom-none-select-basic;
  @include custom-padding-x(6px);
  @include custom-text($font-size: 12px, $font-color: $text-color-light);
  display: flex;
  flex-direction: column;
  justify-content: center; /* 가로축 중앙 정렬 */
  align-items: center; /* 세로축 중앙 정렬 */

  position: relative;
  flex: 1;
  height: 48px;

  &.active {
    color: $text-color-default;
  }
}
.tab-indicator {
  position: absolute; /* tab-title의 기준으로 절대 위치 */
  bottom: 0; /* tab-title의 아래쪽에 위치 */
  left: 0; /* 중앙에서 시작 */
  width: 100%; /* 부모 요소의 전체 너비를 차지 */
  height: 1px; /* 인디케이터 높이 */
  background-color: black;
}

.indicator-enter-active {
  transition: width 0.3s ease, left 0.3s ease;
}
.indicator-enter-from {
  width: 0%;
  left: 50%;
}
</style>
<!-- animation 적용 -->
<!-- https://codesandbox.io/s/taeb1-nkmqr?file=/src/App.vue -->
<!-- https://codesandbox.io/s/taeb2-c4q5j?file=/src/App.vue -->
