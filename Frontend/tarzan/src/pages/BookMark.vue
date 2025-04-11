<template>
  <div class="sub-container non-input-sub-container">
    <div class="top-bar-wrapper">
      <TopBar></TopBar>
    </div>
    <div class="center-container">
      <div>
        <DescriptionComponent
          descriptionImgSrc="/etc/Saly-26.png"
          descriptionTitle="타잔이와 함께 체크해봐요!"
          descriptionContent="집/이사업체/자취필수품<br/>이사에 대한 모든 것을 체크할 수 있어요!"
          backgroundColor="#f2ecff" />
      </div>

      <TabBar :tabs="tabs" v-model:selectedTabIdx="selectedTabIndex"></TabBar>
      <div
        class="center-container-fix-button"
        @click="tabs[selectedTabIndex].onClick">
        <p>{{ tabs[selectedTabIndex].buttonLabel }}</p>
        <img :src="tabs[selectedTabIndex].imgSrc" />
      </div>
    </div>

    <div class="bottom-bar-wrapper">
      <BottomBar></BottomBar>
    </div>
  </div>
</template>

<script lang="ts" setup>
import TopBar from "@/components/common/TopBar.vue";
import BottomBar from "@/components/common/BottomBar.vue";
import TabBar from "@/components/common/TabBar.vue";
import { Tab } from "@/data/tabs";
import { checkListData as originalData } from "@/data/bookmark/newItemTab";

import HouseTap from "@/components/bookmark/HouseTap.vue";
import MoverTap from "@/components/bookmark/MoverTap.vue";
import ItemTap from "@/components/bookmark/ItemTap.vue";
import DescriptionComponent from "@/components/common/Description.vue";

import chevronImgSrc from "@/assets/icons/chevron-right.png";
import exportImgSrc from "@/assets/icons/corner_up_arrows.png";

import { ref, watch, computed } from "vue";
import { useRoute, useRouter } from "vue-router";

import * as XLSX from "xlsx";

const exportToExcel = (data: object[], fileName: string, sheetName: string) => {
  const worksheet = XLSX.utils.json_to_sheet(data);
  const workbook = XLSX.utils.book_new();

  XLSX.utils.book_append_sheet(workbook, worksheet, sheetName);
  XLSX.writeFile(workbook, fileName);
};
const route = useRoute();
const router = useRouter();
const list = ref([]);

watch(
  () => route.query.list,
  (newHouses) => {
    console.log("북마크한 집 목록: ", newHouses);
    if (newHouses) {
      list.value = JSON.parse(newHouses as string);
    }
  },
  { immediate: true }
);
const selectedTabIndex = ref(0); // 선택된 탭 인덱스 추적

interface ExtendedTab extends Tab {
  buttonLabel: string;
  imgSrc: string;
  onClick: () => void;
}
// 탭 데이터 배열 초기화
const tabs: ExtendedTab[] = [
  {
    name: "집 선택",
    component: HouseTap,
    buttonLabel: "비교하기",
    imgSrc: chevronImgSrc,
    onClick: () => {
      router.push({ name: "BookmarkCompare" });
    },
  },
  {
    name: "이사 업체 선택",
    component: MoverTap,
    buttonLabel: "내보내기",
    imgSrc: exportImgSrc,
    onClick: () => {
      const STORAGE_KEY = "checklist-storage-mover";

      const stored = localStorage.getItem(STORAGE_KEY);
      if (!stored) {
        alert("저장된 체크리스트가 없습니다.");
        return;
      }

      const parsed = JSON.parse(stored);

      const flatList = Object.keys(parsed).flatMap((category) =>
        parsed[category].map((item: any) => ({
          카테고리: category,
          항목번호: item.idx,
          내용: item.name,
          체크여부: item.value ? "O" : "X",
        }))
      );

      exportToExcel(flatList, "이사체크리스트.xlsx", "Checklist");
    },
  },
  {
    name: "자취 필수품 선택",
    component: ItemTap,
    buttonLabel: "내보내기",
    imgSrc: exportImgSrc,
    onClick: () => {
      // 태그명 매핑
      const mainTagMap = {
        homeAppliances: "가전",
        fabrics: "가구ㆍ패브릭",
        bathroomSupplies: "욕실 용품",
        ingredients: "필수 식재료",
        kitchenUtensils: "주방 용품",
        householdGoods: "생활 용품",
      };

      const subTagMap = {
        BEFO_MOVE: "이사 전",
        AFTER_MOVE: "이사 후",
      };

      // 원본 데이터 전체 import
      // ⚠️ 실제로는 @/data/bookmark/newItemTab에서 가져와야 함
      const result: {
        메인태그: string;
        서브태그: string;
        항목명: string;
        체크여부: string;
      }[] = [];

      // 전 항목 순회
      Object.entries(originalData).forEach(([mainKey, subMap]) => {
        Object.entries(subMap).forEach(([subKey, items]) => {
          const storageKey = `checklist-${mainKey}-${subKey}`;
          const stored = localStorage.getItem(storageKey);
          let storedMap: Record<number, boolean> = {};

          if (stored) {
            try {
              const parsed = JSON.parse(stored);
              parsed.forEach((item: any) => {
                storedMap[item.idx] = item.value;
              });
            } catch (e) {
              console.warn("저장된 데이터 파싱 실패:", storageKey);
            }
          }

          items.forEach((item: any) => {
            result.push({
              메인태그: mainTagMap[mainKey as keyof typeof mainTagMap],
              서브태그: subTagMap[subKey as keyof typeof subTagMap],
              항목명: item.name,
              체크여부: storedMap[item.idx] ? "체크" : "미체크",
            });
          });
        });
      });

      exportToExcel(result, "자취체크리스트_전체.xlsx", "Checklist");
    },
  },
];
</script>

<style lang="scss" scoped>
// 공통
.top-bar-wrapper {
  width: 100%;
}
.bottom-bar-wrapper {
  display: flex;
  justify-content: center; /* 가로 방향 중앙 정렬 */
  // height: 100px;
  width: 100%;
  z-index: $z-index-bottom-bar-wrapper;
  box-shadow: 0px -2px 4px rgba(0, 0, 0, 0.1);
}

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

.center-container-fix-button {
  @include custom-padding(12px);
  @include custom-text($font-size: 12px);
  position: sticky;
  bottom: $padding-default;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  flex-direction: row;
  gap: $padding-small;

  width: fit-content;

  border-radius: 30px;
  background: rgba(255, 255, 255, 0.7);
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);

  img {
    @include custom-icon-style(12px);
  }
}
</style>
