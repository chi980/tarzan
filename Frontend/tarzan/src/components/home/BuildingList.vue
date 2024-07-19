<template>
  <div class="building-info-list-container">
    <div class="post-items-container">
      <ResultBar />
      <div class="content-indicator"></div>
      <BuildingItem
      v-for="building in buildings"
      :key="building.id"
      :building="building"
      @click="goToBuildingDetail(building.id)"/>
    </div>
    <!--<div class="load-more-container">
      <button id="load-more-btn">
          <span>더보기</span>
            <img 
            id="load-more-icon"
            src="@/assets/icons/Arrows-chevron/Arrow-Down/Arrow-Down.png"
            alt="arrowDown"
            />
      </button>
    </div>-->
  </div>
</template>
<script>
import ResultBar from "@/components/common/ResultBar.vue";
import BuildingItem from './BuildingItem.vue';

export default {
  components: {
    BuildingItem,
    ResultBar,
  },
  data() {
    return {
      buildings: [
        { id: 1, title: 'CNP차앤박피부과도곡양재점', categoty:'종합병원', content: '서울 강남구 강남대로 248 목원빌딩 3층 (도곡동)'},
        { id: 2, title: '한성원룸고시텔', categoty:'고시원, 고시텔', content: '서울 중구 서소문로 11길 8 한성빌딩 4층 (서소문동)'},
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

// content를 구분해주는 회색 긴 선
.content-indicator {
background-color: #ededed;
margin: 0 auto;
height: 1px;
width: 91%;
}

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
display: flex;
align-items: center;
justify-content: center;
}

.input-item-image {
@include custom-icon-style;
}
  
</style>
