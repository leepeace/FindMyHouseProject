<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-primary" @click="moveList">목록</b-button>
      </b-col>
      <b-col v-if="userInfo" class="text-right">
        <b-button v-if="this.userInfo!=null && userInfo.userId===this.article.userId" variant="outline-info" size="sm" @click="moveModifyArticle" class="mr-2"
          >글수정</b-button
        >
        <b-button v-if="this.userInfo!=null && userInfo.userId===this.article.userId" variant="outline-danger" size="sm" @click="deleteArticle">글삭제</b-button>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col>
        <b-card
          :header-html="`<h3>${article.qnaNo}.
          ${article.subject} [${article.hit}]</h3><div><h6>${article.userId}</div><div>${article.registerTime}</h6></div>`"
          class="mb-2"
          border-variant="dark"
          no-body
        >
          <b-card-body class="text-left">
            <div v-html="message"></div>
          </b-card-body>
        </b-card>
      </b-col>
    </b-row>
    <reply-detail></reply-detail>
    <reply-input-item :qnaNo="article.qnaNo" :userId="article.userId" />
  </b-container>
</template>

<script>
// import moment from "moment";
import http from "@/api/http";
import ReplyInputItem from "@/components/qna/item/ReplyInputItem";
import ReplyDetail from "@/components/qna/ReplyDetail";
import { mapState } from "vuex";

const memberStore = "memberStore";


export default {
  name: "QnaDetail",
  data() {
    return {
      article: {},
    };
  },
  components: {
    ReplyInputItem,
    ReplyDetail,
  },
  computed: {
    message() {
      if (this.article.content) return this.article.content.split("\n").join("<br>");
      return "";
    },

    ...mapState(memberStore, ["userInfo", "isKakaoLogin"]),

    //...mapState(["reply"]),
  },
  created() {
    http.get(`/qna/view/${this.$route.params.qnaNo}`).then(({ data }) => {
      this.article = data;
    });
  },
  methods: {
    moveModifyArticle() {
      this.$router.replace({
        name: "qnamodify",
        params: { qnaNo: this.article.qnaNo },
      });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
    },
    deleteArticle() {
      if (confirm("정말로 삭제?")) {
        this.$router.replace({
          name: "qnadelete",
          params: { qnaNo: this.article.qnaNo },
        });
      }
    },
    moveList() {
      this.$router.push({ name: "qnalist" });
    },
    createReply() {
      this.$router.push({ name: "qnalist" });
    },
  },
  // filters: {
  //   dateFormat(regtime) {
  //     return moment(new Date(regtime)).format("YY.MM.DD hh:mm:ss");
  //   },
  // },
};
</script>

<style></style>
