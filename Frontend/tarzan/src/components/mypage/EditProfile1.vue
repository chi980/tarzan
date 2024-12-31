<template>
  <div class="sub-container">
    <TopBarBack title="수정 하기" />
    <div class="center-container">

      <div class="edit-form">
        <div class="input-section">
          <h3 class="input-title">닉네임</h3>
          <div class="nickname">
            <div class="nickname-input">
              <input type="text" placeholder="아이고" />
              <button>중복확인</button>
            </div>
            <span>이미 존재하는 닉네임입니다</span>
          </div>
          <p class="nickname-guidelines">
            닉네임은 영문, 숫자로 이루어져야 합니다<br>
            닉네임은 6글자 이상 10글자 이하여야 합니다
          </p>
        </div>

        <div class="input-section">
          <h3 class="input-title">사는 곳</h3>
          <CustomSelectBox :options = seoulDistrictOption />
        </div>

        <div class="input-section">
          <h3 class="input-title">반려동물 유뮤</h3>
          <CustomSelectBox :options=petOptions />
        </div>

        <div class="input-section">
          <h3 class="input-title">자차 유뮤</h3>
          <CustomSelectBox :options=carOptions />
        </div>
      </div>

      <button class="edit-submit-button">수정하기</button>

    </div>
  </div>
</template>
<!-- <script>
import TopBarBack from '../common/TopBarBack.vue';
import CustomSelectBox from '../common/CustomSelectBox.vue';

export default {
  components: {
    TopBarBack,
    CustomSelectBox,
  },
  data() {
    return {
      seoulDistrictOption: [
        { idx: 1, name: "서울시 종로구", value: "JONGNO" },
        { idx: 2, name: "서울시 중구", value: "JUNG" },
        { idx: 3, name: "서울시 용산구", value: "YONGSAN" },
        { idx: 4, name: "서울시 성동구", value: "SEONGDONG" },
        { idx: 5, name: "서울시 광진구", value: "GWANGJIN" },
        { idx: 6, name: "서울시 동대문구", value: "DONGDAEMUN" },
        { idx: 7, name: "서울시 중랑구", value: "JUNGNANG" },
        { idx: 8, name: "서울시 성북구", value: "SEONGBUK" },
        { idx: 9, name: "서울시 강북구", value: "GANGBUK" },
        { idx: 10, name: "서울시 도봉구", value: "DOBONG" },
        { idx: 11, name: "서울시 노원구", value: "NOWON" },
        { idx: 12, name: "서울시 은평구", value: "EUNPYEONG" },
        { idx: 13, name: "서울시 서대문구", value: "SEODAEMUN" },
        { idx: 14, name: "서울시 마포구", value: "MAPO" },
        { idx: 15, name: "서울시 양천구", value: "YANGCHEON" },
        { idx: 16, name: "서울시 강서구", value: "GANGSEO" },
        { idx: 17, name: "서울시 구로구", value: "GURO" },
        { idx: 18, name: "서울시 금천구", value: "GEUMCHEON" },
        { idx: 19, name: "서울시 영등포구", value: "YEONGDEUNGPO" },
        { idx: 20, name: "서울시 동작구", value: "DONGJAK" },
        { idx: 21, name: "서울시 관악구", value: "GWANAK" },
        { idx: 22, name: "서울시 서초구", value: "SEOCHO" },
        { idx: 23, name: "서울시 강남구", value: "GANGNAM" },
        { idx: 24, name: "서울시 송파구", value: "SONGPA" },
        { idx: 25, name: "서울시 강동구", value: "GANGDONG" },
      ],
      petOptions: [
        { idx: 1, name: "반려동물 없음", value: "DONTHAVE" },
        { idx: 2, name: "반려동물 있음", value: "HAVE" },
      ],
      carOptions: [
        { idx: 1, name: "차 없음", value: "DONTHAVE" },
        { idx: 2, name: "차 있음", value: "HAVE" },
      ]
    }
  }
}
</script> -->
<script setup>
import TopBarBack from '../common/TopBarBack.vue';
import CustomSelectBox from '../common/CustomSelectBox.vue';

