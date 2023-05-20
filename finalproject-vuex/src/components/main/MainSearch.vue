<template>
  <b-row class="text-center">
    <b-col></b-col>
    <b-button
      v-if="userInfo && selected == 'region' && !favoriteFlag && sidoCode && gugunCode && dongCode"
      pill
      variant="warning"
      @click="registLikeDong"
      >관심지역 설정</b-button
    >
    <b-button
      v-if="userInfo && selected == 'region' && favoriteFlag && sidoCode && gugunCode && dongCode"
      pill
      variant="success"
      >나의 관심지역</b-button
    >
    <b-col cols="2">
      <b-form-select v-model="selected" :options="options" @change="clearList">
        <!-- <option value="null">제목</option> -->
      </b-form-select>
    </b-col>
    <b-col v-if="selected == 'region'" class="sm-5">
      <b-form-select v-model="sidoCode" :options="sidos" @change="gugunList"></b-form-select>
    </b-col>
    <b-col v-if="selected == 'region'" class="sm-5">
      <b-form-select v-model="gugunCode" :options="guguns" @change="dongList"></b-form-select>
    </b-col>
    <b-col v-if="selected == 'region'" class="sm-5">
      <b-form-select v-model="dongCode" :options="dongs" @change="aptList"></b-form-select>
    </b-col>
    <!-- <b-button v-if="(selected == 'region') && !favoriteFlag && sidoCode && gugunCode && dongCode" pill variant="warning" @click="registLikeDong">관심지역 설정</b-button>
    <b-button v-if="(selected == 'region') && favoriteFlag && sidoCode && gugunCode && dongCode" pill variant="success">나의 관심지역</b-button> -->
    <b-col v-if="selected == 'apt'">
      <b-form-input
        list="input-list"
        id="input-with-list"
        placeholder="원하는 아파트명을 입력해주세요."
        v-model="aptName"
        @keypress.enter="searchAptName"
      ></b-form-input>
      <!-- <b-form-datalist id="input-list" :options="options"></b-form-datalist> -->
    </b-col>
    <!-- <button class="btn btn-primary btn-circle" v-if="selected == 'apt'" @click="searchAptName">
      <i class="search-icon"></i>
    </button> -->
    <!-- <b-button variant="info" v-if="selected == 'apt'" @click="searchAptName">검색</b-button> -->
    <b-img
      v-if="selected == 'apt'"
      @click="searchAptName"
      :src="require('@/assets/img/search.png')"
      style="width: 40px; height: 40px"
    ></b-img>
    <!-- <b-col class="sm-5">
      <b-form-select v-model="dongCode" :options="dongs" @change="aptList"></b-form-select>
    </b-col> -->
    <b-col></b-col>
    <!-- <b-button type="submit" variant="primary" class="m-1" v-if="this.type === 'register'"
      >글작성</b-button
    > -->
  </b-row>
</template>

<script>
import http from "@/api/http";
import { mapState, mapActions, mapMutations } from "vuex";

const memberStore = "memberStore";

export default {
  name: "MainSearch",
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
      aptName: null,
      selected: "region",
      options: [
        {
          text: "지역별검색",
          value: "region",
        },
        {
          text: "아파트별검색",
          value: "apt",
        },
      ],
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo", "isKakaoLogin"]),
    ...mapState(["sidos", "guguns", "dongs", "houses", "favoriteFlag", "isHouseSearch"]),
  },
  created() {
    // this.$store.dispatch("getSido");
    // this.sidoList();

    // 검색어 설정 - 초기값은 false,
    //this.SET_HOUSE_SEARCH(false);

    this.CLEAR_SIDO_LIST();
    this.CLEAR_GUGUN_LIST();
    this.CLEAR_DONG_LIST();
    this.CLEAR_APT_LIST();
    this.getSido();
  },
  methods: {
    ...mapActions(["getSido", "getGugun", "getHouseList", "getDong", "searchApt", "checkfavorite"]),
    ...mapMutations([
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_APT_LIST",
      "CLEAR_DONG_LIST",
      "SET_CHECK_FAVORITE",
      "SET_HOUSE_SEARCH",
    ]),
    // sidoList() {
    //   this.getSido();
    // },
    gugunList() {
      // console.log(this.sidoCode);
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    dongList() {
      console.log(this.gugunCode);
      this.CLEAR_DONG_LIST();
      this.dongCode = null;
      if (this.gugunCode) this.getDong(this.gugunCode);
    },
    aptList() {
      this.CLEAR_APT_LIST();
      if (this.dongCode) this.getHouseList(this.dongCode);

      if (this.userInfo) {
        const params = {
          dongCode: this.dongCode,
          user_id: this.userInfo.userId,
        };

        this.checkfavorite({ params });
      }
    },
    searchAptName() {
      if (!this.aptName) {
        alert("아파트명을 입력해주세요 !");
        return;
      }
      console.log(this.aptName);
      this.searchApt(this.aptName);
    },
    clearList() {
      // 검색조건 selectbox 값 변경
      if (this.selected == "region") {
        this.SET_HOUSE_SEARCH(false);
      } else {
        this.SET_HOUSE_SEARCH(true);
      }

      this.CLEAR_SIDO_LIST();
      this.CLEAR_GUGUN_LIST();
      this.CLEAR_DONG_LIST();
      this.getSido();
      this.sidoCode = null;
      this.aptName = null;
    },

    // 관심지역 클릭 시 실행될 메서드
    registLikeDong() {
      // 유효성체크
      if (this.sidoCode == null) alert("시도를 선택해주세요");
      else if (this.gugunCode == null) alert("구군을 선택해주세요");
      else if (this.dongCode == null) alert("동을 선택해주세요");

      http
        .post("/info/registArea", {
          dongCode: this.dongCode,
          user_id: this.userInfo.userId,
        })
        .then(({ data }) => {
          let msg = "관심지역 등록 실패.";
          if (data === "success") {
            msg = "관심지역 등록이 완료되었습니다.";
          }
          alert(msg);
          this.SET_CHECK_FAVORITE(true);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style></style>
