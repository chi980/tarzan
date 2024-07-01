<template>
  <div class="tab-container">
    <!-- <div
      v-for="(tab, index) in tabs"
      :key="tab.name"
      :class="['tab', { active: activeTab === index }]"
      @click="selectTab(index)"
    >
      {{ tab.name }}
    </div>
    <div class="tab-content">
      <component :is="activeTabComponent"></component>
    </div> -->

    <div class="tab-titles">
      <button
        v-for="(tab, index) in tabs"
        :key="index"
        @click="selectTab(index)"
        :class="{ active: selectedTab === index }"
      >
        {{ tab.title }}
      </button>
    </div>
    <div class="tab-content">
      <p>{{ tabs[selectedTab].content }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, defineProps } from "vue";

// Tab 타입 정의
// interface Tab {
//   name: string;
//   component: any;
// }
interface Tab {
  title: string;
  content: string;
}
// Props 정의
const props = defineProps({
  tabs: {
    type: Array as () => Tab[],
    required: true,
  },
});

// 상태 변수
const activeTab = ref<number>(0);

// 선택된 탭을 변경하는 메소드
const selectTab = (index: number) => {
  activeTab.value = index;
};

// 현재 선택된 탭의 컴포넌트
// const activeTabComponent = computed(
//   () => props.tabs[activeTab.value].component
// );
</script>

<style lang="scss" scoped>
.tab-container {
  width: 100%;
  flex: 1;
  background-color: aqua;
}
</style>
