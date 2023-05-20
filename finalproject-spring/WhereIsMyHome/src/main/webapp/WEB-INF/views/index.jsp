<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=8493125cc0433687fbab8e3ac6703ea0&libraries=services"></script>
</head>

<body>
	<%@ include file="/WEB-INF/views/include/nav.jsp"%>

	<%-- 페이지만의 내용 --%>

	<%@ include file="/WEB-INF/views/news.jsp" %>
	<div class="container p-4"></div>
	<%@ include file="/WEB-INF/views/map.jsp" %>
	<%@ include file="/WEB-INF/views/include/footer.jsp"%>