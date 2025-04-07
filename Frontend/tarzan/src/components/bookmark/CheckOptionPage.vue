<template>
  <div class="input-form">
    <div class="input-group">
      <h2 class="input-title">주방 및 세탁실 옵션</h2>
      <div class="tag-content">
        <TagButtonGroup :buttons="checkItemsInKitchen" :multiple="true">
          <template v-slot:default="{ button }">
            <span>{{ button.label }}</span>
          </template>
        </TagButtonGroup>
      </div>
    </div>
    <div class="input-group">
      <h2 class="input-title">거실 옵션</h2>
      <div class="tag-content">
        <TagButtonGroup :buttons="checkItemsInLivingRoom" :multiple="true">
          <template v-slot:default="{ button }">
            <span>{{ button.label }}</span>
          </template>
        </TagButtonGroup>
      </div>
    </div>
    <div class="input-group">
      <h2 class="input-title">방 옵션</h2>
      <div class="tag-content">
        <TagButtonGroup :buttons="checkItemsInRoom" :multiple="true">
          <template v-slot:default="{ button }">
            <span>{{ button.label }}</span>
          </template>
        </TagButtonGroup>
      </div>
    </div>
    <div class="input-group">
      <h2 class="input-title">화장실 옵션</h2>
      <div class="tag-content">
        <TagButtonGroup :buttons="checkItemsInBathRoom" :multiple="true">
          <template v-slot:default="{ button }">
            <span>{{ button.label }}</span>
          </template>
        </TagButtonGroup>
      </div>
    </div>
    <div class="input-group">
      <h2 class="input-title">주방 및 세탁실 옵션</h2>
      <div class="tag-content">
        <TagButtonGroup :buttons="checkItemsInEtc" :multiple="true">
          <template v-slot:default="{ button }">
            <span>{{ button.label }}</span>
          </template>
        </TagButtonGroup>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, watchEffect } from "vue";
import { Check } from "@/data/check";
import { useRoute, useRouter } from "vue-router";
import { axiosInstance } from "@/plugins/axiosPlugin";

import TagButtonGroup from "@/components/common/TagButtonGroup.vue";

const route = useRoute();
const router = useRouter();

const bookmarkIdx = ref<number | null>(null);

watchEffect(() => {
  if (route.params.bookmarkIdx) {
    bookmarkIdx.value = Number(route.params.bookmarkIdx);
    console.log("bookmarkIdx from route:", bookmarkIdx.value); // bookmarkIdx 값 확인
  }
});

// ✅ 체크된 옵션을 서버에 저장하는 PUT 요청 함수
const updateCheckOptions = async () => {
  console.log("bookmarkIdx:", bookmarkIdx.value, typeof bookmarkIdx.value);

  // 🔹 bookmarkIdx 유효성 검사
  if (!bookmarkIdx.value || isNaN(bookmarkIdx.value)) {
    console.error("Invalid bookmarkIdx:", bookmarkIdx.value);
    return;
  }

  const updatedCheckItems = {
    kitchen: checkItemsInKitchen.value,
    livingRoom: checkItemsInLivingRoom.value,
    room: checkItemsInRoom.value,
    bathRoom: checkItemsInBathRoom.value,
    etc: checkItemsInEtc.value,
  };

  console.log("PUT 요청 데이터:", JSON.stringify(updatedCheckItems, null, 2)); // 데이터 확인

  try {
    const response = await axiosInstance.put(
      `/v1/bookmark/${bookmarkIdx.value}`,
      updatedCheckItems
    );
    console.log("Successfully updated:", response.data);
  } catch (error) {
    console.error("Error updating check options:", error);
    throw error;
  }
};

const checkItemsInKitchen = ref([
  { idx: 0, label: "식탁", value: false, label: "식탁" },
  { idx: 1, label: "인덕션/가스레인지", value: false },
  { idx: 2, label: "전자레인지", value: false },
  { idx: 3, label: "냉장고", value: false },
  { idx: 4, label: "세탁기", value: false },
  { idx: 5, label: "싱크대", value: false },
]);

const checkItemsInLivingRoom = ref([
  { idx: 0, label: "TV", value: false },
  { idx: 1, label: "천장/벽걸이에어컨", value: false },
  { idx: 2, label: "전자레인지", value: false },
]);

const checkItemsInRoom = ref([
  { idx: 0, label: "침대", value: false },
  { idx: 1, label: "옷장/북박이장", value: false },
]);

const checkItemsInBathRoom = ref([
  { idx: 0, label: "샤워부스", value: false },
  { idx: 1, label: "비데", value: false },
]);

const checkItemsInEtc = ref([
  { idx: 0, label: "경비원/사설경비", value: false },
  { idx: 1, label: "CCTV", value: false },
  { idx: 2, label: "무인택배함", value: false },
  { idx: 3, label: "현관보안", value: false },
  { idx: 4, label: "인터폰/비디오폰", value: false },
  { idx: 5, label: "카드키", value: false },
  { idx: 6, label: "화재경보기", value: false },
  { idx: 7, label: "베란다", value: false },
  { idx: 8, label: "엘레베이터", value: false },
  { idx: 9, label: "WIFI", value: false },
]);

const updateCheckItem = (array: Check[], index: number, updatedItem: Check) => {
  array.splice(index, 1, updatedItem);
  console.log(array[index]);
};
</script>

<style lang="scss" scoped>
.input-form {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: $padding-big;

  .input-group {
    @include custom-padding-x();
    display: flex;
    flex-direction: column;
    gap: 12px;

    .input-title {
      @include custom-text($font-size: 14px);
      text-align: left;

      .input-title-mandatory {
        color: red;
      }
    }

    .input-content {
      display: flex;
      align-items: center;
      gap: $padding-small;

      input[type="text"] {
        @include custom-input-style;
        flex: 1;
      }
      input {
        @include custom-input-style;
        flex: 1;
      }

      .select-content {
        flex-grow: 1;
      }
    }
  }
}

// scoped
.input-form {
  @include custom-padding-y;
}
:deep(.tag-button-container) {
  overflow-x: auto;
  flex-wrap: wrap;
}
</style>
