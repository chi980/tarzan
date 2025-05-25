<template>
  <div class="sub-container">
    <PostTopBar
      v-if="post.board_is_writer !== undefined"
      :isAuthor="post.board_is_writer"
      :boardIdx="boardIdx"
      type="'post'"
      class="top-bar" />
    <div class="center-container">
      <!-- 게시글 상세 내용 -->
      <div class="post-detail-container">
        <div class="post-tag">
          <span>{{ post.board_tag_kor }}</span>
        </div>
        <div class="post-writer">
          <span clas>{{ post.board_writer_nickname }}</span>
        </div>
        <div class="post-title">
          <h2>{{ post.board_title }}</h2>
        </div>
        <div class="post-content">
          <p>{{ post.board_content }}</p>
        </div>
        <div class="post-time">
          <span>{{ formatSmartTime(post.board_created_at) }} · </span>
        </div>
      </div>
      <div>
        <div class="content-indicator"></div>
      </div>
      <!-- 댓글 영역 -->
      <div class="comment-list-container">
        <List
          ref="commentList"
          :fetchItems="fetchComments"
          :params="params"
          :onDelete="handleDeleteComment">
          <template #item="{ item, index, onDelete }">
            <CommentItem
              :comment="item"
              @delete="
                () => {
                  onDelete(item, index);
                }
              " />
          </template>
        </List>
      </div>
      <div class="center-container-fix-button">
        <CommentInput
          class="comment-input"
          :boardIdx="boardIdx"
          @comment-submitted="addComment" />
      </div>
    </div>

    <div class="bottom-bar-wrapper">
      <BottomBar class="bottom-bar" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import { axiosInstance } from "@/plugins/axiosPlugin";

import BottomBar from "@/components/common/BottomBar.vue";
import PostTopBar from "@/components/post/PostTopBar.vue";
import CommentInput from "@/components/post/CommentInput.vue";
import List from "@/components/common/List.vue";
import CommentItem from "@/components/post/CommentItem.vue";

import { formatSmartTime } from "@/utils/formatTime";

const route = useRoute();

// 게시글 상세 정보
const post = ref({}); // 게시글
const boardIdx = Number(route.params.id);

// API: 게시글 상세 정보 가져오기
const fetchPostDetail = async () => {
  try {
    const response = await axiosInstance.get(`/v1/board/${boardIdx}`);
    if (response.data.success) {
      post.value = response.data.data;
      console.log("게시물 상세 성공");
    } else {
      console.error("게시물 상세 실패:", response.data.message);
    }
  } catch (error) {
    console.error("게시물 상세 오류:", error);
  }
};

onMounted(async () => {
  await fetchPostDetail();
});

const params = ref({
  size: 7,
  sortBy: "최신순",
  boardIdx: boardIdx,
});

const fetchComments = async (page, params) => {
  try {
    const response = await axiosInstance.get(`/v1/comments`, {
      params: {
        ...params,
        page: page,
      },
    });

    if (response.data.success) {
      return {
        items: response.data.data.list,
        isNext: response.data.data.isNext,
      };
    } else {
      console.error("Failed to fetch data:", response.data.message);
      return {
        items: [],
        isNext: false,
      };
    }
  } catch (error) {
    console.error("API request error:", error);
    return {
      items: [],
      isNext: false,
    };
  }
};

const handleDeleteComment = async (item) => {
  return Promise.resolve();
};

// 1) List 컴포넌트 인스턴스를 담을 ref
const commentList = ref<InstanceType<typeof List> | null>(null);

// 2) CommentInput에서 emit된 댓글을 받아 리스트에 추가
const addComment = (comment: any) => {
  console.log("댓글 추가:", comment);
  // commentList.value가 있을 때 addItem 호출
  commentList.value?.addItem(comment);
};
</script>

<style lang="scss" scoped>
.sub-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.top-bar,
.bottom-bar {
  flex-shrink: 0;
}

.center-container {
  display: flex;
  flex-direction: column;
  // background-color: #ededed;
  background-color: white;
  flex-grow: 1;
  width: 100%;
  height: 100%;
  overflow-y: auto;
  @include custom-text();
}

.post-detail-container {
  background-color: white;
  text-align: left;
  display: flex;
  flex-direction: column;
  gap: 12px;
  @include custom-padding-x;
  @include custom-padding-y;
}

.center-container .post-detail-container .post-tag span {
  display: inline-block;
  background-color: #f2f3f9;
  @include custom-padding($margin-small);
  border-radius: 10px;
  font-size: 10px;
}

.center-container .post-detail-container .post-writer {
  color: #9f9f9f;
  font-size: 12px;
}

.center-container .post-detail-container .post-title {
  @include custom-text-bold($font-color: $text-color-default, $font-size: 19px);
}

.center-container .post-detail-container .post-content {
  line-height: 1.5;
  font-size: 13px;
}

.center-container .post-detail-container .post-time {
  font-size: 12px;
  color: #9f9f9f;
  margin-top: 12px;
}

.comment-container {
  background-color: white;
  position: relative;
  flex: 1;
}

.comment-list-container {
  @include custom-margin-x;
  margin-bottom: $margin-default;
  flex-grow: 1;
  display: flex;
  flex-direction: column;
}

.center-container-fix-button {
  @include custom-padding-x;
  position: sticky;
  bottom: $padding-default;
}
.bottom-bar-wrapper {
  width: 100%;
}
</style>
