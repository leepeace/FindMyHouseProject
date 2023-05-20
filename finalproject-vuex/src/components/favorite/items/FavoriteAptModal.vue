<template>
  <b-modal id="bv-modal-example" size="lg" ref="modal" hide-footer>
    <!-- <div class="d-block text-center"> -->
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
    <!-- <b-row>
      <b-col>
        <house-deal-list></house-deal-list>
      </b-col>
    </b-row> -->
    <b-card no-body>
      <b-tabs card>
        <b-tab title="실거래가">
          <b-card-text><house-deal-list></house-deal-list></b-card-text>
        </b-tab>
        <b-tab title="리뷰">
          <b-card-text>
            <comments-list></comments-list>
          </b-card-text>
        </b-tab>
      </b-tabs>
    </b-card>
    <!-- </div> -->
    <b-button class="mt-3" block @click="$bvModal.hide('bv-modal-example')">닫기</b-button>
  </b-modal>
</template>

<script>
import { mapState } from "vuex";
import HouseDealList from "@/components/house/HouseDealList.vue";
import CommentsList from "@/components/comments/CommentsList.vue";

export default {
  name: "FavoriteAptModal",
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
  computed: {
    ...mapState(["house", "housedeals"]),
  },
};
</script>

<style></style>
