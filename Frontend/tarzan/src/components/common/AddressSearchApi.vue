<script setup lang="ts">
import { ref, onMounted, defineEmits, watch } from "vue";
import axios from "axios";
import AddressSearchResult from "./AddressSearchResult.vue";
import { debounce } from "lodash"; // lodash의 debounce 사용
import TopBarBack from "@/components/common/TopBarBack.vue";

const emit = defineEmits(["close", "selectAddress"]);

const closeModal = () => {
  emit("close"); // 부모에게 'close' 이벤트 전달
};
</script>
<template>
  <div class="modal-container" @click.self="closeModal">
    <div class="modal-wrapper">
      <!-- 모달 제목 -->
      <div class="modal-title">
        <TopBarBack title="주소 검색" @back="closeModal" />
      </div>

      <div class="search-container">
        <input
          v-model="searchQuery"
          @keyup.enter="searchAddress"
          type="text"
          placeholder="검색할 주소명을 입력해주세요"
          class="search-input"
          aria-label="주소 검색" />
      </div>

      <div class="modal-content">
        <AddressSearchResult
          :addresses="searchResults"
          @selectAddress="selectAddress" />
      </div>
    </div>

    <div class="button-wrapper">
      <button class="button-default" @click="searchAddress">검색</button>
    </div>
  </div>
</template>

<style scoped lang="scss">
.modal-wrapper {
  display: flex;
  flex-direction: column;

  height: 100%;

  .modal-content {
    @include custom-padding-x($padding-default);
    @include custom-padding-y($padding-big);

    display: flex;
    flex-direction: column;

    height: 100%;
    overflow-y: auto; /* 세로 스크롤을 추가 */
    @include custom-scrollbar-style; /* 스크롤바 스타일 적용 */
  }
}
.modal-container {
  @include custom-modal;
}

.search-input {
  @include custom-input-style;
  position: sticky;
  top: 0;
  z-index: 1;
  width: 100%;
  // @include custom-shadow-style;
}

.search-container {
  position: sticky;
  top: 0;
  z-index: 1;
  background: white;
  //width: clac(100% -20px);
  // margin: 0 auto;
  padding: 20px 55px 0 20px;
}

.button-wrapper {
  width: 100%;
  position: absolute;
  bottom: $padding-default;
  display: flex;
  flex-direction: row;
}

.button-default {
  @include custom-button-style(
    $bg-color: $secondary-color-default,
    $font-color: white
  );
  @include custom-margin-x;
}
</style>
