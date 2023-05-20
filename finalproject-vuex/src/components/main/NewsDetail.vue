<template>
<div>
    <inner-header></inner-header>
  <b-container class="bv-example-row mt-3" style="width:100%; height:110%">
   
    <!-- <h4 style="font-weight:bold">부동산 뉴스</h4> -->
            <div class="intro my-5">
                <h2 class="text-center" style="font-weight:bold">부동산 뉴스</h2>
            </div>
  <b-card-group>
    <b-card  img-src="https://www.enewstoday.co.kr/news/photo/202205/1570953_626685_18.jpg" img-alt="Image" img-top>
      <a :href="this.news[0].link"><b-card-title v-html="this.news[0].title"></b-card-title></a>
      <b-card-text v-html="this.news[0].description">
      </b-card-text>

    </b-card>
    <b-card img-src="https://img.etoday.co.kr/pto_db/2020/02/600/20200205110409_1420728_654_462.jpg" img-alt="Image" img-top>
      <a :href="this.news[1].link"><b-card-title v-html="this.news[1].title"></b-card-title></a>
      <b-card-text v-html="this.news[1].description">
      </b-card-text>

    </b-card>
    <b-card img-src="https://newsimg-hams.hankookilbo.com/2021/12/02/5e3e85bd-dae2-4b0d-a2e7-f603de20131a.jpg" img-alt="Image" img-top>
      <a :href="this.news[2].link"><b-card-title v-html="this.news[2].title"></b-card-title></a>
      <b-card-text v-html="this.news[2].description">
      </b-card-text>

    </b-card>
  </b-card-group>
  </b-container>
  <footer-bar></footer-bar>
  </div>
</template>

<script>
import http from "@/api/http";
import innerHeader from "@/components/innerHeader";
import FooterBar from "@/components/FooterBar";

export default {
  name: "MainNews",
  data() {
    return {
      news: [],
    };
  },
  components:{
    innerHeader,
    FooterBar
  },
  created() {
    http
      .get("/info/news")
      .then(({ data }) => {
        this.news = data.items;
      })
      .catch((error) => {
        console.log(error);
      });
    // houses() {
    //   return this.$store.state.houses;
    // },
  },
  // computed: {
  //   ...mapState(["news"]),
  // },
  // methods: {
  //   ...mapActions(["getNews"]),
  // },
};
</script>

<style>
ul > li {
  list-style: none;
}
.card-img-top{
    height: 15rem;
    object-fit: cover;
}
</style>
