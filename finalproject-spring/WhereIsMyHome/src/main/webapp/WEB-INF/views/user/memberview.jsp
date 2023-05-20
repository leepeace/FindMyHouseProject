<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/include/head.jsp" %>
</head>
<body>
<%@ include file="/include/nav.jsp" %>
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-lg-8 col-md-10 col-sm-12">
				<h2 class="my-3 py-3 shadow-sm bg-light text-center">
					<mark class="orange">내 정보</mark>
				</h2>
			</div>
			<div class="col-lg-8 col-md-10 col-sm-12">
				<form action="">
					<div class="mb-3">
						<label for="username" class="form-label">이름 : </label> <span>${userinfo.userName}</span>
					</div>
					<div class="mb-3">
						<label for="userid" class="form-label">아이디 : </label> <input
							type="text" name="userid" value="${userinfo.userId}"></input>
					</div>
					<div class="mb-3">
						<label for="userpwd" class="form-label">비밀번호 : </label> <span>${userinfo.userPwd}</span>
					</div>
					<div class="mb-3">
						<label for="emailid" class="form-label">이메일 : </label> <span>${userinfo.emailId}</span>@<span>${userinfo.emailDomain}</span>
					</div>
					<div class="col-auto text-center">
						<button type="button" id="btn-modify"
							class="btn btn-outline-danger mb-3 ms-1">
							<a class="nav-link" href="${root}/user/modify.jsp">수정</a>
						</button>
						<button type="button" id="btn-ok"
							class="btn btn-outline-danger mb-3 ms-1">
							<a class="nav-link" href="${root}/user/index.jsp">확인</a>
						</button>
						<button type="button" id="btn-del"
							class="btn btn-outline-danger mb-3 ms-1">
							<a class="nav-link"
								href="${root}/user?act=del&userid=${userinfo.userId}">탈퇴</a>
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>

<%@ include file="/include/footer.jsp"%>