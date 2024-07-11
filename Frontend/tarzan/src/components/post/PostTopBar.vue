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
<script>
import backIcon from '@/assets/icons/topbar/icon-back.png';
import settingIcon from '@/assets/icons/topbar/icon-setting.png';

export default {
  data() {
    return {
      backIcon,
      settingIcon,
      isDropDownOpen: false,
      options: [
        { idx: 1, name: '수정' },
        { idx: 2, name: '삭제' },
      ],
      selectedOption: null
    };
  },
  methods: {
    goToBack() {
      this.$router.go(-1);
    },
    controllDropDown() {
      this.isDropDownOpen = !this.isDropDownOpen;
    },
    selectOption(option) {
      this.selectedOption = option;
    }
  }
}
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