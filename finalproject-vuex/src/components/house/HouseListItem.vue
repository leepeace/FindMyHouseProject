<template>
  <b-row
    class="row m-2"
    @click="selectHouse"
    @mouseover="colorChange(true)"
    @mouseout="colorChange(false)"
    :class="{ 'mouse-over-bgcolor': isColor }"
  >
    <!-- <b-col cols="2" class="text-center align-self-center">
      <b-img thumbnail src="https://picsum.photos/250/250/?image=58" alt="Image 1"></b-img>
    </b-col> -->
    <b-col cols="10" class="align-self-center" style="font-weight: bold">
      {{ house.aptName }}
    </b-col>
    <div class="w-100"></div>
    <b-col><font-awesome-icon icon="fa-regular fa-heart" />&nbsp;{{ house.bookmark }}</b-col>
    <b-col class="region">
      [{{ house.sidoName }} {{ house.gugunName }} {{ house.dongName }}]
    </b-col>
    <!-- <b-row>
      <b-col cols="12"
        >{{ house.sidoName }} {{ house.gugunName }} {{ house.dongName }}
      </b-col></b-row
    > -->
  </b-row>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "HouseListItem",
  data() {
    return {
      isColor: false,
    };
  },
  props: {
    house: Object,
  },
  methods: {
    ...mapActions(["detailHouse"]),
    selectHouse() {
      //console.log("listRow : ", this.house);
      // this.$store.dispatch("getHouse", this.house);
      // 아파트의 상세정보 얻어오기.
      this.detailHouse(this.house);
    },
    colorChange(flag) {
      this.isColor = flag;
    },
  },
};
</script>

<style scoped>
.apt {
  width: 50px;
}
.region {
  color: gray;
}

.row {
  border-bottom: 1px solid rgb(231, 231, 231);
}

.mouse-over-bgcolor {
  background-color: lightblue;
}
</style>
