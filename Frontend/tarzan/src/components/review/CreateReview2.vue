<template>
  <form class="input-form" @submit.prevent="submitForm">
    <div class="input-group" id="장점">
      <h2 class="input-title">
        장점 (50자 이상)<span class="input-title-mandatory">*</span>
      </h2>
      <textarea
        class="input-content"
        type="text"
        placeholder="내용를 입력해주세요."
        v-model="reviewStore.reviewData.review_advantage" />

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
      <h2 class="input-title">
        단점 (50자 이상)<span class="input-title-mandatory">*</span>
      </h2>
      <textarea
        class="input-content"
        type="text"
        placeholder="내용를 입력해주세요."
        v-model="reviewStore.reviewData.review_disadvantage" />
      <TagButtonGroup
        v-model:selectedButtons="
          reviewStore.reviewData.review_disadvantage_tags
        "
        :buttons="disadvantageButtons"
        :multiple="true">
        <template v-slot:default="{ button }">
          <span>{{ button.label }}</span>
        </template>
      </TagButtonGroup>
    </div>
  </form>
</template>

<script setup>
import { useReviewStore } from "@/stores/reviewStore";
import { createReview } from "@/api/reviewApi";
import { useRouter } from "vue-router";
import { ref } from "vue";
import TopBarBack from "@/components/common/TopBarBack.vue";
import AddressCard from "./AddressCard.vue";
import TagButtonGroup from "@/components/common/TagButtonGroup.vue";

const router = useRouter();
const reviewStore = useReviewStore();

// 장점 리스트
const advantageButtons = ref([
  { label: "❌ 없음", value: "NONE" },
  { label: "🐛 벌레", value: "BUG" },
  { label: "🧹 건물 관리", value: "BUILDING_MANAGEMENT" },
  { label: "🚘 주차", value: "PARKING" },
  { label: "🔇 방음", value: "SOUND_PROOF" },
  { label: "🚨 치안", value: "SECURITY" },
  { label: "👮🏻 경비실", value: "SECURITY_GUARD" },
  { label: "🌳 산책로", value: "TRAIL" },
  { label: "🪟 환기", value: "VENTILATION" },
  { label: "🤫 조용한 동네", value: "QUITE" },
  { label: "🥶 단열", value: "CLEAN" },
  { label: "🐶 반려동물 키우기", value: "PREVENT_HEAT" },
  { label: "🛗 엘레베이터", value: "ELEVATOR" },
  { label: "🦠 곰팡이", value: "MOLD" },
  { label: "👟 평지", value: "FLAT" },
]);

// 단점 리스트
const disadvantageButtons = ref([
  { label: "❌ 없음", value: "NONE" },
  { label: "🐛 벌레", value: "BUG" },
  { label: "🧹 건물 관리", value: "BUILDING_MANAGEMENT" },
  { label: "🚘 주차", value: "PARKING" },
  { label: "🔇 방음", value: "SOUND_PROOF" },
  { label: "🚨 치안", value: "SECURITY" },
  { label: "👮🏻 경비실", value: "SECURITY_GUARD" },
  { label: "🌳 산책로", value: "TRAIL" },
  { label: "🪟 환기", value: "VENTILATION" },
  { label: "🤫 조용한 동네", value: "QUITE" },
  { label: "🥶 단열", value: "CLEAN" },
  { label: "🐶 반려동물 키우기", value: "PREVENT_HEAT" },
  { label: "🛗 엘레베이터", value: "ELEVATOR" },
  { label: "🦠 곰팡이", value: "MOLD" },
  { label: "👟 평지", value: "FLAT" },
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
      @include custom-text($font-size: 14px);
      text-align: left;

      .input-title-mandatory {
        color: red;
      }
    }

    .input-content {
      display: flex;
      align-items: center;
      gap: $padding-small;

      input[type="text"] {
        @include custom-input-style;
        flex: 1;
      }
    }
  }
}
// scoped
textarea {
  @include custom-input-style;
  @include custom-padding;
  height: 100px;
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
</style>
