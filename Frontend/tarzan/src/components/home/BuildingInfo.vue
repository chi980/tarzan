<template>
  <div class="building-info-list-container" v-if="building" @click="toggleVisibility" :style="{ transform: `translateY(${translateY}px)` }">
    <div class="building-meta">
      <h3 id="building-name">{{ building.name }}</h3>
      <p id="building-type">{{ building.type }}</p>
    </div>
    <p id="building-address">{{ building.address }}</p>
    <div class="content-indicator"></div>
    <div class="graph-container">
      <!--<Chart v-if="building.radarData" :data="building.radarData" />-->
      <div class="graph-description">* 반경 5km 기준 시설 개수</div>
    </div>
    <div class="content-indicator-thick"></div>
  </div>
</template>

<script>
import Chart from "@/components/common/RadarChart.vue";  // RadarChart 컴포넌트 가져오기

export default {
  components: {
    Chart,  // RadarChart 컴포넌트를 등록
  },
  props: {
    building: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      isVisible: false,
      translateY: 0,
    };
  },
  methods: {
    toggleVisibility() {
      this.isVisible = !this.isVisible;
      this.translateY = this.isVisible ? -window.innerHeight * 0.6 : 0;
    },
  },
  mounted() {
    console.log("Radar data:", this.building.radarData);  // Radar data 출력
  },
};
</script>

<style scoped lang="scss">
  .building-info-list-container {
    display: flex;
    flex-direction: column;
    flex-grow: 1;
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 80%;
    gap: 8px;
    padding: 15px 0 5px;
    background-color: white;
    transition: transform 0.3s ease;
    align-items: flex-start;
  }

  .building-meta {
    display: flex;
    align-items: center; /* 제목과 카테고리를 수평 정렬 */
    width: 100%; /* 부모 요소 너비에 맞게 확장 */
    padding: 0 15px; /* 좌우에 여백 추가 */
  }

  #building-name {
    font-size: 14px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    margin-right: 10px; /* 제목과 카테고리 사이의 간격 */
  }

  #building-type {
    font-size: 12px;
    color: #9F9F9F;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  #building-address {
    font-size: 12px;
    color: #9F9F9F;
    width: 100%;
    text-align: left;
    padding: 0 15px 5px; /* 좌우 여백 추가 */
  }

  .graph-container {
    position: relative;
    width: calc(100% - 50px);
    padding: 0 25px;
    overflow: hidden;
    margin: 30px auto 10px auto;
  }

  .responsive-image {
    width: 100%;
    height: auto;
    object-fit: contain;
  }

  .graph-description {
    margin-top: 10px;
    font-size: 12px;
    color: #9F9F9F;
  }

  .content-indicator,
  .content-indicator-thick {
    background-color: #ededed;
    margin: 0 auto;
  }

  .content-indicator {
    height: 1px;
    width: 93%;
  }

  .content-indicator-thick {
    height: 10px;
    width: 100%;
  }
</style>