const seoulDistrictOption = [
  { idx: 1, name: "서울시 종로구", value: "JONGNO" },
  { idx: 2, name: "서울시 중구", value: "JUNG" },
  { idx: 3, name: "서울시 용산구", value: "YONGSAN" },
  { idx: 4, name: "서울시 성동구", value: "SEONGDONG" },
  { idx: 5, name: "서울시 광진구", value: "GWANGJIN" },
  { idx: 6, name: "서울시 동대문구", value: "DONGDAEMUN" },
  { idx: 7, name: "서울시 중랑구", value: "JUNGNANG" },
  { idx: 8, name: "서울시 성북구", value: "SEONGBUK" },
  { idx: 9, name: "서울시 강북구", value: "GANGBUK" },
  { idx: 10, name: "서울시 도봉구", value: "DOBONG" },
  { idx: 11, name: "서울시 노원구", value: "NOWON" },
  { idx: 12, name: "서울시 은평구", value: "EUNPYEONG" },
  { idx: 13, name: "서울시 서대문구", value: "SEODAEMUN" },
  { idx: 14, name: "서울시 마포구", value: "MAPO" },
  { idx: 15, name: "서울시 양천구", value: "YANGCHEON" },
  { idx: 16, name: "서울시 강서구", value: "GANGSEO" },
  { idx: 17, name: "서울시 구로구", value: "GURO" },
  { idx: 18, name: "서울시 금천구", value: "GEUMCHEON" },
  { idx: 19, name: "서울시 영등포구", value: "YEONGDEUNGPO" },
  { idx: 20, name: "서울시 동작구", value: "DONGJAK" },
  { idx: 21, name: "서울시 관악구", value: "GWANAK" },
  { idx: 22, name: "서울시 서초구", value: "SEOCHO" },
  { idx: 23, name: "서울시 강남구", value: "GANGNAM" },
  { idx: 24, name: "서울시 송파구", value: "SONGPA" },
  { idx: 25, name: "서울시 강동구", value: "GANGDONG" },
];

const petOptions = [
  { idx: 1, name: "반려동물 없음", value: "DONTHAVE" },
  { idx: 2, name: "반려동물 있음", value: "HAVE" },
];

const carOptions = [
  { idx: 1, name: "차 없음", value: "DONTHAVE" },
  { idx: 2, name: "차 있음", value: "HAVE" },
];
</script>

<style lang="scss" scoped>
  .sub-container {
    display: flex;
    flex-direction: column;
  }

  .center-container {
    flex-grow: 1;
    display: flex;
    flex-direction: column;
  }

  .edit-form {
    display: flex;
    flex-direction: column;
    flex-grow: 1;
    gap: 24px;
    @include custom-margin-x;
    @include custom-margin-y(24px);
  }

  .edit-form .input-section {
    text-align: left;
    display: flex;
    flex-direction: column;
    gap: 16px;
  }

  .edit-form .input-section .input-title {
    @include custom-text-bold($font-size: 18px);
  }

  .edit-form .input-section .nickname-input {
    display: flex;
    gap: 8px;
    align-items: center
  }

  .edit-form .input-section .nickname input {
    @include custom-input-style;
  }

  .edit-form .input-section .nickname .nickname-input button {
    background-color: #E0F9ED;
    color: $primary-color-400;
    font-size: 12px;
    border-radius: 14;
    width: 70px;
    height: 35px;
    white-space: nowrap;
  }

  .edit-form .input-section .nickname span {
    font-size: 12px;
    color: red;
  }

  .edit-form .input-section .nickname-guidelines {
    font-size: 12px;
    color: #878787;
    line-height: 1.5;
  }

  .edit-submit-button {
    @include custom-margin-x(8px);
    @include custom-margin-y(24px);
    @include custom-text-bold($font-size: 14px);
    background-color: black;
    color: white;
    height: 48px;
  }
  
</style>