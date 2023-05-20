<template>
  <div class="map_wrap">
    <div id="map" style="position: relative; overflow: hidden">
      <ul id="category">
        <li id="BK9" data-order="0" @click="clickCategory('BK9', '0')">
          <span class="category_bg bank"></span>
          은행
        </li>
        <li id="MT1" data-order="1" @click="clickCategory('MT1', '1')">
          <span class="category_bg mart"></span>
          마트
        </li>
        <li id="PM9" data-order="2" @click="clickCategory('PM9', '2')">
          <span class="category_bg pharmacy"></span>
          약국
        </li>
        <li id="OL7" data-order="3" @click="clickCategory('OL7', '3')">
          <span class="category_bg oil"></span>
          주유소
        </li>
        <li id="CE7" data-order="4" @click="clickCategory('CE7', '4')">
          <span class="category_bg cafe"></span>
          카페
        </li>
        <li id="CS2" data-order="5" @click="clickCategory('CS2', '5')">
          <span class="category_bg store"></span>
          편의점
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  data() {
    return {
      map: null,
      ps: null, // 장소검색객체
      currCategory: null, // 현재 체크되어있는 카테고리
      order: null, // 카테고리 순서
      markers: [], // 화면에 표시되어있는 marker들 (apt)
      commercialMarkers: [], // 상권정보 표시할 marker들
      customOverlays: [], // infowindow data
      placeOverlay: null,
      contentNode: null,
    };
  },
  computed: {
    ...mapState(["sidos", "guguns", "dongs", "houses", "house"]),
  },
  watch: {
    // data, computed의 값이 변경될 때 실행되는 속성
    // houses가 변경될 때 실행시킨다.
    houses() {
      //console.log("house list ::" + this.houses);
      if (this.houses.length == 0) this.initMap();
      else this.displayMarkers(this.houses);
    },

    // house가 변경되면 실행한다
    house() {
      this.detailMarker();
    },
  },
  methods: {
    //...mapActions(["getCommercials"]),

    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(37.5012743, 127.039585),
        level: 3,
      };

      this.map = new kakao.maps.Map(container, options);

      // 1) 장소검색 객체를 생성합니다
      this.ps = new kakao.maps.services.Places(this.map);
      this.setCustomOveray();
      // 2) 지도 중심 or 위치변경 시 카테고리에 해당하는 장소검색을 위한 idle 이벤트를 등록합니다
      kakao.maps.event.addListener(this.map, "idle", this.searchPlaces);
    },

    // 상권정보 커스텀오버레이 설정
    setCustomOveray() {
      this.placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 });
      this.contentNode = document.createElement("div"); // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다
      this.contentNode.className = "placeinfo_wrap";

      // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
      // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다
      this.addEventHandle(this.contentNode, "mousedown", kakao.maps.event.preventMap);
      this.addEventHandle(this.contentNode, "touchstart", kakao.maps.event.preventMap);

      this.placeOverlay.setContent(this.contentNode);
    },

    addEventHandle(target, type, callback) {
      if (target.addEventListener) {
        target.addEventListener(type, callback);
      } else {
        target.attachEvent("on" + type, callback);
      }
    },

    // 장소검색 method
    searchPlaces() {
      // 카테고리가 선택되어있지 않다면 그대로 리턴한다
      if (!this.currCategory) return;

      this.placeOverlay.setMap(null);

      // 지도에 표시되어있는 상권정보 마커를 제거하고 마커 배열도 비워준다.
      if (this.commercialMarkers.length > 0) {
        this.commercialMarkers.forEach((item) => {
          item.setMap(null);
        });
      }
      this.commercialMarkers = [];

      this.ps.categorySearch(
        this.currCategory,
        (data, status) => {
          if (status === kakao.maps.services.Status.OK) {
            // 지도에 표시하기
            this.displayPlaces(data);
          }
        },
        { useMapBounds: true }
      );
    },

    // 장소검색 후 지도에 마커를 표출하는 함수
    displayPlaces(places) {
      // 몇번째 카테고리가 선택되어 있는지 얻어온다
      for (var i = 0; i < places.length; i++) {
        // 마커를 생성하고 지도에 표시한다
        var marker = this.addMarker(new kakao.maps.LatLng(places[i].y, places[i].x), this.order);
        // 마커와 검색결과 항목을 클릭 했을 때
        // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
        ((marker, place) => {
          kakao.maps.event.addListener(marker, "click", () => {
            this.displayPlaceInfo(place);
          });
        })(marker, places[i]);
      }
    },

    // 클릭한 마커에 대한 장소 상세정보를 커스텀 오버레이로 표시하는 함수입니다
    displayPlaceInfo(place) {
      var content =
        '<div class="placeinfo">' +
        '   <a class="title" href="' +
        place.place_url +
        '" target="_blank" title="' +
        place.place_name +
        '">' +
        place.place_name +
        "</a>";

      if (place.road_address_name) {
        content +=
          '    <span title="' +
          place.road_address_name +
          '">' +
          place.road_address_name +
          "</span>" +
          '  <span class="jibun" title="' +
          place.address_name +
          '">(지번 : ' +
          place.address_name +
          ")</span>";
      } else {
        content += '    <span title="' + place.address_name + '">' + place.address_name + "</span>";
      }

      content +=
        '    <span class="tel">' + place.phone + "</span>" + "</div>" + '<div class="after"></div>';

      this.contentNode.innerHTML = content;
      this.placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
      this.placeOverlay.setMap(this.map);
    },

    // 마커를 생성하고 지도 위에 마커를 표시하는 함수
    addMarker(position, order) {
      var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(27, 28), // 마커 이미지의 크기
        imgOptions = {
          spriteSize: new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
          spriteOrigin: new kakao.maps.Point(46, order * 36), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(11, 28), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.commercialMarkers.push(marker); // 배열에 생성된 마커를 추가합니다

      return marker;
    },

    removeCustomOverlays() {
      if (this.customOverlays.length > 0) {
        this.customOverlays.forEach((item) => {
          item.setMap(null);
        });
      }
    },

    // 각 카테고리를 클릭했을 때 실행될 메서드
    clickCategory(category, order) {
      // 현재 클릭한 카테고리 순서와 카테고리명을 저장한다

      // 현재 카테고리가 클릭되어있는 상태라면 ?
      if (order == this.order) {
        // 값 비워주고 마커 지워주기
        this.order = null;
        this.currCategory = null;
        // 지도에 표시되어있는 상권정보 마커를 제거하고 마커 배열도 비워준다.
        if (this.commercialMarkers.length > 0) {
          this.commercialMarkers.forEach((item) => {
            item.setMap(null);
          });
        }
        this.commercialMarkers = [];
      } else {
        // 순서와 현재 클릭한 카테고리명으로 변경해주기
        console.log(category, order);
        this.order = order;
        this.currCategory = category;
        this.searchPlaces();
      }

      // 클릭상태가 아니라면 ??
    },

    // 아파트 선택 시 해당 위치의 좌표 표시하기
    detailMarker() {
      // 마커를 생성합니다
      //   var marker = new kakao.maps.Marker({
      //     position: new kakao.maps.LatLng(this.house.lat, this.house.lng),
      //   });
      const position = new kakao.maps.LatLng(this.house.lat, this.house.lng);
      this.map.setCenter(position);
      // 마커가 지도 위에 표시되도록 설정합니다
      this.map.setLevel(3);

      // if (this.customOverlays.length > 0) {
      //   this.customOverlays.forEach((item) => {
      //     item.setMap(null);
      //   });
      // }
      this.removeCustomOverlays();

      var content =
        '<div class ="label"><span class="left"></span><span class="center">' +
        this.house.aptName +
        '</span> <span class="right"></span></div > ';

      var customOverlay = new kakao.maps.CustomOverlay({
        map: this.map,
        clickable: true,
        content,
        position: position,
        xAnchor: 0.5,
        yAnchor: 1,
        zIndex: 3,
      });

      this.customOverlays.push(customOverlay);

      customOverlay.setMap(this.map); // 지도에 올린다.
    },

    // 상권정보 마커 표시하기
    // displayCommercials() {
    //   // 2. 마커 이미지 커스터마이징 하기
    //   // 이미지를 이용하기 위해서는 require 메서드를 이용해야 한다.
    //   const imgSrc = require("@/assets/img/comm_marker.png");
    //   const imgSize = new kakao.maps.Size(24.35);
    //   const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

    //   // 3. 마커 표시하기
    //   this.commercials.map((position) => {
    //     //console.log(position);
    //     const marker = new kakao.maps.Marker({
    //       map: this.map,
    //       position: new kakao.maps.LatLng(position.latitude, position.longitude), // 마커위치
    //       title: position.store_name,
    //       image: markerImage,
    //     });

    //     // 마커에 커서가 오버됐을 때 마커 위에 표시할 인포윈도우를 생성합니다
    //     var content = "<div>" + position.store_name + "</div>"; // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다

    //     var customOverlay = new kakao.maps.CustomOverlay({
    //       map: this.map,
    //       content,
    //       position: new kakao.maps.LatLng(position.latitude, position.longitude),
    //     });

    //     kakao.maps.event.addListener(marker, "mouseover", function () {
    //       customOverlay.setMap(this.map);
    //     });

    //     kakao.maps.event.addListener(marker, "mouseout", function () {
    //       customOverlay.setMap(null);
    //     });

    //     this.commercialMarkers.push(marker);
    //   });
    // },

    // 해당 지역의 모든 마커 표시하기
    displayMarkers(positions) {
      // 1. 현재 표시되어있는 marker들이 있다면 marker에 등록된 map을 없애준다.
      if (this.markers.length > 0) {
        this.markers.forEach((item) => {
          item.setMap(null);
        });
      }

      // 1-1 이전의 커스텀오버레이 모두 지워주기
      this.removeCustomOverlays();

      // 2. 마커 이미지 커스터마이징 하기
      // 이미지를 이용하기 위해서는 require 메서드를 이용해야 한다.
      const imgSrc = require("@/assets/img/marker-img.png");
      const imgSize = new kakao.maps.Size(24.35);
      const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

      // 3. 마커 표시하기
      positions.map((position) => {
        //console.log(position.aptName);
        const marker = new kakao.maps.Marker({
          map: this.map,
          position: new kakao.maps.LatLng(position.lat, position.lng), // 마커위치
          title: position.aptName,
          image: markerImage,
        });

        this.markers.push(marker);
      });

      // 4. 지도를 이동시켜주기 !!!!
      const bounds = positions.reduce(
        (bounds, position) => bounds.extend(new kakao.maps.LatLng(position.lat, position.lng)),
        new kakao.maps.LatLngBounds()
      );

      // 추가된 좌표들을 기준으로 범위를 재설정
      this.map.setBounds(bounds);
    },
  },
  mounted() {
    if (!window.kakao || !window.kakao.maps) {
      const script = document.createElement("script");
      script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=a18f870ca01deabb6439b8d80002f320&libraries=services`;
      //   console.log("kakao link " + script.src);

      /* global kakao */
      script.addEventListener("load", () => {
        kakao.maps.load(this.initMap);
      });

      document.head.appendChild(script);
    } else {
      //console.log("이미 로딩됨");
      this.initMap();
    }
  }, // end mounted
};
</script>

<style>
#map {
  width: 100%;
  height: 700px;
}

.customoverlay {
  position: relative;
  bottom: 85px;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  float: left;
}
.customoverlay:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.customoverlay a {
  display: block;
  text-decoration: none;
  color: #000;
  text-align: center;
  border-radius: 6px;
  font-size: 14px;
  font-weight: bold;
  overflow: hidden;
  background: #d95050;
  background: #d95050 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
    no-repeat right 14px center;
}
.customoverlay .title {
  display: block;
  text-align: center;
  background: #fff;
  margin-right: 35px;
  padding: 10px 15px;
  font-size: 14px;
  font-weight: bold;
}
.customoverlay:after {
  content: "";
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: -12px;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}

.label {
  margin-bottom: 10px;
}
.label * {
  display: inline-block;
  vertical-align: top;
}
.label .left {
  background: url("https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_l.png")
    no-repeat;
  display: inline-block;
  height: 24px;
  overflow: hidden;
  vertical-align: top;
  width: 7px;
}
.label .center {
  background: url(https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_bg.png)
    repeat-x;
  display: inline-block;
  height: 24px;
  font-size: 12px;
  font-weight: bold;
  line-height: 24px;
}
.label .right {
  background: url("https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_r.png") -1px
    0 no-repeat;
  display: inline-block;
  height: 24px;
  overflow: hidden;
  width: 6px;
}
.map_wrap,
.map_wrap * {
  margin: 0;
  padding: 0;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  font-size: 12px;
}
.map_wrap {
  position: relative;
  width: 100%;
  height: 700px;
}
#category {
  position: absolute;
  top: 10px;
  left: 10px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 2;
}
#category li {
  float: left;
  list-style: none;
  width: 50px;
  border-right: 1px solid #acacac;
  padding: 6px 0;
  text-align: center;
  cursor: pointer;
}
#category li.on {
  background: #eee;
}
#category li:hover {
  background: #ffe6e6;
  border-left: 1px solid #acacac;
  margin-left: -1px;
}
#category li:last-child {
  margin-right: 0;
  border-right: 0;
}
#category li span {
  display: block;
  margin: 0 auto 3px;
  width: 27px;
  height: 28px;
}
#category li .category_bg {
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png)
    no-repeat;
}
#category li .bank {
  background-position: -10px 0;
}
#category li .mart {
  background-position: -10px -36px;
}
#category li .pharmacy {
  background-position: -10px -72px;
}
#category li .oil {
  background-position: -10px -108px;
}
#category li .cafe {
  background-position: -10px -144px;
}
#category li .store {
  background-position: -10px -180px;
}
#category li.on .category_bg {
  background-position-x: -46px;
}
.placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
}
.placeinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}
.placeinfo:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.placeinfo_wrap .after {
  content: "";
  position: relative;
  margin-left: -12px;
  left: 50%;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.placeinfo a,
.placeinfo a:hover,
.placeinfo a:active {
  color: #fff;
  text-decoration: none;
}
.placeinfo a,
.placeinfo span {
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
.placeinfo span {
  margin: 5px 5px 0 5px;
  cursor: default;
  font-size: 13px;
}
.placeinfo .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #d95050;
  background: #d95050 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
    no-repeat right 14px center;
}
.placeinfo .tel {
  color: #0f7833;
}
.placeinfo .jibun {
  color: #999;
  font-size: 11px;
  margin-top: 0;
}
.completed {
  text-decoration: line-through;
  font-style: italic;
  font-weight: bold;
}
</style>
