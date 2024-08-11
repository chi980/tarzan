<template>
  <div class="sub-container non-input-sub-container">
    <div>
      <div class="top-bar-back">
        <p>비교하기</p>
      </div>
    </div>
    <div class="center-container">
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
          "
        />
        <img
          :src="emojiSrc"
          class="emoji-image"
          style="
            width: 20%;
            height: auto;
            top: 30%;
            right: 8%;
            transform: rotate(25deg);
          "
        />
        <img
          :src="emojiSrc"
          class="emoji-image"
          style="
            width: 14%;
            height: auto;
            bottom: 10%;
            left: 10%;
            transform: rotate(-20deg);
          "
        />
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
          :key="house.idx"
          class="house-tab"
        >
          <p class="numeric-text">{{ index + 1 }}</p>
          <div class="house-info">
            <div class="house-info-title">
              <p>{{ house.name }}</p>
              <p>{{ house.type }}</p>
            </div>
            <div class="house-info-content">
              <p>{{ house.address }}</p>
            </div>
          </div>
          <p class="numeric-text">{{ house.score }}</p>
        </div>
      </div>
      <div>
        <div class="content-indicator"></div>
      </div>
      <div class="custom-flex-column" style="margin-bottom: 30px">
        <div class="table-wrapper">
          <div class="table-title">
            <img :src="checkImgSrc" />
            <p>가격</p>
          </div>
          <div class="table-content-wrapper">
            <table>
              <thead>
                <tr>
                  <th></th>
                  <th
                    v-for="(house, index) in housesToCompare"
                    :key="house.idx"
                  >
                    {{ house.name }}
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(row, index) in costList" :key="row.idx">
                  <td>{{ row.kor }}</td>
                  <td v-for="house in housesToCompare" :key="house.idx">
                    {{ house[row.eng] }}
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="table-wrapper">
          <div class="table-title">
            <img :src="checkImgSrc" />
            <p>체크 사항</p>
          </div>

          <div class="table-content-wrapper">
            <table>
              <thead>
                <tr>
                  <th></th>
                  <th
                    v-for="(house, index) in housesToCompare"
                    :key="house.idx"
                  >
                    {{ house.name }}
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(row, index) in checkList" :key="row.idx">
                  <td v-html="row.kor"></td>
                  <td v-for="house in housesToCompare" :key="house.idx">
                    {{ house[row.eng] }}
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="table-wrapper">
          <div class="table-title">
            <img :src="checkImgSrc" />
            <p>옵션</p>
          </div>

          <div class="table-content-wrapper">
            <table>
              <thead>
                <tr>
                  <th></th>
                  <th
                    v-for="(house, index) in housesToCompare"
                    :key="house.idx"
                  >
                    {{ house.name }}
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(row, index) in optionList" :key="row.idx">
                  <td v-html="row.kor"></td>
                  <td v-for="house in housesToCompare" :key="house.idx">
                    {{ house[row.eng] }}
                  </td>
                </tr>

                <tr class="special-row">
                  <td>총계</td>
                  <td v-for="house in housesToCompare" :key="house.idx">
                    {{ house.totalScore }}
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
import { ref } from "vue";
import emojiSrc from "@/assets/emoji/face-with-monocle.png";
import checkImgSrc from "@/assets/icons/Check/Check.svg";
import CompareHouse from "@/data/house.ts";

