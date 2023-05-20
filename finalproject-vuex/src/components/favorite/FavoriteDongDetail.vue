<template>
  <b-container class="bv-example-row mt-3">
    <b-row class="mt-4 mb-4 text-center">
      <b-col v-if="donghouses.length==0" cols="10">
        <b-row><br/></b-row>
        <!-- <b-col style="margin-top:16px; height:100px"><b-alert show style="font-weight:bold">관심지역을 선택해주세요 !</b-alert></b-col> -->
      </b-col>
      <b-col cols="10" v-else>
        <b-col><b-alert show variant="light" style="font-weight:bold">{{userInfo.userId}} 님을 위한 &nbsp; '{{donghouses[0].dongName}}' &nbsp; 추천매물</b-alert></b-col>
        <b-table-simple sticky-header hover responsive>
          <b-thead head-variant="dark">
            <b-tr>
              <b-th><a>아파트명</a></b-th>
              <b-th>주소</b-th>
              <b-th>북마크수</b-th>
            </b-tr>
          </b-thead>
          <tbody>
            <!-- 하위 component에 데이터 전달(props) -->
            <favorite-dong-detail-item
              v-for="(house, index) in donghouses"
              :key="index"
              :house="house"
            />
          </tbody>
        </b-table-simple>
      </b-col>
    </b-row>

    <!-- 북마크 상세정보 모달 -->
    <favorite-apt-modal></favorite-apt-modal>
  </b-container>
</template>

<script>
import FavoriteDongDetailItem from "@/components/favorite/items/FavoriteDongDetailItem.vue";
import FavoriteAptModal from "@/components/favorite/items/FavoriteAptModal.vue";
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "FavoriteDongDetail",
  components: {
    FavoriteDongDetailItem,
    FavoriteAptModal,
  },
  computed: {
    ...mapState(["donghouses"]),
    ...mapState(memberStore, ["userInfo", "isKakaoLogin"]),
  },
};
</script>

<style></style>
