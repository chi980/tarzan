<template>
  <div class="input-form">
    <div class="input-group">
      <h2 class="input-title">주방 및 세탁실 옵션</h2>
      <div class="tag-content">
        <TagButtonGroup
          :buttons="checkItemsInKitchen"
          :multiple="true"
          :selected-buttons="selectedCheckItemsInKitchen"
          @update:selectedButtons="
            (updated) => {
              console.log(selectedCheckItemsInKitchen);
              checkItemsInKitchen.forEach((item) => {
                item.value = updated.includes(item.label);
              });
            }
          ">
          <template v-slot:default="{ button }">
            <span>{{ button.label }}</span>
          </template>
        </TagButtonGroup>
      </div>
    </div>
    <div class="input-group">
      <h2 class="input-title">거실 옵션</h2>
      <div class="tag-content">
        <TagButtonGroup
          :buttons="checkItemsInLivingRoom"
          :multiple="true"
          :selected-buttons="selectedCheckItemsInLivingRoom"
          @update:selectedButtons="
            (updated) => {
              checkItemsInLivingRoom.forEach((item) => {
                item.value = updated.includes(item.label);
              });
            }
          ">
          <template v-slot:default="{ button }">
            <span>{{ button.label }}</span>
          </template>
        </TagButtonGroup>
      </div>
    </div>
    <div class="input-group">
      <h2 class="input-title">방 옵션</h2>
      <div class="tag-content">
        <TagButtonGroup
          :buttons="checkItemsInRoom"
          :multiple="true"
          :selected-buttons="selectedCheckItemsInRoom"
          @update:selectedButtons="
            (updated) => {
              checkItemsInRoom.forEach((item) => {
                item.value = updated.includes(item.label);
              });
            }
          ">
          <template v-slot:default="{ button }">
            <span>{{ button.label }}</span>
          </template>
        </TagButtonGroup>
      </div>
    </div>
    <div class="input-group">
      <h2 class="input-title">화장실 옵션</h2>
      <div class="tag-content">
        <TagButtonGroup
          :buttons="checkItemsInBathRoom"
          :multiple="true"
          :selected-buttons="selectedCheckItemsInBathRoom"
          @update:selectedButtons="
            (updated) => {
              checkItemsInBathRoom.forEach((item) => {
                item.value = updated.includes(item.label);
              });
            }
          ">
          <template v-slot:default="{ button }">
            <span>{{ button.label }}</span>
          </template>
        </TagButtonGroup>
      </div>
    </div>
    <div class="input-group">
      <h2 class="input-title">보안 및 기타 시설</h2>
      <div class="tag-content">
        <TagButtonGroup
          :buttons="checkItemsInEtc"
          :multiple="true"
          :selected-buttons="selectedCheckItemsInEtc"
          @update:selectedButtons="
            (updated) => {
              checkItemsInEtc.forEach((item) => {
                item.value = updated.includes(item.label);
              });
            }
          ">
          <template v-slot:default="{ button }">
            <span>{{ button.label }}</span>
          </template>
        </TagButtonGroup>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, defineProps, defineModel, onMounted } from "vue";

import TagButtonGroup from "@/components/common/TagButtonGroup.vue";

const props = defineProps<{ bookmarkIdx: number }>();
const bookmarkData = defineModel<Object>("bookmarkData");

const selectedCheckItemsInKitchen = ref([]);
const selectedCheckItemsInLivingRoom = ref([]);
const selectedCheckItemsInRoom = ref([]);
const selectedCheckItemsInBathRoom = ref([]);
const selectedCheckItemsInEtc = ref([]);

const checkItemsInKitchen = ref([]);
const checkItemsInLivingRoom = ref([]);
const checkItemsInRoom = ref([]);
const checkItemsInBathRoom = ref([]);
const checkItemsInEtc = ref([]);

onMounted(() => {
  checkItemsInKitchen.value =
    bookmarkData.value.value.bookmark_checklist.OPTION_UTILITY_ROOM;
  checkItemsInLivingRoom.value =
    bookmarkData.value.value.bookmark_checklist.OPTION_LIVING_ROOM;
  checkItemsInRoom.value =
    bookmarkData.value.value.bookmark_checklist.OPTION_ROOM;
  checkItemsInBathRoom.value =
    bookmarkData.value.value.bookmark_checklist.OPTION_BATH_ROOM;
  checkItemsInEtc.value =
    bookmarkData.value.value.bookmark_checklist.OPTION_SECURITY;
});
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
