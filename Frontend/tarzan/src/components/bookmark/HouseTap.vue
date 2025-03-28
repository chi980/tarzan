<template>
  <div class="tab-content house-content-wrapper">
    <div class="house-item-wrapper">
      <div class="house-item--add-wrapper">
        <div class="house-add-img-wrapper">
          <img :src="houseAddImg" alt="houseAddImg" />
        </div>
        <p>집 추가하기</p>
      </div>
      <HouseItem
        v-for="(house, index) in list"
        :key="index"
        :house="house"
        @navigate="navigateToCheckCostPage(house)" />
      <!-- <NonContent v-if="list.length === 0" :value="'북마크한 집이 존재하지 않습니다.'" /> -->
    </div>
  </div>
</template>

<script setup lang="ts">
/**  */
import { ref, onMounted, defineProps } from "vue";

/** component load */
import houseAddImg from "@/assets/icons/Plus/Pluse.png";
import HouseAddSrc from "@/assets/icons/Plus/Style=Outlined.svg";
import NonContent from "@/components/common/NonContent.vue";
import HouseItem from "@/components/bookmark/HouseItem.vue";

/** data, plugin load */
import { useRouter } from "vue-router";
import { axiosInstance } from "@/plugins/axiosPlugin";

const router = useRouter();

function navigateToMap() {
  router.push("/bookmark/map");
}

const navigateToCheckCostPage = (house) => {
  if (!house || !house.bookmarkIdx) {
    console.error("house 또는 bookmarkIdx가 없습니다.");
    return;
  }

  const bookmarkIdx = house.bookmarkIdx;
  router.push({ name: "CheckCostPage", params: { bookmarkIdx } });
};

const list = ref([]);
list.value = [
  {
    bookmarkIdx: 1,
    house_name: "집 이름 1",
    house_address: "주소 1",
    house_category: "카테고리 1",
    hoconstuse_review_score: 4.5,
    house_latitude: 37.5665,
    house_longitude: 126.978,
    created_at: "2025.03.16 10:00:00",
  },
  {
    bookmarkdx: 2,
    house_name: "집 이름 2",
    house_address: "주소 2",
    house_category: "카테고리 2",
    house_review_score: 4.5,
    house_latitude: 37.5665,
    house_longitude: 126.978,
    created_at: "2025.03.16 10:00:00",
  },
];

const fetchRecentHouses = async () => {
  try {
    const response = await axiosInstance.get(`/v1/bookmark`, {
      params: {
        size: 3,
        page: 0,
        sortBy: "최신순",
        status: "CHECK_PENDING",
      },
    });

    if (response.data.success) {
      // 데이터를 변환하여 필요한 필드만 저장, bookmark_id를 bookmarkIdx로 변경
      list.value = response.data.data.list
        .map((item) => ({
          bookmarkIdx: item.bookmark_id, // bookmark_id를 bookmarkIdx로 변경
          house_name: item.bookmark_house_name || "이름 없음",
          house_address: item.bookmark_house_address,
          house_category: item.bookmark_house_category || "카테고리 없음",
          house_latitude: item.bookmark_house_latitude,
          house_longitude: item.bookmark_house_longitude,
          created_at: item.bookmark_created_at, // created_at도 같이 저장
        }))
        .filter((house) => house.bookmarkIdx !== undefined); // bookmarkIdx가 undefined인 항목은 제거

      // created_at을 기준으로 내림차순 정렬 (날짜 형식이 잘못된 경우 parse 처리를 추가할 수 있음)
      list.value = list.value.sort((a, b) => {
        // 날짜 형식이 "2025.03.16 10:00:00" 형태인 경우 "2025-03-16T10:00:00"으로 변환
        const dateA = new Date(
          a.created_at.replace(/\./g, "-").replace(" ", "T")
        );
        const dateB = new Date(
          b.created_at.replace(/\./g, "-").replace(" ", "T")
        );
        return dateB - dateA; // 내림차순 정렬
      });
    } else {
      console.error("Failed to fetch data:", response.data.message);
    }
  } catch (error) {
    console.error("API request error:", error);
  }
};

onMounted(fetchRecentHouses);
</script>

<style lang="scss" scoped>
.tab-content {
  margin-top: 0;
}
.house-content-wrapper {
  @include custom-padding-x($padding-default);
}

/** non content css */
.non-content {
  display: flex;
  justify-content: center;
  align-items: center;
  border-top: 1px solid #d9d9d9;
  min-height: 140px;

  p {
    @include custom-text-description(
      $font-color: $text-color-light,
      $font-size: 12px
    );
  }
}

.house-item-wrapper {
  display: flex;
  flex-direction: column;
  gap: $padding-small;
}

/** 집 추가하기 버튼 */
.house-item--add-wrapper {
  @include custom-padding-y($padding-small);
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 12px;

  .house-add-img-wrapper {
    width: 36px;
    height: 36px;
    border-radius: $border-radius-default;
    background-color: #ededed;

    display: flex;
    justify-content: center;
    align-items: center;
    img {
      width: 14px;
      height: 14px;
    }
  }

  p {
    @include custom-text(
      $font-size: 12px,
      $font-color: #969696,
      $font-weight: 500
    );

    flex: 1;
    text-align: left;
  }
}

.house-content-wrapper {
  background-color: beige;
  display: flex;
  flex-direction: column;
  gap: $padding-small;
}
</style>
