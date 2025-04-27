<template>
  <div>
    <div class="tag-button-wrapper scroll-hidden-box">
      <TagButtonGroup
        :buttons="mainTagOptions"
        v-model:selectedButton="selectedMainTag"
        :multiple="false"
      />
    </div>
    <Transition name="slide-fade">
      <div v-if="showSubTags" class="tag-button-wrapper sub scroll-hidden-box">
        <TagButtonGroup
          :buttons="subTagOptions"
          v-model:selectedButton="selectedSubTag"
          activeClass="sub-active"
          :multiple="false"
        />
      </div>
    </Transition>
    <!-- 필터링된 체크리스트 항목 -->
    <CheckListItem
      v-for="item in filteredChecklist"
      :key="`${selectedMainTag}-${item.subKey}-${item.idx}`"
      :checkListItem="item"
      @change="onChange(item)"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, computed, watch, onMounted } from "vue";
import TagButtonGroup from "@/components/common/TagButtonGroup.vue";
import CheckListItem from "@/components/common/CheckListItem.vue";
import { checkListData as originalData } from "@/data/bookmark/newItemTab";

// 메인 / 서브 태그 옵션
const mainTagOptions = ref([
  { label: "가전", value: "homeAppliances" },
  { label: "가구ㆍ패브릭", value: "fabrics" },
  { label: "욕실 용품", value: "bathroomSupplies" },
  { label: "필수 식재료", value: "ingredients" },
  { label: "주방 용품", value: "kitchenUtensils" },
  { label: "생활 용품", value: "householdGoods" },
]);

const subTagOptions = ref([
  { label: "전체", value: "ALL" },
  { label: "이사전", value: "BEFO_MOVE" },
  { label: "이사후", value: "AFTER_MOVE" },
]);

const selectedMainTag = ref(null);
const selectedSubTag = ref(null);

const showSubTags = ref(false);

watch(selectedMainTag, () => {
  showSubTags.value = true; // 메인 태그 바뀔 때마다 슬라이드다운
});

// ✅ 체크리스트 복사본 - 원본을 오염시키지 않도록 deep copy
const checkList = ref(JSON.parse(JSON.stringify(originalData)));

// ✅ localStorage 키 생성기
const getStorageKey = (main: string, sub: string) => `checklist-${main}-${sub}`;

// ✅ 저장된 상태 불러오기
const loadChecklistFromStorage = () => {
  for (const main in checkList.value) {
    for (const sub in checkList.value[main]) {
      const key = getStorageKey(main, sub);
      const stored = localStorage.getItem(key);
      if (stored) {
        try {
          checkList.value[main][sub] = JSON.parse(stored);
        } catch (e) {
          console.warn(`로컬스토리지 데이터 파싱 실패: ${key}`);
        }
      }
    }
  }
};

onMounted(loadChecklistFromStorage);

// ✅ 상태 저장 함수
const saveChecklistToStorage = (main: string, sub: string) => {
  const key = getStorageKey(main, sub);
  const data = checkList.value[main][sub];
  localStorage.setItem(key, JSON.stringify(data));
};

// 체크리스트 필터링
const filteredChecklist = computed(() => {
  const main = selectedMainTag.value?.value;
  const sub = selectedSubTag.value?.value;

  if (sub === "ALL") {
    return [
      ...(checkList.value[main]?.BEFO_MOVE ?? []).map((item) => ({
        ...item,
        subKey: "BEFO_MOVE",
      })),
      ...(checkList.value[main]?.AFTER_MOVE ?? []).map((item) => ({
        ...item,
        subKey: "AFTER_MOVE",
      })),
    ];
  } else {
    return (checkList.value[main]?.[sub] ?? []).map((item) => ({
      ...item,
      subKey: sub,
    }));
  }
});

// ✅ 체크 상태 변경 처리
function onChange(item: { idx: number; subKey: "BEFO_MOVE" | "AFTER_MOVE" }) {
  const main = selectedMainTag.value;
  const sub = item.subKey;
  const target = checkList.value[main][sub].find((el) => el.idx === item.idx);

  if (target) {
    target.value = !target.value;
    saveChecklistToStorage(main, sub); // 💾 저장
  }
}
</script>
<style lang="scss" scoped>
.tag-button-wrapper {
  @include custom-padding-x;
  @include custom-padding-y($padding-small);

  overflow-x: auto;

  &.sub {
    background-color: #fafafa;
    border-width: 1px 0px;
    border-style: solid;
    border-color: #f4f4f4;
  }
}

.scroll-hidden-box {
  overflow: auto;
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none; /* IE */
}

.scroll-hidden-box::-webkit-scrollbar {
  display: none; /* Chrome */
}

// scoped
.slide-fade-enter-active,
.slide-fade-leave-active {
  transition: all 0.3s ease;
  overflow: hidden;
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  max-height: 0;
  opacity: 0;
  transform: translateY(-10px);
}

.slide-fade-enter-to,
.slide-fade-leave-from {
  max-height: 100px; // 적절한 높이로 조정
  opacity: 1;
  transform: translateY(0);
}

:deep(.sub-active) {
  border: none;
  background-color: $secondary-color-default;
  color: white !important;
}
</style>
