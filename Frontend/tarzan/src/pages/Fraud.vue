<template>
  <div class="sub-container non-input-sub-container">
    <TopBar></TopBar>
    <div class="center-container">
      <!--중요-->
      <div>
        <DescriptionComponent
          descriptionImgSrc="/src/assets/etc/Fire_perspective.png"
          descriptionTitle="사기를 당할까봐<br/>걱정이신가요?"
          descriptionContent="점검한 집에 대해 사기 여부를<br/>확인할 수 있어요!"
          backgroundColor="#FFEDED"
        />

        <div class="content-wrapper">
          <div
            class="content-container"
            v-for="content in contents"
            :key="content.idx"
            @click="navigateToPage(content.idx)"
          >
            <p class="content-title" v-html="content.title"></p>
            <p class="content-description" v-html="content.description"></p>
          </div>
        </div>
      </div>
    </div>
    <BottomBar></BottomBar>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import TopBar from "@/components/common/TopBar.vue";
import BottomBar from "@/components/common/BottomBar.vue";
import DescriptionComponent from "@/components/common/Description.vue";
import { useRouter } from "vue-router";

interface FraudContent {
  idx: number;
  title: string;
  description: string;
}

const contents: FraudContent[] = ref([
  {
    idx: 1,
    title: "임대인 확인",
    description: "계약의 상대방이 주택 소유자 본인이 맞는지 확인",
  },
  {
    idx: 2,
    title: "공인중개사 확인",
    description: "개업 공인중개사사무소의 공인중개사인지 확인",
  },
  {
    idx: 3,
    title: "시세 확인",
    description: "해당 건물의 시세를 확인하여 깡통주택인지 확인",
  },
  {
    idx: 4,
    title: "계약 내용 확인",
    description: "작성된 계약서가 사전에 협의된 내용과 일치하는지 확인",
  },
  {
    idx: 5,
    title: "특약사항 작성",
    description: "피해를 예방하기 위한 특약사항 작성",
  },
]);

const router = useRouter();

const navigateToPage = (idx: number) => {
  if (idx === 1) {
    router.push({ name: 'CheckLandlord' });
  } else if (idx === 2) {
    router.push({ name: 'CheckRealEstateBroker' });
  } else if (idx === 3) {
    router.push({ name: 'CheckValue' });
  } else if (idx === 4) {
    router.push({ name: 'CheckContract' });
  } else {
    router.push({ name: 'CheckSpecialContract' });
  }
};

</script>

<style lang="scss" setup>
// 공통
.non-input-sub-container {
  @include custom-none-select-basic;
  justify-content: space-between;
}
.center-container {
  position: relative;
  flex-grow: 1;
  width: 100%;

  display: flex;
  flex-direction: column;

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

// scoped
.content-wrapper {
  @include custom-padding;
  display: flex;
  flex-direction: column;
  gap: $padding-default;
}
.content-container {
  @include custom-padding-x;
  @include custom-padding-y($padding-size: $padding-big);

  display: flex;
  flex-direction: column;
  align-items: flex-start; /* 수직 왼쪽 정렬 */
  justify-content: center; /* 수평 중앙 정렬 */
  gap: $padding-default;

  background-color: #f8f8f8;
  border-radius: $border-radius-default;

  p {
    text-align: left;
  }

  .content-title {
    @include custom-text($font-size: 16px, $font-weight: 700);
  }

  .content-description {
    @include custom-text($font-size: 12px);
  }
}
</style>
