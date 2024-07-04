<template>
  <div class="custom-accordion-item">
    <div class="accordion-header" @click="controllAccordion">
      <p>{{ accordionTitle }}</p>

      <img
        :src="arrowDownSrc"
        alt="arrowDown"
        class="input-item-image"
        :class="{ rotated: isRotated, rotate: true }"
      />
    </div>
    <div
      v-for="accordionContent in accordionContents"
      :key="accordionContent.idx"
      class="accordion-collapse"
    >
      <div
        class="accordion-subtitle"
        @click="
          controllSubAccordion(accordionContents, accordionContent.idx - 1)
        "
      >
        <p>
          {{ accordionContent.title }}
          {{ accordionContent.canSee }}
        </p>
        <img
          :src="arrowDownSrc"
          alt="arrowDown"
          class="input-item-image"
          :class="{ rotated: accordionContent.isRotated, rotate: true }"
        />
      </div>
      <div>
        <CheckListItem
          v-for="checkItem in accordionContent.contents"
          :key="checkItem.idx"
          :checkListItem="checkItem"
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, defineProps, defineEmits } from "vue";
import arrowDownSrc from "@/assets/icons/Arrows-chevron/Arrow-Down/Style=Outlined.svg";
import { CheckList, Check } from "@/data/check";
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

// 화살표 상태 관리
const isRotated = ref(false);

// 콘텐츠 상태 관리
const canSeeContent = ref(false);

const controllAccordion = () => {
  isRotated.value = !isRotated.value;
  canSeeContent.value = !canSeeContent.value;
};

const emits = defineEmits(["controllSubAccordion"]);

const controllSubAccordion = () => {
  emits("controllSubAccordion", {});
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
</style>
