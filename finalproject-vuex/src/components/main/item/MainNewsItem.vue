<template>
  <b-row
    class="row m-2"
    @mouseover="colorChange(true)"
    @mouseout="colorChange(false)"
    :class="{ 'mouse-over-bgcolor': isColor }"
  >
    <b-col cols="12" class="align-self-center">
      <a class="list-group-item" @click="openLink" v-html="newsItem.title"></a>
    </b-col>
    <!-- <b-row>
        <b-col cols="12"
          >{{ house.sidoName }} {{ house.gugunName }} {{ house.dongName }}
        </b-col></b-row
      > -->
  </b-row>
</template>

<script>
export default {
  name: "MainNewsItem",
  data() {
    return {
      isColor: false,
    };
  },
  props: {
    newsItem: Object,
  },
  methods: {
    colorChange(flag) {
      this.isColor = flag;
    },
    openLink() {
      window.open(this.newsItem.link);
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

.mouse-over-bgcolor {
  background-color: lightgray;
}
</style>
