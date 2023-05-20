<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/include/head.jsp"%>
</head>

<body>
<%@ include file="/include/nav.jsp"%>
<div class="container">
	<h2 class="mt-3">선택한 공지사항</h2>

	<form id="form-modify" method="post" action="${root}/board">
		<input type="hidden" id="nact" name="act" value="modify">
		<div class="mb-3 mt-3">
			<label for="desc_title" class="form-label">제목</label> <input
				type="text" class="form-control" id="desc_title" name="subject"
				value="${param.subject}" />
		</div>
		<div class="mb-3">
			<label for="desc_content" class="form-label">내용</label>
			<textarea class="form-control" id="desc_content" name="content">${param.content}</textarea>
		</div>
		<div class="mb-3">
			<label for="desc_writer" class="form-label">작성자</label> <input
				class="form-control" id="desc_writer" readonly
				value="${userinfo.getUserName() }"></input>
		</div>
			<input type="hidden" id="articleNo" name="articleNo" value="${param.articleNo}"> 
			<input type="hidden" id="npgno" name="pgno" value="${param.pgno}"> 
			<input type="hidden" id="nkey" name="key" value="${param.key}"> 
			<input type="hidden" id="nword" name="word" value="${param.word}">
		<button type="button" class="btn btn-success modal__updateBtn"
			id="modify-btn">저장</button>
	</form>
</div>
<%@ include file="/include/footer.jsp"%>

<script>
      document.querySelector("#modify-btn").addEventListener("click", function () {
    	  let form = document.querySelector("#form-modify");
    	  
    	  form.setAttribute("action", "${root}/board");
          form.submit();
      });
    </script>




<script src="../assets/js/noticemodal.js">
    </script>
<script>
      function addInterestedArea() {
        // 기존 목록 있으면 저장하기
        var interestedAreas = localStorage.getItem("interetedAreas");

        if (interestedAreas !== null) {
          interestedAreas = JSON.parse(interestedAreas);
        } else {
          interestedAreas = [];
        }

        let sidoSel = document.querySelector("#sido-modal");
        let sidoCode = sidoSel[sidoSel.selectedIndex].value.substr(0, 2) + "*00000";
        let sidoName = sidoSel[sidoSel.selectedIndex].text;
        let gugunSel = document.querySelector("#gugun-modal");
        let gugunCode = gugunSel[gugunSel.selectedIndex].value.substr(2, 5);
        let gugunName = gugunSel[gugunSel.selectedIndex].text;
        let dongSel = document.querySelector("#dong-modal");
        let dongCode = dongSel[dongSel.selectedIndex].value.substr(7, 3);
        let dongName = dongSel[dongSel.selectedIndex].text;

        for (let index = interestedAreas.length - 1; index >= 0; index--) {
          let area = interestedAreas[index];
          if (sidoCode === area.sido && gugunCode === area.gugun && dongCode === area.dong) {
            alert("이미 지정되었습니다.");
            return;
          }
        }

        if (interestedAreas.length == 0) {
          localStorage.setItem("interetedMainArea", sidoName + " " + gugunName + " " + dongName);
        }

        var interestedArea = {
          seq: interestedAreas.length,
          sido: sidoCode,
          sidoName: sidoName,
          gugun: gugunCode,
          gugunName: gugunName,
          dong: dongCode,
          dongName: dongName,
        };

        interestedAreas.push(interestedArea);
        localStorage.setItem("interetedAreas", JSON.stringify(interestedAreas));
        alert("관심지역으로 등록하였습니다.");
        window.location.reload();
        // let areas = localStorage.getItem("interetedAreas");
      }
    </script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js">
</script>
<%@ include file="/include/footer.jsp" %>