const housesToCompare: CompareHouse[] = [
  {
    idx: 1,
    name: "원룸팰리스",
    type: "빌라, 주택",
    address: "서울 강남구 논현로71길 13 (우)06248",
    score: 70,

    moneyType: "전세", // 예시 값
    deposit: 5000, // 예시 값 (단위: 만원)
    rent: 0, // 예시 값
    utilities: 300, // 예시 값 (단위: 만원)

    pet: "가능", // 예시 값
    parking: "있음", // 예시 값
    roomCnt: 1, // 예시 값
    bathroomCnt: 1, // 예시 값
    movingDay: new Date("2024-09-01"), // 예시 값
    floor: 3, // 예시 값
    direction: "남향", // 예시 값

    water: 4, // 예시 값 (평가 점수)
    window: 5, // 예시 값 (평가 점수)
    bathroom: 3, // 예시 값 (평가 점수)
    surrounding: 4, // 예시 값 (평가 점수)
    option: 5, // 예시 값 (평가 점수)
    detail: 4, // 예시 값 (평가 점수)
    security: 3, // 예시 값 (평가 점수)
    etc: 2, // 예시 값 (평가 점수)
    totalScore: 30, // 예시 값 (모든 평가 점수의 합계)
  },

  {
    idx: 3,
    name: "홍시 빌라",
    type: "빌라, 주택",
    address: "서울 강남구 논현로70길 1 (우)06142",
    score: 50,

    moneyType: "월세", // 예시 값
    deposit: 1000, // 예시 값 (단위: 만원)
    rent: 50, // 예시 값 (단위: 만원)
    utilities: 100, // 예시 값 (단위: 만원)

    pet: "불가능", // 예시 값
    parking: "없음", // 예시 값
    roomCnt: 2, // 예시 값
    bathroomCnt: 1, // 예시 값
    movingDay: new Date("2024-10-01"), // 예시 값
    floor: 2, // 예시 값
    direction: "북향", // 예시 값

    water: 3, // 예시 값 (평가 점수)
    window: 4, // 예시 값 (평가 점수)
    bathroom: 4, // 예시 값 (평가 점수)
    surrounding: 3, // 예시 값 (평가 점수)
    option: 3, // 예시 값 (평가 점수)
    detail: 4, // 예시 값 (평가 점수)
    security: 2, // 예시 값 (평가 점수)
    etc: 3, // 예시 값 (평가 점수)
    totalScore: 25, // 예시 값 (모든 평가 점수의 합계)
  },
  {
    idx: 3,
    name: "홍시 빌라",
    type: "빌라, 주택",
    address: "서울 강남구 논현로70길 1 (우)06142",
    score: 50,

    moneyType: "월세", // 예시 값
    deposit: 1000, // 예시 값 (단위: 만원)
    rent: 50, // 예시 값 (단위: 만원)
    utilities: 100, // 예시 값 (단위: 만원)

    pet: "불가능", // 예시 값
    parking: "없음", // 예시 값
    roomCnt: 2, // 예시 값
    bathroomCnt: 1, // 예시 값
    movingDay: new Date("2024-10-01"), // 예시 값
    floor: 2, // 예시 값
    direction: "북향", // 예시 값

    water: 3, // 예시 값 (평가 점수)
    window: 4, // 예시 값 (평가 점수)
    bathroom: 4, // 예시 값 (평가 점수)
    surrounding: 3, // 예시 값 (평가 점수)
    option: 3, // 예시 값 (평가 점수)
    detail: 4, // 예시 값 (평가 점수)
    security: 2, // 예시 값 (평가 점수)
    etc: 3, // 예시 값 (평가 점수)
    totalScore: 25, // 예시 값 (모든 평가 점수의 합계)
  },
];

interface rowInfo {
  idx: number;
  eng: string;
  kor: string;
}
const costList: rowInfo[] = [
  { idx: 1, eng: "moneyType", kor: "유형" },
  { idx: 2, eng: "deposit", kor: "보증금" },
  { idx: 3, eng: "rent", kor: "월세" },
  { idx: 4, eng: "utilities", kor: "공과금" },
];

const checkList: rowInfo[] = [
  { idx: 1, eng: "pet", kor: "반려동물<br>가능여부" },
  { idx: 2, eng: "parking", kor: "주차장 대수" },
  { idx: 3, eng: "roomCnt", kor: "방수" },
  { idx: 4, eng: "bathroomCnt,", kor: "화장실수" },
  { idx: 5, eng: "movingDay,", kor: "입주가능일" },
  { idx: 6, eng: "floor,", kor: "층수" },
  { idx: 7, eng: "direction,", kor: "방향" },
];

const optionList: rowInfo[] = [
  { idx: 1, eng: "water", kor: "수도와 배수" },
  { idx: 2, eng: "window", kor: "창문" },
  { idx: 3, eng: "bathroom", kor: "화장실" },
  { idx: 4, eng: "surrounding,", kor: "주변환경" },
  { idx: 5, eng: "option,", kor: "기본 옵션" },
  { idx: 6, eng: "detail,", kor: "디테일" },
  { idx: 7, eng: "security,", kor: "보안" },
  { idx: 8, eng: "etc,", kor: "기타사항" },
];
</script>

<style lang="scss" scoped>
// 공통
.top-bar-back {
  @include custom-bar-style(
    $height: $height-top-bar,
    $z-index: $z-index-top-bar
  );
}

.center-container {
  position: relative;
  flex-grow: 1;
  width: 100%;

  display: flex;
  flex-direction: column;

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
}
.bottom-button-wrapper {
  @include custom-padding-x;

  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;

  div {
    @include custom-button-style($height: 54px, $font-color: white);
  }
}
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

.table-wrapper {
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
    th,
    td {
      @include custom-padding;
      min-width: 100px;
      line-height: 1.2;
      text-align: center; /* 수평 중앙 정렬 */
      vertical-align: middle; /* 수직 중앙 정렬 */
    }

    th,
    tr td:first-child {
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
    &::-webkit-scrollbar {
      width: 100%; /* 세로축 스크롤바 폭 너비 */
      height: 4px; /* 가로축 스크롤바 폭 너비 */
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

    table {
      margin-top: $margin-default;
      margin-bottom: 4px;
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

      .house-info-title {
        display: flex;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        gap: $padding-extra-small;

        p:first-child {
          @include custom-text($font-size: 16px, $font-weight: 400);
        }
      }

      .house-info-content {
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
