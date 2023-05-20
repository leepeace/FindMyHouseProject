<template>
  <section style="background-color: #eee">
    <div class="container py-5">
      <div class="row">
        <div class="col">
          <nav aria-label="breadcrumb" class="bg-light rounded-3 p-3 mb-4">
            <ol class="breadcrumb mb-0">
              <li class="breadcrumb-item"><router-link to="/">Home</router-link></li>
              <li class="breadcrumb-item active" aria-current="page">
                <router-link to="/user/mypage">User Profile</router-link>
              </li>
            </ol>
          </nav>
        </div>
      </div>

      <div class="row">
        <div class="col-lg-4">
          <div class="card mb-4">
            <div class="card-body text-center">
              <img
                src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3.webp"
                alt="avatar"
                class="rounded-circle img-fluid"
                style="width: 150px"
              />
              <p class="my-3">Upload a different photo</p>
              <div class="d-flex justify-content-center mb-2">
                <input type="file" />
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-8">
          <div class="card mb-4">
            <b-form @submit.prevent="onSubmit" class="card-body">
              <div class="row">
                <div class="col-sm-3">
                  <p class="mb-0">Full Name</p>
                </div>
                <div class="col-sm-9">
                  <b-form-input v-model="users.userName"></b-form-input>
                </div>
              </div>
              <hr />
              <div class="row">
                <div class="col-sm-3">
                  <p class="mb-0">Email</p>
                </div>
                <div class="col-sm-9">
                  <b-input-group class="mb-2">
                    <b-form-input v-model="users.emailId"></b-form-input>
                    <b-input-group-prepend is-text><b>@</b></b-input-group-prepend>
                    <b-form-input v-model="users.emailDomain"></b-form-input>
                  </b-input-group>
                </div>
              </div>
              <hr />
              <div class="row">
                <div class="col-sm-3">
                  <p class="mb-0">ID</p>
                </div>
                <div class="col-sm-9">
                  <b-form-input v-model="users.userId" readonly></b-form-input>
                </div>
              </div>
              <hr />
              <div class="row">
                <div class="col-sm-3">
                  <p class="mb-0">Password</p>
                </div>
                <div class="col-sm-9">
                  <b-form-input v-model="users.userPwd" type="password"></b-form-input>
                </div>
              </div>
              <hr />
              <div class="d-flex justify-content-center mb-2">
                <b-button type="submit" variant="outline-primary">Button</b-button>
              </div>
            </b-form>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import axios from "@/api/http";
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "UserModify",

  data() {
    return {
      users: {
        userId: "",
        userName: "",
        emailDomain: "",
        emailId: "",
        userPwd: "",
      },
    };
  },

  created() {
    const id = this.userInfo.userId;
    axios.get(`/user/mypage/${id}`).then(({ data }) => {
      this.users = data;
    });
  },

  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },

  methods: {
    movePage() {
      this.$router.push({ name: "mypage" });
    },

    onSubmit() {
      console.log("this is update methods");
      axios.put(`/user/modify`, this.users).then((response) => {
        if (response.status == 200) {
          alert("회원 수정 완료");
          this.movePage();
        } else {
          alert("회원 수정 실패");
        }
      });
    },
  },
};
</script>

<style></style>
