
      var date = new Date();
      var yearEl = document.querySelector("#year");
      var yearOpt = `<option value="">매매년도선택</option>`;
      var year = date.getFullYear();
      for (let i = year; i > year - 20; i--) {
        yearOpt += `<option value="${i}">${i}년</option>`;
      }
      yearEl.innerHTML = yearOpt;

      ///////////////////////// select box 설정 (지역, 매매기간) /////////////////////////
      document.querySelector("#year").addEventListener("change", function () {
        var month = date.getMonth() + 1;
        var monthEl = document.querySelector("#month");
        var monthOpt = `<option value="">매매월선택</option>`;
        var yearSel = document.querySelector("#year");
        var m = yearSel[yearSel.selectedIndex].value == date.getFullYear() ? month : 13;
        for (let i = 1; i < m; i++) {
          monthOpt += `<option value="${i < 10 ? "0" + i : i}">${i}월</option>`;
        }
        monthEl.innerHTML = monthOpt;
      });

      window.onload = function () {
    	  
      };

///////////////////////// 아파트 매매 정보 /////////////////////////
      
      

    
//      var mapContainer = document.getElementById("map"), // 지도를 표시할 div
//        mapOption = {
//          center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
//          level: 10, // 지도의 확대 레벨
//        };
//
//      var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
//
//      // HTML5의 geolocation으로 사용할 수 있는지 확인합니다
//      if (navigator.geolocation) {
//        // GeoLocation을 이용해서 접속 위치를 얻어옵니다
//        navigator.geolocation.getCurrentPosition(function (position) {
//          var lat = position.coords.latitude, // 위도
//            lon = position.coords.longitude; // 경도
//
//          var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
//            message = '<div style="padding:5px;"> 여기에 계신가요?! </div>'; // 인포윈도우에 표시될 내용입니다
//
//          // 마커와 인포윈도우를 표시합니다
//          displayMarker(locPosition, message);
//        });
//      } else {
//        // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
//
//        var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),
//          message = "geolocation을 사용할수 없어요..";
//
//        displayMarker(locPosition, message);
//      }
//
//      // 지도에 마커와 인포윈도우를 표시하는 함수입니다
//      function displayMarker(locPosition, message) {
//        // 마커를 생성합니다
//        var marker = new kakao.maps.Marker({
//          map: map,
//          position: locPosition,
//        });
//
//        var iwContent = message, // 인포윈도우에 표시할 내용
//          iwRemoveable = true;
//
//        // 인포윈도우를 생성합니다
//        var infowindow = new kakao.maps.InfoWindow({
//          content: iwContent,
//          removable: iwRemoveable,
//        });
//
//        // 인포윈도우를 마커위에 표시합니다
//        infowindow.open(map, marker);
//
//        // 지도 중심좌표를 접속위치로 변경합니다
//        map.setCenter(locPosition);
//      }

      // 아래 코드는 지도 위의 마커를 제거하는 코드입니다
      // marker.setMap(null);
    

    

    ///////////////////////// select box 설정 (지역, 매매기간) /////////////////////////
    // 구분정보가 바뀌면 시도정보 얻기.
    document.querySelector("#gubun").addEventListener("change", function () {
      var dong = document.querySelector("#dongDiv");
      var apartName = document.querySelector("#aptNameDiv");

      if (this[this.selectedIndex].value == 1) {
        dong.style.display = "block";
        apartName.style.display = "none";
      } else if (this[this.selectedIndex].value == 2) {
        dong.style.display = "none";
        apartName.style.display = "block";
      }

      // 시도정보 얻기.
      sendRequest("sido", "*00000000");
    });

    // 시도가 바뀌면 구군정보 얻기.
    document.querySelector("#sido").addEventListener("change", function () {
      if (this[this.selectedIndex].value) {
        let regcode = this[this.selectedIndex].value.substr(0, 2) + "*00000";
        sendRequest("gugun", regcode);
      } else {
        initOption("gugun");
        initOption("dong");
      }
    });

    // 구군이 바뀌면 동정보 얻기.
    document.querySelector("#gugun").addEventListener("change", function () {
      if (this[this.selectedIndex].value) {
        let regcode = this[this.selectedIndex].value.substr(0, 5) + "*";
        sendRequest("dong", regcode);
      } else {
        initOption("dong");
      }
    });

    function sendRequest(selid, regcode) {
      const url = "https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes";
      let params = "regcode_pattern=" + regcode + "&is_ignore_zero=true";

      fetch(`${url}?${params}`)
        .then((response) => response.json())
        .then((data) => addOption(selid, data));
    }

    function addOption(selid, data) {
      let opt = ``;
      initOption(selid);
      switch (selid) {
        case "sido":

        case "sido-modal":
          opt += `<option value="">시도선택</option>`;
          data.regcodes.forEach(function (regcode) {
            opt += `
        <option value="${regcode.code}">${regcode.name}</option>
        `;
          });
          break;
        case "gugun":

        case "gugun-modal":
          opt += `<option value="">구군선택</option>`;
          for (let i = 0; i < data.regcodes.length; i++) {
            if (i != data.regcodes.length - 1) {
              if (
                data.regcodes[i].name.split(" ")[1] == data.regcodes[i + 1].name.split(" ")[1] &&
                data.regcodes[i].name.split(" ").length != data.regcodes[i + 1].name.split(" ").length
              ) {
                data.regcodes.splice(i, 1);
                i--;
              }
            }
          }
          let name = "";
          data.regcodes.forEach(function (regcode) {
            if (regcode.name.split(" ").length == 2) name = regcode.name.split(" ")[1];
            else name = regcode.name.split(" ")[1] + " " + regcode.name.split(" ")[2];
            opt += `
        <option value="${regcode.code}">${name}</option>
        `;
          });
          break;
        case "dong":
        case "dong-modal":
          opt += `<option value="">동선택</option>`;
          let idx = 2;
          data.regcodes.forEach(function (regcode) {
            if (regcode.name.split(" ").length != 3) idx = 3;
            opt += `
        <option value="${regcode.code}">${regcode.name.split(" ")[idx]}</option>
        `;
          });
      }
      document.querySelector(`#${selid}`).innerHTML = opt;
    }

    function initOption(selid) {
      let options = document.querySelector(`#${selid}`);
      options.length = 0;
    }

      ///////////////////////// 아파트 매매 정보 /////////////////////////
      document.querySelector("#list-btn").addEventListener("click", function () {
        var show = document.querySelector("#aptgo");
        show.style.display = "inline";

        let url = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";
        let gugunSel = document.querySelector("#gugun");
        let dongSel = document.querySelector("#dong");
        let regCode = gugunSel[gugunSel.selectedIndex].value.substr(0, 5);
        let yearSel = document.querySelector("#year");
        let year = yearSel[yearSel.selectedIndex].value;
        let monthSel = document.querySelector("#month");
        let month = monthSel[monthSel.selectedIndex].value;
        let dealYM = year + month;
        let queryParams =
          encodeURIComponent("serviceKey") +
          "=" +
          "W8tskb3ozBWJaXxxw5I%2FVKzmrJ53268CjU%2BcNrKjqwATnE8Y0NQjsSzuxuzf%2FzqDq%2B2joOsA4Q3HR347slp2Yg%3D%3D"; /*Service Key*/
        queryParams += "&" + encodeURIComponent("LAWD_CD") + "=" + encodeURIComponent(regCode); /*아파트소재 구군*/
        queryParams += "&" + encodeURIComponent("DEAL_YMD") + "=" + encodeURIComponent(dealYM); /*조회년월*/
        queryParams += "&" + encodeURIComponent("pageNo") + "=" + encodeURIComponent("1"); /*페이지번호*/
        queryParams += "&" + encodeURIComponent("numOfRows") + "=" + encodeURIComponent("30"); /*페이지당건수*/

        console.log(queryParams);
        console.log(`${url}?${queryParams}`);
        var gubun = document.querySelector("#gubun");
        if (gubun[gubun.selectedIndex].value == 1) {
          fetch(`${url}?${queryParams}`)
            .then((response) => response.text())
            .then((data) => makeList(data, dongSel[dongSel.selectedIndex].value));
        } else if (gubun[gubun.selectedIndex].value == 2) {
          let aptName = document.querySelector("#aptName");

          fetch(`${url}?${queryParams}`)
            .then((response) => response.text())
            .then((data) => makeList2(data, aptName.value));
        }
      });

