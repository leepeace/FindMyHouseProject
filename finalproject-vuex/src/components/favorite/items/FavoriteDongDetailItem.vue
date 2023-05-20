<template>
  <b-tr>
    <b-td
      ><b-link @click="showModal">{{ house.aptName }}</b-link></b-td
    >
    <b-th> {{ house.sidoName }} {{ house.gugunName }} {{ house.dongName }} {{ house.jibun }} </b-th>
    <b-td>{{ house.bookmark }}</b-td>
  </b-tr>
</template>

<script>
import { mapState } from "vuex";
const memberStore = "memberStore";

export default {
  name: "FavoriteDongDetailItem",
  props: {
    house: Object,
  },
  computed: {
    ...mapState(memberStore, ["userInfo", "isKakaoLogin"]),
  },
  methods: {
    showModal() {
      this.$store.commit("SET_DETAIL_HOUSE", this.house);
      //console.log(this.$store.state.house);
      this.$bvModal.show("bv-modal-example");
    },
    deleteBookmark() {
      const delConfirm = confirm("북마크를 삭제하시겠습니까?");
      if (delConfirm) {
        const params = {
          user_id: this.userInfo.userId,
          aptCode: this.bookmark.aptCode,
        };

        this.$store.dispatch("deletebookmarks", { params });
      } else {
        alert("삭제가 취소되었습니다.");
      }
    },
  },
};
</script>

<style></style>
