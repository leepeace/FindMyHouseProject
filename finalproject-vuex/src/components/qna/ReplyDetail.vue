<template>
  <b-container class="bv-example-row">
    <b-row class="mt-4 mb-4 text-center">
      <b-col>
        <reply-detail-item v-for="(reply, index) in replys" :key="index" :reply="reply" />
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState} from "vuex";
import ReplyDetailItem from "@/components/qna/item/ReplyDetailItem";

export default {
  name: "ReplyDetail",
  components: {
    ReplyDetailItem,
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
    ...mapState(["replys"])

  },
  created(){
    // 값 가져오기
    this.$store.dispatch("getReply",`${this.$route.params.qnaNo}`);
  },
};
</script>

<style></style>
