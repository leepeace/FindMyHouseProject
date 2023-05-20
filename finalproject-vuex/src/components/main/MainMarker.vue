<template>
  <div class="row">
    <div class="col-md-3">
      <div style="width: 100%; height: 700px; overflow: auto; background-color: beige">
        <table class="table table-hover text-center">
          <tbody id="aptList">
            <!-- <c:if test="${aptList ne null}">
											<c:forEach items="${aptList}" var="apt">
												<tr>
													<td>아파트 : ${apt.getName()} <br> 면적 :
														${apt.getArea()} <br> 금액 :
														${apt.getDealAmount().trim()} 만원
													</td>

												</tr>
											</c:forEach>
										</c:if> -->
          </tbody>
        </table>
      </div>
    </div>

    <div class="col-sm-9">
      <div id="map" style="width: 100%; height: 700px"></div>
    </div>
  </div>
</template>

<script
  type="text/javascript"
  src="//dapi.kakao.com/v2/maps/sdk.js?appkey=f4469e3141710684a6e12b008a8d58b3
  &libraries=services"
></script>

<script>
export default {};
</script>

<script>
var container = document.getElementById("map"); //지도를 담을 영역의 DOM 레퍼런스
var options = {
  //지도를 생성할 때 필요한 기본 옵션
  center: new kakao.maps.LatLng(37.5012743, 127.039585), //지도의 중심좌표.
  level: 3,
  //지도의 레벨(확대, 축소 정도)
};

if (navigator.geolocation) {
  // GeoLocation을 이용해서 접속 위치를 얻어옵니다
  navigator.geolocation.getCurrentPosition(function (position) {
    var lat = position.coords.latitude, // 위도
      lon = position.coords.longitude; // 경도

    var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
      message = '<div style="padding:5px;"> 여기에 계신가요?! </div>'; // 인포윈도우에 표시될 내용입니다

    // 마커와 인포윈도우를 표시합니다
    displayMarker(locPosition, message);
  });
} else {
  // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다

  var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),
    message = "geolocation을 사용할수 없어요..";

  displayMarker(locPosition, message);
}

var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

// 마커가 표시될 위치입니다
var markerPosition = new kakao.maps.LatLng(37.5012743, 127.039585);

// 마커를 생성합니다
var marker = new kakao.maps.Marker({
  position: markerPosition,
});

// 마커가 지도 위에 표시되도록 설정합니다
marker.setMap(map);
map.setCenter(locPosition);
</script>
