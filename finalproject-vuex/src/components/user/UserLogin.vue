<template>
  <div>
    <b-container class="bv-example-row mt-3" style="width: 100%; height: 110%">
      <b-form class="login-wrap" @submit.prevent="confirm" style="margin-top: 200px">
        <div class="login-html">
          <b-alert show variant="danger" v-if="isError">아이디 또는 비밀번호를 확인하세요.</b-alert>
          <label for="tab-1" class="tab" style="color: white">Sign In</label>
          <div class="login-form">
            <div class="sign-in-htm">
              <b-form-group class="group">
                <label for="user" class="label">UserId</label>
                <b-form-input id="user" type="text" class="input" v-model="user.userid" />
              </b-form-group>
              <b-form-group class="group">
                <label for="pass" class="label">Password</label>
                <b-form-input
                  id="pass"
                  type="password"
                  class="input"
                  data-type="password"
                  v-model="user.userpwd"
                />
              </b-form-group>
              <div class="group">
                <input id="check" type="checkbox" class="check" checked />
                <label for="check"><span class="icon"></span> Keep me Signed in</label>
              </div>
              <div class="group">
                <b-button type="submit" class="button" style="color: white">Sign In</b-button>
              </div>
              <div class="hr"></div>
              <div class="foot-lnk" style="color: white">
                <a @click="showModal">Forgot Password?</a>
              </div>
            </div>
          </div>
        </div>
      </b-form>
      <b-modal ref="my-modal" hide-footer title="비밀번호 찾기">
        <div class="d-block text-center">
          <b-form-input
            v-model="email"
            type="email"
            placeholder="이메일을 입력하세요"
          ></b-form-input>
        </div>
        <b-button class="mt-3" variant="outline-danger" block @click="hideModal">Close Me</b-button>
        <b-button class="mt-2" variant="outline-warning" block @click="findPassword"
          >Toggle Me</b-button
        >
      </b-modal>
    </b-container>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import axios from "@/api/http";

const memberStore = "memberStore";

export default {
  name: "UserLogin",
  data() {
    return {
      isError: false,
      user: {
        userid: null,
        userpwd: null,
      },
      modalShow: false,
      email: null,
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError", "userInfo"]),
  },

  methods: {
    ...mapActions(memberStore, ["userConfirm", "getUserInfo"]),
    ...mapMutations(memberStore, ["SET_IS_LOGIN"]),
    async confirm() {
      this.checkForm();
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("access-token");
      console.log("1. confirm() token >> " + token);

      if (this.isLogin) {
        await this.getUserInfo(sessionStorage.getItem("access-token"));

        console.log("4. confirm() userInfo :: ", this.userInfo);
        this.movePage();
      } else {
        this.isError = true;
      }
    },
    showModal() {
      this.$refs["my-modal"].show();
    },
    hideModal() {
      this.$refs["my-modal"].hide();
    },
    findPassword() {
      axios.get(`/user/find/${this.email}`).then((response) => {
        if (response.status == 200) {
          console.log("success");
        } else {
          alert("fail");
        }
      });
      this.$refs["my-modal"].toggle("#toggle-btn");
    },
    movePage() {
      this.$router.push({ name: "main" });
    },
    checkForm() {
      if (
        this.user.userid === "" ||
        this.user.userpwd === "" ||
        this.user.userid === null ||
        this.user.userpwd === null
      ) {
        this.isError = true;
        this.SET_IS_LOGIN(false);
      }
    },
  },
};
</script>

<style scoped>
body {
  margin: 0;
  color: #6a6f8c;
  background: #c8c8c8;
  font: 600 16px/18px "Open Sans", sans-serif;
}
*,
:after,
:before {
  box-sizing: border-box;
}
.clearfix:after,
.clearfix:before {
  content: "";
  display: table;
}
.clearfix:after {
  clear: both;
  display: block;
}
a {
  color: inherit;
  text-decoration: none;
}

.login-wrap {
  width: 100%;
  margin: auto;
  max-width: 525px;
  min-height: 670px;
  position: relative;
  background: url(https://raw.githubusercontent.com/khadkamhn/day-01-login-form/master/img/bg.jpg)
    no-repeat center;
  box-shadow: 0 12px 15px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19);
}
.login-html {
  width: 100%;
  height: 100%;
  position: absolute;
  padding: 90px 70px 50px 70px;
  background: rgba(40, 57, 101, 0.9);
}
.login-html .sign-in-htm,
.login-html .sign-up-htm {
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  position: absolute;
}
.login-html .sign-in,
.login-html .sign-up,
.login-form .group .check {
  display: none;
}
.login-html .tab,
.login-form .group .label,
.login-form .group .button {
  text-transform: uppercase;
}
.login-html .tab {
  font-size: 22px;
  margin-right: 15px;
  padding-bottom: 5px;
  margin: 0 15px 10px 0;
  display: inline-block;
  border-bottom: 2px solid transparent;
}
.login-html .sign-in:checked + .tab,
.login-html .sign-up:checked + .tab {
  color: #fff;
  border-color: #1161ee;
}
.login-form {
  min-height: 345px;
  position: relative;
  perspective: 1000px;
  transform-style: preserve-3d;
}
.login-form .group {
  margin-bottom: 15px;
}
.login-form .group .label,
.login-form .group .input,
.login-form .group .button {
  width: 100%;
  color: #fff;
  display: block;
}
.login-form .group .input,
.login-form .group .button {
  border: none;
  padding: 15px 20px;
  border-radius: 25px;
  background: rgba(255, 255, 255, 0.1);
}
.login-form .group input[data-type="password"] {
  text-security: circle;
  -webkit-text-security: circle;
}
.login-form .group .label {
  color: #aaa;
  font-size: 12px;
}
.login-form .group .button {
  background: #1161ee;
}
.login-form .group label .icon {
  width: 15px;
  height: 15px;
  border-radius: 2px;
  position: relative;
  display: inline-block;
  background: rgba(255, 255, 255, 0.1);
}
.login-form .group label .icon:before,
.login-form .group label .icon:after {
  content: "";
  width: 10px;
  height: 2px;
  background: #fff;
  position: absolute;
  transition: all 0.2s ease-in-out 0s;
}
.login-form .group label .icon:before {
  left: 3px;
  width: 5px;
  bottom: 6px;
  transform: scale(0) rotate(0);
}
.login-form .group label .icon:after {
  top: 6px;
  right: 0;
  transform: scale(0) rotate(0);
}
.login-form .group .check:checked + label {
  color: #fff;
}
.login-form .group .check:checked + label .icon {
  background: #1161ee;
}
.login-form .group .check:checked + label .icon:before {
  transform: scale(1) rotate(45deg);
}
.login-form .group .check:checked + label .icon:after {
  transform: scale(1) rotate(-45deg);
}
.login-html .sign-in:checked + .tab + .sign-up + .tab + .login-form .sign-in-htm {
  transform: rotate(0);
}
.login-html .sign-up:checked + .tab + .login-form .sign-up-htm {
  transform: rotate(0);
}

.hr {
  height: 2px;
  margin: 60px 0 50px 0;
  background: rgba(255, 255, 255, 0.2);
}
.foot-lnk {
  text-align: center;
}
</style>
