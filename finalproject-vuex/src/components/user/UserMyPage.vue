<template>
  <section style="background-color: #eee">
    <div class="container py-5" style="margin-top: 60px">
      <div class="row">
        <div class="col">
          <nav aria-label="breadcrumb" class="bg-light rounded-3 p-3 mb-4">
            <ol class="breadcrumb mb-0">
              <li class="breadcrumb-item"><router-link to="/">Home</router-link></li>
              <li class="breadcrumb-item active" aria-current="page">User Profile</li>
            </ol>
          </nav>
        </div>
      </div>

      <div class="row">
        <div class="col-lg-4">
          <div class="card mb-4">
            <div class="card-body text-center">
              <img
                src="https://img.lovepik.com/free-png/20211106/lovepik-simple-man-icon-png-image_400345200_wh1200.png"
                alt="avatar"
                class="rounded-circle img-fluid"
                style="width: 150px"
              />
              <h5 class="my-3">{{ this.userInfo.userId }}</h5>
              <p class="text-muted mb-1" v-show="isKakaoLogin">소셜 회원</p>
              <p class="text-muted mb-1" v-show="!isKakaoLogin && userInfo.userId != 'admin'">일반 회원</p>
              <p class="text-muted mb-1" v-show="userInfo.userId == 'admin'">관리자</p>
              <div class="d-flex justify-content-center mb-2" style="padding: 10px">
                <b-button pill variant="info" style="margin-right: 10px">
                  <router-link to="/user/modify" style="color: white">회원 수정</router-link>
                </b-button>

                <b-button pill variant="info" @click="showModal">회원 탈퇴</b-button>

                <b-button pill v-if="this.isKakaoLogin" @click="unlink" variant="success">카카오 탈퇴</b-button>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-8">
          <div class="card mb-4">
            <div class="card-body" style="height: 340px; padding-top: 50px">
              <div class="row">
                <div class="col-sm-3">
                  <p class="mb-0" style="font-weight: bold">Full Name</p>
                </div>
                <div class="col-sm-9">
                  <p class="text-muted mb-0">{{ this.userInfo.userName }}</p>
                </div>
              </div>
              <hr />
              <div class="row">
                <div class="col-sm-3">
                  <p class="mb-0" style="font-weight: bold">Email</p>
                </div>
                <div class="col-sm-9">
                  <p class="text-muted mb-0">{{ this.userInfo.emailId }}@{{ this.userInfo.emailDomain }}</p>
                </div>
              </div>
              <hr />
              <div class="row">
                <div class="col-sm-3">
                  <p class="mb-0" style="font-weight: bold">ID</p>
                </div>
                <div class="col-sm-9">
                  <p class="text-muted mb-0">{{ this.userInfo.userId }}</p>
                </div>
              </div>
              <hr />
              <div class="row">
                <div class="col-sm-3">
                  <p class="mb-0" style="font-weight: bold">Age</p>
                </div>
                <div class="col-sm-9">
                  <p class="text-muted mb-0">{{ this.userInfo.age }} 대</p>
                </div>
              </div>
              <hr />
            </div>
          </div>
          <div class="row">
            <div class="col-md-12">
              <div class="card mb-7 mb-md-0">
                <div class="card-body">
                  <p class="mb-4" style="font-weight: bold">내가 남긴 리뷰</p>

                  <b-list-group class="mb-4">
                    <b-list-group-item
                      href="#"
                      class="d-flex justify-content-between align-items-center"
                      v-for="(data, i) in review"
                      v-bind:key="i"
                    >
                      <div class="d-flex w-100 justify-content-between">
                        <small>{{ data.registerTime }}</small>
                      </div>
                      <div class="d-flex w-100 justify-content-between">
                        <small>( {{ data.aptName }} 아파트 )</small>
                      </div>
                      <div class="d-flex w-100 justify-content-between">
                        <small> {{ data.content }}</small>
                      </div>

                      <small>
                        <b-form-rating
                          id="rating-inline"
                          variant="warning"
                          v-model="data.score"
                          readonly
                          inline
                          value="4"
                        ></b-form-rating
                      ></small>
                    </b-list-group-item>
                  </b-list-group>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <b-modal ref="my-modal" hide-footer title="회원 탈퇴">
      <div class="d-block text-center">
        <h7>회원 탈퇴를 진행하시겠습니까?</h7>
      </div>
      <b-button class="mt-3" variant="outline-danger" block @click="hideModal">Cancel</b-button>
      <b-button class="mt-2" variant="outline-warning" block @click="withdraw">OK</b-button>
    </b-modal>
  </section>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import axios from "@/api/http";
const memberStore = "memberStore";

export default {
  name: "UserMyPage",

  data() {
    return {
      form: {
        userid: "",
        username: "",
        emailId: "",
        emailDomain: "",
        age: "",
      },
      review: {
        score: Number,
        userId: null,
        aptCode: Number,
        content: null,
        registerTime: null,
        aptName: null,
      },
    };
  },

  computed: {
    ...mapState(memberStore, ["userInfo", "isKakaoLogin"]),
    loadItem() {
      return this.review ?? false;
    },
  },

  created() {
    axios.get(`/comment/${this.userInfo.userId}`).then((response) => {
      this.review = response.data;
    });
  },

  methods: {
    ...mapActions(memberStore, ["getUserInfo"]),
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO", "SET_IS_VALID_TOKEN", "SET_IS_KAKAO_LOGIN"]),
    async confirm() {
      this.getUserInfo(sessionStorage.getItem("access-token"));
      this.movePage();
    },
    movePage() {
      this.$router.push({ name: "main" });
    },
    showModal() {
      this.$refs["my-modal"].show();
    },
    hideModal() {
      this.$refs["my-modal"].hide();
    },
    withdraw() {
      //회원 탈퇴 진행
      axios.delete(`/user/${this.userInfo.userId}`).then((response) => {
        if (response.status == 200) {
          localStorage.clear();
          sessionStorage.clear();
          this.SET_USER_INFO(null);
          this.SET_IS_LOGIN(false);
          this.SET_IS_VALID_TOKEN(false);
          this.SET_IS_KAKAO_LOGIN(false);
          alert("회원 탈퇴 성공");
          this.$refs["my-modal"].toggle("#toggle-btn");
          this.movePage();
        } else {
          alert("회원 탈퇴 실패");
          this.$refs["my-modal"].toggle("#toggle-btn");
        }
      });
    },
    unlink() {
      // 카카오 계정 연결끊기
      window.Kakao.init(process.env.VUE_APP_KAKAO_LOGIN_API_KEY);
      console.log("초기화 " + window.Kakao.isInitialized());
      console.log(window.Kakao.Auth.getAccessToken());
      let kakaoDelete = this.kakaoDelete;
      window.Kakao.API.request({
        url: "/v1/user/unlink",
      })
        .then(function (response) {
          console.log("성공");
          console.log(response);
          kakaoDelete("unlink");
        })
        .catch(function (error) {
          alert(error);
        });
    },
    kakaoDelete(type) {
      if (type) {
        axios.delete(`/user/${this.userInfo.userId}`).then((response) => {
          if (response.status == 200) {
            localStorage.clear();
            sessionStorage.clear();
            this.SET_USER_INFO(null);
            this.SET_IS_LOGIN(false);
            this.SET_IS_VALID_TOKEN(false);
            this.SET_IS_KAKAO_LOGIN(false);
            alert("회원 탈퇴 성공");
            this.movePage();
          } else {
            alert("회원 탈퇴 실패");
          }
        });
      } else {
        alert("logout account");
      }
    },
  },
};
</script>

<style></style>
