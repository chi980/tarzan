<template>
  <div
    class="building-info-list-container"
    @click="toggleVisibility"
    :style="{ transform: `translateY(${translateY}px)` }"
  >
    <BuildingItem
      v-for="building in buildings"
      :key="building.id"
      :building="building"
      @click="goToBuildingDetail(building.id)"
    />
    <div class="content-indicator"></div>
    <div class="graph-container">
      <img src="/src/assets/etc/graph.png" alt="Graph Image" class="responsive-image"/>
      <div class="graph-description">* 반경 5km 기준 시설 개수</div>
    </div>
    <div class="content-indicator-thick"></div>  
  </div>
</template>

<script>
import BuildingItem from './BuildingItem.vue';

export default {
  components: {
    BuildingItem,
  },
  data() {
    return {
      buildings: [
        { id: 1, title: 'CNP차앤박피부과도곡양재점', categoty:'종합병원', content: '서울 강남구 강남대로 248 목원빌딩 3층 (도곡동)'},
      ],
      startY: 0,
      translateY: 0,
      isVisible: false,
    };
  },
  methods: {
    toggleVisibility() {
      this.isVisible = !this.isVisible;
      // 클릭 시 이동 거리 조정 (화면 높이의 60%만큼 위로 이동)
      this.translateY = this.isVisible ? -window.innerHeight * 0.6 : 0;
    },
    goToBuildingDetail(buildingId) {
      this.$router.push({ name: 'buildingDetail', params: { id: buildingId } });
    }
  }
};
</script>
<style scoped lang="scss">
  .building-info-list-container {
  display: flex;
  flex-direction: column;
  flex-grow: 1; /* 부모 요소에 높이를 채우도록 설정 */
  position: absolute;
  bottom: -590px;
  left: 0;
  width: 100%;
  height: 70%;
  background-color: white;
  transition: transform 0.3s ease; /* 스와이프 후 애니메이션 추가 */
  }

  .load-more-container {
    background-color: orange;
  }
  
  #load-more-btn {
    width: 100%;
    height: 100%;
    border-radius: 0;
    font-size: 14px;
  }

  .input-item-image {
  @include custom-icon-style;
  }

  .content-indicator {
  background-color: #ededed;
  margin: 0 auto;
  height: 1px;
  width: 93%;
  }

  .content-indicator-thick {
  background-color: #ededed;
  margin: 0 auto;
  height: 10px;
  width: 100%;
  }

  .graph-container {
  position: relative;
  width: calc(100% - 50px); /* 좌우 패딩을 제외한 너비 */
  padding: 0 25px; /* 좌우 패딩 50px */
  overflow: hidden;
  margin: 30px auto 0px auto;
}

.responsive-image {
  width: 100%;
  height: auto;
  object-fit: contain;

  img {
    @include custom-icon-style;
  }
}

.graph-description {
  margin-top: 10px; /* 이미지와 텍스트 사이의 간격 */
  font-size: 12px;
  margin: 30px;
  color: #9F9F9F;
  
  }

</style>
