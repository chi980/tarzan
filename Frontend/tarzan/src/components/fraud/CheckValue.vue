<template>
  <div class="sub-container">
    <TopBarBack title="시세 확인하기" />
    <div class="center-container">
      <div class="address-conatienr">
        <h2 class="title">주소</h2>
        <div class="content">
          <div class="input-wapper">
            <CustomSelectBox :options="seoulDistrictOptions" />
            <CustomSelectBox :options="dongOptions" />
          </div>
          <div class="input-wapper">
            <CustomSelectBox :options="yearOptions" />
            <CustomSelectBox :options="monthOptions" />
          </div>
        </div>
      </div>

      <div class="transaction-history-container">
        <ResultBar resultTitle="거래 내역" />
        <Divider />
        <div class="content">
          <TransactionHistory />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import CustomSelectBox from '../common/CustomSelectBox.vue';
import Divider from '../common/Divider.vue';
import ResultBar from '../common/ResultBar.vue';
import TopBarBack from '../common/TopBarBack.vue';
import TransactionHistory from './TransactionHistory.vue';

export default {
  components: {
    TopBarBack,
    CustomSelectBox,
    ResultBar,
    Divider,
    TransactionHistory,
  },
  data() {
    return {
      seoulDistrictOptions: [
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
      dongOptions: [
        { idx: 1, name: "신계동", value: "trans" },
        { idx: 2, name: "효창동", value: "food" },
        { idx: 3, name: "청파동", value: "tip" },
        { idx: 4, name: "원효로 1동", value: "question" },
        { idx: 5, name: "원효로 2동", value: "?" }
      ],
      yearOptions: this.generateYearOptions(),
      monthOptions: [
        { idx: 1, name: "1월", value: "1" },
        { idx: 2, name: "2월", value: "2" },
        { idx: 3, name: "3월", value: "3" },
        { idx: 4, name: "4월", value: "4" },
        { idx: 5, name: "5월", value: "5" },
        { idx: 6, name: "6월", value: "6" },
        { idx: 7, name: "7월", value: "7" },
        { idx: 8, name: "8월", value: "8" },
        { idx: 9, name: "9월", value: "9" },
        { idx: 10, name: "10월", value: "10" },
        { idx: 11, name: "11월", value: "11" },
        { idx: 12, name: "12월", value: "12" },
      ],
    }
  },
  methods: {
    generateYearOptions() {
      const currentYear = new Date().getFullYear();
      const startYear = 2000; // 선택 가능한 시작 연도 설정
      let options = [];

      for (let year = startYear; year <= currentYear; year++) {
        options.push({ 
          idx: year - startYear + 1, 
          name: `${year}년`, 
          value: year.toString() });
      }

      return options;
    }
  }
}
</script>
<style lang="scss" scoped>
  .center-container {
    display: flex;
    flex-direction: column;
    flex-grow: 1;
    width: 100%;
    gap: 10px;
    background-color: #EDEDED;
  }

  .address-conatienr {
    background-color: white;
    @include custom-padding-x;
  }

  .address-conatienr .title {
    text-align: left;
    @include custom-text-bold($font-size: 16px);
    @include custom-padding-y;
  }

  .address-conatienr .content {
    display: flex;
    gap: 8px;
    padding-bottom: 16px;
  }

  .address-conatienr .content .input-wapper {
    width: 100%;
    display: flex;
    flex-direction: column;
    gap: 16px;
  }

  .transaction-history-container {
    background-color: white;
    flex-grow: 1;
    @include custom-padding-x;
  }
</style>