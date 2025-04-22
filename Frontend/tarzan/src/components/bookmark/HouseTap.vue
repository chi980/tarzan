<template>
  <div>
    <div class="tag-button-wrapper">
      <TagButtonGroup
        :buttons="tagOptions"
        :selectedButton="selectedTag"
        :multiple="false" />
    </div>

    <div class="house-content-wrapper">
      <div class="house-item--add-wrapper" @click="emitOpenModal">
        <div class="house-add-img-wrapper">
          <img :src="houseAddImg" alt="houseAddImg" />
        </div>
        <p>집 추가하기</p>
      </div>
      <div class="house-list-wrapper">
        <SwipeItem
          v-for="(bookmark, index) in bookmarks"
          :key="index"
          @delete="handleDelete(index)"
          @click="navigateToDetailPage(bookmark)"
        >
          <div class="house-item-wrapper">
            <HouseItem :house="bookmark" />
          </div>
        </SwipeItem>
      </div>

      <!-- <NonContent v-if="list.length === 0" :value="'북마크한 집이 존재하지 않습니다.'" /> -->
    </div>
  </div>
</template>

<script setup lang="ts">
/**  */
import { ref, onMounted, defineProps, defineExpose, defineEmits, watch } from "vue";

/** component load */
import AddressHouseSearch from "@/components/common/AddressHouseSearch.vue";

import houseAddImg from "@/assets/icons/Plus/Pluse.png";
import HouseAddSrc from "@/assets/icons/Plus/Style=Outlined.svg";
import NonContent from "@/components/common/NonContent.vue";
import HouseItem from "@/components/bookmark/HouseItem.vue";
import TagButtonGroup from "@/components/common/TagButtonGroup.vue";
import SwipeItem from "@/components/common/SwipeItem.vue";

/** data, plugin load */
import { useRouter } from "vue-router";
import { axiosInstance } from "@/plugins/axiosPlugin";

const emit = defineEmits(["openAddressSearch"]);

const emitOpenModal = () => {
  emit("openAddressSearch");
};

defineExpose({
  emitOpenModal,
});

const router = useRouter();

function navigateToMap() {
  router.push("/bookmark/map");
}

const navigateToDetailPage = (house) => {
  try {
    if (!house.bookmarkIdx) throw new Error("bookmarkIdx 없음");
    router.push({ name: "BookMarkDetail", params: { id: house.bookmarkIdx } });
  } catch (e) {
    console.error("navigate 에러:", e);
  }
};


const handleDelete = async (idx) => {
  console.log("삭제");
  console.log(bookmarks.value[idx]);
  const bookmarkIdx = bookmarks.value[idx].bookmarkIdx;
  try {
    bookmarks.value.splice(idx, 1);
    const response = await axiosInstance.delete(`/v1/bookmark/${bookmarkIdx}`);
  } catch {
    console.error("잘못된 요청입니다.");
  }
};

const showAddressSearch = ref(false);

const openAddressSearch = () => {
  showAddressSearch.value = true;
};

const closeAddressSearch = () => {
  showAddressSearch.value = false;
};

const list = ref([]);
list.value = [
  {
    bookmarkIdx: 1,
    house_name: "집 이름 1",
    house_address: "주소 1",
    house_category: "카테고리 1",
    house_review_score: 4.5,
    house_latitude: 37.5665,
    house_longitude: 126.978,
    created_at: "2025.03.16 10:00:00",
  },
  {
    bookmarkIdx: 2,
    house_name: "집 이름 2",
    house_address: "주소 2",
    house_category: "카테고리 2",
    house_review_score: 4.5,
    house_latitude: 37.5665,
    house_longitude: 126.978,
    created_at: "2025.03.16 10:00:00",
  },
];

const handleCLick = (bookmark) => {
  router.push({
    name: "BookMarkDetail",
    params: { id: bookmark.bookmarkIdx },
    query: { bookmarkObj: JSON.stringify(bookmark) },
  });
};

const tagOptions = [
  { label: "전체", value: "ALL" },
  { label: "점검 전", value: "CHECK_PENDING" },
  { label: "점검 완료", value: "CHECK_COMPLETED" },
];

const selectedTag = ref("ALL"); // 단일 선택용

const bookmarks = ref<House[]>([]);

const fetchRecentHouses = async () => {
  try {
    const response = await axiosInstance.get(`/v1/bookmark`, {
      params: {
        size: 3,
        page: 0,
        sortBy: "최신순",
        status: selectedTag.value,
      },
    });

    if (response.data.success) {
      console.log(response.data);
      bookmarks.value = response.data.data.list
        .map((item) => ({
          bookmarkIdx: item.bookmark_id,
          house_name: item.bookmark_house_name || "이름 없음",
          house_address: item.bookmark_house_address,
          house_category: item.bookmark_house_category || "카테고리 없음",
          house_latitude: item.bookmark_house_latitude,
          house_longitude: item.bookmark_house_longitude,
          created_at: item.bookmark_created_at, // created_at도 같이 저장
        }))
        .filter((house) => house.bookmarkIdx !== undefined); // bookmarkIdx가 undefined인 항목은 제거

      console.log(bookmarks.value);
    } else {
      console.error("Failed to fetch data:", response.data.message);
    }
  } catch (error) {
    console.error("API request error:", error);
  }
};

onMounted(fetchRecentHouses);
watch(selectedTag, (newSeletedTag) => {
  fetchRecentHouses();
});
</script>

<style lang="scss" scoped>
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

.house-content-wrapper {
  @include custom-padding-x;
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
.tag-button-wrapper {
  @include custom-padding-x;
  @include custom-padding-y($padding-small);
}

.house-item-wrapper {
  @include custom-padding-y(4px);
}
</style>