//      function makeList(data, region) {
//        console.log("mklist1");
//        var mapContainer = document.getElementById("map"), // 지도를 표시할 div
//          mapOption = {
//            center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
//            level: 3, // 지도의 확대 레벨
//          };
//
//        var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
//
//        document.querySelector("table").setAttribute("style", "display: ;");
//        let tbody = document.querySelector("#aptlist");
//        let parser = new DOMParser();
//        const xml = parser.parseFromString(data, "application/xml");
//        // console.log(xml);
//        initTable();
//        let apts = xml.querySelectorAll("item");
//
//        apts.forEach((apt) => {
//          let regionCode = apt.querySelector("법정동시군구코드").textContent + "" + apt.querySelector("법정동읍면동코드").textContent;
//          if (region) {
//            if (regionCode == region) {
//              let tr = document.createElement("tr");
//
//              let nameTd = document.createElement("td");
//              nameTd.appendChild(document.createTextNode(apt.querySelector("아파트").textContent));
//              tr.appendChild(nameTd);
//
//              let floorTd = document.createElement("td");
//              floorTd.appendChild(document.createTextNode(apt.querySelector("거래금액").textContent + "만원"));
//              tr.appendChild(floorTd);
//
//              let areaTd = document.createElement("td");
//              areaTd.appendChild(document.createTextNode(apt.querySelector("전용면적").textContent));
//              tr.appendChild(areaTd);
//
//              let dongTd = document.createElement("td");
//              dongTd.appendChild(
//                document.createTextNode(
//                  apt.querySelector("년").textContent +
//                  "년 " +
//                  apt.querySelector("월").textContent +
//                  "월 " +
//                  apt.querySelector("일").textContent +
//                  "일 "
//                )
//              );
//              tr.appendChild(dongTd);
//
//              tbody.appendChild(tr);
//
//              // 주소-좌표 변환 객체를 생성합니다
//              var geocoder = new kakao.maps.services.Geocoder();
//
//              geocoder.addressSearch(
//                apt.querySelector("법정동").textContent + " " + apt.querySelector("지번").textContent,
//                function (result, status) {
//                  // 정상적으로 검색이 완료됐으면
//                  if (status === kakao.maps.services.Status.OK) {
//                    var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
//
//                    // 결과값으로 받은 위치를 마커로 표시합니다
//                    var marker = new kakao.maps.Marker({
//                      map: map,
//                      position: coords,
//                    });
//
//
//                    var infowindow = new kakao.maps.InfoWindow({
//                      content: `<div style="width:250px;padding:10px 10px; color:black;">
//                                    <div>일련번호 : ${apt.querySelector("일련번호").textContent}</div>
//                                    <div>아파트 이름 : ${apt.querySelector("아파트").textContent}</div>
//                                    <div>층 : ${apt.querySelector("층").textContent}</div>
//                                    <div>아파트 면적(m^2) : ${apt.querySelector("전용면적").textContent}</div>  
//                                    </div>`,
//                    });
//                    // infowindow.open(map, marker);
//
//                    kakao.maps.event.addListener(marker, "mouseover", function () {
//                      // 마커에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
//                      infowindow.open(map, marker);
//                    });
//
//                    // 마커에 마우스아웃 이벤트를 등록합니다
//                    kakao.maps.event.addListener(marker, "mouseout", function () {
//                      // 마커에 마우스아웃 이벤트가 발생하면 인포윈도우를 제거합니다
//                      infowindow.close();
//                    });
//
//                    // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
//                    map.setCenter(coords);
//                  }
//                }
//              );
//            }
//          } else {
//            let tr = document.createElement("tr");
//
//            let nameTd = document.createElement("td");
//            nameTd.appendChild(document.createTextNode(apt.querySelector("아파트").textContent));
//            tr.appendChild(nameTd);
//
//            let floorTd = document.createElement("td");
//            floorTd.appendChild(document.createTextNode(apt.querySelector("거래금액").textContent + "만원"));
//            tr.appendChild(floorTd);
//
//            let areaTd = document.createElement("td");
//            areaTd.appendChild(document.createTextNode(apt.querySelector("전용면적").textContent));
//            tr.appendChild(areaTd);
//
//            let dongTd = document.createElement("td");
//            dongTd.appendChild(
//              document.createTextNode(
//                apt.querySelector("년").textContent +
//                "년 " +
//                apt.querySelector("월").textContent +
//                "월 " +
//                apt.querySelector("일").textContent +
//                "일 "
//              )
//            );
//            tr.appendChild(dongTd);
//
//            tbody.appendChild(tr);
//
//            // 주소-좌표 변환 객체를 생성합니다
//            var geocoder = new kakao.maps.services.Geocoder();
//
//            geocoder.addressSearch(apt.querySelector("법정동").textContent + " " + apt.querySelector("지번").textContent, function (result, status) {
//              // 정상적으로 검색이 완료됐으면
//              if (status === kakao.maps.services.Status.OK) {
//                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
//
//                // 결과값으로 받은 위치를 마커로 표시합니다
//                var marker = new kakao.maps.Marker({
//                  map: map,
//                  position: coords,
//                });
//
//
//                var infowindow = new kakao.maps.InfoWindow({
//                  content: `<div style="width:250px;padding:10px 10px;color:black;">
//                                    <div>일련번호 : ${apt.querySelector("일련번호").textContent}</div>
//                                    <div>아파트 이름 : ${apt.querySelector("아파트").textContent}</div>
//                                    <div>층 : ${apt.querySelector("층").textContent}</div>
//                                    <div>아파트 면적(m^2) : ${apt.querySelector("전용면적").textContent}</div>  
//                                    </div>`,
//                });
//                // infowindow.open(map, marker);
//
//                kakao.maps.event.addListener(marker, "mouseover", function () {
//                  // 마커에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
//                  infowindow.open(map, marker);
//                });
//
//                // 마커에 마우스아웃 이벤트를 등록합니다
//                kakao.maps.event.addListener(marker, "mouseout", function () {
//                  // 마커에 마우스아웃 이벤트가 발생하면 인포윈도우를 제거합니다
//                  infowindow.close();
//                });
//
//                // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
//                map.setCenter(coords);
//              }
//            });
//          }
//        });
//      }

