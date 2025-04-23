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
        <List
          :fetchItems="fetchBookmarks"
          :params="params"
          :onDelete="handleDelete"
          :onClick="handleCLick">
          <template #item="{ item, index, onClick, onDelete }">
            <SwipeItem
              :key="item.bookmarkIdx"
              @click="onClick(item)"
              @delete="onDelete(item, index)">
              <div class="house-item-wrapper">
                <BookmarkItem :bookmark="item" />
              </div>
            </SwipeItem>
          </template>
        </List>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
/**  */
import {
  ref,
  onMounted,
  defineProps,
  defineExpose,
  defineEmits,
  watch,
} from "vue";

/** component load */
import AddressHouseSearch from "@/components/common/AddressHouseSearch.vue";

import houseAddImg from "@/assets/icons/Plus/Pluse.png";
import BookmarkItem from "@/components/bookmark/BookmarkItem.vue";
import TagButtonGroup from "@/components/common/TagButtonGroup.vue";
import List from "@/components/common/List.vue";
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

const handleDelete = async (bookmark: any) => {
  try {
    const bookmarkIdx = bookmark.bookmark_id;
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

const handleCLick = (bookmark) => {
  console.log(bookmark);
  router.push({
    name: "BookMarkDetail",
    params: { id: bookmark.bookmark_id },
    query: { bookmarkObj: JSON.stringify(bookmark) },
  });
};
/** tag button 관련 */
const tagOptions = [
  { label: "전체", value: "ALL" },
  { label: "점검 전", value: "CHECK_PENDING" },
  { label: "점검 완료", value: "CHECK_COMPLETED" },
];

const selectedTag = ref("ALL"); // 단일 선택용

/** bookmark item 관련 */
watch(selectedTag, (newSeletedTag) => {
  params.value.status = newSeletedTag;
});

const params = ref({
  size: 10,
  status: "ALL", // 나중에 필터링 추가
  sortBy: "최신순",
});

const fetchBookmarks = async (page: number, params: any) => {
  try {
    const response = await axiosInstance.get(`/v1/bookmark`, {
      params: {
        ...params,
        page: page,
      },
    });

    if (response.data.success) {
      return response.data.data.list;
    } else {
      console.error("Failed to fetch data:", response.data.message);
      return [];
    }
  } catch (error) {
    console.error("API request error:", error);
    return [];
  }
};
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
