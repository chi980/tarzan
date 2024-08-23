<template>
  <div class="sub-container">
    <TopBarBack title="임대인 확인" />

    <div class="center-container">
      <div class="title">
        <img id="check-icon" src="@/assets/icons/Check/Check.svg" alt="check-icon">
        <span>임대인 확인 시 전세사기 유형 및 대처방법</span>
      </div>

      <div class="accordion-wrapper">
        <BasicAccordion accordionTitle="가짜 임대인과의 계약">
          <div class="content-wrapper">
            <div class="content">
              <img class="content-img" src="@/assets/fake_landlord.png" alt="fake-landlord.png">
              <p class="content-main">
                ‘가짜 임대인’이 건물을 임대하면서 알아낸 ‘실제 임대인’의 인적사항을 이용하여 
                자신이 건물주인 척 거짓된 전세계약을 한 후 세입자의 보증금을 가로채는 거죠. 
              </p>
              <p class="content-sub">
                혹은 임대인의 위임장 또는 증명서류를 위조해 
                대리인 행세를 하면서 세입자의 보증금을 가로채기도 해요.
              </p>
            </div>

            <div class="content">
              <p class="content-main">예방법</p>
              <p class="content-sub">
                1. 등기부등본 임대인이 계약 당사자인지 확인하기<br>
                2. 임대인 신분증 확인하기<br>
                3. 대리인 확인절차 거치기
              </p>
            </div>
          </div>
        </BasicAccordion>

        <BasicAccordion accordionTitle="신탁회사의 동의 없는 계약">
          <div class="content-wrapper">
            <div class="content">
              <img class="content-img" src="@/assets/fake_landlord.png" alt="fake-landlord.png">
              <p class="content-main">
                신탁이란?
                임대인이 주택을 비롯한 자신의 부동산을 전문가에게 맡기는 걸 의미해요. 
                임대인은 주택을 담보로 대출을 받을 수도 있고요, 
                혹은 다른 수익을 얻기 위해서도 신탁을 해요.
              </p>
              <p class="content-sub">
                혹은 임대인의 위임장 또는 증명서류를 위조해 대리인 행세를 하면서 세입자의 보증금을 가로채기도 해요.
              </p>
            </div>

            <div class="content">
              <p class="content-main">사례</p>
              <p class="content-sub">
                2019년, 마산에 사는 임대인 A씨는 2019년 1월에 한 오피스텔을 신탁회사에 
                넘기고 그 증서를 담보로 거액의 대출을 받았어요. 
                그럼 임대인 A씨에게는 신탁회사의 동의 없이는 집을 임대할 권한이 없어져요.  
                하지만 A씨는 이를 속이고 15가구의 세입자들과 계약을 강행하여 보증금을 가로챘어요. 
                신탁회사 동의 없이 계약했으니, 계약 자체가 무효가 되어 세입자들은 하루아침에 쫓겨나게 됐죠. 
                그렇게 세입자들은 총 5억 원 규모의 신탁 부동산 사기 피해를 입게 됐어요.
              </p>
            </div>

            <div class="content">
              <p class="content-main">예방법</p>
              <p class="content-sub">
                1. 신탁 여부 확인하기 -> 등기부등본 확인<br>
                2. 신탁이 맞다면 -> 임대해줄 권한자 확인
              </p>
            </div>
          </div>
        </BasicAccordion>
      </div>
    </div>
  </div>
</template>
<script>
import TopBarBack from '../common/TopBarBack.vue';
import BasicAccordion from "@/components/common/BasicAccordion.vue";

export default {
  components: {
    TopBarBack,
    BasicAccordion,
  }
}
</script>
<style lang="scss" scoped>
  .content-img {
    width: 292px;
  }
</style>