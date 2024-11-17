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
    <div class="accordion-contents" v-if="canSeeContent">
      <div
        v-for="accordionContent in props.accordionContents"
        :key="accordionContent.idx"
        class="accordion-collapse"
      >
        <div
          class="accordion-subtitle-wrapper"
          :class="{ active: !accordionContent.canSee }"
          @click="toggleSubAccordion(accordionContent.idx)"
        >
          <div class="accordion-subtitle">
            <p>
              {{ accordionContent.title }}
            </p>
            <img :src="starEmoji" alt="star" v-if="accordionContent.idx == 1" />
          </div>
          <img
            :src="arrowDownSrc"
            alt="arrowDown"
            class="input-item-image"
            :class="{ rotated: accordionContent.isRotated, rotate: true }"
          />
        </div>
        <div class="accordion-sub-content" v-if="accordionContent.canSee">
          <CheckListItem
            v-for="checkItem in accordionContent.contents"
            :key="checkItem.idx"
            :checkListItem="checkItem"
          />
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
// @ts-ignore
import { ref, defineProps, defineEmits } from "vue";
// @ts-ignore
import arrowDownSrc from "@/assets/icons/Arrows-chevron/Arrow-Down/Style=Outlined.svg";

// @ts-ignore
import starEmoji from "@/assets/emoji/Emoji-Star.png";

// @ts-ignore
import { CheckList } from "@/data/check";

// @ts-ignore
import CheckListItem from "./CheckListItem.vue";

const props = defineProps({
  accordionTitle: {
    type: String,
    required: true,
  },
  accordionContents: {
    type: Array as () => CheckList[],
    required: true,
  },
});

// Emit 정의
const emit = defineEmits<{
  (event: "toggleAccordion"): void;
  (event: "toggleSubAccordion", idx: number): void;
}>();

// 화살표 상태 관리
const isRotated = ref<boolean>(false); // boolean 타입 지정

// 콘텐츠 상태 관리
const canSeeContent = ref<boolean>(false); // boolean 타입 지정

const controllAccordion = () => {
  isRotated.value = !isRotated.value;
  canSeeContent.value = !canSeeContent.value;
  emit("toggleAccordion");
};

const toggleSubAccordion = (idx: number) => {
  emit("toggleSubAccordion", idx);
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

  .accordion-contents {
    display: flex;
    flex-direction: column;
    gap: $padding-small;
  }

  .accordion-collapse {
    display: flex;
    flex-direction: column;
    gap: $padding-small;
    border: #e5e5e5 solid 1px;
    border-radius: $border-radius-default;
    overflow: hidden;
  }
  .accordion-subtitle-wrapper {
    @include custom-padding;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    border-bottom: #e5e5e5 solid 1px;

    .accordion-subtitle {
      display: flex;
      flex-direction: row;
      justify-content: flex-start; /* 수평으로 왼쪽 정렬 */
      align-items: center; /* 수직으로 중앙 정렬 */
      gap: 6px;

      img {
        @include custom-icon-style($size: 12px);
      }
    }
    p {
      @include custom-text($font-size: 14px);
    }

    img {
      @include custom-icon-style;
    }

    &.active {
      border: none;
    }
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
</style>
