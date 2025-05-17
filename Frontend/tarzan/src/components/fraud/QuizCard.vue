<template>
  <div class="quiz-card" :class="{ flipped: isFlipped }">
    <div class="quiz-inner">
      <div class="quiz-front">
        <div class="quiz-time">{{ timeLeft }} 뒤에 끝나요</div>
        <div class="quiz-content">{{ quiz.question }}</div>
        <div class="quiz-answer-wrapper">
          <div class="correct" @click="selectAnswer(true)">
            <img :src="CorrectImgSrc" alt="o" />
            <p>그렇다</p>
          </div>
          <div class="wrong" @click="selectAnswer(false)">
            <img :src="WrongImgSrc" alt="x" />
            <p>아니다</p>
          </div>
        </div>
      </div>
      <div class="quiz-back">
        <div class="result" v-if="isCorrect">
          <p class="title">정답입니다!</p>
          <p>내일 또 만나요! 😊</p>
        </div>
        <div class="result" v-else>
          <p class="title">아쉽지만 틀렸습니다! 😥</p>
          <p>{{ quiz.explanation }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from "vue";
import CorrectImgSrc from "@/assets/icons/fraud/F-Tick Square.png";
import WrongImgSrc from "@/assets/icons/fraud/F-Close Square.png";

interface QuizItem {
  id: number;
  question: string;
  explanation: string;
  answer: boolean;
}
interface FetchResult {
  item: QuizItem;
  alreadySolved: boolean;
  solvedCorrect: boolean;
}

interface SubmitQuizRequest {
  quizId: number;
  isCorrect: boolean;
}

const props = defineProps<{
  fetchQuiz: () => Promise<FetchResult>;
  fetchSubmitQuiz: (SubmitQuizRequest) => Promise<void>;
}>();
const quiz = ref<QuizItem>({
  id: -1,
  question: "",
  explanation: "",
  answer: true,
});

onMounted(async () => {
  const { item, alreadySolved, solvedCorrect } = await props.fetchQuiz();
  quiz.value = item;

  isFlipped.value = alreadySolved;
  if (alreadySolved) {
    isCorrect.value = solvedCorrect;
  }
});

const isFlipped = ref(false);
const isCorrect = ref(null);

const selectAnswer = async (userAnswer) => {
  isCorrect.value = userAnswer === quiz.value.answer;
  isFlipped.value = true;

  try {
    await props.fetchSubmitQuiz({
      quizId: quiz.value.id,
      isCorrect: userAnswer,
    });
  } catch (error) {
    console.error("API request error:", error);
  }
};

// 1초마다 갱신될 현재 시각
const now = ref(Date.now());

// 자정까지 남은 시간을 계산하는 computed
const timeLeft = computed(() => {
  const midnight = new Date();
  midnight.setHours(24, 0, 0, 0);
  const diff = midnight.getTime() - now.value;

  const h = String(Math.floor(diff / 3_600_000)).padStart(2, "0");
  const m = String(Math.floor((diff % 3_600_000) / 60_000)).padStart(2, "0");
  const s = String(Math.floor((diff % 60_000) / 1000)).padStart(2, "0");
  return `${h}:${m}:${s}`;
});

let timer: number;
onMounted(() => {
  timer = window.setInterval(() => {
    now.value = Date.now();
  }, 1000);
});
onUnmounted(() => {
  clearInterval(timer);
});
</script>

<style scoped lang="scss">
.quiz-card {
  perspective: 1000px;
  position: relative;
}

.quiz-inner {
  display: grid;
  grid-template-areas: "overlap";
  transform-style: preserve-3d;
  transition: transform 0.6s;
  box-shadow: 0px 1px 10px rgba(0, 0, 0, 0.1);
  border-radius: 20px;
}
.quiz-front,
.quiz-back {
  grid-area: overlap;
  backface-visibility: hidden;
  transition: transform 0.6s;
  @include custom-padding-x;
  @include custom-padding-y(24px);
}
.quiz-card.flipped .quiz-inner {
  transform: rotateY(180deg);
}
.quiz-front {
  transform: rotateY(0deg);
  z-index: 2;
}
.quiz-back {
  transform: rotateY(180deg);
}

.quiz-front {
  display: flex;
  flex-direction: column;
  gap: 16px;

  .quiz-time {
    @include custom-padding($padding-small);
    @include custom-text(
      $font-size: 12px,
      $font-color: #f97979,
      $font-weight: 600
    );
    width: fit-content;
    display: flex;
    background-color: #f8f8f8;
    border: 1px solid #d9d9d9;
    border-radius: 30px;
  }
  .quiz-content {
    @include custom-text($font-size: 16px, $font-weight: 700);
    text-align: left;

    line-height: 150%;
    letter-spacing: -0.024em;
  }

  .quiz-answer-wrapper {
    display: flex;
    flex-direction: row;
    gap: $padding-default;
    div {
      @include custom-padding-y;
      @include custom-text($font-size: 16px, $font-weight: 600);
      flex: 1;
      border-radius: 20px;

      display: flex;
      flex-direction: column;
      gap: $padding-small;
      justify-content: center; /* 세로 방향 가운데 정렬 */
      align-items: center; /* 가로 방향 가운데 정렬 */
      img {
        width: 30px;
        height: 30px;
      }
    }

    .correct {
      background-color: #e9f3ff;
      color: #338af9 !important;
    }

    .wrong {
      background-color: #feeeee;
      color: #f14352 !important;
    }
  }
}

.quiz-back {
  line-height: 1.2;
  .result {
    @include custom-text($font-size: 14px, $font-color: $text-color-light);
    text-align: left;
    display: flex;
    flex-direction: column;
    gap: $padding-default;

    justify-content: center;
    align-items: center;
    height: 100%;

    .title {
      @include custom-text($font-size: 16px, $font-weight: 700);
    }
  }
}
</style>
