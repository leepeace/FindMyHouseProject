<template>
  <b-container class="list bv-example-row">
    <b-row>
      <b-col cols="11">
        <h3 v-if="!this.isbookmark">
          <font-awesome-icon icon="fa-regular fa-heart" @click="insertBookmark" />&nbsp;{{
            house.aptName
          }}
        </h3>
        <h3 v-if="this.isbookmark">
          <font-awesome-icon icon="fa-solid fa-heart" @click="deleteBookmark" />&nbsp;{{
            house.aptName
          }}
        </h3>
      </b-col>
      <b-col cols="1"><font-awesome-icon icon="fa-solid fa-xmark" @click="deleteDetail" /></b-col>
    </b-row>
    <hr />
    <!-- <b-row class="mb-2 mt-1">
        <b-col><b-img :src="require('@/assets/apt.png')" fluid-grow></b-img></b-col>
      </b-row> -->
    <b-row>
      <!-- <b-col>
          <b-alert show variant="secondary">일련번호 : {{ house.일련번호 }}</b-alert>
        </b-col> -->
    </b-row>
    <b-row>
      <b-col>
        <b-alert show variant="primary">아파트 이름 : {{ house.aptName }} </b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-alert show variant="info">법정동 : {{ house.dongName }} </b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-alert show variant="warning">준공년도 : {{ house.buildYear }} 년</b-alert>
      </b-col>
      <b-col>
        <b-alert show variant="warning">지번 : {{ house.jibun }}</b-alert>
      </b-col>
    </b-row>
    <br />

    <b-card no-body>
      <b-tabs card>
        <b-tab title="거래가">
          <b-card-text><house-deal-list></house-deal-list></b-card-text>
        </b-tab>
        <b-tab title="리뷰">
          <b-card-text>
            <comments-list></comments-list>
          </b-card-text>
        </b-tab>
      </b-tabs>
    </b-card>
    <div></div>
  </b-container>
</template>

<script>
import http from "@/api/http";
import { mapState } from "vuex";
import HouseDealList from "@/components/house/HouseDealList.vue";
import CommentsList from "@/components/comments/CommentsList.vue";

const memberStore = "memberStore";

export default {
  name: "HouseDetail",

  data() {
    return {
      isbookmark: false,
    };
  },
  computed: {
    ...mapState(["house", "housedeals"]),
    ...mapState(memberStore, ["userInfo"]),
    // ...mapActions(["SET_DETAIL_HOUSE"]),
    // house() {
    //   return this.$store.state.house;
    // },
  },
  created() {
    this.checkBookmark();
  },
  components: {
    HouseDealList,
    CommentsList,
  },
  filters: {
    price(value) {
      if (!value) return value;
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
  methods: {
    deleteDetail() {
      // 다시 리스트 출력하기
      // this.SET_DETAIL_HOUSE(null);
      this.$store.commit("SET_DETAIL_HOUSE", null);
    },
    insertBookmark() {
      // insert 성공 시 -> flag값 true로 변경
      // console.log(this.house.aptCode);
      // console.log(this.$store.state.userId);

      http
        .post(`/info/bookmark`, {
          aptCode: this.house.aptCode,
          user_id: this.userInfo.userId,
        })
        .then(({ data }) => {
          if (data == 1) {
            // 등록 성공시
            this.isbookmark = true;
          } else {
            // 등록 실패시
            this.isbookmark = false;
          }
        });
    },
    deleteBookmark() {
      // delete 시 -> flag값 false로 변경
      //   const params = {
      //     aptCode: this.house.aptCode,
      //     user_id: this.$store.state.userId
      // };

      http
        .delete(`/info/bookmark/${this.userInfo.userId}/${this.house.aptCode}`)
        .then(({ data }) => {
          if (data == 1) {
            // 삭제 성공시
            this.isbookmark = false;
          } else {
            // 삭제 실패시
            this.isbookmark = true;
          }
        });
    },
    checkBookmark() {
      // 북마크여부 체크하기
      const params = {
        aptCode: this.house.aptCode,
        user_id: this.userInfo.userId,
      };

      http
        .get(`/info/isbookmark`, {
          params,
        })
        .then(({ data }) => {
          if (data == 1) {
            this.isbookmark = true;
          } else {
            this.isbookmark = false;
          }
        });
    },
  },
};
</script>

<style>
.list {
  overflow: scroll;
  height: 700px;
}
</style>
