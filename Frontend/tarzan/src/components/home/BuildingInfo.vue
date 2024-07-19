<template>
  <div class="building-info-list-container">
      <ResultBar />
      <BuildingItem
      v-for="building in buildings"
      :key="building.id"
      :building="building"
      @click="goToBuildingDetail(building.id)"/>
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
        { id: 1, title: 'CNP차앤박피부과 도곡양재점', categoty:'종합병원', content: '서울 강남구 강남대로 248 목원빌딩 3층 (도곡동)'},
      ],
    };
  },
  methods: {
    goToBuildingDetail(building) {
      this.$router.push({ name: 'buildingDetail', params: { id: buildingId } });
    },
  },
};
</script>
<style scoped lang="scss">
  .building-info-list-container {
    display: flex;
    flex-direction: column;
    flex-grow: 1; /* 부모 요소에 높이를 채우도록 설정 */
    justify-content: space-between;
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
  
</style>
