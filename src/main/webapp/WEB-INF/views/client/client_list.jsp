<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@include file = "../included/included_head.jsp" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/client/client-list.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/client/client-list.js"></script>
<script>
	$(document).ready(function(){
		$("#marketId").val('${marketId}');
		$("#enabled").val('${enabled}');
		$("#lang").val('${language}');
	});
</script>
</head>
<body>
	<div class="wrapper">	
		<%@include file = "../included/included_nav.jsp" %>
		
		<div class="wp-client-list">
			<div class="wrap-search-opt">
				<table class="search-opt">
					<tr>
					<td>코인ID</td>
					<td>
					<select id="coinId" name="coinId">
						<option value="">전체</option>
						<c:forEach var="entry" items="${coin}">
					 	<option value="${entry.key}">${entry.key}</option>
						</c:forEach>
					</select>
					</td>
					
					<td>사용자 ID</td>
					<td><input type="text" id="userId" name="userId" value="${userId}" autocomplete="off"/></td> 
					
					<td>사용자 이름</td>
					<td><input type="text" id="username" name="username" value="${username}" autocomplete="off"/></td>
					</tr>
					
					<tr>
					<td>거래소</td>
					<td> 
					<select id="marketId" name="marketId">
						<option value="">전체</option>
						<c:forEach var="entry" items="${market}">
					 	<option value="${entry.value.code}">${entry.key}</option>
						</c:forEach>
					</select>
					</td>
					
					<td>사용자 유효</td>
					<td> 
					<select id="enabled" name="enabled">
						<option value="">전체</option>
						<option value="1">Y</option>
						<option value="0">N</option>
					</select>
					</td>
					
					<td>언어</td>
					<td> 
					<select id="language" name="language">
						<option value="">전체</option>
						<c:forEach var="entry" items="${lang}">
					 	<option value="${entry.key}">${entry.key}</option>
						</c:forEach>
					</select>
					</td>
					</tr>
					
					<tr>
					<td>시작일</td>
					<td><input type="text" id="openDate" name="openDate" value="<c:out value='${openDate}'/>"/></td>
					
					<td>메뉴</td>
					<td>
					<select id="stateId" name="stateId">
						<option value="">전체</option>
						<c:forEach var="entry" items="${menuState}">
					 	<option value="${entry.value.code}">${entry.key}</option>
						</c:forEach>
					</select>
					</td>
					</tr>
					
					<tr>
					<td>최종메세지</td>
					<td><input type="text" id="msgDate1" name="msgDate1" value="<c:out value='${msgDate1}'/>"/></td>
					<td><input type="text" id="msgDate2" name="msgDate2" value="<c:out value='${msgDate2}'/>"/></td>
					</tr>
				</table>
			
				<div class="search-menu">
					<a class="btn" onclick="sendMessageToAll()" >전체 메세지</a>
					<a class="btn" onclick="searchClient()">검색</a>
				</div>
			</div>
		
			<div class="wrap-dg">
				<table id="dg" style="width:100%; height:100%;"></table>
			</div>
		</div>
	</div>
</body>
</html>