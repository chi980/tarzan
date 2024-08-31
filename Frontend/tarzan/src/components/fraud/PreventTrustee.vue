<template>
  <div class="sub-container">
    <TopBarBack title="신탁 임대인 예방" />
    <div class="center-container">
      <div class="accordian-section">
        <BasicAccordion accordionTitle = "1. 신탁 여부 확인하기">
          <div class="content-container">
            <div class="content-wapper">
              <span class="content-main">
                등기부등본을 통해 부동산이 신탁되었는지
                여부를 확인해야만 해요.
              </span>
              <p class="content-sub">
                등기부등본 서류에는 표제부, 갑구, 을구가 있어요. 
                이 중 ‘갑구’를 보면, ‘신탁’ 여부를 바로 알아볼 수 있어요. 
                임대인이 신탁회사에게 신탁 계약을 맺었다는 것이 ‘갑구’에 적혀 
                있다면, 진짜 소유권은 임대인에게 없어요. 
                보통은 임대인이 곧 집의 소유주인지 확인한 뒤 계약서에 서명을
                하는데, 만약 신탁등기라면 이렇게 계약해선 안 돼요.
              </p>
            </div>
            <div class="content-prevention">
                <span>등기부등본 확인하기</span>
                <img id="post-write-icon" src="@/assets/icons/Arrows-chevron/Arrow-up/Arrow-Right-Up.svg" />
              </div>
          </div>
        </BasicAccordion>
        <BasicAccordion accordionTitle = "2. 신탁이 맞는 경우">
          
          <div class="content-container">
            <span class="content-main">
              만일 등기부등본을 확인했는데 신탁등기가 되어 있다면<br>
              <span id="red-text">서류를 추가적으로 확인</span>
              해야 해요.
            </span>
            <div class="content-wapper">
              <span class="content-main">
                ‘신탁원부’란?
              </span>
              <p class="content-sub">
                위탁자와 수탁자 그리고 수익자와 신탁관리인의 성명 및 주소, 
                신탁의 목적, 신탁재산의 관리방법, 신탁 종료의 사유 등을 
                포함한 서류
                <br><br>
                따라서 신탁원부를 확인하면 누구에게 이 부동산을 임대해줄 
                권한이 있는지, 해당 부동산을 담보로 받은 대출이 있는지 등을 
                알 수 있어요. 
              </p>
            </div> 
            <div class="content-wapper">
              <span class="content-main">
                ‘신탁원부’ 발급 방법
              </span>
              <p class="content-sub">
                신탁원부는 인터넷 으로는 발급받을 수 없기 때문에 등기소에서 
                직접 발급받아야만 해요. 
                <br><br>
                그리고 한 가지 말씀드리자면, 신탁원부를 통해 권리관계를 
                파악하는 일이 개인에게는 다소 생소하고 어려울 수 있기 때문에 
                전문가의 도움을 받기를 추천해요.
              </p>               
            </div>
          </div>
        </BasicAccordion>
      </div>
    </div>
  </div>
</template>
<script>
import BasicAccordion from '../common/BasicAccordion.vue';
import TopBarBack from '../common/TopBarBack.vue';

export default {
  components: {
    TopBarBack,
    BasicAccordion,
  }
}
</script>
<style lang="scss" scoped>
  .sub-container {
    display: flex;
    flex-direction: column;
  }
  .center-container {
    flex-grow: 1;
    text-align: left;
    width: 100%;
  }

  .accordian-section {
    margin-top: $margin-big;
    display: flex;
    flex-direction: column;
    gap: $padding-small;
  }

  .accordian-section .content-container {
    padding: $padding-default;
    display: flex;
    flex-direction: column;
    gap: $padding-default;
  }

  .accordian-section .content-container .content-main {
    @include custom-text($text-color-default, 12px, 500);
    line-height: 1.5;
  }

  .accordian-section .content-container .content-sub {
    @include custom-text($text-color-light, 12px, 500);
    line-height: 1.3;
  }

  .accordian-section .content-container .content-prevention {
    display: flex;
    align-items: center;
    @include custom-text($primary-color-default, 12px, 500);
    text-decoration: underline;
  }

  #red-text {
    color: $warning-color;
  }
</style>