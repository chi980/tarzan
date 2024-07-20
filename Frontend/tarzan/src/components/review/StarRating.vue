<template>
  <div class="star-rating">
    <img 
      v-for="n in maxStars" 
      :key="n" 
      class="star" 
      :src="getStarSrc(n)"
      @click="setRating(n)"
      :style="{ cursor: readOnly ? 'default' : 'pointer' }"
    />
  </div>
</template>

<script>
export default {
  props: {
    value: {
      type: Number,
      required: true
    },
    maxStars: {
      type: Number,
      default: 5
    },
    readOnly: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      filledStar: require('@/assets/icons/star/filled-star.png'),  // 채워진 별 이미지 경로
      emptyStar: require('@/assets/icons/star/empty-star.png')    // 빈 별 이미지 경로
    }
  },
  methods: {
    setRating(newRating) {
      if (!this.readOnly) {
        this.$emit('input', newRating);
      }
    },
    getStarSrc(n) {
      return n <= this.value ? this.filledStar : this.emptyStar;
    }
  }
}
</script>

<style scoped>
.star-rating {
  display: flex;
}

.star {
  width: 24px;
  height: 24px;
}
</style>
