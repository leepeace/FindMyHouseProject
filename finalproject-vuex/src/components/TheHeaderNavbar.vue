<template>
  <div>
    <nav class="nav" :class="{ affix: hello }">
      <div class="container" style="margin-left: 80px; margin-right: -50px">
        <div class="logo">
          <a href="/" style="margin-right: 50px">where is my home</a>
        </div>
        <div id="mainListDiv" class="main_list">
          <ul class="navlinks">
            <li><router-link to="/news">뉴스</router-link></li>
            <li><router-link to="/board">공지사항</router-link></li>
            <li><router-link v-if="userInfo" to="/qna">Q&A</router-link></li>
            <li><router-link v-if="userInfo" to="/favorite">관심목록</router-link></li>
          </ul>
        </div>
        <!-- <span class="navTrigger">
                        <li><a href="#">로그인</a></li>
            <li><a href="#">회원가입</a></li>
          <i></i>
        </span> -->
      </div>

      <b-navbar-nav class="ml-auto" style="margin-right: 20px">
        <b-nav-item-dropdown right>
          <template #button-content>
            <b-icon icon="people" font-scale="2"></b-icon>
          </template>
          <b-dropdown-item href="#" v-if="!userInfo">
            <router-link :to="{ name: 'join' }" class="link">
              <b-icon icon="person-circle"></b-icon> 회원가입
            </router-link>
          </b-dropdown-item>
          <b-dropdown-item href="#" v-if="!userInfo">
            <router-link :to="{ name: 'login' }" class="link"> <b-icon icon="key"></b-icon> 로그인 </router-link>
          </b-dropdown-item>
          <b-dropdown-item v-if="userInfo">
            <router-link :to="{ name: 'mypage' }" class="link"> <b-icon icon="key"></b-icon> 마이페이지 </router-link>
          </b-dropdown-item>
          <b-dropdown-item v-if="userInfo">
            <a @click.prevent="logout"> <b-icon icon="key"></b-icon> 로그아웃 </a>
          </b-dropdown-item>
          <b-dropdown-item v-if="userInfo && this.SET_IS_KAKAO_LOGIN == true">
            <a @click.prevent="kakaoLogout"> <b-icon icon="key"></b-icon>카카오 로그아웃 </a>
          </b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>

      <!-- <li v-if="!userInfo" style="margin-right:10px"><router-link to="/user/login">로그인</router-link></li>
            <li v-if="!userInfo"><router-link to="/user/join">회원가입</router-link></li>
            <li v-if="userInfo" style="margin-right:10px"><router-link to="/user/mypage">마이페이지</router-link></li>
            <li v-if="userInfo"><router-link to="/user/mypage">로그아웃</router-link></li> -->
    </nav>

    <section class="home"></section>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
const memberStore = "memberStore";

