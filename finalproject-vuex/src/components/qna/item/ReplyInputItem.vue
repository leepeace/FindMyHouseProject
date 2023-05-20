<template>
  <div class="col">
    <div role="group" class="input-group mt-3">
      <div class="input-group-prepend"><div class="input-group-text">답변</div></div>
      <input type="text" v-model="content" class="form-control" id="__BVID__444" />
      <div class="input-group-append">
        <button type="button" class="btn btn-outline-success" @click="createReply">등록</button>
      </div>
      <!---->
    </div>
  </div>
</template>

<script>
// import moment from "moment";
const memberStore = "memberStore";
import { mapState } from "vuex";

export default {
  name: "ReplyInputItem",
  data() {
    return {
      content: "",
    };
  },
  props: {
    userId: { type: String },
    qnaNo: { type: Number },
  },
  computed:{
    ...mapState(memberStore, ["userInfo", "isKakaoLogin"]),
  },
  methods: {
    createReply() {
      
      const reply = {
        content: this.content,
        userId : this.userInfo.userId,
        qnaNo : this.qnaNo
      };

      if (reply.content) this.$store.dispatch("createReply", reply);
      this.content = "";
  
    },

  },
  // filters: {
  //   dateFormat(regtime) {
  //     return moment(new Date(regtime)).format("YY.MM.DD hh:mm:ss");
  //   },
  // },
};
</script>

<style></style>
