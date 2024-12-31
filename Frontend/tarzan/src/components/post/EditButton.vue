<template>
  <div class="edit">
  <div class="edit-button">
    <img :src="settingIcon" alt="setting-icon" @click="controllDropDown">
  </div>
    <div class="scrollable-container dropdown-content"
      :class="['dropdown-content', { show: isDropDownOpen }]"
      @click="controllDropDown"
    >
      <div class="scrollable-list">
        <ul>
          <li
            v-for="option in filteredOptions"
            :key="option.idx"
            @click="selectOption(option)"
          >
            {{ option.name }}
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, defineProps, defineEmits } from 'vue';
import { useRouter } from 'vue-router';
import { axiosInstance } from "@/plugins/axiosPlugin";
import settingIcon from '@/assets/icons/topbar/icon-setting.png';

const router = useRouter();

const isDropDownOpen = ref(false);
const controllDropDown = () => {
  isDropDownOpen.value = !isDropDownOpen.value;
};

const props = defineProps({
  isAuthor: Boolean,    // 작성자 여부
  targetId: String | Number,     // 게시글 또는 댓글의 ID
  type: {               // 'post' 또는 'comment'
    type: String,
    required: true
  }
});

const emit = defineEmits(); // emit 정의 추가

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

const selectOption = (option) => {
  if (option.idx === 1) { // 수정
    if (props.type === 'post') {
      router.push(`/community/${props.targetId}/edit`);
    } else if (props.type === 'comment') {
      // router.push(`/edit-comment/${props.commentId}`); // 댓글 수정 페이지로 이동
    }
  } else if (option.idx === 2) { // 삭제
    if (confirm('정말로 삭제하시겠습니까?')) {
      if (props.type === 'post') {
        deletePost();   // 게시글 삭제
      } else if (props.type === 'comment') {
        deleteComment(); // 댓글 삭제
      }
    }
  }
};

// console.log("게시글/댓글 ID:", props.targetId, props.isAuthor, props.type); // 값을 확인

// API: 게시물 삭제
const deletePost = async () => {
  try {
    const response = await axiosInstance.delete(`/v1/board/${props.targetId}`);
    console.log("게시글/댓글 ID:", props.targetId); // 값을 확인
    
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

// API: 댓글 삭제
const deleteComment = async () => {
  try {
    const response = await axiosInstance.delete(`/v1/comments/${props.targetId}`);
    if (response.data.success) {
      alert('댓글이 삭제되었습니다.');
      emit('deleteItem', props.targetId, 'comment'); // 삭제 후 처리
    } else {
      alert('댓글 삭제에 실패했습니다.');
    }
  } catch (error) {
    console.error('댓글 삭제 오류:', error);
    alert('댓글 삭제에 실패했습니다.');
  }
};
</script>

<style lang="scss" scoped>
.edit {
  position: relative;
}

.edit img {
  width: 24px;
  height: 24px;
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