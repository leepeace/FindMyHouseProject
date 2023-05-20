// 지점 선택시 지도 이동.
let offices = document.querySelectorAll(".accordion-body");
offices.forEach(function (office) {
  office.addEventListener("click", function () {
    viewMarker(this);
  });
});

function viewMarker(office) {
  officeLatLng = officePosition[office.textContent];
  marker.setMap(null);
  myLatLng = new kakao.maps.LatLng(officeLatLng.lat, officeLatLng.lng);
  let message = `<div style="padding:5px;">${office.textContent}</div>`;
  const imageSrc = "../assets/img/my_position.png"; // 마커이미지의 주소입니다
  let imageSize = new kakao.maps.Size(50, 54); // 마커이미지의 크기입니다
  let imageOption = { offset: new kakao.maps.Point(27, 69) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

  // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
  var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

  displayMarker(myLatLng, message, markerImage);
}
