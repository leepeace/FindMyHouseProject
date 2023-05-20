<template>
  <b-container class="bv-example-row mt-3 text-center">
    <!-- <the-header-navbar></the-header-navbar> -->
    <b-row>
      <b-col>
        <b-img alt="Happy House" :src="require('@/assets/happyhouse.png')" />
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <house-search-bar></house-search-bar>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="6" align="left">
        <house-list />
      </b-col>
      <b-col cols="6">
        <house-detail />
      </b-col>
    </b-row>
    <footer-bar></footer-bar> 
  </b-container>
</template>
<script>
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
import HouseList from "@/components/house/HouseList.vue";
import HouseDetail from "@/components/house/HouseDetail.vue";
import FooterBar from "@/components/FooterBar";
// import TheHeaderNavbar from "@/components/TheHeaderNavbar";

export default {
  name: "AppHouse",
  components: {
    // TheHeaderNavbar,
    HouseSearchBar,
    HouseList,
    HouseDetail,
    FooterBar
  },
};
</script>
<style scoped>
.underline-orange {
  display: inline-block;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0) 70%, rgba(231, 149, 27, 0.3) 30%);
}
</style>
