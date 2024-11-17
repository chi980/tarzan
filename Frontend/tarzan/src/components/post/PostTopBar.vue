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
// import settingIcon from '@/assets/icons/topbar/icon-setting.png';
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

const options = ref([
  { idx: 1, name: '수정' },
  { idx: 2, name: '삭제' },
  { idx: 3, name: '신고하기' }
]);

// `isAuthor` 값에 따라 옵션을 필터링하는 computed 속성
const filteredOptions = computed(() => {
  return props.isAuthor
    ? options.value.filter(option => option.idx !== 3) // 작성자인 경우 '수정'과 '삭제'만
    : options.value.filter(option => option.idx === 3); // 작성자가 아닌 경우 '신고하기'만
});

const goToBack = () => {
  router.go(-1);
};

const controllDropDown = () => {
  isDropDownOpen.value = !isDropDownOpen.value;
};

const deletePost = async () => {
  try {
    const response = await axiosInstance.delete(`/v1/board/${props.boardIdx}`);
    console.log(response);
    
    if (response.data.success) {
      console.log('게시글 삭제 성공:', response.data);
      alert('게시글이 성공적으로 삭제되었습니다.');
      router.push('/community'); // 삭제 후 이동
    } else {
      console.error('Failed:', response.data.message);
      alert('게시글 삭제에 실패했습니다.');
    }
  } catch (error) {
    console.error('Error fetching posts:', error);
    alert('게시글 삭제에 실패했습니다.');
  }
};

const selectOption = (option) => {
  if (option.idx === 2) { // '삭제' 옵션의 idx가 2라고 가정
    if (confirm('정말로 이 게시글을 삭제하시겠습니까?')) {
      deletePost();
    }
  } else {
    selectedOption.value = option;
  }
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

.edit {
  position: relative;
}

.edit-button{
  height: 100%;
  width: 100%;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  transform: translateX(-70%); /* 드랍 다운 목록 위치 조절 */
}

.dropdown-content.show {
  display: block;
  min-width: max-content;
  margin-top: $margin-small;
  z-index: $z-index-dropdown;
  width: inherit;
}

.scrollable-container {
  border-radius: $border-radius-default;
  background-color: white;
}

.scrollable-list li {
  padding: 14px 30px; /* 리스트 아이템의 내부 패딩 설정 */
}

.dropdown-exterior {
  position: fixed; /* 뷰포트에 대해 고정 위치 설정 */
  top: 0; /* 화면의 맨 위에 위치 */
  left: 0; /* 화면의 왼쪽에 위치 */
  width: 100vw; /* 화면 너비를 모두 차지하도록 설정 */
  height: 100vh; /* 화면 높이를 모두 차지하도록 설정 */
  background-color: transparent; /* 배경색을 투명하게 설정 */
  z-index: 999; /* 드롭다운 목록보다 낮은 z-index를 설정하여 드롭다운 외부 클릭 시 드롭다운을 닫을 수 있도록 함 */
}
</style>