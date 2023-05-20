<template>
  <b-container class="bv-example-row mt-3">
    <b-form class="login-wrap" @submit.prevent="checkForm" style="margin-top:200px">
      <div class="login-html">
        <b-alert show variant="danger" v-if="isDuplicateError">중복된 아이디입니다.</b-alert>
        <label for="tab-2" class="tab">Sign Up</label>
        <div class="login-form">
          <div class="sign-up-htm">
            <b-form-group class="group">
              <label for="id" class="label">ID</label>
              <b-form-input id="userid" type="text" class="input" v-model="form.id" />
            </b-form-group>

            <b-form-group class="group">
              <label for="user" class="label">Username</label>
              <b-form-input id="username" type="text" class="input" v-model="form.username" />
            </b-form-group>

            <b-form-group class="group">
              <label for="pass" class="label">Password</label>
              <b-form-input id="pwd" type="password" class="input" data-type="password" v-model="form.pwd" />
            </b-form-group>

            <b-form-group class="group">
              <label for="pass" class="label">Email Address</label>
              <b-form-input id="email" type="email" class="input" v-model="form.email" />
            </b-form-group>

            <b-form-select
              v-model="selected"
              :options="options"
              style="opacity: 0.33; margin-bottom:30px"
              size="sm"
              class="mt-3"
            ></b-form-select>
            <div class="group">
              <button type="submit" class="button" style="color: white; padding-bottom: 10%">Sign Up</button>
            </div>
            <hr/>
            <div class="foot-lnk" for="tab-1" style="color: white">
              <router-link to="/user/login">Already Member?</router-link>
              OR
              <user-kakao></user-kakao>
            </div>
          </div>
        </div>
      </div>
    </b-form>
  </b-container>
</template>

<script>
import axios from "@/api/http";
import UserKakao from "@/components/user/UserKakao";

export default {
  name: "UserRegister",

  data() {
    return {
      isDuplicateError: false,
      form: {
        email: "",
        id: "",
        pwd: "",
        username: "",
      },
      emailId: "",
      emailDomain: "",
      errors: [],
      selected: null,
      options: [
        { value: null, text: "본인의 연령대를 선택하시오" },
        { value: 10, text: "10대" },
        { value: 20, text: "20대" },
        { value: 30, text: "30대" },
        { value: 40, text: "40대" },
        { value: 50, text: "50대" },
      ],
    };
  },

  components: {
    UserKakao,
  },

  methods: {
    onSubmit() {
      axios
        .post(`/user/user`, {
          userId: this.form.id,
          userName: this.form.username,
          userPwd: this.form.pwd,
          emailId: this.emailId,
          emailDomain: this.emailDomain,
          age: this.selected,
        })
        .then((response) => {
          if (response.status == 200) {
            alert("회원가입 성공 ");
            this.moveMainPage();
          } else if (response.status == 204) {
            this.isDuplicateError = true;
          } else {
            alert("회원 가입 실패");
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    moveMainPage() {
      this.$router.push({ name: "login" });
    },

    checkForm() {
      this.errors = [];
      if (this.form.username === "" || this.form.pwd === "" || this.form.id === "" || this.form.email === "") {
        this.errors.push(`field is required`);
      } else {
        if (this.form.username.length < 3 || this.form.id.length < 3) {
          this.errors.push(`field should have length of 3`);
        }
      }

      if (this.errors.length == 0) {
        this.splitEmail();
        this.onSubmit();
      } else {
        alert("아이디와 비밀번호를 다시 입력하세요");
      }
    },

    splitEmail() {
      const emailArr = this.form.email.split("@");
      this.emailId = emailArr[0];
      this.emailDomain = emailArr[1];
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
.tab {
  color: white;
}
.login-wrap {
  width: 100%;
  margin: auto;
  max-width: 525px;
  min-height: 670px;
  position: relative;
  background: url(https://raw.githubusercontent.com/khadkamhn/day-01-login-form/master/img/bg.jpg) no-repeat center;
  box-shadow: 0 12px 15px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19);
}
.login-html {
  width: 100%;
  height: 100%;
  position: absolute;
  padding: 40px 70px 50px 70px;
  background: rgba(40, 57, 101, 0.9);
}
.login-html .sign-in-htm,
.login-html .sign-up-htm {
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  position: absolute;

  transition: all 0.4s linear;
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

.login-form .group .check:checked + label {
  color: #fff;
}
.login-form .group .check:checked + label .icon {
  background: #1161ee;
}

.hr {
  height: 2px;
  margin: 60px 0 50px 0;
  background: rgba(255, 255, 255, 0.2);
}
.foot-lnk {
  text-align: center;
}

button {
  height: 50px;
}
</style>
