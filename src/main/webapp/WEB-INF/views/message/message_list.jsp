<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@include file = "../included/included_head.jsp" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/message/message-list.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/message/message-list.js"></script>
</head>
<body>
	<div class="wrapper">
		<%@include file = "../included/included_nav.jsp" %>
		
		<div class="wp-message-list">
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
					<td><input type="text" id="userId" name="userId" value="<c:out value='${userId}'/>"/></td>
					
					<td>사용자 이름</td>
					<td><input type="text" id="username" name="username" value="<c:out value='${username}'/>"/></td>
					</tr>
					
					<tr>
					<td>내용</td>
					<td><input type="text" id="contents" name="contents" value="<c:out value='${contents}'/>"/></td>
					<td>시작날짜</td>
					<td><input type="text" id="startDate" name="startDate" value="<c:out value='${startDate}'/>"/></td>
					<td>종료날짜</td>
					<td><input type="text" id="endDate" name="endDate" value="<c:out value='${endDate}'/>"/></td>
					</tr>
				</table>
				
				<div class="search-menu">
					<a class="btn" onclick="doSearch()">검색</a>
				</div>
				
			</div>
		
		<div class="wrap-dg">
		<table id="dg" style="width:100%; height:100%;"></table>
		</div>
		</div>
	</div>
</body>
</html>