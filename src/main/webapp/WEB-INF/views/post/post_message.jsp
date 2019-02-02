<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@include file = "../included/included_head.jsp" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/post/post-message.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/post/post-message.js"></script>
<script>
var data = ${data};
var count = ${count};
var caseId = '${caseId}';
</script>
</head>
<body>
	<div class="wrapper">
		<%@include file = "../included/included_nav.jsp" %>
		
		<div class="wp-post">
			<div class="wrap-message-area">
				<div class="wrap-search-opt">
					<table class="search-opt">
						<tr>
						<td>총 사용자</td>
						<td><strong><c:out value="${count}"/></strong> 명</td>
						</tr>
						
						<tr>
						<td>코인ID</td>
						<td><input type="text" id="coinId" name="coinId" disabled="disabled" value="<c:out value='${coinId}'/>"/></td>
						</tr>
						
						<tr>
						<td>사용자 ID</td>
						<td><input type="text" id="userId" name="userId" disabled="disabled" value="<c:out value='${userId}'/>"/></td>
						</tr>
						
						<tr>
						<td>사용자 이름</td>
						<td><input type="text" id="username" name="username" disabled="disabled" value="<c:out value='${username}'/>"/></td>
						</tr>
						
						<tr>
						<td>거래소</td>
						<td> 
							<select id="marketId" name="marketId" disabled="disabled" >
							</select>
						</td>
						</tr>
						
						<tr>
						<td>사용자 유효</td>
						<td> 
							<select id="enabled" name="enabled" disabled="disabled" >
							</select>
						</td>
						</tr>
						
						<tr>				
						<td>시작일</td>
						<td> <input type="text" id="openDate" name="openDate" disabled="disabled" value="<c:out value='${openDate}'/>"/> </td>
						</tr>
					</table>
				</div>
				
				<div>
					<textarea id="message-area" class="message-area"></textarea>
				</div>
		
				<div>
					<a class="btn send" onclick="sendMessage()">전송</a>
				</div>
			
			</div>
		
			<div class="wrap-user-list">
				<table id="dg" style="width:100%; height:100%"></table>
			</div>	
		</div>
	</div>
</body>
</html>