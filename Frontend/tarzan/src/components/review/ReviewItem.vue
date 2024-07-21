<template>
  <div class="review-item">

    <div class="profile-container">
      <img src="@/assets/icons/Filter/profile-icon.png" alt="profile-img">
      <div class="profile-info">
        <span class="username">{{ review.username }}</span>
        <span class="residence-period">{{ review.period }}</span>
      </div>
    </div>

    <div class="review-container">
      <div class="review-content">
        <h4>장점</h4>
        <p>{{ review.pros }}</p>
        <!-- <p>1. 용자 없는 건물임. 2. 역이나 버스정류장에 가까워서 교통에 편함</p> -->
      </div>
      <div class="review-content">
        <h4>단점</h4>
        <p>{{ review.cons }}</p>
        <!-- <p>여름 천장 누수가 발생하고 집주인한테 따져물었다가 엄청 꼼꼼한가보다 천장에 있는 누수도 발견하고 …</p> -->
      </div>
    </div>

  </div>
</template>
<script>
export default {
  props: {
    review: {
      type: Object,
      required: true
    }
  }
}
</script>
<style lang="scss" scoped>
  .review-item {
    display: flex;
    flex-direction: column;
    gap: 16px;
  }

  .profile-container {
    display: flex;
    gap: 16px;
  }

  .profile-container img {
    width: 40px;
    height: 40px;
  }

  .profile-container .profile-info {
    display: flex;
    flex-direction: column;
    text-align: left;
    gap: 4px;
  }

  .profile-container .profile-info .username {
    font-size: 14px;
    font-weight: 500;
  }

  .profile-container .profile-info .residence-period {
    font-size: 12px;
  }

  .review-container {
    background-color: #F8F8F8;
    border-radius: 16px;
    display: flex;
    flex-direction: column;
    gap: 24px;
    @include custom-padding;
  }

  .review-container .review-content {
    display: flex;
    gap: 12px;
    text-align: left;
    align-items: baseline;
  }

  .review-container .review-content h4 {
    font-size: 12px;
    white-space: nowrap;
  }

  .review-container .review-content p {
    font-size: 12px;
    color: #969696;
    line-height: 1.5;
  }
  
</style>