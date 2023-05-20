<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include/head.jsp"%>

<div class="container">
	<form id="form-register" method="POST" action="">
		<input type="hidden" name="act" value="write">
		<div class="mb-3 mt-3">
			<label for="subject" class="form-label">제목</label> <input type="text"
				class="form-control" id="subject" placeholder="제목을 입력하세요."
				name="subject">
		</div>
		<div class="mb-3">
			<label for="content" class="form-label">내용</label> <input type="text"
				class="form-control" id="content" placeholder="내용을 입력하세요."
				name="content">
		</div>
		<button type="button" class="btn btn-primary" id="btn-register">등록하기</button>
	</form>
</div>
<%@ include file="/include/footer.jsp"%>
<script src="../assets/js/makenotice.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
      document.querySelector("#btn-register").addEventListener("click", function () {
        if (!document.querySelector("#subject").value) {
          alert("제목 입력!!");
          return;
        } else if (!document.querySelector("#content").value) {
          alert("내용 입력!!");
          return;
        } else {
          let form = document.querySelector("#form-register");
          form.setAttribute("action", "${root}/board");
          form.submit();
        }
      });
      
      document.querySelector("#btn-list").addEventListener("click", function () {
    	if(confirm("취소를 하시면 작성한 글은 삭제됩니다.\n취소하시겠습니까?")) {

   	    }
      });
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
      }
    </script>
</body>
</html>