//      function makeList2(data, name) {
//        var mapContainer = document.getElementById("map"), // 지도를 표시할 div
//          mapOption = {
//            center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
//            level: 3, // 지도의 확대 레벨
//          };
//
//        var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
//
//        document.querySelector("table").setAttribute("style", "display: ;");
//        let tbody = document.querySelector("#aptlist");
//        let parser = new DOMParser();
//        const xml = parser.parseFromString(data, "application/xml");
//        // console.log(xml);
//        initTable();
//        let apts = xml.querySelectorAll("item");
//        apts.forEach((apt) => {
//          if (apt.querySelector("아파트").textContent.indexOf(name) != -1) {
//            let tr = document.createElement("tr");
//
//            let nameTd = document.createElement("td");
//            nameTd.appendChild(document.createTextNode(apt.querySelector("아파트").textContent));
//            tr.appendChild(nameTd);
//
//            let floorTd = document.createElement("td");
//            floorTd.appendChild(document.createTextNode(apt.querySelector("거래금액").textContent + "만원"));
//            tr.appendChild(floorTd);
//
//            let areaTd = document.createElement("td");
//            areaTd.appendChild(document.createTextNode(apt.querySelector("전용면적").textContent));
//            tr.appendChild(areaTd);
//
//            let dongTd = document.createElement("td");
//            dongTd.appendChild(
//              document.createTextNode(
//                apt.querySelector("년").textContent +
//                "년 " +
//                apt.querySelector("월").textContent +
//                "월 " +
//                apt.querySelector("일").textContent +
//                "일 "
//              )
//            );
//            tr.appendChild(dongTd);
//
//            tbody.appendChild(tr);
//
//            // 주소-좌표 변환 객체를 생성합니다
//            var geocoder = new kakao.maps.services.Geocoder();
//
//            geocoder.addressSearch(apt.querySelector("법정동").textContent + " " + apt.querySelector("지번").textContent, function (result, status) {
//              // 정상적으로 검색이 완료됐으면
//              if (status === kakao.maps.services.Status.OK) {
//                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
//
//                // 결과값으로 받은 위치를 마커로 표시합니다
//                var marker = new kakao.maps.Marker({
//                  map: map,
//                  position: coords,
//                });
//
//                // 인포윈도우로 장소에 대한 설명을 표시합니다
//                // var infowindow = new kakao.maps.InfoWindow({
//                //   content: `<div style="width:150px; text-align:center; padding:6px 0; color:black;"><%--${apt.querySelector("아파트").textContent}--%></div>`,
//                // });
//                // infowindow.open(map, marker);
//
//                // 인포윈도우로 장소에 대한 설명을 표시합니다
//                var infowindow = new kakao.maps.InfoWindow({
//                  content: `<div style="width:250px;padding:10px 10px;">
//                                    <div>일련번호 : ${apt.querySelector("일련번호").textContent}</div>
//                                    <div>아파트 이름 : ${apt.querySelector("아파트").textContent}</div>
//                                    <div>층 : ${apt.querySelector("층").textContent}</div>
//                                    <div>아파트 면적(m^2) : ${apt.querySelector("전용면적").textContent}</div>  
//                                    </div>`,
//                });
//                // infowindow.open(map, marker);
//
//                kakao.maps.event.addListener(marker, "mouseover", function () {
//                  // 마커에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
//                  infowindow.open(map, marker);
//                });
//
//                // 마커에 마우스아웃 이벤트를 등록합니다
//                kakao.maps.event.addListener(marker, "mouseout", function () {
//                  // 마커에 마우스아웃 이벤트가 발생하면 인포윈도우를 제거합니다
//                  infowindow.close();
//                });
//
//                // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
//                map.setCenter(coords);
//              }
//            });
//          }
//        });
//      }

      function initTable() {
        let tbody = document.querySelector("#aptlist");
        let len = tbody.rows.length;
        for (let i = len - 1; i >= 0; i--) {
          tbody.deleteRow(i);
        }
      }
    
    