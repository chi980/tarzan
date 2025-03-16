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
          <textarea class="input-content" type="text" placeholder="내용를 입력해주세요." v-model="reviewStore.reviewData.review_advantage" />

          <TagButtonGroup 
            v-model:selectedButtons="reviewStore.reviewData.review_advantage_tags"
            :buttons="advantageButtons"
            :multiple="true">
            <template v-slot:default="{ button }">
              <span>{{ button.label }}</span>
            </template>
          </TagButtonGroup>
          <!-- <TagButtonGroup 
            v-model:selectedButtons="selectedAdvantageButtons"
            :buttons="advantageButtons"
            :multiple="true">
            <template v-slot:default="{ button }">
              <span>{{ button.label }}</span>
            </template>
          </TagButtonGroup> -->

        </div>

        <div class="input-group" id="단점">
          <h2 class="input-title">단점 (50자 이상)</h2>
          <textarea class="input-content" type="text" placeholder="내용를 입력해주세요." v-model="reviewStore.reviewData.review_disadvantage" />
          <TagButtonGroup 
            v-model:selectedButtons="reviewStore.reviewData.review_disadvantage_tags"
            :buttons="disadvantageButtons"
            :multiple="true">
            <template v-slot:default="{ button }">
              <span>{{ button.label }}</span>
            </template>
          </TagButtonGroup>
          <!-- <TagButtonGroup 
            v-model:selectedButtons="selectedDisadvantageButtons"
            :buttons="disadvantageButtons"
            :multiple="true">
            <template v-slot:default="{ button }">
              <span>{{ button.label }}</span>
            </template>
          </TagButtonGroup> -->
        </div>

        <button @click="submitReview">후기 작성 완료</button>

      </div>
    </div>
  </div>
</template>

<script setup>
import { useReviewStore } from "@/stores/reviewStore";
import { createReview } from "@/api/reviewApi";
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import TopBarBack from "@/components/common/TopBarBack.vue";
import AddressCard from "./AddressCard.vue";
import TagButtonGroup from "@/components/common/TagButtonGroup.vue";

const router = useRouter();
const reviewStore = useReviewStore();

// 장점 리스트
const advantageButtons = ref([
  { label: '❌ 없음', value: 'NONE' },
  { label: '🐛 벌레', value: 'BUG' },
  { label: '🧹 건물 관리', value: 'BUILDING_MANAGEMENT' },
  { label: '🚘 주차', value: 'PARKING' },
  { label: '🔇 방음', value: 'SOUND_PROOF' },
  { label: '🚨 치안', value: 'SECURITY' },
  { label: '👮🏻 경비실', value: 'SECURITY_GUARD' },
  { label: '🌳 산책로', value: 'TRAIL' },
  { label: '🪟 환기', value: 'VENTILATION' },
  { label: '🤫 조용한 동네', value: 'QUITE' },
  { label: '🥶 단열', value: 'CLEAN' },
  { label: '🐶 반려동물 키우기', value: 'PREVENT_HEAT' },
  { label: '🛗 엘레베이터', value: 'ELEVATOR' },
  { label: '🦠 곰팡이', value: 'MOLD' },
  { label: '👟 평지', value: 'FLAT' },
]);

// 단점 리스트
const disadvantageButtons = ref([
  {  label: '❌ 없음', value: 'NONE' },
  { label: '🐛 벌레', value: 'BUG' },
  { label: '🧹 건물 관리', value: 'BUILDING_MANAGEMENT' },
  { label: '🚘 주차', value: 'PARKING' },
  { label: '🔇 방음', value: 'SOUND_PROOF' },
  { label: '🚨 치안', value: 'SECURITY' },
  { label: '👮🏻 경비실', value: 'SECURITY_GUARD' },
  { label: '🌳 산책로', value: 'TRAIL' },
  { label: '🪟 환기', value: 'VENTILATION' },
  { label: '🤫 조용한 동네', value: 'QUITE' },
  { label: '🥶 단열', value: 'CLEAN' },
  { label: '🐶 반려동물 키우기', value: 'PREVENT_HEAT' },
  { label: '🛗 엘레베이터', value: 'ELEVATOR' },
  { label: '🦠 곰팡이', value: 'MOLD' },
  { label: '👟 평지', value: 'FLAT' },
]);

// 선택된 태그
// const selectedAdvantageButtons = ref([]);  // 장점 태그 선택
// const selectedDisadvantageButtons = ref([]); // 단점 태그 선택

const submitReview = async () => {
  try {
    await createReview(reviewStore.reviewData);
    alert("리뷰가 성공적으로 등록되었습니다!");
    reviewStore.resetReviewData(); // 데이터 초기화
    router.push("/review"); // 리뷰 리스트 페이지로 이동
  } catch (error) {
    alert("리뷰 등록에 실패했습니다.");
  }
  console.log("🚀 CreateReview2:", reviewStore.reviewData);
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