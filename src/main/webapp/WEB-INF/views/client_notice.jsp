<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js"></script>
<style>
	.btn-send{
		cursor: pointer;
	}
</style>
<script>
	/* context Path */
	var contextPath = "${pageContext.request.contextPath}";
	function getContextPath() {
		return contextPath;
	}

	function send() {
		var question = "정말로 전송하시겠습니까?";
		if(confirm(question)){
			var text = $(".notice-area").val();
			$.ajax({
				type : "POST",
				url : getContextPath() + "/notice.do",
				data : {
					"text" : text
				},
				dataType : 'JSON',
				async : false,
				success : function(result) {
					alert("공지사항 완료하였습니다");
				},
				error : function(e) {
					console.log(e);
					alert("공지사항 실패하였습니다");
				}
			});
		}
	}
</script>
</head>
<body>
	<h1>공지사항 전송</h1>
	<jsp:useBean id="date" class="java.util.Date" />
	<textarea cols="50" rows="20" class="notice-area">
공지사항 #<fmt:formatDate value="${date}" pattern="yyyy-MM-dd HH:mm:ss" />
</textarea>
	<div>
	<a class="btn-send" onclick="send()">전송</a>
	</div>
</body>
</html>
