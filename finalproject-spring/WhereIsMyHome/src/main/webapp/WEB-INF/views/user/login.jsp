<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<c:if test="${cookie.ssafy_id.value ne null}">
	<c:set var="idck" value=" checked"/>
	<c:set var="saveid" value="${cookie.ssafy_id.value}"/>
</c:if>

<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="../include/head.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous" />
<link href="${root}/assets/css/app.css" rel="stylesheet" />
<title>SSAFY</title>
</head>

<body>
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-lg-8 col-md-10 col-sm-12">
				<h2 class="my-3 py-3 shadow-sm bg-light text-center">
					<mark class="orange">로그인</mark>
				</h2>
			</div>
			<div class="col-lg-8 col-md-10 col-sm-12">
				<form id="form-login" method="POST" action="">
					<div class="form-check mb-3 float-end">
						<input class="form-check-input" type="checkbox" value="ok"
							id="saveid" name="saveid" ${idck} /> <label
							class="form-check-label" for="saveid"> 아이디저장 </label>
					</div>
					<div class="mb-3">
						<label for="userid" class="form-label">아이디 : </label> <input
							type="text" class="form-control" id="userid" name="userid"
							placeholder="아이디..." value="${saveid}" />
					</div>
					<div class="mb-3">
						<label for="userpwd" class="form-label">비밀번호 : </label> <input
							type="password" class="form-control" id="userpwd" name="userpwd"
							placeholder="비밀번호..." />
					</div>
					<div class="text-danger mb-2"></div>
					<div class="col-auto text-center">
						<button type="button" id="btn-login"
							class="btn btn-outline-primary mb-3">로그인</button>
						<button type="button" id="btn-mv-join"
							class="btn btn-outline-success mb-3">회원가입</button>
						<button type="button" id="btn-search-pwd"
							class="btn btn-outline-primary mb-3">비밀번호 찾기</button>
					</div>
				</form>
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
							href="${root}/board?act=list">공지사항</a>
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
                    <li><a class="dropdown-item" href="${root}/info?act=view">관심지역 정보</a></li>
                  </ul>
                </div>
              </li>
              <li class="nav-item" style="padding-right: 30px">
                <a class="nav-link" aria-current="page" href="${root}/board?act=list">공지사항</a>
              </li>
              <li class="nav-item" style="padding-right: 30px">
                <a class="nav-link" aria-current="page" href="${root}/apt?act=mvRegist">아파트 관리</a>
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
        location.href = "${root}/user?act=logout";
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
          let form = document.querySelector("#form-login");
          form.setAttribute("action", "${root}/user/login");
          form.submit();
        }
      });

	</script>

