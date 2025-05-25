<template>
  <div class="dropdown" ref="dropdownRef" @click="toggleDropdown">
    <img :src="settingIcon" alt="..." />
    <div class="dropdown-content" v-if="isDropDownOpen">
      <div
        v-for="(option, index) in props.options"
        :key="index"
        @click="handleClick(option)">
        {{ option.name }}
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, defineProps, onMounted, onUnmounted } from "vue";
import settingIcon from "@/assets/icons/topbar/icon-setting.png";

const isDropDownOpen = ref(false);
const dropdownRef = ref<HTMLElement | null>(null);

const toggleDropdown = () => {
  // 열기 전에 다른 드롭다운들 닫으라고 알림
  window.dispatchEvent(new CustomEvent("close-all-dropdowns"));
  isDropDownOpen.value = !isDropDownOpen.value;
};

// 외부에서 발생한 전역 닫기 이벤트 처리
const handleCloseEvent = () => {
  isDropDownOpen.value = false;
};

// ✅ 외부 클릭 시 닫기
const handleClickOutside = (event: MouseEvent) => {
  if (dropdownRef.value && !dropdownRef.value.contains(event.target as Node)) {
    isDropDownOpen.value = false;
  }
};
onMounted(() => {
  window.addEventListener("close-all-dropdowns", handleCloseEvent);
  document.addEventListener("click", handleClickOutside);
});

onUnmounted(() => {
  window.removeEventListener("close-all-dropdowns", handleCloseEvent);
  document.removeEventListener("click", handleClickOutside);
});

const props = defineProps<{
  options: {
    name: string;
    onClick: () => void | Promise<void>;
  }[];
}>();

const handleClick = (option: {
  name: string;
  onClick: () => void | Promise<void>;
}) => {
  const result = option.onClick?.();
  if (result instanceof Promise) {
    result.then(() => {
      isDropDownOpen.value = false;
    });
  } else {
    isDropDownOpen.value = false;
  }
};
</script>

<style lang="scss" scoped>
.dropdown {
  position: relative;
  img {
    @include custom-icon-style(16px);
  }

  .dropdown-content {
    @include custom-padding(16px);
    @include custom-text($font-size: 12px);
    position: absolute;
    right: 0;

    width: max-content;

    border-radius: 10px;
    background-color: white;
    box-shadow: 0px -1px 10px rgba(0, 0, 0, 0.05);

    div {
      display: flex;
      flex-direction: column;
      gap: $padding-default;
    }
  }
}
</style>
