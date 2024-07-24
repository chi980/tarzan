<template>
  <div class="review-list-container">
    <div class="review-items-container">
      <ReviewItem 
        v-for="(review, index) in reviews" 
        :key="index" 
        :review="review" 
      />
    </div>

    <div class="review-more-container">
      <button id="review-more-button">
        <span>더보기</span>
        <img 
          id="review-more-icon"
          src="@/assets/icons/Arrows-chevron/Arrow-Down/Arrow-Down.png"
          alt="arrowDown"
        />
      </button>
    </div>
  </div>
</template>
<script>
import { Comment } from 'vue';
import ReviewItem from './ReviewItem.vue';

export default {
  components: {
    ReviewItem
  },
  data() {
    return {
      reviews: [
        {
          username: "abc0813",
          period: "2024년도 거주/고층",
          pros: "용자 없는 건물임. 역이나 버스정류장에 가까워서 교통에 편함",
          cons: "여름 천장 누수가 발생하고 집주인한테 따져물었다가 엄청 꼼꼼한가보다 천장에 있는 누수도 발견하고 …",
        },
        {
          username: "abc0813",
          period: "2024년도 거주/고층",
          pros: "용자 없는 건물임. 역이나 버스정류장에 가까워서 교통에 편함",
          cons: "여름 천장 누수가 발생하고 집주인한테 따져물었다가 엄청 꼼꼼한가보다 천장에 있는 누수도 발견하고 …",
        },
        {
          username: "abc0813",
          period: "2024년도 거주/고층",
          pros: "용자 없는 건물임. 역이나 버스정류장에 가까워서 교통에 편함",
          cons: "여름 천장 누수가 발생하고 집주인한테 따져물었다가 엄청 꼼꼼한가보다 천장에 있는 누수도 발견하고 …",
        },
      ],
    };
  },
};
</script>
<style scoped lang="scss">
  .review-list-container {
    display: flex;
    flex-direction: column;
    flex-grow: 1; 
    justify-content: space-between;
    gap: 16px;
  }

  .review-list-container .review-items-container {
    display: flex;
    flex-direction: column;
    gap: 15px;
  }
  
  #review-more-button {
    width: 100%;
    height: 100%;
    border-radius: 0;
    font-size: 14px;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  #review-more-icon {
  @include custom-icon-style;
  }
</style>