<template>
  <b-container class="bv-example-row mt-3">
    <b-row class="mt-4 mb-4 text-center">
      <b-col>
        <b-table-simple sticky-header hover responsive>
          <b-thead head-variant="dark">
            <b-tr>
              <b-th><a>아파트명</a></b-th>
              <b-th>주소</b-th>
              <b-th>북마크수</b-th>
              <b-th></b-th>
            </b-tr>
          </b-thead>
          <tbody>
            <!-- 하위 component에 데이터 전달(props) -->
            <favorite-apt-list-item v-for="(bookmark, index) in bookmarks" :key="index" :bookmark="bookmark" />
          </tbody>
        </b-table-simple>
      </b-col>
    </b-row>

    <!-- 북마크 상세정보 모달 -->
    <favorite-apt-modal></favorite-apt-modal>
  </b-container>
</template>

<script>
import FavoriteAptListItem from "@/components/favorite/items/FavoriteAptListItem.vue";
import FavoriteAptModal from "@/components/favorite/items/FavoriteAptModal.vue";
import { mapActions, mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "FavoriteAptList",
  components: {
    FavoriteAptListItem,
    FavoriteAptModal,
  },
  computed: {
    ...mapState(["house", "bookmarks"]),
    ...mapState(memberStore, ["userInfo"]),
  },
  created() {
    // 북마크 리스트 불러오기
    this.getbookmarks(this.userInfo.userId);
  },
  methods: {
    ...mapActions(["getbookmarks"]),
  },
};
</script>

<style></style>
