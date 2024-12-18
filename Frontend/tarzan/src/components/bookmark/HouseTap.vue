<template>
  <div class="tab-content">
    <div class="button-in-tab" @click="navigateToMap">
      <p>직접 추가하기</p>
      <img :src="HouseAddSrc" />
    </div>

    <div class="content-wrapper">
      <div class="content-title-wrapper">
        <p>북마크한 집 <span>0</span></p>
        <p>
          지도에서 집을 북마크해보세요! <br />
          북마크한 집에 대해 여러가지를 점검할 수 있어요!
        </p>
      </div>
      <div class="content">
        <NonContent :value="'북마크한 집이 존재하지 않습니다.'" />
      </div>
    </div>
    <div class="content-indicator"></div>
    <div class="content-wrapper">
      <div class="content-title-wrapper">
        <p>점검 완료한 집 <span>0</span></p>
        <p>점검 완료한 집은 최대 3개까지 비교할 수 있어요!</p>
      </div>
      <div class="content">
        <NonContent :value="'점검 완료한 집이 존재하지 않습니다.'" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from "vue-router"; // Vue Router import
import HouseAddSrc from "@/assets/icons/Plus/Style=Outlined.svg";
import NonContent from "@/components/common/NonContent.vue";

const router = useRouter(); // useRouter hook to access the router

function navigateToMap() {
  router.push("/bookmark/map"); // Navigate to /bookmark/map
}
</script>

<style lang="scss" scoped>
.tab-content {
  margin-top: 0;
}
// content를 구분해주는 회색 긴 선
.content-indicator {
  @include custom-margin-y;
  background-color: #ededed;
  height: 10px;
}

// content가 없을 때 보여주는 요소
.non-content {
  display: flex;
  justify-content: center; /* 가로 중앙 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
  border-top: 1px solid #d9d9d9;
  min-height: 140px;

  p {
    @include custom-text-description(
      $font-color: $text-color-light,
      $font-size: 12px
    );
  }
}
.button-in-tab {
  // @include custom-margin-x;
  // @include custom-button-style(
  //   $bg-color: #f2f2f2,
  //   $icon-size: 18px,
  //   $height: 52px
  // );
  // cursor: pointer; /* Pointer cursor to indicate it's clickable */
}
// scoped

.content-wrapper {
  @include custom-padding-x;
  display: flex;
  flex-direction: column;

  gap: $padding-default;

  .content-title-wrapper {
    text-align: left;
    // title
    p:first-child {
      @include custom-text-bold($font-size: 16px);

      span {
        @include custom-text($font-color: $primary-color-default);
      }
    }
    // extra desc
    p {
      @include custom-text-description(
        $font-color: $text-color-light,
        $font-size: 12px
      );
    }
  }
}

.content-wrapper:last-child {
  margin-bottom: $margin-default;
}
</style>
