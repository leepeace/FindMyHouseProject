<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="dark">
      <b-navbar-brand href="#">
        <router-link :to="{ name: 'main' }">
          <!-- <b-img
            :src="require('@/assets/houselogo.png')"
            id="logo"
            class="d-inline-block align-top"
            alt="logo"
            style="width:100px;height:100px"
          ></b-img> -->
        </router-link>
      </b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="#">
            <router-link :to="{ name: 'main' }" class="link"> 메인 </router-link>
          </b-nav-item>
          <b-nav-item href="#">
            <router-link :to="{ name: 'board' }" class="m-2 link"> 공지사항 </router-link>
          </b-nav-item>
          <b-nav-item href="#" v-if="userInfo">
            <router-link :to="{ name: 'news' }" class="link"> 뉴스 </router-link>
          </b-nav-item>
          <b-nav-item href="#" v-if="userInfo">
            <router-link :to="{ name: 'qna' }" class="link"> QnA </router-link>
          </b-nav-item>
          <b-nav-item href="#" v-if="userInfo">
            <router-link :to="{ name: 'favorite' }" class="link"> 관심목록 </router-link>
          </b-nav-item>
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto">
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
              <router-link :to="{ name: 'login' }" class="link">
                <b-icon icon="key"></b-icon> 로그인
              </router-link>
            </b-dropdown-item>
            <b-dropdown-item v-if="userInfo">
              <router-link :to="{ name: 'mypage' }" class="link">
                <b-icon icon="key"></b-icon> 마이페이지
              </router-link>
            </b-dropdown-item>
            <b-dropdown-item v-if="userInfo">
              <a @click.prevent="logout"> <b-icon icon="key"></b-icon> 로그아웃 </a>
            </b-dropdown-item>
            <b-dropdown-item v-if="userInfo && this.SET_IS_KAKAO_LOGIN == true" >
              <a @click.prevent="kakaoLogout" > <b-icon icon="key"></b-icon>카카오 로그아웃 </a>
            </b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
  
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
const memberStore = "memberStore";

export default {
  
  name: "TheHeaderNavbar",
  data() {
    return {
      type:null,
    }
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },

  // mounted() {
  //   let scripts = document.createElement('script');
  //   scripts.setAttribute('src', 'https://developers.kakao.com/sdk/js/kakao.js');

  //   document.head.appendChild(scripts);
  // },

  // mounted() {
  //   window.Kakao.init(process.env.VUE_APP_KAKAO_LOGIN_API_KEY);
  //   console.log("초기화 " + window.Kakao.isInitialized());
  // },
 
  
  methods: {
    ...mapActions(memberStore, ["userLogout"]),
 
    ...mapMutations(memberStore, [
      "SET_IS_LOGIN",
      "SET_USER_INFO",
      "SET_IS_VALID_TOKEN",
      "SET_IS_KAKAO_LOGIN",
    ]),
    async logout() {
      if (this.isLogin) {
        await this.userLogout(this.userInfo.userId);
        this.SET_IS_LOGIN(false);
        this.$router.push({ name: "login" });
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
  },
};
</script>

<style scoped>
 #logo {
  width: 120px;
}

.link {
  text-decoration: none;
}

