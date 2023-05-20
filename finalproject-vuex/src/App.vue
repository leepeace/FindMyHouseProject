<template>
  <div id="app">
    <!-- <the-header-navbar></the-header-navbar> -->
    <router-view style="height: 1000px"></router-view>
    <!-- <footer-bar></footer-bar> -->
  </div>
</template>

<script>
// import TheHeaderNavbar from "@/components/TheHeaderNavbar";
// import FooterBar from "@/components/FooterBar";

export default {
  name: "App",
  components: {
    // TheHeaderNavbar,
    // FooterBar,
  },
  mounted() {
    let scripts = document.createElement("script");
    scripts.setAttribute("src", "https://developers.kakao.com/sdk/js/kakao.js");

    document.head.appendChild(scripts);
  },


};
</script>

<style>
/* @import url("https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@400;700;800&display=swap"); */
@import url("https://hangeul.pstatic.net/hangeul_static/css/nanum-square-neo.css");

#app {
  /* font-family: Avenir, Helvetica, Arial, sans-serif; */
  /* font-family: "Nanum Gothic", sans-serif; */
  font-family: "NanumSquareNeo";
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background-color: #ffffff;
}

a.router-link-exact-active {
  color: #89bfef;
}

a.router-link-exact-active:hover {
  text-decoration: none;
}
.affix {
  padding: 0;
  background-color: #111;
}
</style>
