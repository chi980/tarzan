<template>
  <div class="sub-container">
    <TopBarBack title="후기 작성하기" />

    <div class="center-container">
      <AddressCard />

      <div class="photo-upload-container">
        <PhotoUpload />
        <p>캡쳐한 이미지, 본인이 촬용하지 않은 사진, 식별 불가능한 이미지를 등록하는 경우
           이미지 비노출 및 정상 후기로 인정되지 않습니다.</p>
      </div>

      <Divider />

      <div class="star-rating-container">
        <h2 class="input-title">만족도</h2>
        <div class="star">
          <StarRating />
        </div>
      </div>

      <Divider />

      <div class="input-container">

        <div class="input-group" id="living-expense">
          <h2 class="input-title">관리비 및 생활 요금</h2>
          <div class="input-content">
            <div id="rent-type">
              <CustomSelectBox :options="rentalOptions" />
              <input type="text" placeholder="월세를 입력해주세요." />
            </div>
            <input type="text" placeholder="보증금을 입력해주세요." />
            <input type="text" placeholder="관리비를 입력해주세요." />
          </div>
        </div>

        <div class="input-group" id="residence-year">
          <h2 class="input-title">거주 년도</h2>
          <div class="input-content">
            <input type="text" placeholder="거주 년도를 입력해주세요." />
          </div>
        </div>

        <div class="input-group" id="floor">
          <h2 class="input-title">층수</h2>
          <div class="input-content">
            <input type="text" placeholder="층수를 입력해주세요." />
          </div>
        </div>

        <div class="input-group" id="waste">
          <h2 class="input-title">쓰레기 버리는 방법</h2>
          <div class="input-content">
            <input type="text" placeholder="쓰레기 버리는 방법을 입력해주세요." />
          </div>
        </div>

        <button @click="goToNextPage">다음으로</button>

      </div> 
    </div>
  </div>
</template>
<script>
import TopBarBack from "@/components/common/TopBarBack.vue"
import AddressCard from "./AddressCard.vue"
import PhotoUpload from "./PhotoUpload.vue"
import StarRrating from "./StarRating.vue"
import Divider from "../common/Divider.vue"
import CustomSelectBox from "../common/CustomSelectBox.vue"
import StarRating from "./StarRating.vue"

export default {
  name: 'CreateReview1',
  components: {
    TopBarBack,
    AddressCard,
    PhotoUpload,
    Divider,
    CustomSelectBox,
    StarRating,
  },
  data() {
    return {
      rentalOptions: [
      { idx: 1, name: "월세", value: "monthly" },
      { idx: 2, name: "전세", value: "yearly" },
      ]
    }
  },
  methods: {
    goToNextPage() {
      this.$router.push({ name: 'CreateReview2' });
    }
  },
}
</script>
<style scoped lang="scss">
  .sub-container {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }

  .center-container {
    display: flex;
    flex-direction: column;
    gap: 16px;
    flex-grow: 1;
    width: 100%;
    box-sizing: border-box;
    @include custom-padding-x;
    padding-top: 16px;
    padding-bottom: 26px;

    overflow-y: auto;
    @include custom-scrollbar-style;
  }

  .photo-upload-container {
    display: flex;
    flex-direction: column;
    gap: 26px;
  }

  .photo-upload-container p {
    font-size: 12px;
    font-weight: 500;
    color: #969696;
    text-align: left;
  }

  .star-rating-container .star {
    margin-top: 10px;
    margin-bottom: 10px;
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

  .center-container .input-title {
    @include custom-text-bold;
    text-align: left;
  }

  .input-container .input-group .input-content {
    display: flex;
    flex-direction: column;
    gap: $padding-small;
  }

  .input-container .input-group .input-content input {
    @include custom-input-style;
  }

  .input-container #living-expense .input-content #rent-type {
    display: flex;
    gap: $padding-small;
  }
</style>