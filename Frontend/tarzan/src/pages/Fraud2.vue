<template>
  <div class="sub-container non-input-sub-container">
    <div class="top-bar-wrapper">
      <TopBar></TopBar>
    </div>
    <div class="center-container">
      <!-- <div>
        <DescriptionComponent
          descriptionImgSrc="/etc/Fire_perspective.png"
          descriptionTitle="사기를 당할까봐<br/>걱정이신가요?"
          descriptionContent="점검한 집에 대해 사기 여부를<br/>확인할 수 있어요!"
          backgroundColor="#FFEDED" />
      </div> -->
      <div class="content-wrapper">
        <div class="content-header">
          <p class="content-title">오늘의 퀴즈</p>
          <p>전세 계약 전, 사기 예방을 위한 체크리스트! 퀴즈로 점검해보세요</p>
        </div>
        <div class="quiz-card">
          <div class="quiz-time">{{ timeLeft }} 뒤에 끝나요</div>

          <div class="quiz-content">
            전세 계약 시, 계약서에 명시된 보증금이 실제 집주인의 계좌로 입금되지
            않으면, 사기일 가능성이 높다.
          </div>
          <div class="quiz-answer-wrapper">
            <div class="correct" @click="clickCorrect">
              <img :src="CorrectImgSrc" alt="o" />
              <p>그렇다</p>
            </div>
            <div class="wrong" @click="clickWrong">
              <img :src="WrongImgSrc" alt="x" />
              <p>아니다</p>
            </div>
          </div>
        </div>
      </div>
      <div class="content-wrapper">
        <div class="content-header">
          <p class="content-title">이런 서비스도 있어요!</p>
        </div>

        <div class="rectange-card-wrapper">
          <div
            v-for="(card, idx) in cards"
            :key="idx"
            class="card"
            @click="card.onClick">
            <p class="card-title" v-html="card.title"></p>
            <img :src="card.img" alt="" class="card-image" />
          </div>
        </div>
      </div>
    </div>
    <div class="bottom-bar-wrapper">
      <BottomBar></BottomBar>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from "vue";
import { useRouter } from "vue-router";
import TopBar from "@/components/common/TopBar.vue";
import BottomBar from "@/components/common/BottomBar.vue";
// import DescriptionComponent from "@/components/common/Description.vue";
import CorrectImgSrc from "@/assets/icons/fraud/F-Tick Square.png";
import WrongImgSrc from "@/assets/icons/fraud/F-Close Square.png";
import Img1 from "@/assets/icons/fraud/F-User.png";
import Img2 from "@/assets/icons/fraud/F-Building Library.png";
import Img3 from "@/assets/icons/fraud/F-Graph Up.png";
import Img4 from "@/assets/icons/fraud/F-File.png";
import Img5 from "@/assets/icons/fraud/F-Star.png";

const router = useRouter();

const answer = ref(true);
const clickCorrect = () => {
  if (answer) alert("정답입니다.");
  else alert("아쉽지만 틀렸습니다! 다시 도전해보세요");
};

const clickWrong = () => {
  if (!answer) alert("정답입니다.");
  else alert("아쉽지만 틀렸습니다! 다시 도전해보세요");
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
interface Card {
  img: string;
  title: string;
  onClick: () => void;
}

const cards: Card[] = ref([
  {
    img: Img1,
    title: "임대인<br>확인",
    onClick: () => {
      router.push({ name: "CheckLandlord" });
    },
  },
  {
    img: Img2,
    title: "공인중개사<br>확인",
    onClick: () => {
      router.push({ name: "CheckRealEstateBroker" });
    },
  },
  {
    img: Img3,
    title: "시세<br>확인",
    onClick: () => {
      router.push({ name: "CheckValue" });
    },
  },
  {
    img: Img4,
    title: "계약 내용<br>확인",
    onClick: () => {
      router.push({ name: "CheckContract" });
    },
  },
  {
    img: Img5,
    title: "특약사항<br>확인",
    onClick: () => {
      router.push({ name: "CheckSpecialContract" });
    },
  },
]);
</script>
<style scoped lang="scss">
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
.top-bar-wrapper {
  width: 100%;
}
.bottom-bar-wrapper {
  display: flex;
  justify-content: center; /* 가로 방향 중앙 정렬 */
  // height: 100px;
  width: 100%;
  z-index: $z-index-bottom-bar-wrapper;
  box-shadow: 0px -2px 4px rgba(0, 0, 0, 0.1);
}
// scoped
.center-container {
  @include custom-padding-y;
  gap: 24px;
}

.content-wrapper {
  @include custom-padding-x;
  display: flex;
  flex-direction: column;
  gap: 12px;

  .content-header {
    display: flex;
    flex-direction: column;
    gap: 6px;

    @include custom-text($font-size: 12px, $font-color: $text-color-light);
    text-align: left;

    .content-title {
      @include custom-text($font-size: 16px, $font-weight: 700);
    }
  }
}

.quiz-card {
  @include custom-padding-x;
  @include custom-padding-y(24px);
  display: flex;
  flex-direction: column;
  gap: 16px;
  box-shadow: 0px 1px 10px rgba(0, 0, 0, 0.1);
  border-radius: 20px;

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

.rectange-card-wrapper {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: $padding-default;
  div {
    position: relative;
    height: 102px;
    background-color: #f8f8f8;
    border-radius: 16px;

    .card-title {
      @include custom-text($font-size: 16px, $font-weight: 600);
      position: absolute;
      left: $padding-big;
      top: $padding-big;
      text-align: left;
      line-height: 150%;
      letter-spacing: -0.024em;
    }
    .card-image {
      position: absolute;
      right: $padding-default;
      bottom: $padding-default;
      width: 50px;
      height: 50px;
      filter: drop-shadow(0px 4px 4px rgba(0, 0, 0, 0.25));
    }
  }
}
</style>
