<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@include file = "../included/included_head.jsp" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/suggest/suggest-list.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/suggest/suggest-list.js"></script>
</head>
<body>
	<div class="wrapper">
		<%@include file = "../included/included_nav.jsp" %>
		<div class="wp-suggest-list">
			<div class="wrap-dg">
				<table id="dg" style="width:100%; height:100%;"></table>
			</div>
		</div>
	</div>
</body>
</html>