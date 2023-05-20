<template>
  <div>
    <the-header-navbar></the-header-navbar>
    <b-row>
      <main-map></main-map>
    </b-row>
    <footer-bar></footer-bar> 
  </div>
</template>

<script
  type="text/javascript"
  src="//dapi.kakao.com/v2/maps/sdk.js?appkey=f4469e3141710684a6e12b008a8d58b3&libraries=services"
></script>

<script>
import TheHeaderNavbar from "@/components/TheHeaderNavbar";
import MainMenu from "@/components/main/MainMenu.vue";
import MainNews from "@/components/main/MainNews.vue";
import MainMap from "@/components/main/MainMap.vue";
import FooterBar from "@/components/FooterBar";
// import MainMarker from "@/components/main/MainMarker.vue";

export default {
  name: "AppMain",

  components: {
    TheHeaderNavbar,
    FooterBar,
    MainMenu,
    MainNews,
    MainMap,
    // MainMarker,
  },
};
</script>

<style scoped>
.underline-steelblue {
  display: inline-block;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0) 70%, rgba(72, 190, 233, 0.3) 30%);
}
</style>
