<template>
  <b-container class="bv-example-row">
    <b-container class="bv-example-row mt-3">
      <b-row class="mt-4 mb-4 text-center">
        <b-col></b-col>
        <b-col v-if="favoriteDong.length>0" cols="9">
          <favorite-dong-list-item
            v-for="(favoritedong, index) in favoriteDong"
            :key="index"
            :favoritedong="favoritedong"
          />
        </b-col>
        <b-container v-else class="bv-example-row mt-3">
          <b-row>
             <b-col><b-alert show>관심 목록이 없습니다.</b-alert></b-col>
           </b-row>
        </b-container>
        <b-col></b-col>
      </b-row>
    </b-container>
  </b-container>
</template>

<script>
import { mapState } from "vuex";
import FavoriteDongListItem from "@/components/favorite/items/FavoriteDongListItem";

const memberStore = "memberStore";

export default {
  name: "FavoriteDongList",
  components: {
    FavoriteDongListItem,
  },
  computed: {
    ...mapState(memberStore, ["userInfo", "isKakaoLogin"]),
    ...mapState(["favoriteDong"])
  },
  created() {
    // 값 가져오기

    //this.clearHouses();
    console.log(this.userInfo.userId);
    const params = {
      userId: this.userInfo.userId,
    };

    // 관심지역 가져오기
    this.$store.dispatch("getMyFavoriteDong",{params});
  },
};
</script>

<style></style>
