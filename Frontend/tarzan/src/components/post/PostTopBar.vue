<template>
  <div class="post-topbar">
    <div class="post-topbar-button">
      <img :src="backIcon" alt="back-icon" @click="goToBack" />
    </div>
    <EditButton :options="options" />

    <Modal v-model="show">
      <template #default="{ close }">
        <ReportComponent
          :reportTargetType="'POST'"
          :reportTargetId="props.boardIdx"
          @close="close" />
      </template>
    </Modal>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { axiosInstance } from "@/plugins/axiosPlugin";
import backIcon from "@/assets/icons/topbar/icon-back.png";
import EditButton from "@/components/common/EditButton.vue";

import Modal from "@/components/common/Modal.vue";
import ReportComponent from "@/components/common/ReportComponent.vue";

// 상태 변수 정의
const router = useRouter();
const isDropDownOpen = ref(false);

const props = defineProps({
  isAuthor: Boolean,
  boardIdx: Number,
});

const goToBack = () => {
  router.go(-1);
};
const deletePost = async () => {
  try {
    const response = await axiosInstance.delete(`/v1/board/${props.boardIdx}`);

    if (response.data.success) {
      console.log("게시글 삭제 성공:", response.data);
      alert("게시글이 성공적으로 삭제되었습니다.");
      router.push("/community"); // 삭제 후 이동
    } else {
      console.error("Failed:", response.data.message);
      alert("게시글 삭제에 실패했습니다.");
    }
  } catch (error) {
    console.error("Error fetching posts:", error);
    alert("게시글 삭제에 실패했습니다.");
  }
};
const options = computed(() => {
  if (props.isAuthor) {
    return [
      {
        name: "수정하기",
        onClick: () => router.push(`/community/${props.boardIdx}/edit`),
      },
      {
        name: "삭제하기",
        onClick: () => deletePost(),
      },
    ];
  } else {
    return [
      {
        name: "신고하기",
        onClick: onReport,
      },
    ];
  }
});

const show = ref(false);
const onReport = async () => {
  show.value = true;
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
  @include custom-icon-style;
}
</style>
