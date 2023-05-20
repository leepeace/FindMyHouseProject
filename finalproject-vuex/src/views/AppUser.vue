<template>
<div>
  <inner-header></inner-header>
  <b-container class="bv-example-row mt-3 text-center" style="width:100%; height:110%">
    <router-view></router-view>
  </b-container>
    <footer-bar></footer-bar>
</div>
</template>

<script>
import innerHeader from "@/components/innerHeader";
import FooterBar from "@/components/FooterBar";
export default {
  name: "AppUser",
    components:{
    innerHeader,
    FooterBar
  },
};
</script>

<style scoped>
.underline-steelblue {
  display: inline-block;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0) 70%, rgba(72, 190, 233, 0.3) 30%);
}
</style>
