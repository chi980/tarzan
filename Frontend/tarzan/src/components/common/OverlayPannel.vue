<template>
  <div
    class="overlay-panel"
    ref="panelRef"
    :style="{ height: panelHeight + 'px' }"
    @mousedown="startDrag">
    <div class="indicator-wrapper" ref="indicatorWrapper">
      <div class="indicator"></div>
    </div>
    <div class="overlay-slot-wrapper">
      <slot></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import {
  ref,
  defineProps,
  defineEmits,
  onMounted,
  onUnmounted,
  nextTick,
  watch,
} from "vue";

const props = defineProps({
  minHeight: { type: Number, default: 0 },
  midHeight: { type: Number, required: true },
  maxHeight: { type: Number, required: true },
  initialHeight: { type: Number, required: true },
  type: { type: String as PropType<"full" | "small">, default: "full" },
  isStretch: { type: Boolean, default: false }, // 부모로부터 isStretch 받기
});
const emit = defineEmits<{
  (event: "updateIsStretch", value: boolean): void;
}>();

// 내부 상태
const baseHeight = ref(props.initialHeight); // min/mid/max 중 하나
const indicatorHeight = ref(0);
const panelHeight = ref(baseHeight.value); // 실제 표시될 높이 (base + indicator)

// 현재 단계
const currentState = ref<"min" | "mid" | "max">("min");

// 드래그 관련
const isDragging = ref(false);
const startY = ref(0);
const threshold = 30;

// 요소 참조
const indicatorWrapper = ref<HTMLDivElement | null>(null);

// 드래그 시작
const startDrag = (e: MouseEvent) => {
  isDragging.value = true;
  startY.value = e.clientY;
  window.addEventListener("mousemove", onDrag);
  window.addEventListener("mouseup", stopDrag);
};
const onDrag = (e: MouseEvent) => {
  if (!isDragging.value) return;

  const diff = e.clientY - startY.value;

  if (diff <= -threshold) {
    // 드래그 위로
    if (props.type === "small") {
      // 바로 max로
      if (currentState.value === "min") {
        baseHeight.value = props.maxHeight;
        currentState.value = "max";
        startY.value = e.clientY;
      }
    } else {
      if (currentState.value === "min") {
        baseHeight.value = props.midHeight;
        currentState.value = "mid";
        startY.value = e.clientY;
      } else if (currentState.value === "mid") {
        baseHeight.value = props.maxHeight - indicatorHeight.value;
        currentState.value = "max";
        startY.value = e.clientY;
      }
    }
  } else if (diff >= threshold) {
    // 드래그 아래로
    if (props.type === "small") {
      // 바로 min으로
      if (currentState.value === "max") {
        baseHeight.value = props.minHeight;
        currentState.value = "min";
        startY.value = e.clientY;
      }
    } else {
      if (currentState.value === "max") {
        baseHeight.value = props.midHeight;
        currentState.value = "mid";
        startY.value = e.clientY;
      } else if (currentState.value === "mid") {
        baseHeight.value = props.minHeight;
        currentState.value = "min";
        startY.value = e.clientY;
      }
    }
  }

  updatePanelHeight();
};

// 드래그 끝
const stopDrag = () => {
  isDragging.value = false;
  window.removeEventListener("mousemove", onDrag);
  window.removeEventListener("mouseup", stopDrag);
};

// 높이 갱신
const updatePanelHeight = () => {
  panelHeight.value = baseHeight.value + indicatorHeight.value;
};

// indicator height 감지
let observer: ResizeObserver | null = null;

onMounted(async () => {
  await nextTick();

  if (indicatorWrapper.value) {
    indicatorHeight.value = indicatorWrapper.value.clientHeight;
    updatePanelHeight();

    observer = new ResizeObserver(() => {
      indicatorHeight.value = indicatorWrapper.value?.clientHeight ?? 0;
      updatePanelHeight();
    });
    observer.observe(indicatorWrapper.value);
  } else {
    updatePanelHeight();
  }
});

onUnmounted(() => {
  if (observer && indicatorWrapper.value) {
    observer.unobserve(indicatorWrapper.value);
    observer.disconnect();
  }
  window.removeEventListener("mousemove", onDrag);
  window.removeEventListener("mouseup", stopDrag);
});

// props가 바뀌었을 때 반영
watch(
  () => props.initialHeight,
  (val) => {
    baseHeight.value = val;
    updatePanelHeight();
  }
);

watch(
  () => props.maxHeight,
  (val) => {
    if (currentState.value === "max") {
      if (props.type === "full") {
        baseHeight.value = val - indicatorHeight.value;
      } else {
        baseHeight.value = val;
      }
      updatePanelHeight();
    }
  }
);
// watch로 isStretch 상태 변경 시 처리
watch(
  () => props.isStretch,
  (val) => {
    nextTick(() => {
      console.log(props.type);
      if (val) {
        if (props.type === "full") {
          baseHeight.value = props.midHeight;
          currentState.value = "mid";
        } else {
          baseHeight.value = props.maxHeight;
          currentState.value = "max";
        }
      } else {
        baseHeight.value = props.minHeight;
        currentState.value = "min";
      }
      updatePanelHeight(); // 패널 높이 갱신
    });
  }
);

const panelRef = ref<HTMLElement | null>(null);

// 외부 클릭 시 접기
const onClickOutside = (e: MouseEvent) => {
  const clickedOutside =
    panelRef.value && !panelRef.value.contains(e.target as Node);
  if (clickedOutside) {
    baseHeight.value = props.minHeight;
    currentState.value = "min";
    updatePanelHeight();

    // 부모 컴포넌트로 isStretch 상태 전달
    emit("updateIsStretch", false);
  }
};

onMounted(() => {
  window.addEventListener("mousedown", onClickOutside);
});

onUnmounted(() => {
  window.removeEventListener("mousedown", onClickOutside);
});
</script>

<style scoped lang="scss">
.overlay-panel {
  display: flex;
  flex-direction: column;
  width: 100%;
  cursor: grab;
  transition: height 0.3s ease;
}

.indicator-wrapper {
  background-color: white;
  border-top-left-radius: $border-radius-default;
  border-top-right-radius: $border-radius-default;
  @include custom-padding-y($padding-small);
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: grab;
  height: fit-content;

  .indicator {
    width: 134px;
    height: 4px;
    border-radius: 100px;
    background-color: #e8e8e8;
  }
}

.overlay-slot-wrapper {
  flex: 1;
  overflow: auto;
  background-color: white;
}
</style>
