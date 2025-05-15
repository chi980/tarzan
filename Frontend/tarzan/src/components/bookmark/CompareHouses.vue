<template>
  <div>
    <div id="compare-container-banner-wrapper">
      <img
        :src="emojiSrc"
        class="emoji-image"
        style="
          width: 30%;
          height: auto;
          top: -80px;
          left: 20%;
          transform: rotate(-15deg);
        " />
      <img
        :src="emojiSrc"
        class="emoji-image"
        style="
          width: 20%;
          height: auto;
          top: 30%;
          right: 8%;
          transform: rotate(25deg);
        " />
      <img
        :src="emojiSrc"
        class="emoji-image"
        style="
          width: 14%;
          height: auto;
          bottom: 10%;
          left: 10%;
          transform: rotate(-20deg);
        " />
      <div id="compare-container-banner">
        <p>
          선택한 집을<br />
          비교해봐요
        </p>

        <a href="#">자세히 보기</a>
      </div>
    </div>
    <div class="house-tab-wrapper">
      <div
        v-for="(house, index) in housesToCompare"
        :key="house.bookmark_id"
        class="house-tab">
        <p class="numeric-text">{{ index + 1 }}</p>
        <div class="house-info">
          <div class="house-info-title">
            <p>{{ house.house_name }}</p>
          </div>
          <div class="house-info-content">
            <p>{{ house.house_category }}</p>
            <p>{{ house.house_address }}</p>
          </div>
        </div>
        <p class="numeric-text">{{ house.house_score }}</p>
      </div>
    </div>
    <div class="chart-wrapper">
      <Chart :chartData="chartData"></Chart>
    </div>

    <!-- <div>
      <div class="content-indicator"></div>
    </div> -->
    <div
      class="custom-flex-column"
      style="margin-top: 8px; margin-bottom: 30px; gap: 24px">
      <div class="table">
        <div class="table-title">
          <img :src="checkImgSrc" />
          <p>가격</p>
        </div>
        <div class="table-content-wrapper">
          <div class="table-wrapper">
            <table>
              <thead>
                <tr>
                  <th></th>
                  <th
                    v-for="(house, index) in housesToCompare"
                    :key="house.bookmark_id">
                    {{ house.house_name }}
                    <span>{{ formatDate(house.bookmark_created_at) }}</span>
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="row in costList" :key="row.idx">
                  <td>{{ row.kor }}</td>
                  <td v-for="house in housesToCompare" :key="house.bookmark_id">
                    {{ house.house_costs[row.eng] }}
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="table">
        <div class="table-title">
          <img :src="checkImgSrc" />
          <p>세부 사항</p>
        </div>

        <div class="table-content-wrapper">
          <div class="table-wrapper">
            <table>
              <thead>
                <tr>
                  <th></th>
                  <th
                    v-for="(house, index) in housesToCompare"
                    :key="house.bookmark_id">
                    {{ house.house_name }}
                    <span>{{ formatDate(house.bookmark_created_at) }}</span>
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="row in detailList" :key="row.idx">
                  <td v-html="row.kor"></td>
                  <td v-for="house in housesToCompare" :key="house.idx">
                    {{ house.house_details[row.eng] }}
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="table">
        <div class="table-title">
          <img :src="checkImgSrc" />
          <p>체크 사항</p>
        </div>

        <div class="table-content-wrapper">
          <div class="table-wrapper">
            <table>
              <thead>
                <tr>
                  <th></th>
                  <th
                    v-for="(house, index) in housesToCompare"
                    :key="house.bookmark_id">
                    {{ house.house_name }}
                    <span>{{ formatDate(house.bookmark_created_at) }}</span>
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="row in optionList" :key="row.idx">
                  <td v-html="row.kor"></td>
                  <td v-for="house in housesToCompare" :key="house.idx">
                    {{
                      house.house_checks[row.eng] !== undefined
                        ? house.house_checks[row.eng]
                        : "N/A"
                    }}
                  </td>
                </tr>

                <tr class="special-row">
                  <td>총계</td>
                  <td v-for="house in housesToCompare" :key="house.idx">
                    {{ getTotalScore(house.house_checks) }}
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, defineProps, onMounted } from "vue";

