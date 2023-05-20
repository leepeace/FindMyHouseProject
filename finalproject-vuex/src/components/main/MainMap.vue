<template>
  <div class="col-md-12" style="margin-top:20px; height:1000px">
    <br />

    <main-search></main-search>

    <hr />
    <b-row>
      <b-col cols="7" style="margin-left: 100px; margin-top: 10px">
        <!-- 카카오맵 -->
        <kakao-map />
      </b-col>
      <b-col v-if="!house" cols="4" align="left">
        <b-dropdown size="sm" text="정렬조건" class="m-2">
          <b-dropdown-item-button @click="sortApt('bookmark')">북마크순</b-dropdown-item-button>
          <!-- <b-dropdown-item-button @click="sortApt('recommend')">추천순</b-dropdown-item-button> -->
          <b-dropdown-item-button @click="sortApt('recentBuild')">최근입주순</b-dropdown-item-button>
        </b-dropdown>
        <house-list />
      </b-col>
      <b-col v-else cols="4" align="left">
        <house-detail></house-detail>
      </b-col>
    </b-row>
    <!-- <hr /> -->
    <br />
  </div>
</template>

<script>
import MainSearch from "@/components/main/MainSearch.vue";
import HouseList from "@/components/house/HouseList.vue";
import HouseDetail from "@/components/house/HouseDetail.vue";
import KakaoMap from "@/components/main/KakaoMap.vue";
import { mapState } from "vuex";

export default {
  data() {
    return {
      aptLists: [],
      options: ["Apple", "Banana", "Grape", "Kiwi", "Orange"],
    };
  },
  components: {
    MainSearch,
    HouseList,
    KakaoMap,
    HouseDetail,
  },
  computed: {
    ...mapState(["house", "isHouseSearch"]),
    // axios
    //   .get(`http://localhost:9999/apt/list`)
    //   .then(({ data }) => {
    //     console.log(data);
    //     this.aptLists = data;
    //   })
    //   .catch(function (error) {
    //     console.log(error);
    //   });
  },

  methods: {
    sortApt(sort) {
      // alert(sort);
      if (sort == "bookmark") {
        // 북마크순 정렬인 경우
        this.$store.commit("SORT_APT_BOOKMARK");
      } else if (sort == "recentBuild") {
        this.$store.commit("SORT_RECENT_BUILD");
      }
    },
  },
};
</script>

<style></style>
