<template>
  <b-row class="mb-1">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset">
        <b-form-group id="userid-group" label="작성자:" label-for="userId">
          <b-form-input
            id="userid"
            :disabled="isUserid"
            v-model="article.userId"
            type="text"
            required
            readonly
          ></b-form-input>
        </b-form-group>

        <b-form-group id="subject-group" label="제목:" label-for="subject" description="제목을 입력하세요.">
          <b-form-input
            id="subject"
            v-model="article.subject"
            type="text"
            required
            placeholder="제목 입력..."
          ></b-form-input>
        </b-form-group>

        <b-form-group id="content-group" label="내용:" label-for="content">
          <b-form-textarea
            id="content"
            v-model="article.content"
            placeholder="내용 입력..."
            rows="10"
            max-rows="15"
          ></b-form-textarea>
        </b-form-group>

        <b-button type="submit" variant="primary" class="m-1" v-if="this.type === 'register'">글작성</b-button>
        <b-button type="submit" variant="primary" class="m-1" v-else>글수정</b-button>
        <b-button type="reset" variant="danger" class="m-1">초기화</b-button>
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
import http from "@/api/http";
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "BoardInputItem",
  data() {
    return {
      article: {
        articleNo: 0,
        userId: "",
        subject: "",
        content: "",
        userName: "",
        hit: "",
        registerTime: "",
      },
      isUserid: false,
    };
  },
  props: {
    type: { type: String },
  },
  created() {
    if (this.type === "modify") {
      console.log("test");
      http.get(`/board/view/${this.$route.params.articleNo}`).then(({ data }) => {
        this.article = data;
      });
      this.isUserid = true;
    }
    // 초기값 그냥 ssafy로 지정 -> 로그인 되면 수정 예정
    this.article.userId = this.userInfo.userId;
  },
  computed: {
    ...mapState(memberStore, ["userInfo", "isKakaoLogin"]),
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      !this.article.userId && ((msg = "작성자 입력해주세요"), (err = false), this.$refs.userid.focus());
      err && !this.article.subject && ((msg = "제목 입력해주세요"), (err = false), this.$refs.subject.focus());
      err && !this.article.content && ((msg = "내용 입력해주세요"), (err = false), this.$refs.content.focus());

      if (!err) alert(msg);
      else this.type === "register" ? this.registArticle() : this.modifyArticle();
    },
    onReset(event) {
      event.preventDefault();
      this.article.articleNo = 0;
      this.article.subject = "";
      this.article.content = "";
      this.moveList();
    },
    registArticle() {
      http.post(`/board/`, this.article).then((response) => {
        let msg = "";
        if (response.status == 200) {
          msg = "등록이 완료되었습니다.";
        } else {
          msg = "등록 처리시 문제가 발생했습니다.";
        }
        alert(msg);
        this.moveList();
      });
    },
    modifyArticle() {
      http.put(`/board/modify`, this.article).then((response) => {
        let msg = "수정 처리시 문제가 발생했습니다.";
        if (response.status == 200) {
          msg = "수정이 완료되었습니다.";
        }
        alert(msg);
        // 현재 route를 /list로 변경.
        this.moveList();
      });
    },
    moveList() {
      this.$router.push({ name: "boardlist" });
    },
  },
};
</script>

<style></style>
