<template>
  <div class="sub-container">
    <TopBarBack title="후기 작성하기" />

    <div class="center-container">
      <div class="address-card-container">
        <AddressCard />
      </div>
      
      <div class="input-container">

        <div class="input-group" id="장점">
          <h2 class="input-title">장점 (50자 이상)</h2>
          <textarea class="input-content" type="text" placeholder="내용를 입력해주세요." />
          <TagButtonGroup />
        </div>

        <div class="input-group" id="단점">
          <h2 class="input-title">단점 (50자 이상)</h2>
          <textarea class="input-content" type="text" placeholder="내용를 입력해주세요." />
          <TagButtonGroup />
        </div>

        <button @click="goToNextPage">후기 작성 완료</button>

      </div>
    </div>
  </div>
</template>
<script>
import TopBarBack from "@/components/common/TopBarBack.vue"
import AddressCard from "./AddressCard.vue"
import TagButtonGroup from "@/components/common/TagButtonGroup.vue"

export default {
  name: 'CreateReview2',
  components: {
    TopBarBack,
    AddressCard,
    TagButtonGroup,
  },
  methods: {
    goToNextPage() {
      this.$router.push({ name: 'Review' });
    }
  },
}
</script>
<style lang="scss" scoped>
  .center-container {
    display: flex;
    flex-direction: column;
    flex-grow: 1;
    width: 100%;
    box-sizing: border-box;
    @include custom-padding-x;
    gap: 32px;

    overflow-y: auto;
    @include custom-scrollbar-style;
  }

  .address-card-container {
    margin-top: 16px;
  }

  .input-container {
    display: flex;
    flex-direction: column;
    gap: 32px;
  }
  
  .input-container .input-group {
    display: flex;
    flex-direction: column;
    gap: 16px;
  }

  .input-container .input-group .input-title {
    @include custom-text-bold;
    text-align: left;
  }

  .input-container .input-group .input-content {
    @include custom-input-style;
    text-align: left;
    flex-basis: 120px;
    padding: 16px;
  }

  :deep(.tag-button-container) {
    overflow-x: auto;
    flex-wrap: wrap;
  }

  button {
    margin-bottom: 26px;
  }
</style>