import qs from "qs";

import { axiosInstance } from "@/plugins/axiosPlugin";

import emojiSrc from "@/assets/emoji/face-with-monocle.png";
import checkImgSrc from "@/assets/icons/Check/Check.svg";

import { HouseChecks, HouseIndexes, HouseCompareInfo } from "@/data/house";
import { ChartRawDataOption } from "@/data/chart";
import Chart from "@/components/common/RadarChart.vue";
import { formatDate } from "@/utils/date";
const props = defineProps<{
  list: Number[];
}>();
const housesToCompare = ref<HouseCompareInfo[] | null>(null);

onMounted(() => {
  fetchCompareBookmark();
});

const chartData = ref({
  7: {
    houseName: "우리집1",
    indexes: [
      { label: "교통", value: 4 },
      { label: "상업시설", value: 2 },
      { label: "편의시설", value: 4 },
      { label: "치안", value: 5 },
      { label: "보건", value: 2 },
    ],
  },
  8: {
    houseName: "우리집2",
    indexes: [
      { label: "교통", value: 3 },
      { label: "상업시설", value: 1 },
      { label: "편의시설", value: 3 },
      { label: "치안", value: 4 },
      { label: "보건", value: 1 },
    ],
  },
});

const fetchCompareBookmark = async () => {
  try {
    // bookmark_ids 배열을 올바르게 직렬화
    const params = qs.stringify(
      {
        bookmark_ids: props.list,
      },
      { arrayFormat: "repeat" }
    );

    const response = await axiosInstance.get(`/v1/bookmark/compare?${params}`);

    if (response.data && response.data.data) {
      console.log(response.data);
      const data = response.data.data.list;
      housesToCompare.value = data;

      chartData.value = data.reduce((acc, item) => {
        acc[item.bookmark_id] = {
          houseId: item.house_id,
          houseName: item.house_name,
          bookmarkId: item.bookmark_id,
          bookmarkCreatedAt: item.bookmark_created_at,
          indexes: [
            { label: "교통", value: item.house_indexes.TRANSPORTATION },
            { label: "상업시설", value: item.house_indexes.SHOPPING },
            { label: "편의시설", value: item.house_indexes.AMENITY },
            { label: "치안", value: item.house_indexes.SECURITY },
            { label: "보건", value: item.house_indexes.CLINIC },
          ],
        };
        return acc;
      }, {});
      console.log(data);
      console.log(chartData.value);
    } else {
      console.error("Invalid response data:", response.data);
    }
  } catch (error) {
    console.error(error);
  }
};
interface rowInfo {
  idx: number;
  eng: string;
  kor: string;
}
const costList: rowInfo[] = [
  { idx: 1, eng: "money-type", kor: "유형" },
  { idx: 2, eng: "deposit", kor: "보증금" },
  { idx: 3, eng: "rent", kor: "월세" },
  { idx: 4, eng: "utilities", kor: "공과금" },
];

const detailList: rowInfo[] = [
  { idx: 1, eng: "pet", kor: "반려동물<br>가능여부" },
  { idx: 2, eng: "parking", kor: "주차장 대수" },
  { idx: 3, eng: "roomCnt", kor: "방수" },
  { idx: 4, eng: "bathroomCnt", kor: "화장실수" },
  { idx: 5, eng: "movingDay", kor: "입주가능일" },
  { idx: 6, eng: "floor", kor: "층수" },
  { idx: 7, eng: "direction", kor: "방향" },
];

