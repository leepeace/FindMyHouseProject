<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
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
<%@ include file="/include/head.jsp"%>
</head>
<body>
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-lg-8 col-md-10 col-sm-12">
				<h2 class="my-3 py-3 shadow-sm bg-light text-center">
					<mark class="orange">아파트 등록</mark>
				</h2>
			</div>
			<div class="col-lg-8 col-md-10 col-sm-12">
				<form id="form-join" method="POST" action="">
					<input type="hidden" name="act" value="regist" />
					<div class="mb-3">
						<label for="username" class="form-label">건설 년도 : </label> <input
							type="text" class="form-control" id="username" name="build_year"
							placeholder="건설 년도..." />
					</div>
					<div class="mb-3">
						<label for="userid" class="form-label">동 : </label> <input
							type="text" class="form-control" id="userid" name="dong"
							placeholder="동..." />
					</div>
					<div id="idcheck-result"></div>
					<div class="mb-3">
						<label for="userpwd" class="form-label">이름 : </label> <input
							type="text" class="form-control" id="userpwd" name="name"
							placeholder="이름..." />
					</div>
					<div class="mb-3">
						<label for="pwdcheck" class="form-label">면적 : </label> <input
							type="text" class="form-control" id="area" name="area"
							placeholder="면적..." />
					</div>
					<div class="mb-3">
						<label for="pwdcheck" class="form-label">지번 : </label> <input
							type="text" class="form-control" id="jibun" name="jibun"
							placeholder="지번..." />
					</div>
					<div class="mb-3">
						<label for="pwdcheck" class="form-label">동코드 : </label> <input
							type="text" class="form-control" id="regional_code" name="regional_code"
							placeholder="동코드..." />
					</div>
					<div class="col-auto text-center">
						<button type="button" id="btn-join"
							class="btn btn-outline-primary mb-3">회원가입</button>
						<button type="button" class="btn btn-outline-success mb-3">초기화</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
		crossorigin="anonymous"></script>
	<script>
      document.querySelector("#btn-join").addEventListener("click", function () {
          let form = document.querySelector("#form-join");
          form.setAttribute("action", "${root}/apt");
          form.submit();
      });
    </script>
</body>
</html>
