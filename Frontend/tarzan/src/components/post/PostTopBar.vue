<template>
  <div class="post-topbar">
    <div class="post-topbar-button">
      <img :src="backIcon" alt="back-icon" @click="goToBack">
    </div>
    <EditButton />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { axiosInstance } from "@/plugins/axiosPlugin";
import backIcon from '@/assets/icons/topbar/icon-back.png';
import EditButton from './EditButton.vue';

// 상태 변수 정의
const router = useRouter();
const isDropDownOpen = ref(false);

const props = defineProps({
  isAuthor: Boolean,
  boardIdx: String
});
console.log("isAuthor value:", props.isAuthor);
console.log("postId value:", props.boardIdx);

const goToBack = () => {
  router.go(-1);
};

// const options = ref([
//   { idx: 1, name: '수정' },
//   { idx: 2, name: '삭제' },
//   { idx: 3, name: '신고하기' }
// ]);

// // `isAuthor` 값에 따라 옵션을 필터링하는 computed 속성
// const filteredOptions = computed(() => {
//   return props.isAuthor
//     ? options.value.filter(option => option.idx !== 3) // 작성자인 경우 '수정'과 '삭제'만
//     : options.value.filter(option => option.idx === 3); // 작성자가 아닌 경우 '신고하기'만
// });

// const controllDropDown = () => {
//   isDropDownOpen.value = !isDropDownOpen.value;
// };

// const deletePost = async () => {
//   try {
//     const response = await axiosInstance.delete(`/v1/board/${props.boardIdx}`);
//     console.log(response);
    
//     if (response.data.success) {
//       console.log('게시글 삭제 성공:', response.data);
//       alert('게시글이 성공적으로 삭제되었습니다.');
//       router.push('/community'); // 삭제 후 이동
//     } else {
//       console.error('Failed:', response.data.message);
//       alert('게시글 삭제에 실패했습니다.');
//     }
//   } catch (error) {
//     console.error('Error fetching posts:', error);
//     alert('게시글 삭제에 실패했습니다.');
//   }
// };

// const selectOption = (option) => {
//   if (option.idx === 2) { // '삭제' 옵션의 idx가 2라고 가정
//     if (confirm('정말로 이 게시글을 삭제하시겠습니까?')) {
//       deletePost();
//     }
//   } else {
//     selectedOption.value = option;
//   }
// };
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