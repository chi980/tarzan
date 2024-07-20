<template>
  <div class="sub-container">
    <TopBarBack title="실거주 후기" />
    <div class="center-container">
      
      <div class="rating-contaier">
        <!-- <StarRating :value="averageRating" :readOnly="true" /> -->
      </div>

      <div class="review-container">

        <div class="review-group" id="photo0">
          <ResultBar resultTitle="사진 후기" />
          <div class="reivew-content">
            <PhotoUpload />
          </div>
        </div>

        <Divider />

        <div class="review-group" id="residence">
          <ResultBar resultTitle="실거주 후기" :sortOptions="sortOptions" />
          <div class="reivew-content">
            <ReviewItem 
              v-for="(review, index) in reviews" 
              :key="index" 
              :review="review" 
            />
          </div>
        </div>

        <button>더보기</button>

      </div>
    </div>
  </div>
</template>
<script>
import TopBarBack from "@/components/common/TopBarBack.vue"
import ResultBar from "@/components/common/ResultBar.vue"
import PhotoUpload from "./PhotoUpload.vue";
import Divider from "../common/Divider.vue";
import ReviewItem from "./ReviewItem.vue";
import StarRating from "./StarRating.vue"; 

export default {
  components: {
    TopBarBack,
    ResultBar,
    PhotoUpload,
    Divider,
    ReviewItem,
    StarRating,
  },
  data() {
    return {
      averageRating: 3,  // 부모 컴포넌트에서 전달된 평균 별점
      userRating: 0,     // 사용자가 매긴 별점
      sortOptions: [
        {idx: 1, value: 'date', name: '날짜순' },
        { idx: 2, value: 'popularity', name: '인기순' },
        { idx: 3, value: 'rating', name: '평점순' },
      ],
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
    }
  }
}
</script>
<style lang="scss" scoped>
  .sub-container {
    display: flex;
    flex-direction: column;
    
  }

  .center-container {
    background-color: #EDEDED;
    display: flex;
    flex-direction: column;
    flex-grow: 1;
    width: 100%;
    gap: 10px;
    overflow-y: auto;
    @include custom-scrollbar-style;
  }

  .rating-contaier {
    background-color: white;
    // box-sizing: border-box;
    // @include custom-padding-x;
  }

  .review-container {
    background-color: white;
    display: flex;
    flex-direction: column;
    flex-grow: 1;
    gap: 24px;
    padding-top: 24px;
    box-sizing: border-box;
    @include custom-padding-x;
  }

  .review-container .review-group {
    display: flex;
    flex-direction: column;
    gap: 16px;
  }

  .review-container .review-group .reivew-content {
    display: flex;
    flex-direction: column;
    gap: 16px;
  }
</style>
