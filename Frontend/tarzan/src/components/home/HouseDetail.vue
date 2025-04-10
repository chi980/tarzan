<script setup lang="ts">
import { defineProps, watch, ref } from "vue";
import { House, HouseDetail, Review, Tag } from "@/data/house";
import { Building } from "@/data/building";
import { Tab } from "@/data/tabs";

import shareIconImg from "@/assets/icons/Share.png";
import bookmarkIconImg from "@/assets/icons/Bookmark.png";

import { axiosInstance } from "@/plugins/axiosPlugin";
import BuildingDetail from "@/components/home/BuildingDetail.vue";
import TabBar from "@/components/common/TabBar.vue";
import ReviewTap from "@/components/home/ReviewTap.vue";
import BuildingInfoTap from "@/components/home/BuildingInfoTap.vue";

const props = defineProps<{ house: House }>();
const house = props.house;

// 상세 정보 담을 reactive 상태
const houseDetail = ref<HouseDetail | null>(null);

const building = ref<Building | null>(null);

// 상세 정보 요청 함수
const fetchHouseDetail = async (houseId: number) => {
  try {
    // const res = await axiosPlugin.get(`/api/v1/houses/${houseId}`);
    // houseDetail.value = res.data.data;
    houseDetail.value = {
      house_id: 1,
      house_name: "타잔타워",
      house_address: "서울시 강남구 테헤란로 101",
      house_category: "아파트",
      house_latitude: 37.123456,
      house_longitude: 127.654321,
      house_indexes: {
        house_index_amenity: 3,
        house_index_clinic: 2,
        house_index_security: 4,
        house_index_shopping: 3,
        house_index_transportation: 5,
      },
      house_review_images: [
        "https://via.placeholder.com/300x200?text=리뷰사진1",
        "https://via.placeholder.com/300x200?text=리뷰사진2",
        "https://via.placeholder.com/300x200?text=리뷰사진3",
      ],
      house_reviews: [
        {
          review_id: 101,
          review_img_url: "https://via.placeholder.com/300x200?text=유저1",
          review_score: 4,
          review_lease_type: "월세",
          review_deposit: 500,
          review_management_fee: 10,
          review_residence_period: 12,
          review_floor: 3,
          review_advantage: "엘리베이터 빠름",
          review_advantage_tags: [{ name: "빠른엘베" }],
          review_disadvantage: "근처에 병원이 없음",
          review_disadvantage_tags: [{ name: "의료시설부족" }],
          review_writer_nickname: "마루치",
          review_is_writer: false,
        },
        {
          review_id: 102,
          review_img_url: "https://via.placeholder.com/300x200?text=유저2",
          review_score: 5,
          review_lease_type: "전세",
          review_deposit: 10000,
          review_management_fee: 0,
          review_residence_period: 24,
          review_floor: 10,
          review_advantage: "뷰가 좋고 주변이 조용함",
          review_advantage_tags: [{ name: "뷰좋음" }, { name: "조용함" }],
          review_disadvantage: "편의점까지 조금 멀어요",
          review_disadvantage_tags: [{ name: "편의시설멀다" }],
          review_writer_nickname: "두치",
          review_is_writer: true,
        },
        {
          review_id: 103,
          review_img_url: "https://via.placeholder.com/300x200?text=유저3",
          review_score: 3,
          review_lease_type: "반전세",
          review_deposit: 3000,
          review_management_fee: 15,
          review_residence_period: 6,
          review_floor: 1,
          review_advantage: "집이 따뜻함",
          review_advantage_tags: [{ name: "단열굿" }],
          review_disadvantage: "1층이라 소음 있음",
          review_disadvantage_tags: [{ name: "소음" }],
          review_writer_nickname: "타잔",
          review_is_writer: false,
        },
      ],
    };

    building.value = {
      building_name: houseDetail.value.house_name,
      building_category: houseDetail.value.house_category,
      building_address: houseDetail.value.house_address,
      building_latitude: houseDetail.value.house_latitude,
      building_longitude: houseDetail.value.house_longitude,
      building_type: houseDetail.value.house_category,
    };
    console.log(houseDetail.value);
  } catch (err) {
    console.error("하우스 상세 정보 로딩 실패", err);
  }
};

// house prop이 변경될 때마다 요청
watch(
  () => props.house,
  (newHouse) => {
    if (newHouse?.house_id) {
      fetchHouseDetail(newHouse.house_id);
    }
  },
  { immediate: true } // 처음 로드시에도 호출
);

// 탭 데이터 배열 초기화
const tabs: Tab[] = [
  {
    name: "리뷰",
    component: ReviewTap,
    props: houseDetail.value
      ? {
          reviews: houseDetail.value.house_reviews,
          houseId: houseDetail.value.house_id,
        }
      : undefined,
  },
  {
    name: "정보",
    component: BuildingInfoTap,
    props: houseDetail.value
      ? { indexes: houseDetail.value.house_indexes }
      : undefined,
  },
];

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
  <div v-if="house" class="house-detail-wrapper">
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
    <div class="tab-bar-wrapper">
      <TabBar :tabs="tabs" v-model:selectedTabIdx="selectedTabIndex"></TabBar>
    </div>
  </div>
</template>

<style scoped lang="scss">
.house-detail-wrapper {
  background-color: white;
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

    display: flex;
    align-items: center;
    justify-content: center;

    img {
      @include custom-icon-style(18px);
    }
  }
}
</style>
