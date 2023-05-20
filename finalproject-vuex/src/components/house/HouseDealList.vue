<template>
  <b-container v-if="housedeals && housedeals.length != 0" class="bv-example-row">
    <h5>실거래가 / 평균거래가 : {{ housedeals[0].dealAvg | price }}</h5>
    <b-row class="mt-4 mb-4 text-center">
      <b-col v-if="housedeals.length">
        <b-table-simple sticky-header hover responsive>
          <b-thead head-variant="dark">
            <b-tr>
              <b-th>거래년월</b-th>
              <b-th>거래금액</b-th>
              <b-th>층수</b-th>
              <b-th>면적</b-th>
            </b-tr>
          </b-thead>
          <tbody>
            <!-- 하위 component인 BoardListItem에 데이터 전달(props) -->
            <house-deal-item
              v-for="(housedeal, index) in housedeals"
              :key="index"
              :housedeal="housedeal"
            />
          </tbody>
        </b-table-simple>
      </b-col>
    </b-row>
  </b-container>
  <b-container v-else class="bv-example-row">
    <b-row>
      <b-col><b-alert show>거래 목록이 없습니다.</b-alert></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState } from "vuex";
import HouseDealItem from "@/components/house/HouseDealItem.vue";

export default {
  name: "HouseDealList",
  components: {
    HouseDealItem,
  },
  computed: {
    //   1.
    // todos() {
    //   return this.$store.state.todos;
    // },
    // 2.
    // ...mapState({
    //   todos: 'todos',
    // }),
    // 3.
    ...mapState(["housedeals", "house"]),
  },
  filters: {
    price(value) {
      if (!value) return value;
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
  created() {
    // 값 가져오기
    //console.log("아파트 코드 :: " + this.$store.state.house.aptCode);
    this.$store.dispatch("getHouseDeal", this.$store.state.house.aptCode);
  },
};
</script>

<style></style>
