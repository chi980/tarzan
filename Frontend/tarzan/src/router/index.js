// src/router/index.js
import { ref } from "vue";
import { useAuthStore } from "@/stores/authStore.js";
import { createRouter, createWebHistory } from "vue-router";

const routes = [
  {
    path: "/",
    name: "Home",
    component: () => import("@/pages/Home.vue"),
  },
  {
    path: "/login",
    name: "Login",
    component: () => import("@/pages/Login.vue"),
  },
  {
    path: "/signup",
    name: "SignUp",
    component: () => import("@/pages/SignUp.vue"),
  },
  {
    path: "/bookmark",
    name: "BookMark",

    children: [
      {
        path: "",
        component: () => import("@/pages/BookMark.vue"),
      },
      {
        path: ":id",
        name: "BookMarkDetail",
        component: () => import("@/pages/BookmarkDetail.vue"),
        props: true,
      },
      {
        path: "create",
        component: () => import("@/components/bookmark/AddHousePage.vue"),
      },
      {
        path: "user",
        name: "AddHousePage",
        component: () => import("@/components/bookmark/AddHousePage.vue"),
      },
      {
        path: "compare",
        name: "BookmarkCompare",
        component: () => import("@/pages/BookmarkCompare.vue"),
      },
    ],
  },

  {
    path: "/fraud",
    name: "Fraud",
    children: [
      {
        path: "",
        component: () => import("@/pages/Fraud.vue"),
      },
      {
        path: "landlord",
        name: "CheckLandlord",
        component: () => import("@/components/fraud/CheckLandlord.vue"),
      },
      {
        path: "fakeLandlord",
        name: "PreventFakeLandlord",
        component: () => import("@/components/fraud/PreventFakeLandlord.vue"),
      },
      {
        path: "trustee",
        name: "PreventTrustee",
        component: () => import("@/components/fraud/PreventTrustee.vue"),
      },
      {
        path: "contract",
        name: "CheckContract",
        component: () => import("@/components/fraud/CheckContract.vue"),
      },
      {
        path: "special-contract",
        name: "CheckSpecialContract",
        component: () => import("@/components/fraud/CheckSpecialContract.vue"),
      },
      {
        path: "real-estate",
        name: "CheckRealEstateBroker",
        component: () => import("@/components/fraud/CheckRealEstateBroker.vue"),
      },
      {
        path: "value-check",
        name: "CheckValue",
        component: () => import("@/components/fraud/CheckValue.vue"),
      },
    ],
  },

  {
    path: "/login-processing",
    name: "LoginProcessing",
    component: () => import("@/pages/LoginProcessing.vue"),
  },
  {
    path: "/login-failure",
    name: "LoginFailure",
    component: () => import("@/pages/LoginFailure.vue"),
  },

  {
    path: "/community",
    // name: "Community",
    children: [
      {
        path: "",
        name: "Community",
        component: () => import("@/pages/Community.vue"),
      },
      {
        path: ":id",
        name: "PostDetail",
        component: () => import("@/components/post/PostDetail.vue"),
      },
      {
        path: ":id/edit",
        name: "PostEdit",
        component: () => import("@/components/post/PostEdit.vue"),
      },
      {
        path: "postcreate",
        name: "PostCreate",
        component: () => import("@/components/post/PostCreate.vue"),
      },
      {
        path: "search",
        name: "PostSearch",
        component: () => import("@/components/post/PostSearchView.vue"),
      },
    ],
  },

  {
    path: "/review",
    children: [
      {
        path: "",
        name: "Review",
        component: () => import("@/pages/Review.vue"),
      },
      {
        path: ":reviewIdx",
        name: "ReviewDetail",
        component: () => import("@/pages/Review.vue"),
      },
      {
        path: "create",
        name: "ReviewCreate",
        component: () => import("@/pages/ReviewCreate.vue"),
      },
    ],
  },

  {
    path: "/mypage",
    // name: "MyPage",
    children: [
      {
        path: "",
        name: "MyPage",
        component: () => import("@/pages/MyPage.vue"),
      },
      {
        path: "edit-profile",
        name: "EditProfile",
        component: () => import("@/pages/EditProfile.vue"),
      },
    ],
  },
  {
    path: "/:catchAll(.*)",
    name: "NotFound",
    component: () => import("@/pages/ErrorNotFound.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior() {
    // always scroll to top
    return { top: 0 };
  },
});

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();
  const isAuthenticated = authStore.isAuthenticated;

  const publicPages = ["/login-processing", "/login-failure", "/login"];
  const isPublicPage = publicPages.includes(to.path);

  // 인증된 상태이거나 모두에게 공개된 페이지의 경우 통과
  // 인증이 필요 없는 페이지인 경우
  if (true) {
    next();
  } else if (isAuthenticated) {
    // 인증된 사용자일 경우
    if (authStore.isGuest) {
      // GUEST인 사용자는 SignUp 페이지로 리디렉션
      if (to.path == "/signup") {
        next();
      } else {
        alert("개인 정보를 먼저 입력하셔야 합니다!");
        next({ name: "SignUp" });
      }
    } else {
      // 인증된 사용자는 정상적으로 페이지에 접근
      next();
    }
  } else {
    // 인증되지 않은 사용자일 경우 로그인 페이지로 리디렉션
    alert("로그인을 먼저 하셔야 합니다!");
    next({ name: "Login" });
  }
});

export default router;
