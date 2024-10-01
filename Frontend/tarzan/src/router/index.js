// src/router/index.js
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
        path: "map",
        component: () => import("@/components/bookmark/MapPage.vue"),
      },
      {
        path: "add",
        component: () => import("@/components/bookmark/AddItemPage.vue"),
      },
      {
        path: "check/cost",
        component: () => import("@/components/bookmark/CheckCostPage.vue"),
      },
      {
        path: "check/option",
        component: () => import("@/components/bookmark/CheckOptionPage.vue"),
      },
      {
        path: "check/checklist",
        component: () => import("@/components/bookmark/CheckCheckListPage.vue"),
      },
      {
        path: "compare",
        component: () => import("@/components/bookmark/CompareHouses.vue"),
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
    path: "/home2",
    name: "Home2",
    component: () => import("@/components/Home2.vue"),
    meta: { requiresAuth: true },
  },
  {
    path: "/tmp",
    name: "Tmp",
    component: () => import("@/pages/Tmp.vue"),
  },
  {
    path: "/login-processing",
    name: "LoginProcessing",
    component: () => import("@/pages/LoginProcessing.vue"),
    props: (route) => ({
      accessToken: route.query.accessToken,
      refreshToken: route.query.refreshToken,
    }),
  },

  {
    path: "/community",
    name: "Community",
    children: [
      {
        path: "",
        component: () => import("@/pages/Community.vue"),
      },
      {
        path: ":id",
        name: "PostDetail",
        component: () => import("@/components/post/PostDetail.vue"),
      },
      {
        path: "postcreate",
        name: "PostCreate",
        component: () => import("@/components/post/PostCreate.vue"),
      },
    ],
  },

  {
    path: "/review",
    name: "Review",
    children: [
      {
        path: "",
        name: "Review",
        component: () => import("@/components/review/Review.vue"),
      },
      {
        path: "create1",
        name: "CreateReview1",
        component: () => import("@/components/review/CreateReview1.vue"),
      },
      {
        path: "create2",
        name: "CreateReview2",
        component: () => import("@/components/review/CreateReview2.vue"),
      },
    ],
  },

  {
    path: "/mypage",
    name: "MyPage",
    children: [
      {
        path: "",
        name: "MyPage",
        component: () => import("@/pages/MyPage.vue"),
      },
      {
        path: "edit-profile",
        name: "EditProfile",
        component: () => import("@/components/mypage/EditProfile1.vue"),
      },
    ],
  },

  {
    path: "/auth",
    // component: AuthLayout, // auth 레이아웃을 사용할 수도 있습니다.
    meta: { requiresAuth: true },
    children: [],
  },

  // Always leave this as last one,
  // but you can also remove it
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

  // 예외 처리할 경로들
  const publicPages = ["/login", "/signup", "/login-processing"];

  // 현재 경로가 예외 처리 대상인지 확인
  const isPublicPage = publicPages.includes(to.path);

  if (isPublicPage) {
    next(); // 예외 처리 경로라면 통과
  } else {
    // Token 확인 로직
    if (authStore.accessToken && authStore.refreshToken) {
      next(); // accessToken과 refreshToken이 모두 있으면 통과
    } else if (authStore.accessToken && authStore.role == "GUEST") {
      if (to.name !== "SignUp") {
        next({ name: "SignUp" }); // accessToken만 있고 refreshToken이 없으면 /signup으로 리다이렉트
      } else {
        next(); // 이미 /signup 페이지로 가는 경우에는 그대로 진행
      }
    } else {
      if (to.name !== "Login") {
        next({ name: "Login" }); // 둘 다 없으면 /login으로 리다이렉트
      } else {
        next(); // 이미 /login 페이지로 가는 경우에는 그대로 진행
      }
    }
  }
});

export default router;
