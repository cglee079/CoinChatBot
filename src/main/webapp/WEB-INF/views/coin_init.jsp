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

	function usd2krwInit() {
		var question = "정말로 초기화 하시겠습니까?";
		if(confirm(question)){
			$.ajax({
				type : "POST",
				url : getContextPath() + "/usd2krwInit.do",
				data : {
					"usd2krw" : $(".usd2krw").val()
				},
				dataType : 'JSON',
				async : false,
				success : function(result) {
					alert("초기화 완료하였습니다");
					load();
				},
				error : function(e) {
					alert("초기화 실패하였습니다");
				}
			});
		}
	}
	
	function load() {
		$.ajax({
			type : "POST",
			url : getContextPath() + "/load.do",
			dataType : 'JSON',
			async : false,
			success : function(data) {
				var usd2krw = data["usd2krw"];
				var wrapper = $(".coin-value-list"); 
				wrapper.empty();
				if(usd2krw){
					$("<h1>", { text : "환율"}).appendTo(wrapper);
					$("<div>", { text : usd2krw}).appendTo(wrapper);						
				}
				alert("로드 성공하였습니다");
			},
			error : function(e) {
				console.log(e);
				alert("로드 실패하였습니다");
			}
		});
	}
</script>
</head>
<body>
	<div>
	<h1>환율 초기화</h1>
		<div>
			<input type="number" class="usd2krw">
		</div>
		<div>
		<a class="btn-send" onclick="usd2krwInit()">전송</a>
		</div>	
	</div>
	
	<hr>
	
	<div>
	
	</div>	
		<h1>코인값  불러오기</h1>
		<a class="btn-send" onclick="load()">불러오기</a>
		<div class="coin-value-list">
		</div>
	</body>
</html>
