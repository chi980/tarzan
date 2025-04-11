<template>
  <div class="sub-container">
    <div class="top-bar-wrapper">
      <TopBarBack :title="'내 정보'" />
    </div>
    <div class="center-container">
      <div class="my-prorfile-wrapper">
        <div
          class="my-profile"
          v-for="(profile, index) in profiles"
          :key="index">
          <p>{{ profile.name }}</p>
          <p
            class="profile-data"
            :class="{ 'editable-data': profile.isEditable }">
            {{ transformData(profile.data) }}
          </p>
          <img :src="iconImgSrc" alt=">" v-if="profile.isEditable" />
        </div>
      </div>

      <div class="content-indicator"></div>

      <div class="tab-bar-wrapper">
        <TabBar :tabs="tabs" v-model:selectedTabIdx="selectedTabIndex"></TabBar>
      </div>
    </div>
    <div>
      <BottomBar class="bottom-bar" />
    </div>
  </div>
</template>

<script lang="ts" setup scoped>
import { ref, onMounted, computed } from "vue";
import { useRouter } from "vue-router";
import { axiosInstance } from "@/plugins/axiosPlugin";
import { useAuthStore } from "@/stores/authStore";

import TopBarBack from "@/components/common/TopBarBack.vue";
import BottomBar from "@/components/common/BottomBar.vue";
import iconImgSrc from "@/assets/icons/Arrows-chevron/Arrow-Down/Style=Outlined.svg";
import TabBar from "@/components/common/TabBar.vue";
import PostList from "@/components/post/PostList.vue";
import CommentList from "@/components/post/CommentList.vue";
import ReviewList from "@/components/review/ReviewList.vue";

import { Tab } from "@/data/tabs";

interface ProfileInfo {
  name: string;
  data: any;
  isEditable: boolean;
}
const profiles: ProfileInfo[] = [
  {
    name: "이메일",
    data: "aldms8960@naver.com",

    isEditable: false,
  },
  {
    name: "닉네임",
    data: "미은1",
    isEditable: true,
  },
  {
    name: "사는 곳",
    data: "서울시 중구",
    isEditable: true,
  },
  {
    name: "반려동물",
    data: false,
    isEditable: true,
  },
  {
    name: "자차",
    data: false,
    isEditable: true,
  },
];

function transformData(data: any): string {
  if (typeof data === "boolean") {
    return data ? "있음" : "없음";
  }
  return String(data);
}

const selectedTabIndex = ref(0); // 선택된 탭 인덱스 추적
const tabs: Tab[] = [
  {
    name: "게시글",
    component: PostList,
    props: {
      posts: [],
    },
  },
  {
    name: "댓글",
    component: CommentList,
    props: {
      comments: [],
    },
  },
  {
    name: "후기",
    component: ReviewList,
  },
];
</script>

<style lang="scss">
.top-bar-wrapper {
  width: 100%;
}
.sub-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  justify-content: space-between;
}
.center-container {
  position: relative;
  flex-grow: 1;
  width: 100%;

  display: flex;
  flex-direction: column;

  overflow-y: auto;
  /* 스크롤바 전체 영역 */
  &::-webkit-scrollbar {
    width: 4px; /* 세로축 스크롤바 폭 너비 */
    height: 100%; /* 가로축 스크롤바 폭 너비 */
  }
  &::-webkit-scrollbar-button {
    display: none;
  }
  /* 스크롤바 막대 제외 부분 */
  &::-webkit-scrollbar-track {
    background: transparent;
  }
  /* 스크롤바 막대 */
  &::-webkit-scrollbar-thumb {
    border-radius: calc($border-radius-default * 2);
    background: #f2f2f2;
  }
}

.my-prorfile-wrapper {
  background-color: white;

  .my-profile {
    @include custom-padding-x;
    @include custom-padding-y(24px);
    @include custom-text;
    display: flex;
    flex-direction: row;
    gap: 4px;
  }

  .profile-data {
    flex: 1;
    text-align: right;

    &.editable-data {
      @include custom-text($font-color: $text-color-light);
    }
  }

  img {
    @include custom-icon-style(14px);
    transform: rotate(-90deg);
  }
}

.tab-bar-wrapper {
  flex: 1;
  overflow-y: auto;
  /* 스크롤바 전체 영역 */
  &::-webkit-scrollbar {
    width: 4px; /* 세로축 스크롤바 폭 너비 */
    height: 100%; /* 가로축 스크롤바 폭 너비 */
  }
  &::-webkit-scrollbar-button {
    display: none;
  }
  /* 스크롤바 막대 제외 부분 */
  &::-webkit-scrollbar-track {
    background: transparent;
  }
  /* 스크롤바 막대 */
  &::-webkit-scrollbar-thumb {
    border-radius: calc($border-radius-default * 2);
    background: #f2f2f2;
  }
}
</style>
