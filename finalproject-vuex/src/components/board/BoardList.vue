<template>
  <b-container class="bv-example-row mt-3">
    <b-row class="mb-1">
      <b-col class="text-right">
        <b-button
          v-if="this.userInfo != null && this.userInfo.userId == 'admin'"
          variant="outline-primary"
          @click="moveWrite()"
          >글쓰기</b-button
        >
      </b-col>
    </b-row>
    <b-row style="margin-top:100px; ">
      <b-col>
        <b-table striped hover :items="articles" :fields="fields" @row-clicked="viewArticle">
          <template #cell(subject)="data">
            <router-link :to="{ name: 'boardview', params: { articleNo: data.item.articleNo } }">
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

const memberStore = "memberStore";
import { mapState } from "vuex";

export default {
  name: "BoardList",
  data() {
    return {
      articles: [],
      fields: [
        { key: "articleNo", label: "글번호", tdClass: "tdClass" },
        { key: "subject", label: "제목", tdClass: "tdSubject" },
        { key: "userId", label: "작성자", tdClass: "tdClass" },
        { key: "registerTime", label: "작성일", tdClass: "tdClass" },
        { key: "hit", label: "조회수", tdClass: "tdClass" },
      ],
      pageNum: 1,
      perPage: "",
      totalData: "",
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo", "isKakaoLogin"]),
  },
  created() {
    //글 목록 불러옴
    this.showList();
  },
  methods: {
    moveWrite() {
      this.$router.push({ name: "boardwrite" });
    },
    viewArticle(article) {
      this.$router.push({
        name: "boardview",
        params: { articleNo: article.articleNo },
      });
    },
    showList() {
      http.get(`/board/list/${this.pageNum}`).then(({ data }) => {
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
