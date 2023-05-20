<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp"%>
	<div class="container mt-3">
		<h2>공지사항</h2>
		<div style="display: flex;" class="justify-content-center">
			<div class="col-md-7 offset-1">
				<form class="d-flex" id="form-search" action="">
					<select class="form-select form-select-sm ms-5 me-1 w-50"
						name="key" aria-label="검색조건">
						<option value="" selected>검색조건</option>
						<option value="subject">제목</option>
						<option value="userid">작성자</option>
					</select>
					<div class="input-group input-group-sm">
						<input type="text" class="form-control" name="word"
							placeholder="검색어..." />
						<button id="btn-search" class="btn btn-dark" type="button">검색</button>
					</div>
				</form>
			</div>
		</div>

		<div style="display: flex;" class="justify-content-end">
			<c:if test="${userinfo.userId == 'admin'}">
				<button type="button" class="btn btn-primary mb-"
					id="btn-mv-register">새로 등록하기</button>
			</c:if>
		</div>
		<table class="table table-striped">
			<thead>
				<tr>
					<th class="col-2 text-center">번호</th>
					<th class="col-4 text-center">제목</th>
					<th class="col-4 text-center">작성자</th>
					<th class="col-2 text-center">조회 수</th>
				</tr>
			</thead>
			<tbody class="table notice__table">
				<c:forEach var="article" items="${articles}">
					<tr class="text-center">
						<th scope="row">${article.articleNo}</th>
						<td class="text-start"><a
							href="${root}/board?act=view&articleno=${article.articleNo}"
							class="article-title link-dark" data-no="${article.articleNo}"
							style="text-decoration: none">
								${article.subject} </a></td>
						<td>${article.userId}</td>
						<td>${article.hit}</td>
						<td>${article.registerTime}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="row">
		<ul class="pagination justify-content-center">
			<li class="page-item"><a class="page-link" href="#">이전</a></li>
			<li class="page-item active"><a class="page-link" href="#">1</a></li>
			<li class="page-item"><a class="page-link" href="#">2</a></li>
			<li class="page-item"><a class="page-link" href="#">3</a></li>
			<li class="page-item"><a class="page-link" href="#">4</a></li>
			<li class="page-item"><a class="page-link" href="#">5</a></li>
			<li class="page-item"><a class="page-link" href="#">다음</a></li>
		</ul>
	</div>
<form id="form-no-param" method="get" action="${root}/board">
	<input type="hidden" id="act" name="act" value="view"> <input
		type="hidden" id="pgno" name="pgno" value="${param.pgno}"> <input
		type="hidden" id="key" name="key" value="${param.key}"> <input
		type="hidden" id="word" name="word" value="${param.word}"> <input
		type="hidden" id="articleno" name="articleno" value="">
</form>

<script src="../assets/js/notice.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
     let titles = document.querySelectorAll(".article-title");
     titles.forEach(function (title) {
       title.addEventListener("click", function () {
      	 	document.querySelector("#articleno").value = this.getAttribute("data-no");
      	 	document.querySelector("#form-no-param").submit();
       });
     });

     document.querySelector("#btn-mv-register").addEventListener("click", function () {
       location.href = "${root}/notice/makenotice.jsp";
     });
     
     document.querySelector("#btn-search").addEventListener("click", function () {
   	  let form = document.querySelector("#form-search");
         form.setAttribute("action", "${root}/notice");
         form.submit();
     });
</script>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
