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

          <TagButtonGroup 
            v-model:selectedButtons="selectedAdvantageButtons"
            :buttons="advantageButtons"
            :multiple="true">
            <template v-slot:default="{ button }">
              <span>{{ button.label }}</span>
            </template>
          </TagButtonGroup>

        </div>

        <div class="input-group" id="단점">
          <h2 class="input-title">단점 (50자 이상)</h2>
          <textarea class="input-content" type="text" placeholder="내용를 입력해주세요." />
          <TagButtonGroup 
            v-model:selectedButtons="selectedDisadvantageButtons"
            :buttons="disadvantageButtons"
            :multiple="true">
            <template v-slot:default="{ button }">
              <span>{{ button.label }}</span>
            </template>
          </TagButtonGroup>
        </div>

        <button @click="goToNextPage">후기 작성 완료</button>

      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import TopBarBack from "@/components/common/TopBarBack.vue";
import AddressCard from "./AddressCard.vue";
import TagButtonGroup from "@/components/common/TagButtonGroup.vue";

const router = useRouter();

// 장점 리스트
const advantageButtons = ref([
  { label: '깨끗함', value: 'CLEAN' },
  { label: '조용함', value: 'QUIET' },
  { label: '편리함', value: 'CONVENIENT' },
  { label: '친절함', value: 'KIND' },
]);

// 단점 리스트
const disadvantageButtons = ref([
  { label: '시끄러움', value: 'NOISY' },
  { label: '비쌈', value: 'EXPENSIVE' },
  { label: '불친절함', value: 'UNFRIENDLY' },
  { label: '불편함', value: 'INCONVENIENT' },
]);

// 선택된 태그
const selectedAdvantageButtons = ref([]);  // 장점 태그 선택
const selectedDisadvantageButtons = ref([]); // 단점 태그 선택

const goToNextPage = () => {
  router.push({ name: 'Review' });
};
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