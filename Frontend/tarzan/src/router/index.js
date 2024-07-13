// src/router/index.js
import { useAuthStore } from "@/stores/authStore.js";
import { createRouter, createWebHistory } from "vue-router";
// import axios from "axios";
import { createApiInstance } from "@/axios/axiosInstance.js";

const routes = [
  {
    path: "/",
    name: "Home",
    component: () => import("@/components/Home.vue"),
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
    component: () => import("@/pages/BookMark.vue"),
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
        path: "homeowner",
        component: () => import("@/components/fraud/CheckHouseOwner.vue"),
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
    component: () => import("@/pages/Community.vue"),
  },

  {
    path: "/community/:id",
    name: "PostDetail",
    component: () => import("@/components/post/PostDetail.vue"),
  },

  {
    path: "/community/postcreate",
    name: "PostCreate",
    component: () => import("@/components/post/PostCreate.vue"),
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
  const authRequired = to.meta.requiresAuth;

  // 인증이 필요한 페이지이고, 사용자가 로그인되어 있지 않은 경우 로그인 페이지로 리다이렉트
  if (authRequired && !useAuthStore.isAuthenticated) {
    if (!useAuthStore.isRefreshToken) {
      alert("아직 refreshToken이 존재합니다!");
      const postData = {
        refreshTokenString: userAuthStore.getAccessToken(),
      };
      createApiInstance
        .post("/api/oauth/token/refresh", postData)
        .then((res) => {
          console.log("재발급 성공");
          console.log(res.data);
          // 새 토큰을 저장하는 로직
          // 다음 단계로 이동
        })
        .catch((error) => {
          console.error(error);
        });
    }

    alert("로그인이 필요합니다!");
    next("/");
  } else {
    next(); // 그렇지 않으면 다음 단계로 이동
  }
});

export default router;
