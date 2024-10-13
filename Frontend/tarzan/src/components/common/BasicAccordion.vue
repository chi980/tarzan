<template>
  <div class="custom-accordion-item">
    <div class="accordion-header" @click="controllAccordion">
      <p>{{ props.accordionTitle }}</p>

      <img
        :src="arrowDownSrc"
        alt="arrowDown"
        class="input-item-image"
        :class="{ rotated: isRotated, rotate: true }"
      />
    </div>
    <div class="accordion-collapse" v-if="canSeeContent">
      <slot></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
// @ts-ignore
import { ref, defineProps } from "vue";

// @ts-ignore
import arrowDownSrc from "@/assets/icons/Arrows-chevron/Arrow-Down/Style=Outlined.svg";

const props = defineProps({
  accordionTitle: {
    type: String,
    required: true,
  },
});

// 화살표 상태 관리
// @ts-ignore
const isRotated = ref(false);

// 콘텐츠 상태 관리
// @ts-ignore
const canSeeContent = ref(false);

// @ts-ignore
const controllAccordion = () => {
  isRotated.value = !isRotated.value;
  canSeeContent.value = !canSeeContent.value;
};
</script>

<style lang="scss" scoped>
// 공통
.rotate {
  transition: transform 0.5s ease;
}

.rotated {
  transform: rotate(180deg);
}

// scoped
.custom-accordion-item {
  @include custom-padding-x;
  display: flex;
  flex-direction: column;
  gap: $padding-small;

  .accordion-header {
    @include custom-padding-x;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;

    height: 52px;
    border-radius: $border-radius-default;
    background-color: #f8f8f8;
    p {
      @include custom-text($font-weight: 600);
    }
    img {
      @include custom-icon-style;
    }
  }

  .accordion-collapse {
    display: flex;
    flex-direction: column;
    gap: $padding-small;
    background-color: white;

    border: #e5e5e5 solid 1px;
    border-radius: $border-radius-default;
    overflow: hidden;
  }
}

.accordion-display-enter-active,
.accordion-display-leave-active {
  transition: opacity 0.3s ease;
}
.accordion-display-enter-from,
.accordion-display-leave-to {
  opacity: 0; // 등장하기 시작, 퇴장의 마지막은 투명도 0
}

.slide-enter-active,
.slide-leave-active {
  transition: height 0.5s ease;
}

.slide-enter-from,
.slide-leave-to {
  height: 0;
}

.slide-enter-to,
.slide-leave-from {
  height: auto;
}
</style>
