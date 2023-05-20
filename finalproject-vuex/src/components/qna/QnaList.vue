<template>
  <b-container class="bv-example-row mt-3">
    <b-row class="mb-2" style="margin-top:100px; ">
      <b-col cols="7" class="text-left">
        <b-button v-if="userInfo" variant="outline-primary" @click="moveWrite()">글쓰기</b-button>
      </b-col>
      <b-col cols="5" class="d-flex justify-content-end">
        <b-form class="form-inline">
          <b-form-select v-model="selected" :options="options">
            <!-- <option value="null">제목</option> -->
          </b-form-select>

          <b-form-input v-model="word" type="text" class="ml-1" />
          <b-button type="button" class="ml-1 btn md-warning md-sm" variant="outline-primary" @click="searchQna()"
            >검색</b-button
          >
        </b-form>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-table striped hover :items="articles" :fields="fields" @row-clicked="viewArticle" id="my-table">
          <template #cell(subject)="data">
            <router-link :to="{ name: 'qnaview', params: { qnaNo: data.item.qnaNo } }">
              {{ data.item.subject }}
            </router-link>
          </template>
        </b-table>
      </b-col>
    </b-row>
    <div class="overflow-auto">
      <b-pagination
        v-model="pageNum"
        pills
        :total-rows="totalData"
        :per-page="perPage"
        aria-controls="my-table"
        v-on:click.native="showList"
        first-number
        page
        align="center"
      ></b-pagination>
    </div>
  </b-container>
</template>

<script>
import http from "@/api/http";
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "QnaList",
  data() {
    return {
      articles: [],
      fields: [
        { key: "qnaNo", label: "no", tdClass: "tdClass" },
        { key: "subject", label: "제목", tdClass: "tdSubject" },
        { key: "userId", label: "작성자", tdClass: "tdClass" },
        { key: "registerTime", label: "작성일", tdClass: "tdClass" },
        { key: "hit", label: "조회수", tdClass: "tdClass" },
      ],
      selected: null,
      word: "",
      options: [
        { value: null, text: "키워드 선택" },
        {
          text: "제목, 내용",
          value: "subject",
        },
        {
          text: "작성자",
          value: "userId",
        },
      ],
      pageNum: 1,
      perPage: "",
      totalData: "",
    };
  },
  created() {
    this.showList();
  },
  computed: {
    ...mapState(memberStore, ["userInfo", "isKakaoLogin"]),

    //...mapState(["reply"]),
  },

  methods: {
    moveWrite() {
      this.$router.push({ name: "qnawrite" });
    },
    viewArticle(article) {
      this.$router.push({
        name: "qnaview",
        params: { qnaNo: article.qnaNo },
      });
    },
    searchQna() {
      // 검색method
      // 검색 클릭 시 -> keyword와 key는 state에 저장한다.
      this.$store.commit("SET_KEYWORD", this.selected);
      this.$store.commit("SET_WORD", this.word);
      http
        .get(`/qna/search`, {
          params: {
            key: this.selected,
            word: this.word,
          },
        })
        .then(({ data }) => {
          this.articles = data.boardList;
        });
    },

    pageClick(button, page) {
      console.log(page);
      this.pageNum = page;
      this.showList;
    },

    showList() {
      //글 목록 불러옴
      http.get(`/qna/list/${this.pageNum}`).then(({ data }) => {
        console.log(data);
        this.totalData = data.pageDto.total;
        this.perPage = data.pageDto.amount;
        this.articles = data.boardList;
      });
    },
  },
};
</script>

<style scope>
.tdClass {
  width: 50px;
  text-align: center;
}
.tdSubject {
  width: 300px;
  text-align: left;
}
</style>
