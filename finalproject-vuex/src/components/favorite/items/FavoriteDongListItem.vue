<template>
  <b-row>
    <b-col>
      <b-alert show variant="info">
        <b-link @click="favoritedongDetail">
          {{ favoritedong.sidoName }} {{ favoritedong.gugunName }} {{ favoritedong.dongName }}
        </b-link>
      </b-alert>
    </b-col>
    <b-img
        :src="require('@/assets/img/deleteImg.png')"
        style="width: 20px; height: 20px; margin-top:15px"
        @click="deletefavoriteDong"
      ></b-img>
  </b-row>
</template>

<script>
import { mapMutations, mapState } from "vuex";
const memberStore = "memberStore";

export default {
  name: "FavoriteDongListItem",
  props: {
    favoritedong: Object,
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    ...mapState(["favoriteDong"])
  },
  methods: {
    ...mapMutations(['CLEAR_DONG_HOUSES']),

    favoritedongDetail() {
      console.log(this.userInfo);
      console.log(this.userInfo.age);
      const params = {
        dongCode : this.favoritedong.dongCode,
        age : this.userInfo.age
      }

      this.$store.dispatch("getDongDetailHouse", {params});
    },
    deletefavoriteDong(){ 
      
      const delConfirm = confirm("관심지역을 삭제하시겠습니까?");
      if(delConfirm){
        const params = {
          dongCode : this.favoritedong.dongCode,
          user_id : this.userInfo.userId
        }
        this.$store.dispatch("deletefavoriteDong",{params});
        this.CLEAR_DONG_HOUSES();

      }else{
        alert("삭제가 취소되었습니다.");
        return;
      }

    }
  },
};
</script>

<style scoped></style>
