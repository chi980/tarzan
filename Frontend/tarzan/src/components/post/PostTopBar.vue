<template>
  <div class="post-topbar">
    <div class="post-topbar-button">
      <img :src="backIcon" alt="back-icon" @click="goToBack">
    </div>
    <div class="edit">
      <div class="edit-button">
        <img :src="settingIcon" alt="setting-icon" @click="controllDropDown">
      </div>
      <div class="scrollable-container dropdown-content"
        :class="['dropdown-content', { show: isDropDownOpen }]"
        @click="controllDropDown"
      >
        <div class="scrollable-list">
          <ul>
            <li
              v-for="option in options"
              :key="option.idx"
              @click="selectOption(option)"
            >
              {{ option.name }}
            </li>
          </ul>
        </div>
        
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import backIcon from '@/assets/icons/topbar/icon-back.png';
import settingIcon from '@/assets/icons/topbar/icon-setting.png';

// 상태 변수 정의
const router = useRouter();
const isDropDownOpen = ref(false);
const props = defineProps({ isAuthor: Boolean }); // isAuthor props
console.log("isAuthor value:", props.isAuthor);

const options = ref([
  { idx: 1, name: '수정' },
  { idx: 2, name: '삭제' },
  { idx: 3, name: '신고하기' }
]);

// 작성자 여부에 따라 옵션 필터링
const filteredOptions = computed(() => {
  if (props.isAuthor) {
    // 작성자라면 수정/삭제만 보여줌
    return options.filter(option => option.idx !== 3);
  }
  // 작성자가 아니라면 신고하기만 보여줌
  return options.filter(option => option.idx === 3);
});

const goToBack = () => {
  router.go(-1);
};

const controllDropDown = () => {
  isDropDownOpen.value = !isDropDownOpen.value;
};

const selectOption = (option) => {
  selectedOption.value = option;
};
</script>

<style scoped lang="scss">
.post-topbar {
  width: 100%;
  height: 54px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  @include custom-padding-x;
  box-sizing: border-box;
}

.post-topbar img {
  width: 24px;
  height: 24px;
}

.edit {
  position: relative;

}

.edit-button{
  height: 100%;
  width: 100%;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  transform: translateX(-70%); /* 드랍 다운 목록 위치 조절 */
}

.dropdown-content.show {
  display: block;
  min-width: max-content;
  margin-top: $margin-small;
  z-index: $z-index-dropdown;
  width: inherit;
}

.scrollable-container {
  border-radius: $border-radius-default;
  background-color: white;
}

.scrollable-list li {
  padding: 14px 30px; /* 리스트 아이템의 내부 패딩 설정 */
}

.dropdown-exterior {
  position: fixed; /* 뷰포트에 대해 고정 위치 설정 */
  top: 0; /* 화면의 맨 위에 위치 */
  left: 0; /* 화면의 왼쪽에 위치 */
  width: 100vw; /* 화면 너비를 모두 차지하도록 설정 */
  height: 100vh; /* 화면 높이를 모두 차지하도록 설정 */
  background-color: transparent; /* 배경색을 투명하게 설정 */
  z-index: 999; /* 드롭다운 목록보다 낮은 z-index를 설정하여 드롭다운 외부 클릭 시 드롭다운을 닫을 수 있도록 함 */
}
</style>