<template>
  <div class="tab-container">
    <div class="tab-titles">
      <!-- 탭 버튼들을 동적으로 생성 -->
      <button
        v-for="(tab, index) in tabs"
        :key="index"
        @click="selectTab(index)"
        :class="{ active: selectedTab === index }"
      >
        {{ tab.name }}
      </button>
    </div>

    <!-- 선택된 탭의 컨텐츠 -->
    <div class="tab-content">
      <component :is="currentTabComponent" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from "vue";
import { Tab } from "@/data/tabs";
// 부모로부터 받아온 options
const props = defineProps({
  tabs: {
    type: Array as () => Tab[],
    required: true,
  },
});

// 상태 변수
const selectedTab = ref<number>(0); // 초기값: 첫 탭

// 선택된 탭의 컴포넌트 계산
const currentTabComponent = computed(() => {
  return props.tabs[selectedTab.value].component;
});

// 선택된 탭을 변경하는 메소드
const selectTab = (index: number) => {
  selectedTab.value = index;
  console.log(props.tabs[index]);
};
</script>

<style lang="scss" scoped>
.tab-container {
  width: 100%;
  flex: 1;
  background-color: aqua;
}
</style>
