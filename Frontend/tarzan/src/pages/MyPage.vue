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
          :key="index"
          @click="editProfile">
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
import { ref, onMounted, computed, watch } from "vue";
import { useRouter } from "vue-router";
import { axiosInstance } from "@/plugins/axiosPlugin";
import { useAuthStore } from "@/stores/authStore";

import { seoulSiGunGu } from "@/data/seoulsigungu";

import TopBarBack from "@/components/common/TopBarBack.vue";
import BottomBar from "@/components/common/BottomBar.vue";
import iconImgSrc from "@/assets/icons/Arrows-chevron/Arrow-Down/Style=Outlined.svg";
import TabBar from "@/components/common/TabBar.vue";
import MyPagePostList from "@/components/member/MyPagePostList.vue";
import MyPageReviewList from "@/components/member/MyPageReviewList.vue";

import { Tab } from "@/data/tabs";
import MyPageCommentList from "@/components/member/MyPageCommentList.vue";

const router = useRouter();
const user = ref(null);

// 데이터 저장용 변수 추가
const posts = ref([]);
const userComments = ref([]);
const userReviews = ref([]);

onMounted(async () => {
  await fetchUser();
});
const fetchUser = async () => {
  try {
    const response = await axiosInstance.get("/v1/user");
    user.value = response.data.data;

    profiles.value = [
      {
        name: "이메일",
        data: user.value.user_email,
        isEditable: false,
      },
      {
        name: "닉네임",
        data: user.value.user_nickname,
        isEditable: true,
      },
      {
        name: "사는 곳",
        data: getGuNameByValue(user.value.user_gu),
        isEditable: true,
      },
      {
        name: "반려동물",
        data: user.value.user_have_animal,
        isEditable: true,
      },
      {
        name: "자차",
        data: user.value.user_have_car,
        isEditable: true,
      },
    ];
  } catch (error) {
    console.error(error);
  }
};

interface ProfileInfo {
  name: string;
  data: any;
  isEditable: boolean;
}
const profiles = ref<ProfileInfo[] | null>(null);

function transformData(data: any): string {
  if (typeof data === "boolean") {
    return data ? "있음" : "없음";
  }
  return String(data);
}
function getGuNameByValue(value: string): string | undefined {
  return seoulSiGunGu.find((item) => item.value === value)?.name;
}
const selectedTabIndex = ref(0); // 선택된 탭 인덱스 추적
const tabs: Tab[] = [
  {
    name: "게시글",
    component: MyPagePostList,
  },
  {
    name: "댓글",
    component: MyPageCommentList,
  },
  {
    name: "후기",
    component: MyPageReviewList,
  },
];

const editProfile = () => {
  router.push({ name: "EditProfile" });
};
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
