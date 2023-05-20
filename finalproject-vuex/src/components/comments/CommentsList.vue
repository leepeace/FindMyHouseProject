<template>
  <div>
    <section class="chatbox" style="width: 100%">
      <b-row v-if="items.length > 0">
      <b-col cols="2" style="font-weight: bold"> 리뷰 ({{ items.length }}) </b-col>
        <b-col style="margin-left: -40px; margin-top: -9px">
          <small>
            <b-form-rating
              id="rating-inline"
              variant="warning"
              readonly
              inline
              value=4
              v-model=items[0].avg
              no-border
            ></b-form-rating
          ></small>
        </b-col>
      </b-row>
      <section v-if="items.length > 0" class="chat-window">
        <article class="msg-container msg-remote" id="msg-0">
          <div
            class="msg-box"
            v-for="(data, i) in items"
            v-bind:key="i"
            style="margin-bottom: 10px"
          >
            <img
              v-if="i % 2 === 0"
              class="user-img"
              id="user-0"
              src="//gravatar.com/avatar/00034587632094500000000000000000?d=retro"
            />
            <img
              v-if="i % 2 === 1"
              class="user-img"
              id="user-0"
              src="//gravatar.com/avatar/56234674574535734573000000000001?d=retro"
            />
            <div class="flr">
              <div class="messages">
                <p class="msg" id="msg-0">
                  {{ data.content }}
                </p>
              </div>
              <span class="timestamp"
                ><span class="username"> {{ data.userId }}</span
                >&bull;<span class="posttime">{{ data.registerTime }}</span></span
              >
            </div>
          </div>
          <div />
        </article>
        <br />
      </section>
      <b-row class="mx-auto" v-else>
        <b-alert show variant="light">아직 등록된 리뷰가 없습니다</b-alert>
      </b-row>
      <b-button
        style="margin-top: 20px"
        variant="outline-primary"
        @click="showModal"
        v-if="this.userInfo != null"
        >리뷰남기기</b-button
      >
    </section>
    <b-modal ref="my-modal" hide-footer title="리뷰 작성하기">
      <b-form-group class="d-block text-center">
        <label for="rating-md-no-border" class="mt-3">별점을 선택하세요</label>
        <b-form-rating
          id="rating-inline"
          v-model="value"
          inline
          value="4"
          style="width: 90%"
          no-border
        ></b-form-rating>
        <b-row><br /></b-row>
        <label for="rating-md-no-border" class="mt-3">이 아파트의 리뷰를 남겨주세요</label>
        <b-form-input style="width: 100%; height: 100px" v-model="review.content"></b-form-input>
      </b-form-group>
      <b-row>
        <b-col>
          <b-button class="mt-3" variant="outline-warning" block @click="submitReview"
            >작성하기</b-button
          >
        </b-col>
        <b-col>
          <b-button class="mt-3" variant="outline-danger" block @click="hideModal">닫기</b-button>
        </b-col>
      </b-row>
    </b-modal>
  </div>
</template>

<script>
import http from "@/api/http";
import { mapState } from "vuex";
const memberStore = "memberStore";

export default {
  computed: {
    ...mapState(["house"]),
    ...mapState(memberStore, ["isLogin", "isLoginError", "userInfo"]),
  },
  data() {
    return {
      items: [],
      review: {
        score: Number,
        userId: null,
        aptCode: Number,
        content: null,
      },
      modalShow: false,
      value: 0,
    };
  },
  created() {
    http.get(`/comment/list/${this.house.aptCode}`).then(({ data }) => {
      console.log(data.commentList);
      this.items = data.commentList;
    });
  },
  methods: {
    submitReview() {
      this.review.aptCode = this.house.aptCode;
      this.review.userId = this.userInfo.userId;
      this.review.score = this.value;

      http.post(`/comment/`, this.review).then(({ data }) => {
        this.items = data.commentList;
        this.hideModal();
      });
    },
    showModal() {
      this.$refs["my-modal"].show();
    },
    hideModal() {
      this.$refs["my-modal"].hide();
    },
  },
};
</script>

<style scoped>
html,
body {
  height: 100%;
  width: 100%;
  margin: 0;
  padding: 0;
  font-family: sans-serif;
}

::-webkit-scrollbar {
  width: 4px;
}

.chatbox {
  width: 300px;
  height: 400px;
  max-height: 400px;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
.chat-window {
  flex: auto;
  max-height: calc(100% - 60px);
  background: #ffffff;
  overflow: auto;
}
.chat-input {
  flex: 0 0 auto;
  height: 60px;
  background: #ffffff;
  border-top: 1px solid #2671ff;
  box-shadow: 0 0 4px rgba(0, 0, 0, 0.14), 0 4px 8px rgba(0, 0, 0, 0.28);
}
.chat-input input {
  height: 59px;
  line-height: 60px;
  outline: 0 none;
  border: none;
  width: calc(100% - 60px);
  color: white;
  text-indent: 10px;
  font-size: 12pt;
  padding: 0;
  background: #b0b4c0;
}
.chat-input button {
  float: right;
  outline: 0 none;
  border: none;
  background: rgba(154, 191, 201, 0.25);
  height: 40px;
  width: 40px;
  border-radius: 50%;
  padding: 2px 0 0 0;
  margin: 10px;
  transition: all 0.15s ease-in-out;
}
.chat-input input[good] + button {
  box-shadow: 0 0 2px rgba(0, 0, 0, 0.12), 0 2px 4px rgba(0, 0, 0, 0.24);
  background: #2671ff;
}
.chat-input input[good] + button:hover {
  box-shadow: 0 8px 17px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
.chat-input input[good] + button path {
  fill: white;
}
.msg-container {
  position: relative;
  display: inline-block;
  width: 100%;
  margin: 0 0 10px 0;
  padding: 0;
}
.msg-box {
  display: flex;
  background: #e4e9fa;
  padding: 10px 10px 0 10px;
  border-radius: 10px 10px 10px 10px;
  max-width: 100%;
  width: auto;
  float: center;
  margin-left: 0px;
  box-shadow: 0 0 2px rgba(0, 0, 0, 0.12), 0 2px 4px rgba(0, 0, 0, 0.24);
}
.user-img {
  display: inline-block;
  border-radius: 50%;
  height: 40px;
  width: 40px;
  background: #2671ff;
  margin: 0 10px 10px 0;
}
.flr {
  flex: 1 0 auto;
  display: flex;
  flex-direction: column;
  width: calc(100% - 50px);
}
.messages {
  flex: 1 0 auto;
}
.msg {
  display: inline-block;
  font-size: 11pt;
  line-height: 13pt;
  color: rgba(8, 8, 8, 0.7);
  margin: 0 0 4px 0;
  font-weight: bold;
}
.msg:first-of-type {
  margin-top: 8px;
}
.timestamp {
  color: rgba(0, 0, 0, 0.38);
  font-size: 8pt;
  margin-bottom: 10px;
}
.username {
  margin-right: 3px;
}
.posttime {
  margin-left: 3px;
}
.msg-self .msg-box {
  border-radius: 6px 0 0 6px;
  background: #2671ff;
  float: right;
}
.msg-self .user-img {
  margin: 0 0 10px 10px;
}
.msg-self .msg {
  text-align: right;
}
.msg-self .timestamp {
  text-align: right;
}
</style>
