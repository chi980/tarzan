<template>
  <div class="post-topbar">
    <div class="post-topbar-button">
      <img :src="backIcon" alt="back-icon" @click="goToBack">
    </div>
    <EditButton
      v-if="isAuthor" 
      :isAuthor="props.isAuthor"
      :targetId="props.boardIdx"
      :type="'post'" 
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import backIcon from '@/assets/icons/topbar/icon-back.png';
import EditButton from './EditButton.vue';

// 상태 변수 정의
const router = useRouter();
const isDropDownOpen = ref(false);

const props = defineProps({
  isAuthor: Boolean,
  boardIdx: String
});

// console.log("isAuthor value:", props.isAuthor);
// console.log("postId value:", props.boardIdx);

const goToBack = () => {
  router.go(-1);
};
</script>

<style scoped lang="scss">
.post-topbar {
  width: 100%;
  height: 54px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  @include custom-padding-x;
  box-sizing: border-box;
}

.post-topbar img {
  width: 24px;
  height: 24px;
}
</style>