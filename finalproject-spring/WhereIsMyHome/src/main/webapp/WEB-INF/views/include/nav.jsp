<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- jstl 사용하기 위한 코드 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


	<nav>
	<div class="container-fluid">
		<div class="row">
			<div class="top">
				<!--로그인 안 됐을 때-->
				<ul class="list-group list-group-horizontal" id="login-off  "
					style="padding-top: 13px">
					<c:if test="${empty userinfo}">
						<li class="top-button list-group-item"><a class="nav-link"
							href="${root}/user/mvlogin"><img
								src="data:image/jpeg;base64,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"
								width="20px" height="20px" />로그인</a></li>
						<li class="top-button list-group-item"><a class="nav-link"
							href="${root}/user/join.jsp"><img
								src="https://png.pngtree.com/png-clipart/20190705/original/pngtree-vector-notification-icon-png-image_4221919.jpg"
								width="25px" height="25px" />회원가입</a></li>
					</c:if>
					<c:if test="${!empty userinfo}">
						<li class="nav-item nav-link">${userinfo.userName}님 안녕하세요</li>
						<li class="top-button list-group-item">
							<a class="nav-link" href="${root}/user/memberview.jsp">마이페이지</a>
						</li>
						<button type="button" id="btn-logout"
							class="btn btn-outline-danger mb-3 ms-1">로그아웃</button>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
	</nav>

	<nav class="navbar navbar-expand-lg navbar-light bg-white shadow">
		<div class="container-fluid">
			<a class="navbar-brand text-primary" href="${root}/index.jsp">
            <img
              src="https://play-lh.googleusercontent.com/nza1fOdLWUtyN1Z-rJQA7XrwHux0dGs2x5wdNpwZNaY677OMVD-4Lm-QDgFuY-XvZVbB=w600-h300-pc0xffffff-pd"
              width="150"
              alt=""
            />
       		</a>
			<c:if test="${empty userinfo}">
				<button class="navbar-toggler" type="button"
					data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
					aria-controls="navbarSupportedContent" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse justify-content-end"
					id="navbarSupportedContent">
					<ul class="navbar-nav mb-5" id="logout-menu">
						<li class="nav-item">
							<a class="nav-link" aria-current="page"
							href="${root}/board/list">공지사항</a>
						</li>
					</ul>
				</div>
		</c:if>

		<c:if test="${!empty userinfo}">
           <ul class="navbar-nav" id="login-menu">
              <li class="nav-item" style="padding-right: 30px">
                <div class="dropdown">
                  <button type="button" class="nav-link border-0 bg-white dropdown-toggle" data-bs-toggle="dropdown")>관심지역</button>
                  <ul class="dropdown-menu">
                    <li>
                      <a
                        class="dropdown-item"
                        href="#"
                        onclick='sendRequest("sido-modal", "*00000000");'
                        data-bs-toggle="modal"
                        data-bs-target="#interestingEnrollModal"
                        class="top-button"
                        >관심지역 등록</a
                      >
                    </li>
                    <li><a class="dropdown-item" href="${root}/info/view">관심지역 정보</a></li>
                  </ul>
                </div>
              </li>
              <li class="nav-item" style="padding-right: 30px">
                <a class="nav-link" aria-current="page" href="${root}/board/list">공지사항</a>
              </li>
              <li class="nav-item" style="padding-right: 30px">
                <a class="nav-link" aria-current="page" href="${root}/apt/mvRegist">아파트 관리</a>
              </li>
           </ul>
        </c:if>
     </div>
   </nav>
   <div class="modal" id="interestingEnrollModal">
     <div class="modal-dialog">
        <div class="modal-content">
          <!-- Modal Header -->
          <div class="modal-header">
            <h4 class="modal-title">관심지역 등록</h4>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
          </div>

          <!-- Modal body -->
          <div class="modal-body">
            <form method="POST" action="${root}/info">
            <input type="hidden" name="act" value="registArea">
              <div class="content-search">
                <div class="container">
                  <div class="row col-md-12 justify-content-center mb-2">
                    <div class="form-group col-md-4">
                      <select class="form-select bg-secondary text-light" id="sido-modal" name="sido">
                        <option value="">시도선택</option>
                      </select>
                    </div>
                    <div class="form-group col-md-4">
                      <select class="form-select bg-secondary text-light" id="gugun-modal" name="gugun">
                        <option value="">구군선택</option>
                      </select>
                    </div>
                    <div class="form-group col-md-4">
                      <select class="form-select bg-secondary text-light" id="dong-modal" name="dong">
                        <option value="">동선택</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div>
	          <!-- Modal footer -->
	          <div class="modal-footer">
	            <button type="submit" class="btn btn-primary" >등록</button>
	            <button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
	          </div>
            </form>
          </div>
        </div>
      </div>
    </div>

	<script>
	document.querySelector("#btn-logout").addEventListener("click", function () {
        location.href = "${root}/user/logout";
      });

      // 시도가 바뀌면 구군정보 얻기.
      document.querySelector("#sido-modal").addEventListener("change", function () {
        if (this[this.selectedIndex].value) {
          let regcode = this[this.selectedIndex].value.substr(0, 2) + "*00000";
          sendRequest("gugun-modal", regcode);
        } else {
          initOption("gugun-modal");
          initOption("dong-modal");
        }
      });

      // 구군이 바뀌면 동정보 얻기.
      document.querySelector("#gugun-modal").addEventListener("change", function () {
        if (this[this.selectedIndex].value) {
          let regcode = this[this.selectedIndex].value.substr(0, 5) + "*";
          sendRequest("dong-modal", regcode);
        } else {
          initOption("dong-modal");
        }
      });

	</script>