const optionList: rowInfo[] = [
  { idx: 1, eng: "CHECK_WATER", kor: "수도와 배수" },
  { idx: 2, eng: "CHECK_WINDOW", kor: "창문" },
  { idx: 3, eng: "CHECK_BATHROOM", kor: "화장실" },
  { idx: 4, eng: "CHECK_SURROUNDINGS", kor: "주변환경" },
  { idx: 5, eng: "CHECK_OPTION", kor: "기본 옵션" },
  { idx: 6, eng: "CHECK_DETAIL", kor: "디테일" },
  { idx: 7, eng: "CHECK_SECURITY", kor: "보안" },
  { idx: 8, eng: "CHECK_ETC", kor: "기타사항" },
];

const getTotalScore = (checks: Record<string, number | undefined>) => {
  if (!checks) return 0;
  return Object.entries(checks)
    .filter(([key, val]) => key.startsWith("CHECK") && typeof val === "number")
    .reduce((sum, [, val]) => sum + (val ?? 0), 0);
};
</script>

<style lang="scss" scoped>
// 공통

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
      @include custom-text-bold($font-size: 18px);
      text-align: left;
    }

    .input-content-wrapper {
      display: flex;
      flex-direction: column;
      gap: $padding-small;

      .input-content {
        margin: 0;
      }
    }

    .input-content {
      display: flex;
      align-items: center;
      gap: $padding-small;

      input[tgype="text"] {
        @include custom-input-style-basic;
        padding: 14px;
        box-sizing: border-box;
        flex: 1; /* 남은 공간을 모두 차지하도록 설정 */
      }

      .select-content {
        flex-grow: 1;
      }
    }
    .input-description {
      @include custom-text($font-size: 12px, $font-color: $text-color-light);

      p {
        display: block;
        @include custom-padding-y(4px);
        text-align: left;
      }
    }
  }
}

.custom-flex-column {
  display: flex;
  flex-direction: column;
  gap: $padding-default;
}

.table {
  @include custom-margin-x;

  p {
    text-align: left;
  }

  .table-title {
    @include custom-text($font-size: 18px, $font-weight: 600);
    display: flex;
    flex-direction: row;
    gap: $padding-small;

    img {
      @include custom-icon-style;
    }
  }

  table {
    @include custom-text;
    th,
    td {
      @include custom-padding;
      min-width: 100px;
      line-height: 1.2;
      text-align: center; /* 수평 중앙 정렬 */
      vertical-align: middle; /* 수직 중앙 정렬 */
    }

    // th,
    // tr td:first-child {
    //   @include custom-text($font-size: 16px, $font-weight: 600);
    // }
    th,
    tr {
      @include custom-text($font-size: 16px, $font-weight: 600);
    }

    td {
      @include custom-text;
    }

    tr.special-row td {
      color: $primary-color-default !important;
    }
  }

  .table-content-wrapper {
    overflow-x: auto;
    /* 스크롤바 전체 영역 */
    // &::-webkit-scrollbar {
    //   width: 100%; /* 세로축 스크롤바 폭 너비 */
    //   height: 4px; /* 가로축 스크롤바 폭 너비 */
    // }
    // &::-webkit-scrollbar-button {
    //   display: none;
    // }
    // /* 스크롤바 막대 제외 부분 */
    // &::-webkit-scrollbar-track {
    //   background: transparent;
    // }
    // /* 스크롤바 막대 */
    // &::-webkit-scrollbar-thumb {
    //   border-radius: calc($border-radius-default * 2);
    //   background: #f2f2f2;
    // }
    // Webkit 기반 브라우저 (Chrome, Safari 등)
    &::-webkit-scrollbar {
      display: none;
    }

    // Firefox
    scrollbar-width: none;

    // IE, Edge
    -ms-overflow-style: none;

    .table-wrapper {
      @include custom-margin-y;
      @include custom-padding($padding-size: 12px);
      // background-color: aqua;
      background-color: $light-gray;
      border-radius: $border-radius-default;
      width: fit-content;
    }
    table {
    }
  }
}

// content를 구분해주는 회색 긴 선
.content-indicator {
  background-color: #ededed;
  height: 10px;
}

