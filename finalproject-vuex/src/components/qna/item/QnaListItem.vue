<template>
  <b-tr>
    <b-td>{{ qnaNo }}</b-td>
    <b-th class="text-left">
      <router-link :to="{ name: 'qnaview', params: { qnaNo: qnaNo } }">{{ subject }}</router-link>
    </b-th>
    <b-td>{{ hit }}</b-td>
    <b-td>{{ userId }}</b-td>
    <b-td>{{ registerTime | dateFormat }}</b-td>
  </b-tr>
</template>

<script>
import moment from "moment";

export default {
  name: "QnaListItem",
  props: {
    qnaNo: Number,
    userId: String,
    subject: String,
    hit: Number,
    registerTime: String,
  },
  filters: {
    dateFormat(registerTime) {
      return moment(new Date(registerTime)).format("YY.MM.DD");
    },
  },
};
</script>

<style></style>
