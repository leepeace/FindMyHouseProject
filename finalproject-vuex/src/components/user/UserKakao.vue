<template>
  <kakaoLogin
    api-key="f4469e3141710684a6e12b008a8d58b3"
    image="kakao_login_btn_medium"
    :on-success="onSuccess"
    :on-failure="onFailure"
  />
</template>

<script>
import kakaoLogin from "vue-kakao-login";
import axios from "@/api/http";
import { mapState, mapMutations } from "vuex";
const memberStore = "memberStore";

let onFailure = (data) => {
  console.log(data);
  alert("failure");
};
export default {
  components: {
    kakaoLogin,
  },

  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },

  methods: {
    ...mapMutations(memberStore, [
      "SET_IS_LOGIN",
      "SET_IS_LOGIN_ERROR",
      "SET_IS_VALID_TOKEN",
      "SET_USER_INFO",
      "SET_IS_KAKAO_LOGIN",
    ]),
    onSuccess(data) {
      axios.post(`/user/kakao`, data).then((response) => {
        if (response.status == 200) {
          alert("success");

          //console.log(response.data.userInfo);
          //this.setUserInfo(response.data.userInfo);
          this.SET_IS_LOGIN(true);
          this.SET_IS_KAKAO_LOGIN(true);
          this.SET_IS_LOGIN_ERROR(false);
          this.SET_IS_VALID_TOKEN(true);
          this.SET_USER_INFO(response.data.userInfo);

          this.movePage();
        }
      });
    },
    onFailure,
    movePage() {
      this.$router.push({ name: "main" });
    },
  },
};
</script>

<style></style>