export default {
  name: "TheHeaderNavbar",
  data() {
    return {
      type: null,
      navClass: null,
      hello: false,
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  mounted() {
    window.addEventListener("scroll", this.scrollEvent);
  },
  methods: {
    ...mapActions(memberStore, ["userLogout"]),

    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO", "SET_IS_VALID_TOKEN", "SET_IS_KAKAO_LOGIN"]),

    async logout() {
      if (this.isLogin) {
        await this.userLogout(this.userInfo.userId);
        this.SET_IS_LOGIN(false);
        this.$router.push({ name: "login" });
      }
    },

    css() {
      var scrollPosition = document.body.scrollTop;
      var element = document.getElementsByClassName("nav");
      if (scrollPosition > 50) {
        element.classList.add("affix");
      } else {
        element.classList.remove("affix");
      }
    },

    kakaoLogout() {
      window.Kakao.init(process.env.VUE_APP_KAKAO_LOGIN_API_KEY);
      console.log("초기화 " + window.Kakao.isInitialized());
      let setessionInfo = this.setessionInfo;

      window.Kakao.Auth.logout(function () {
        console.log(window.Kakao.Auth.getAccessToken());
        setessionInfo("logout");
        location.href = "user/login";
      });
    },
    setessionInfo(type) {
      if (type === "logout") {
        this.SET_USER_INFO(null);
        this.SET_IS_LOGIN(false);
        this.SET_IS_VALID_TOKEN(false);
        this.SET_IS_KAKAO_LOGIN(false);
      }
    },
    scrollEvent() {
      var scrollPosition = document.documentElement.scrollTop;
      if (scrollPosition > 1000) {
        this.hello = true;
      } else {
        this.hello = false;
      }
    },
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css?family=Quicksand:400,500,700");
html,
body {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Quicksand", sans-serif;
  font-size: 62.5%;
  font-size: 10px;
  overflow: scroll;
}
/*-- Inspiration taken from abdo steif -->
/* --> https://codepen.io/abdosteif/pen/bRoyMb?editors=1100*/

/* Navbar section */

.nav {
  width: 100%;
  height: 65px;
  position: fixed;
  line-height: 65px;
  text-align: center;
}

.nav div.logo {
  float: left;
  width: auto;
  height: auto;
  padding-left: 1rem;
}

.nav div.logo a {
  text-decoration: none;
  color: #fff;
  font-size: 2.5rem;
}

.nav div.logo a:hover {
  color: #2300e6;
}

.nav div.main_list {
  height: 65px;
  float: left;
}

.nav div.main_list ul {
  width: 100%;
  height: 65px;
  display: flex;
  list-style: none;
  margin: 0;
  padding: 0;
}

.nav div.main_list ul li {
  width: auto;
  height: 65px;
  padding: 0;
  padding-right: 3rem;
}

.nav div.main_list ul li a {
  text-decoration: none;
  color: #fff;
  line-height: 65px;
  font-size: 2.2rem;
}

.nav div.main_list ul li a:hover {
  color: #2300e6;
}

/* Home section */

.home {
  width: 100%;
  height: 75vh;
  background-image: url("../assets/bnav.jpg");
  background-position: center top;
  background-size: cover;
}

.navTrigger {
  display: none;
}

.nav {
  padding-top: 20px;
  padding-bottom: 20px;
  -webkit-transition: all 0.4s ease;
  transition: all 0.4s ease;
}

/* Media qurey section */

@media screen and (min-width: 768px) and (max-width: 1024px) {
  .container {
    margin: 0;
  }
}

@media screen and (max-width: 768px) {
  .navTrigger {
    display: block;
  }
  .nav div.logo {
    margin-left: 15px;
  }
  .nav div.main_list {
    width: 100%;
    height: 0;
    overflow: hidden;
  }
  .nav div.show_list {
    height: auto;
    display: none;
  }
  .nav div.main_list ul {
    flex-direction: column;
    width: 100%;
    height: 100vh;
    right: 0;
    left: 0;
    bottom: 0;
    background-color: #111;
    /*same background color of navbar*/
    background-position: center top;
  }
  .nav div.main_list ul li {
    width: 100%;
    text-align: right;
  }
  .nav div.main_list ul li a {
    text-align: center;
    width: 100%;
    font-size: 3rem;
    padding: 20px;
  }
  .nav div.media_button {
    display: block;
  }
}

/* Animation */
/* Inspiration taken from Dicson https://codemyui.com/simple-hamburger-menu-x-mark-animation/ */

.navTrigger {
  cursor: pointer;
  width: 30px;
  height: 25px;
  margin: auto;
  position: absolute;
  right: 30px;
  top: 0;
  bottom: 0;
}

.navTrigger i {
  background-color: #fff;
  border-radius: 2px;
  content: "";
  display: block;
  width: 100%;
  height: 4px;
}

.navTrigger i:nth-child(1) {
  -webkit-animation: outT 0.8s backwards;
  animation: outT 0.8s backwards;
  -webkit-animation-direction: reverse;
  animation-direction: reverse;
}

.navTrigger i:nth-child(2) {
  margin: 5px 0;
  -webkit-animation: outM 0.8s backwards;
  animation: outM 0.8s backwards;
  -webkit-animation-direction: reverse;
  animation-direction: reverse;
}

.navTrigger i:nth-child(3) {
  -webkit-animation: outBtm 0.8s backwards;
  animation: outBtm 0.8s backwards;
  -webkit-animation-direction: reverse;
  animation-direction: reverse;
}

.navTrigger.active i:nth-child(1) {
  -webkit-animation: inT 0.8s forwards;
  animation: inT 0.8s forwards;
}

.navTrigger.active i:nth-child(2) {
  -webkit-animation: inM 0.8s forwards;
  animation: inM 0.8s forwards;
}

.navTrigger.active i:nth-child(3) {
  -webkit-animation: inBtm 0.8s forwards;
  animation: inBtm 0.8s forwards;
}

@-webkit-keyframes inM {
  50% {
    -webkit-transform: rotate(0deg);
  }
  100% {
    -webkit-transform: rotate(45deg);
  }
}

@keyframes inM {
  50% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(45deg);
  }
}

@-webkit-keyframes outM {
  50% {
    -webkit-transform: rotate(0deg);
  }
  100% {
    -webkit-transform: rotate(45deg);
  }
}

@keyframes outM {
  50% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(45deg);
  }
}

@-webkit-keyframes inT {
  0% {
    -webkit-transform: translateY(0px) rotate(0deg);
  }
  50% {
    -webkit-transform: translateY(9px) rotate(0deg);
  }
  100% {
    -webkit-transform: translateY(9px) rotate(135deg);
  }
}

@keyframes inT {
  0% {
    transform: translateY(0px) rotate(0deg);
  }
  50% {
    transform: translateY(9px) rotate(0deg);
  }
  100% {
    transform: translateY(9px) rotate(135deg);
  }
}

@-webkit-keyframes outT {
  0% {
    -webkit-transform: translateY(0px) rotate(0deg);
  }
  50% {
    -webkit-transform: translateY(9px) rotate(0deg);
  }
  100% {
    -webkit-transform: translateY(9px) rotate(135deg);
  }
}

@keyframes outT {
  0% {
    transform: translateY(0px) rotate(0deg);
  }
  50% {
    transform: translateY(9px) rotate(0deg);
  }
  100% {
    transform: translateY(9px) rotate(135deg);
  }
}

@-webkit-keyframes inBtm {
  0% {
    -webkit-transform: translateY(0px) rotate(0deg);
  }
  50% {
    -webkit-transform: translateY(-9px) rotate(0deg);
  }
  100% {
    -webkit-transform: translateY(-9px) rotate(135deg);
  }
}

@keyframes inBtm {
  0% {
    transform: translateY(0px) rotate(0deg);
  }
  50% {
    transform: translateY(-9px) rotate(0deg);
  }
  100% {
    transform: translateY(-9px) rotate(135deg);
  }
}

@-webkit-keyframes outBtm {
  0% {
    -webkit-transform: translateY(0px) rotate(0deg);
  }
  50% {
    -webkit-transform: translateY(-9px) rotate(0deg);
  }
  100% {
    -webkit-transform: translateY(-9px) rotate(135deg);
  }
}

@keyframes outBtm {
  0% {
    transform: translateY(0px) rotate(0deg);
  }
  50% {
    transform: translateY(-9px) rotate(0deg);
  }
  100% {
    transform: translateY(-9px) rotate(135deg);
  }
}

.affix {
  padding: 0;
  background-color: #111;
}
</style>
