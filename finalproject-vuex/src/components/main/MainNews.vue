<template>
  <b-container class="bv-example-row mt-3">
    <inner-header></inner-header>
    <h4 style="font-weight: bold">부동산 뉴스</h4>
    <main-news-item v-for="(newsItem, index) in news" :key="index" :newsItem="newsItem" />
  </b-container>
</template>

<script>
import http from "@/api/http";
import MainNewsItem from "@/components/main/item/MainNewsItem";
import innerHeader from "@/components/TheHeaderNavbar";

export default {
  name: "MainNews",
  data() {
    return {
      news: [],
    };
  },
  components: {
    MainNewsItem,
    innerHeader
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
</style>
