<template>
  <div class="bookmark-item-container" @click="handleClick">
    <div class="house-content">
      <div class="house-meta">
        <h3 id="house_name" style="color: black">
          {{ props.bookmark.bookmark_house_name }}
        </h3>
        <p id="house_category">{{ props.bookmark.bookmark_house_category }}</p>
      </div>
      <p id="house_address">{{ props.bookmark.bookmark_house_address }}</p>
      <p id="created_date">
        {{ formattedDate(props.bookmark?.bookmark_created_at) }}
      </p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineProps, defineEmits, computed } from "vue";
import { format } from "date-fns";

const props = defineProps({
  bookmark: Object, // House object passed from parent
});

const emit = defineEmits(["navigate"]);

// 날짜 포맷팅된 문자열
const formattedDate = (rawDate) => {
  if (!rawDate) return "";
  const date = new Date(rawDate);
  return format(date, "yy.MM.dd") + " 추가";
};

const handleClick = () => {
  const house = props.bookmark; // Get the house object from props
  if (house && house.bookmark_id) {
    // navigateToCheckCostPage 호출 등
    emit("navigate", house); // Emit the 'navigate' event with the house object
  } else {
    console.error("bookmarkIdx가 없습니다.");
  }
};
</script>

<style scoped lang="scss">
.bookmark-item-container {
  @include custom-padding-y($padding-small);
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 8px;
}

.bookmark-item-container .house-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 8px;
  width: 100%;
  text-align: left;
}

#house_name {
  @include custom-text($font-size: 14px);
  white-space: nowrap; /* 텍스트를 한 줄로 유지 */
  overflow: hidden; /* 내용이 넘치면 숨김 */
  text-overflow: ellipsis; /* 넘치는 텍스트를 말줄임표로 표시 */
}

#house_category {
  font-size: 12px;
  color: #9f9f9f;
  white-space: nowrap; /* 텍스트를 한 줄로 유지 */
  overflow: hidden; /* 내용이 넘치면 숨김 */
  text-overflow: ellipsis; /* 넘치는 텍스트를 말줄임표로 표시 */
}

#house_address {
  font-size: 12px;
  color: #9f9f9f;
  width: 100%;
  white-space: nowrap; /* 텍스트를 한 줄로 유지 */
  overflow: hidden; /* 내용이 넘치면 숨김 */
  text-overflow: ellipsis; /* 넘치는 텍스트를 말줄임표로 표시 */
}

#created_date {
  font-size: 12px;
  color: #9f9f9f;
  width: 100%;
  white-space: nowrap; /* 텍스트를 한 줄로 유지 */
  overflow: hidden; /* 내용이 넘치면 숨김 */
  text-overflow: ellipsis; /* 넘치는 텍스트를 말줄임표로 표시 */
}

.bookmark-item-container .house-meta {
  align-items: flex-end;
  display: flex;
  gap: 8px;
  font-size: 12px;
  color: #9f9f9f;
}
</style>
