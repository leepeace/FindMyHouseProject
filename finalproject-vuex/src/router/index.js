import Vue from "vue";
import VueRouter from "vue-router";
import AppMain from "@/views/AppMain";
import AppInstagram from "@/views/AppInstagram";
import AppHouse from "@/views/AppHouse";
import store from "@/store";

Vue.use(VueRouter);

const onlyAuthUser = async (to, from, next) => {
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  const checkToken = store.getters["memberStore/checkToken"];
  let token = sessionStorage.getItem("access-token");
  console.log("로그인 처리 전", checkUserInfo, token);

  if (checkUserInfo != null && token) {
    console.log("토큰 유효성 체크하러 가자!!!!");
    await store.dispatch("memberStore/getUserInfo", token);
  }
  if (!checkToken || checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    // next({ name: "login" });
    router.push({ name: "login" });
  } else {
    console.log("로그인 했다!!!!!!!!!!!!!.");
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "main",
    component: AppMain,
  },
  {
    path: "/instagram",
    name: "insta",
    component: AppInstagram,
  },
  {
    path: "/news",
    name: "news",
    component: () => import("@/components/main/NewsDetail"),
  },
  {
    path: "/house",
    name: "house",
    component: AppHouse,
  },
  {
    path: "/favorite",
    name: "favorite",
    component: () => import("@/components/favorite/FavoriteMain"),
  },
  {
    path: "/todo",
    name: "todo",
    component: () => import("@/views/AppTodo"),
  },
  {
    path: "/user",
    name: "user",
    component: () => import("@/views/AppUser"),
    children: [
      {
        path: "join",
        name: "join",
        component: () => import("@/components/user/UserRegister"),
      },
      {
        path: "login",
        name: "login",
        component: () => import("@/components/user/UserLogin"),
      },
      {
        path: "mypage",
        name: "mypage",
        component: () => import("@/components/user/UserMyPage"),
      },
      {
        path: "modify",
        name: "userModify",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/user/UserModify"),
      },
    ],
  },
  {
    path: "/board",
    name: "board",
    component: () => import("@/views/AppBoard"),
    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "boardlist",
        component: () => import("@/components/board/BoardList"),
      },
      {
        path: "write",
        name: "boardwrite",
        component: () => import("@/components/board/BoardWrite"),
      },
      {
        path: "view/:articleNo",
        name: "boardview",
        component: () => import("@/components/board/BoardView"),
      },
      {
        path: "modify",
        name: "boardmodify",
        component: () => import("@/components/board/BoardModify"),
      },
      {
        path: "delete/:articleNo",
        name: "boarddelete",
        component: () => import("@/components/board/BoardDelete"),
      },
    ],
  },
  {
    path: "/qna",
    name: "qna",
    component: () => import("@/views/AppQna"),
    redirect: "/qna/list",
    children: [
      {
        path: "list",
        name: "qnalist",
        component: () => import("@/components/qna/QnaList"),
      },
      {
        path: "write",
        name: "qnawrite",
        component: () => import("@/components/qna/QnaWrite"),
      },
      {
        path: "view/:qnaNo",
        name: "qnaview",
        component: () => import("@/components/qna/QnaView"),
      },
      {
        path: "modify",
        name: "qnamodify",
        component: () => import("@/components/qna/QnaModify"),
      },
      {
        path: "delete/:qnaNo",
        name: "qnadelete",
        component: () => import("@/components/qna/QnaDelete"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
