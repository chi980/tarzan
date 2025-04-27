<script setup lang="ts">
import { ref, defineProps, computed, onMounted, reactive, watch } from "vue";
import { useRouter, useRoute } from "vue-router";

import { HouseOverview } from "@/data/house";

import TopBarBackBookmark from "@/components/common/TopBarBackBookmark.vue";
import AddressCard from "@/components/common/\bAddressCard.vue";
import Step1 from "@/components/bookmark/CheckCostPage.vue";
import Step2 from "@/components/bookmark/CheckOptionPage.vue";
import Step3 from "@/components/bookmark/CheckCheckListPage.vue";
import BottomDefaultButton from "@/components/common/BottomDefaultButton.vue";
import { axiosInstance } from "@/plugins/axiosPlugin";
const router = useRouter();
const route = useRoute();

const topBarHandler = () => {
  if (step.value > 0) {
    prev();
    return;
  }
  router.back();
};

// props, 등등
const props = defineProps<{ id: string }>();
const bookmarkIdx = computed(() => {
  return Number(props.id);
});
const bookmarkHouse = ref<HouseOverview | null>(null);

onMounted(() => {
  const raw = route.query.bookmarkObj as string;
  if (raw) {
    try {
      bookmarkHouse.value = JSON.parse(raw);
    } catch (e) {
      console.error("JSON 파싱 실패", e);
    }
  }

  fetchBookmark(bookmarkIdx);
});

// 리뷰 작성 컴포넌트들
const steps = [
  {
    component: Step1,
    onClick: () => {
      console.log("step1");
    },
  },
  {
    component: Step2,
    onClick: () => {
      console.log("step2");
    },
  },
  {
    component: Step3,
    onClick: () => {
      console.log("step3");
    },
  },
];
const step = ref(0);
const prev = () => {
  if (step.value > 0) step.value--;
};
const next = () => {
  steps[step.value].onClick();
  if (step.value < steps.length - 1) step.value++;
  else router.push("/bookmark");
};

const bookmarkData = reactive({});
watch(bookmarkData, (newBookmarkData) => {
  console.log("변경됨", newBookmarkData.value);
});
const fetchBookmark = async (bookmarkIdx: number) => {
  try {
    const response = await axiosInstance.get(
      `/v1/bookmark/${bookmarkIdx.value}`
    );
    if (response.data && response.data.data) {
      bookmarkData.value = response.data.data;
      // console.log("서버에서 받아온 bookmarkData:", response.data.data);
    } else {
      new Error("잘못된 요청입니다.");
    }
  } catch (error) {
    console.error(error.message);
  }
};
</script>

<template>
  <div class="sub-container">
    <TopBarBackBookmark :title="'점검하기'"/>
    <div class="center-container">
      <div class="address-card-wrapper">
        <AddressCard v-if="bookmarkHouse" :houseOverview="bookmarkHouse" />
      </div>
      <div class="create-bookmark-tabs" v-if="bookmarkData.value">
        <component
          :is="steps[step].component"
          :bookmarkIdx="bookmarkIdx"
          v-model:bookmarkData="bookmarkData"
        />
      </div>
    </div>
    <BottomDefaultButton
      :label="step < steps.length - 1 ? '다음' : '제출'"
      :onClick="next"
    />
  </div>
</template>

<style scoped lang="scss">
.center-container {
  flex: 1;
  width: 100%;
}
.center-container {
  overflow-x: hidden;
  overflow-y: auto;
  /* 스크롤바 전체 영역 */
  &::-webkit-scrollbar {
    width: 4px; /* 세로축 스크롤바 폭 너비 */
    height: 100%; /* 가로축 스크롤바 폭 너비 */
  }
  &::-webkit-scrollbar-button {
    display: none;
  }
  /* 스크롤바 막대 제외 부분 */
  &::-webkit-scrollbar-track {
    background: transparent;
  }
  /* 스크롤바 막대 */
  &::-webkit-scrollbar-thumb {
    border-radius: calc($border-radius-default * 2);
    background: #f2f2f2;
  }
}
.padding-container {
  @include custom-padding-x;
}

//scoped
.center-container {
  @include custom-margin-y;
}
.address-card-wrapper {
  @include custom-margin-x;
}
.create-bookmark-tabs {
  // background-color: aqua;
}
</style>
