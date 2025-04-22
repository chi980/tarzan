<script setup lang="ts">
import { defineProps, watch, ref, computed } from "vue";
import { House, HouseDetail, Review, Tag } from "@/data/house";
import { Building } from "@/data/building";
import { Tab } from "@/data/tabs";

import shareIconImg from "@/assets/icons/Share.png";
import bookmarkIconImg from "@/assets/icons/Bookmark.svg";

import { axiosInstance } from "@/plugins/axiosPlugin";
import BuildingDetail from "@/components/home/BuildingDetail.vue";
import TabBar from "@/components/common/TabBar.vue";
import ReviewTap from "@/components/home/ReviewTap.vue";
import BuildingInfoTap from "@/components/home/BuildingInfoTap.vue";

const props = defineProps<{ house: House }>();

// 상세 정보 담을 reactive 상태
const houseDetail = ref<HouseDetail | null>(null);

const building = ref<Building | null>(null);

// 상세 정보 요청 함수
const fetchHouseDetail = async (houseId: number) => {
  try {
    const res = await axiosInstance.get(`/v1/houses/${houseId}`);
    houseDetail.value = res.data.data;
    building.value = {
      building_name: houseDetail.value.house_name,
      building_category: houseDetail.value.house_category,
      building_address: houseDetail.value.house_address,
      building_latitude: houseDetail.value.house_latitude,
      building_longitude: houseDetail.value.house_longitude,
      building_type: houseDetail.value.house_category,
    };
  } catch (err) {
    console.error("하우스 상세 정보 로딩 실패", err);
  }
};

// house prop이 변경될 때마다 요청
watch(
  () => props.house,
  async (newHouse) => {
    if (newHouse?.house_id) {
      console.log("houseId", newHouse.house_id);
      await fetchHouseDetail(newHouse.house_id);
    }
  },
  { immediate: true } // 처음 로드시에도 호출
);

const tabs = computed<Tab[]>(() => {
  if (!houseDetail.value) return [];

  return [
    {
      name: "리뷰",
      component: ReviewTap,
      props: {
        reviews: houseDetail.value.house_reviews,
        houseId: houseDetail.value.house_id,
      },
    },
    {
      name: "정보",
      component: BuildingInfoTap,
      props: {
        house: houseDetail,
      },
    },
  ];
});

const selectedTabIndex = ref(0); // 선택된 탭 인덱스 추적

const bookmarkThis = async () => {
  try {
    const response = await axiosInstance.post("/v1/bookmark", {
      house_id: houseDetail.value.house_id,
    });

    console.log("북마크 등록 성공!");
  } catch (error) {
    if (error.response && error.response.status === 400) {
      console.error("잘못된 요청입니다.");
    }
  }
};
const shareThis = () => {};
</script>

<template>
  <div v-if="props.house" class="house-detail-wrapper">
    <div>
      <BuildingDetail v-if="building" :building="building" />
      <div class="house-detail-buttons">
        <div class="button">
          <img :src="bookmarkIconImg" alt="button" @click="bookmarkThis" />
        </div>
        <div class="button">
          <img :src="shareIconImg" alt="button" @click="shareThis" />
        </div>
      </div>
    </div>
    <div class="tab-bar-wrapper" v-if="tabs.length > 0">
      <TabBar :tabs="tabs" v-model:selectedTabIdx="selectedTabIndex"></TabBar>
    </div>
  </div>
</template>

<style scoped lang="scss">
.house-detail-wrapper {
  display: flex;
  flex-direction: column;
}

.house-detail-buttons {
  @include custom-margin-x;
  display: flex;
  flex-direction: row;
  gap: $padding-small;

  .button {
    @include custom-padding($padding-small);
    border-radius: 20px;
    background-color: $primary-color-light;
    // border: 1px solid $primary-color-light;

    display: flex;
    align-items: center;
    justify-content: center;

    img {
      @include custom-icon-style(18px);
    }
  }
}

.tab-bar-wrapper {
  flex: 1;
}
</style>
