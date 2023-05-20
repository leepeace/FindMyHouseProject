import Vue from "vue";
import Vuex from "vuex";
import http from "@/api/http";
import createPersistedState from "vuex-persistedstate";
import memberStore from "@/store/modules/memberStore";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    memberStore,
  },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],

  state: {
    sidos: [{ value: null, text: "시도선택" }],
    guguns: [{ value: null, text: "구군선택" }],
    dongs: [{ value: null, text: "동선택" }],
    houses: [],
    donghouses: [],
    house: null,
    housedeals: [], // 거래내역 
    commercials: [], // 상권정보
    todos: [
      // { title: '할 일1', completed: false },
      // { title: '할 일2', completed: false },
    ],
    qnas: [], // 리스트
    qna: null, // qna글 
    replys: [], // 하나의 글에 여러개 답변 가능
    //userId : "ssafy",
    keyword: null, // 큐엔에이 검색 키워드
    word: null, // 큐엔에이 검색어
    favoriteDong: [], // 나의 관심지역
    isHouseSearch:false, // 아파트 검색 
    favoriteFlag: false, // 관심지역여부
    bookmarks: [],
    
  },
  getters: {
    allTodosCount(state) {
      return state.todos.length;
    },
    completedTodosCount(state) {
      return state.todos.filter((todo) => {
        return todo.completed === true;
      }).length;
    },
    unCompletedTodosCount(state) {
      return state.todos.filter((todo) => {
        return todo.completed === false;
      }).length;
    },
    getReplyList(state){
      return state.replys;
    },

    // 가격 오름차순 정렬 
    // AptPriceAscSort(state){
    //   return state.houses.sort(
    //     (house1, house2)=>{
    //       return house2.bookmark - house1.bookmark;
    //     }
    //   )
    // },

    // 가격 내림차순 정렬 
    // AptPriceDescSort(state){

    // }

  },
  mutations: {
    /////////////////////////////// House start /////////////////////////////////////
    SET_SIDO_LIST(state, sidos) {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST(state, guguns) {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_DONG_LIST(state, dongs) {
      dongs.forEach((dong) => {
        state.dongs.push({ value: dong.dongCode, text: dong.dongName });
      });
    },

    CLEAR_SIDO_LIST(state) {
      state.sidos = [{ value: null, text: "시도선택" }];
    },
    CLEAR_APT_LIST(state) {
      state.houses = [];
      state.house = null;
    },
    CLEAR_GUGUN_LIST(state) {
      state.guguns = [{ value: null, text: "구군선택" }];
    },
    CLEAR_DONG_LIST(state) {
      state.dongs = [{ value: null, text: "동선택" }];
    },
    CREAR_COMMERCIALS(state) {
      state.commercials = [];
    },
    SET_HOUSE_LIST(state, houses) {
      state.houses = houses;
    },
    SET_DETAIL_HOUSE(state, house) {
      // console.log("Mutations", house);
      state.house = house;
    },
    SET_HOUSE_DEAL(state, housedeals) {
      state.housedeals = housedeals;
    },

    SET_HOUSE_SEARCH(state, flag) {
      state.isHouseSearch = flag;
    },

    SET_DONG_DETAIL_HOUSE(state, houses) {
      state.donghouses = houses;
    },

    CLEAR_DONG_HOUSES(state) {
      state.donghouses = [];
    },
    /////////////////////////////// House end /////////////////////////////////////

    //////////////////////////// Todo List start //////////////////////////////////
    CREATE_TODO(state, todoItem) {
      // console.log('mutation CREATE_TODO Call');
      // console.log(state);
      // console.log(todoItem);
      state.todos.push(todoItem);
    },
    DELETE_TODO(state, todoItem) {
      const index = state.todos.indexOf(todoItem);
      state.todos.splice(index, 1);
    },
    UPDATE_TODO_STATUS(state, todoItem) {
      // console.log(state);
      // console.log(todoItem);
      state.todos = state.todos.map((todo) => {
        if (todo === todoItem) {
          return {
            // title: todoItem.title,
            ...todo,
            completed: !todoItem.completed,
          };
        }
        return todo;
      });
    },
    //////////////////////////// Todo List end //////////////////////////////////
    

    //////////////////////////// Qna List start //////////////////////////////////

    CREATE_QNA(state, qnaItem) {
      // console.log('mutation CREATE_TODO Call');
      // console.log(state);
      // console.log(todoItem);
      state.qnas.push(qnaItem);
    },
    SET_QNA_LIST(state, qnas) {
      state.qnas.push(qnas);
    },

    // 해당 글에 대한 답변 불러오기
    SET_REPLY(state,reply){
      state.replys = reply;
    },

    // 해당 글에 대한 답변 저장
    CREATE_REPLY(state,reply){
      state.replys.push(reply);
    },

    // 해당 글에 대한 답변 삭제
    DELETE_REPLY(state,replyNo){
      state.replys = state.replys.filter(function(item) {
        return item.replyNo != replyNo;
      });
    },

    /// 검색 - keyword, word 저장 
    SET_KEYWORD(state,keyword){
      state.keyword = keyword;
    },

    SET_WORD(state,word){
      state.word = word;
    },

    SET_COMMERCIALS(state, commercials) {
      state.commercials = commercials;
    },

    SET_CHECK_FAVORITE(state,flag){
      state.favoriteFlag = flag;
    },

    // 북마크순으로 정렬하기 
    SORT_APT_BOOKMARK(state){
      state.houses.sort(
        (a, b)=>{
          // console.log(a);
          return b.bookmark - a.bookmark;
        }
      )
    },

        // 최근입주순 정렬 
    SORT_RECENT_BUILD(state){
      state.houses.sort(
        (a, b)=>{
          // console.log(a.buildYear);
          return b.buildYear - a.buildYear;
        }
      )
    },

    // 북마크 불러오기 
    GET_BOOKMARKS(state,bookmarks){
      state.bookmarks = bookmarks;
    },

    // 북마크 삭제하기 
    DELETE_BOOKMARKS(state,aptCode){
      state.bookmarks = state.bookmarks.filter(function(item) {
        return item.aptCode != aptCode;
      });
    },

    // 관심지역 세팅하기 
    SET_FAVORITE_DONG(state, favoriteDong) {
      state.favoriteDong = favoriteDong;
    },

    // 관심지역 삭제하기
    DELETE_FAVORITE_DONG(state,dongCode){
      state.favoriteDong = state.favoriteDong.filter(function(item) {
        return item.dongCode != dongCode;
      });
      
    }

  },
  actions: {
    /////////////////////////////// House start /////////////////////////////////////
    getSido({ commit }) {
      http
        .get(`/apt/sido`)
        .then(({ data }) => {
          // console.log(data);
          commit("SET_SIDO_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getGugun({ commit }, sidoCode) {
      const params = { sido: sidoCode };
      http
        .get(`/apt/gugun`, { params })
        .then(({ data }) => {
          // console.log(commit, response);
          commit("SET_GUGUN_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getDong({ commit }, gugunCode) {
      const params = { gugun: gugunCode };
      http
        .get(`/apt/dong`, { params })
        .then(({ data }) => {
          // console.log(commit, response);
          commit("SET_DONG_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getHouseList({ commit }, dongCode) {

      const params = {
        dong: dongCode,
      };

      http
        .get(`/apt/apt`, { params })
        .then(({ data }) => {
          console.log(commit, data);
          commit("SET_HOUSE_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },

    searchApt({ commit }, aptName, sort) {

      const params = {
        aptName: aptName,
        sort: sort
      };

      http
        .get(`/apt/aptSearch`, { params })
        .then(({ data }) => {
          console.log("아파트명 검색!!!");
          //console.log(commit, data);
          commit("SET_HOUSE_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },

    detailHouse({ commit }, house) {
      // 나중에 house.일련번호를 이용하여 API 호출
      //console.log(commit, house);
      commit("SET_DETAIL_HOUSE", house);
    },
    
    getHouseDeal({ commit }, aptCode) {
      // 아파트 거래내역 가져오기 
      const params = {
        aptCode: aptCode,
      };

      http
        .get(`/apt/deal`, { params })
        .then(({ data }) => {
          console.log(commit, data);
          commit("SET_HOUSE_DEAL", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },

    // 상권정보 가져오기 
    getCommercials({ commit }) {
      http
      .get(`/info/commercials`)
      .then(({ data }) => {
        console.log(commit, data);
        commit("SET_COMMERCIALS", null);
      })
      .catch((error) => {
        console.log(error);
      });
    },

    checkfavorite({commit},{params}){
    // 관심지역 체크하기

      http
        .get(`/info/checkfavorite`,{
          params
      })
        .then(({ data }) => {
          //console.log("COUNT : "+data);
          if(data===1){
            commit("SET_CHECK_FAVORITE",true);
          }else{
            commit("SET_CHECK_FAVORITE",false);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },

    /////////////////////////////// House end /////////////////////////////////////

    //////////////////////////// Todo List start //////////////////////////////////
    // createTodo(context, todoItem) {
    //   console.log(context);
    //   console.log('actions createTodo Call');
    //   context.commit('CREATE_TODO', todoItem);
    // },
    // destructuring 활용
    createTodo({ commit }, todoItem) {
      commit("CREATE_TODO", todoItem);
    },
    deleteTodo({ commit }, todoItem) {
      // console.log(context);
      // console.log(todoItem);
      commit("DELETE_TODO", todoItem);
    },
    updateTodoStatus({ commit }, todoItem) {
      commit("UPDATE_TODO_STATUS", todoItem);
    },
    //////////////////////////// Todo List end //////////////////////////////////

    // 답글 달기
    createReply({ commit }, reply) {
      http
        .post(`/qna/reply`, {
          content: reply.content,
          userId: reply.userId,
          qnaNo: reply.qnaNo
        })
        .then(({ data }) => {
          console.log(commit, data);
          commit("SET_REPLY", data);
        })
        .catch((error) => {
          console.log(error);
        });

    },

    getReply({ commit }, qnaNo) {
      http
        .get("/qna/reply/" + qnaNo)
        .then(({ data }) => {
          console.log(commit, data);
          commit("SET_REPLY", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },

    // 답변 삭제
    deleteReply({ commit }, replyNo) {
      http
        .delete("/qna/deleteReply/" + replyNo)
        .then(({ data }) => {
          console.log(commit, data);
          commit("DELETE_REPLY", replyNo);
        })
        .catch((error) => {
          console.log(error);
        });
    },

    // 북마크목록 가져오기 
    getbookmarks({commit},user_id){
      const params = {
        user_id : user_id
      }

      http
        .get("/info/mybookmark",{params})
        .then(({ data }) => {
          console.log(data);
          commit("GET_BOOKMARKS",data);
        })
        .catch((error) => {
          console.log(error);
        });
    },


    // 북마크 삭제하기 
    deletebookmarks({ commit }, {params}){
      const user_id = params.user_id;
      const aptCode = params.aptCode;
      // console.log(user_id + " " + aptCode);
      http.delete(`/info/bookmark/${user_id}/${aptCode}`)
        .then(({ data }) => {
          console.log(commit,data);
          commit("DELETE_BOOKMARKS",aptCode); 
        })
        .catch((error) => {
          console.log(error);
        });
    },

    //나의 관심지역 추천 리스트 보여주기 
    getDongDetailHouse({ commit }, {params}) {

      // console.log(params.age);
      // console.log(params.dongCode);

      http
        .get(`/info/recommendHouse`, {params})
        .then(({ data }) => {
          // console.log("추천리스트!");
          // console.log(commit, data);
          commit("SET_DONG_DETAIL_HOUSE", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },

    // 관심지역 삭제하기 
    deletefavoriteDong({ commit }, {params}){
      http.delete(`/info/deletefavorite`,{params})
        .then(({ data }) => {
          // console.log("관심지역 삭제결과");
          if(data>0){
            commit("DELETE_FAVORITE_DONG",params.dongCode); 
            alert("관심지역을 삭제하였습니다.");
            return;
          }else{
            alert("관심지역을 삭제 실패.");
          }
          
        })
        .catch((error) => {
          console.log(error);
        });
    },

    // 관심지역 보기
    getMyFavoriteDong({commit},{params}){
      http
      .get("/info/favorite", { params })
      .then(({ data }) => {
        //console.log("관심지역 " + data);
        commit("SET_FAVORITE_DONG",data);
      })
      .catch((error) => {
        console.log(error);
      });
    }

  },

});
