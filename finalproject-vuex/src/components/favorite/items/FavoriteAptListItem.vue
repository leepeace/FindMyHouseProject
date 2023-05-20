<template>
  <!-- <b-container class="bv-example-row">
    <b-row class="mt-2">
      <b-col cols="12" class="bg-light p-4 pl-6 text-left">
        <span
          >{{ housedeal.aptName }} 거래금액 : {{ housedeal.dealAmount }} / 면적 :
          {{ housedeal.area }}㎡ / 층수 : {{ housedeal.floor }} / 거래년월 : {{ housedeal.dealYear
          }}{{ housedeal.dealMonth }}</span
        >
      </b-col>
    </b-row>
  </b-container> -->

  <b-tr>
    <b-td
      ><b-link @click="showModal">{{ bookmark.aptName }}</b-link></b-td
    >
    <b-th>
      {{ bookmark.sidoName }} {{ bookmark.gugunName }} {{ bookmark.dongName }} {{ bookmark.jibun }}
    </b-th>
    <b-td>{{ bookmark.bookmark }}</b-td>
    <b-td
      ><b-img
        :src="require('@/assets/img/deleteImg.png')"
        style="width: 20px; height: 20px"
        @click="deleteBookmark"
      ></b-img
    ></b-td>
  </b-tr>
</template>

<script>
import { mapState } from "vuex";
const memberStore = "memberStore";

export default {
  name: "FavoriteAptListItem",
  props: {
    bookmark: Object,
  },
  computed: {
    ...mapState(memberStore, ["userInfo", "isKakaoLogin"]),
  },
  methods: {
    showModal() {
      this.$store.commit("SET_DETAIL_HOUSE", this.bookmark);
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
