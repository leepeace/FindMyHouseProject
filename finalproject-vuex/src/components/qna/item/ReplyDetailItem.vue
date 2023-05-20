<template>
  <b-container class="bv-example-row">
    <b-row class="mt-2">
      <b-col cols="10" class="p-1 text-left" style="text-w"> ID : {{ reply.userId }} </b-col>
      <b-col v-if="this.userInfo!=null && userInfo.userId == this.reply.userId" class="p-2 text-right">
        <font-awesome-icon icon="fa-solid fa-xmark" @click="deleteReply" />
      </b-col>
      <b-col cols="12" class="bg p-4 pl-6 text-left" style="border-radius: 15px 15px 15px 15px;">
        <span>{{ reply.content }}</span>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "ReplyDetailItem",
  props: {
    reply: Object,
  },
  computed: {
    ...mapState(memberStore, ["userInfo", "isKakaoLogin"]),
  },
  methods: {
    deleteReply() {
      console.log(this.reply.replyNo);
      this.$store.dispatch("deleteReply", this.reply.replyNo);
    },
  },
};
</script>

<style scoped>
.bg{
  background-color:rgb(252, 252, 252)
}

</style>
