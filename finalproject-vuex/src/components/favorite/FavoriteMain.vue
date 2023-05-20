<template>
  <div>
    <inner-header></inner-header>
    <b-card no-body style="width:100%; height:110%">
      <b-tabs card>
        <b-tab no-body title="나의 관심단지">
          <b-row style="height: 500px">
            <favorite-apt-list />
          </b-row>
        </b-tab>

        <b-tab no-body title="나의 관심지역" @click="clear">
          <b-row><br /></b-row>
          <b-row>
            <b-col cols="6" align="center">
              <h3 style="font-weight:bold">내 관심지역</h3>
              <b-alert show variant="light" style="font-weight:bold">관심지역을 선택해주세요 !</b-alert>
              <b-row>
                <favorite-dong-list></favorite-dong-list>
              </b-row>
            </b-col>
            <b-col cols="6" align="center">
              <!-- <h3>&nbsp;</h3> -->
              <b-row>
                <favorite-dong-detail></favorite-dong-detail>
              </b-row>
            </b-col>
          </b-row>
        </b-tab>
      </b-tabs>
    </b-card>
      <footer-bar></footer-bar>
  </div>
</template>

<script>
import FavoriteAptList from "@/components/favorite/FavoriteAptList.vue";
import FavoriteDongList from "@/components/favorite/FavoriteDongList.vue";
import FavoriteDongDetail from "@/components/favorite/FavoriteDongDetail.vue";
import innerHeader from "@/components/innerHeader";
import FooterBar from "@/components/FooterBar";

const memberStore = "memberStore";
import { mapState } from "vuex";

export default {
  name: "FavoriteMain",
  components: {
    FavoriteAptList,
    FavoriteDongList,
    FavoriteDongDetail,
        innerHeader,
    FooterBar
  },
  computed: {
    ...mapState(memberStore, ["userInfo", "isKakaoLogin"]),
    ...mapState(["favoriteDong"])
  },
  methods: {
    clear() {
      this.$store.commit("CLEAR_DONG_HOUSES");
    },
  },
};
</script>

<style></style>