.result-wrapper {
  @include custom-padding-x;
  flex: 1;
  overflow-y: auto;
  /* 스크롤바 전체 영역 */
  &::-webkit-scrollbar {
    width: 4px; /* 세로축 스크롤바 폭 너비 */
    height: 100%; /* 가로축 스크롤바 폭 너비 */
  }
  &::-webkit-scrollbar-button {
    display: none;
  }
  /* 스크롤바 막대 제외 부분 */
  &::-webkit-scrollbar-track {
    background: transparent;
  }
  /* 스크롤바 막대 */
  &::-webkit-scrollbar-thumb {
    border-radius: calc($border-radius-default * 2);
    background: #f2f2f2;
  }

  .result-bar {
    height: 48px;
    display: flex;
    flex-direction: row; /* 기본값이 row이지만 명시적으로 지정 */
    align-items: center; /* 수직 중앙 정렬 */
    justify-content: flex-start; /* 수평 왼쪽 정렬 */
    gap: 4px;

    p {
      @include custom-text;

      &:first-child {
        font-weight: 600; // weight가 아니라 font-weight
      }

      &.result-cnt {
        @include custom-text(
          $font-color: $primary-color-default,
          $font-size: 16px,
          $font-weight: 400
        );
      }
    }
  }
  hr {
    border: none;
    margin: 0;
    height: 1px;
    background-color: #ccc;
  }
}
.numeric-text {
  @include custom-numeric-text;
}
.chart-wrapper {
  @include custom-padding;
  display: flex;
  align-items: center;
  justify-content: center;
}
// scoped

#compare-container-banner {
  height: 204px;
  display: flex;
  flex-direction: column;
  justify-content: center; /* 수평 중앙 정렬 */
  align-items: center; /* 수직 중앙 정렬 */
  gap: $padding-default;
  background: rgba(255, 255, 255, 0.25);
  backdrop-filter: blur(6px);

  p:first-child {
    @include custom-text($font-size: 20px);
    line-height: 1.5;
  }

  a {
    @include custom-text(
      $font-size: 16px,
      $font-color: #969696,
      $font-weight: 200
    );

    text-decoration: underline; /* 밑줄 추가 */
    text-decoration-thickness: 1px; /* 밑줄 두께 설정 (얇은 두께) */
  }
}

#compare-container-banner-wrapper {
  position: relative;
}

.emoji-image {
  position: absolute;
}
.house-tab-wrapper {
  @include custom-padding-x;
  display: flex;
  flex-direction: column;
  gap: $padding-small;

  .house-tab {
    @include custom-padding;

    &:first-child {
      box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.16);
      border: 1px solid $primary-color-default;

      .numeric-text {
        color: $primary-color-default;
      }
    }

    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: flex-start;

    gap: $padding-default;

    border-radius: $border-radius-default;

    .house-info {
      @include custom-text(
        $font-color: $text-color-light,
        $font-size: 12px,
        $font-weight: 400
      );
      display: flex;
      flex-direction: column;
      gap: $padding-extra-small;
      flex: 1;
      align-items: flex-start;
      justify-content: center;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;

      .house-info-title {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: start;
        text-align: left;
        gap: $padding-extra-small;

        p:first-child {
          @include custom-text($font-size: 16px, $font-weight: 400);
        }
      }

      .house-info-content {
        display: flex;
        flex-direction: column;
        gap: 2px;
        text-align: left;
      }
    }
  }
}

table {
  background-color: $light-gray;
  border-radius: $border-radius-default;
  border: none; /* 바깥쪽 테두리 제거 */

  th,
  td {
    border: 1px solid #ddd; /* 셀 안쪽의 테두리 설정 */
  }

  th {
    width: fit-content;
  }
  span {
    @include custom-text($font-size: 8px, $font-color: $text-color-light);
  }
  th:first-child,
  td:first-child {
    border-left: 0;
  }
  th:last-child,
  td:last-child {
    border-right: 0;
  }

  th {
    border-top: 0;
  }

  tr:last-child td {
    border-bottom: 0;
  }
}
</style>
