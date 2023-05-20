<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/include/head.jsp"%>
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=8493125cc0433687fbab8e3ac6703ea0&libraries=services"></script>
</head>
<%@ include file="/include/nav.jsp"%>
<body>

	<%-- 페이지만의 내용 --%>
	<div class="container p-4" style="text-align: center">관심 지역 이름</div>

	<div class="container md-6">
		선별진료소
		<table class="table table-striped">
			<thead>
				<tr>
					<th class="col-2 text-center">번호</th>
					<th class="col-4 text-center">이름</th>
					<th class="col-4 text-center">주소</th>
					<th class="col-2 text-center">전화번호</th>
				</tr>
			</thead>
			<tbody class="table notice__table">
				<c:set var="cnt" value="0"/>
				<c:forEach var="clinic" items="${clinics}">
				<c:set var="cnt" value="${cnt+1}"/>
					<tr class="text-center">
						<th scope="row">${cnt}</th>
						<td>${clinic.hospital_name}</td>
						<td>${clinic.hospital_address}</td>
						<td>${clinic.representative_phone_number}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		상권 정보
		<table class="table table-striped">
			<thead>
				<tr>
					<th class="col-2 text-center">번호</th>
					<th class="col-4 text-center">이름</th>
					<th class="col-4 text-center">주소</th>
					<th class="col-2 text-center">분류</th>
				</tr>
			</thead>
			<tbody class="table notice__table">
				<c:set var="cnt" value="0"/>
				<c:forEach var="commercialArea" items="${commercialAreas}">
					<c:set var="cnt" value="${cnt+1}"/>
					<tr class="text-center">
						<th scope="row">${cnt}</th>
						<td>${commercialArea.store_name}</td>
						<td>${commercialArea.address}</td>
						<td>${commercialArea.store_type_name_detail}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		숙박
		<table class="table table-striped">
			<thead>
				<tr>
					<th class="col-2 text-center">번호</th>
					<th class="col-4 text-center">이름</th>
					<th class="col-4 text-center">주소</th>
					<th class="col-2 text-center">전화번호</th>
				</tr>
			</thead>
			<tbody class="table notice__table">
				<c:set var="cnt" value="0"/>
				<c:forEach var="acc" items="${accs}">
					<c:set var="cnt" value="${cnt+1}"/>
					<tr class="text-center">
						<th scope="row">${cnt}</th>
						<td>${acc.name}</td>
						<td>${acc.address}</td>
						<td>${acc.phone_number}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		병원
		<table class="table table-striped">
			<thead>
				<tr>
					<th class="col-2 text-center">번호</th>
					<th class="col-4 text-center">이름</th>
					<th class="col-4 text-center">주소</th>
					<th class="col-2 text-center">전화번호</th>
				</tr>
			</thead>
			<tbody class="table notice__table">
				<c:set var="cnt" value="0"/>
				<c:forEach var="hospital" items="${hospitals}">
					<c:set var="cnt" value="${cnt+1}"/>
					<tr class="text-center">
						<th scope="row">${cnt}</th>
						<td>${hospital.hospital_name}</td>
						<td>${hospital.hospital_address}</td>
						<td>${hospital.phone_number}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<%@ include file="/include/footer.jsp"%>