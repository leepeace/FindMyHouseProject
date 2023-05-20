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
    <b-td>{{ housedeal.dealYear }}.{{ housedeal.dealMonth }}</b-td>
    <b-th>
      {{ housedeal.dealAmount }}
    </b-th>
    <b-td>{{ housedeal.floor }}</b-td>
    <b-td>{{ housedeal.area }} ㎡</b-td>
  </b-tr>
</template>

<script>
export default {
  name: "HouseDealItem",
  props: {
    housedeal: Object,
  },
};
</script>

<style scoped></style>
