<script setup lang="ts">
import { defineProps, defineEmits } from "vue";
// 부모로부터 상태 props로 받음
const props = defineProps<{
  bookmark: Object;
  checked: boolean;
}>();

// 부모에게 알림
const emit = defineEmits<{
  (e: "click", item: any): void;
}>();

// 체크박스나 HouseItem 클릭 시
const toggle = () => {
  const newBookmark = {
    ...props.bookmark,
    checked: !props.checked,
  };
  emit("click", newBookmark);
};
</script>

<template>
  <div class="bookmark-item-wrapper">
    <input type="checkbox" :checked="props.checked" @change="toggle" />
    <div class="house-item-wrapper">
      <div class="house-meta">
        <h3 id="house_name">{{ props.bookmark.bookmark_house_name }}</h3>
        <p id="house_category">{{ props.bookmark.bookmark_house_category }}</p>
      </div>
      <p id="house_address">{{ props.bookmark.bookmark_house_address }}</p>
    </div>
  </div>
</template>
<style lang="scss" scoped>
.bookmark-item-wrapper {
  @include custom-margin-x;
  @include custom-padding-y;
  display: flex;
  flex-direction: row;
  gap: $padding-default;

  align-items: center;
}
.house-item-wrapper {
  @include custom-text($font-size: 12px, $font-color: $text-color-light);
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 8px;
  width: 100%;
  text-align: left;
}

.house-meta {
  display: flex;
  align-items: center;
  flex-wrap: wrap; // 👉 자동 줄바꿈 허용
  gap: 8px;
  width: 100%;
}

#house_name {
  @include custom-text($font-size: 14px);
  white-space: nowrap; // 줄바꿈 없음
  flex-shrink: 0; // 줄어들지 않음
}

#house_category {
  min-width: 0; // 💡 flex-item에서 말줄임 가능하게 해줌
  flex: 1; // 남는 공간 채움
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
