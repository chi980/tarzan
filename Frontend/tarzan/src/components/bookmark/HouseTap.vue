<template>
  <div class="tab-content">
    <div class="button-in-tab" @click="navigateToMap">
      <p>직접 추가하기</p>
      <img :src="HouseAddSrc" />
    </div>
    <div class="content-wrapper">
      <div class="content-title-wrapper">
        <p>북마크한 집 <span>{{ list.length }}</span></p>
        <p>
          지도에서 집을 북마크해보세요! <br />
          북마크한 집에 대해 여러가지를 점검할 수 있어요!
        </p>
      </div>
      <p class="thin-line"></p>
      <div class="house-list">
        <HouseItem v-for="house in list" :key="house.bookmark_id" :house="house" />
        <NonContent v-if="list.length === 0" :value="'북마크한 집이 존재하지 않습니다.'" />
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
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { axiosInstance } from "@/plugins/axiosPlugin";
import HouseAddSrc from "@/assets/icons/Plus/Style=Outlined.svg";
import NonContent from "@/components/common/NonContent.vue";
import HouseItem from "@/components/bookmark/HouseItem.vue";

const router = useRouter();

function navigateToMap() {
  router.push("/bookmark/map");
}

const list = ref([]);

const fetchRecentHouses = async () => {
  const queryParams = {
    size: "3",
    page: "1",
    sortBy: "최신순",
    status: "CHECK_PENDING"
  };

  try {
    const response = await axiosInstance.get(
      `/v1/bookmark`,
      { params: queryParams }  // 쿼리 파라미터로 전달
    );

    if (response.data.success) {
      list.value = response.data.data.list;
    } else {
      console.error("Failed to fetch data:", response.data.message);
    }
  } catch (error) {
    console.error("API request error:", error);
  }
};


onMounted(fetchRecentHouses);
</script>

<style lang="scss" scoped>
.tab-content {
  margin-top: 0;
}

.content-indicator {
  @include custom-margin-y;
  background-color: #ededed;
  height: 10px;
}

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

.button-in-tab {
  // @include custom-margin-x;
  // @include custom-button-style(
  //   $bg-color: #f2f2f2,
  //   $icon-size: 18px,
  //   $height: 52px
  // );
  // cursor: pointer;
}

.content-wrapper {
  @include custom-padding-x;
  display: flex;
  flex-direction: column;
  gap: $padding-default;

  .content-title-wrapper {
    text-align: left;
    
    p:first-child {
      @include custom-text-bold($font-size: 16px);

      span {
        @include custom-text($font-color: $primary-color-default);
      }
    }

    p {
      @include custom-text-description(
        $font-color: $text-color-light,
        $font-size: 12px
      );
    }
  }
}

.thin-line {
  border-top: 1px solid #d9d9d9;
}

.content-wrapper:last-child {
  margin-bottom: $margin-default;
}
</style>
