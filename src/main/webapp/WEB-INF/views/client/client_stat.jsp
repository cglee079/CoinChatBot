<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@include file = "../included/included_head.jsp" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/client/client-stat.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/client/client-stat.js"></script>
<script>
var newClientByDay = ${newClientByDay};
var newClientByMonth = ${newClientByMonth};
</script>
</head>
<body>
	<div class="wrapper">
		<%@include file = "../included/included_nav.jsp" %>
		
		<div class="wp-client-stat">
			<div class="wrap-client-info">
				<div class="info-block">
					<div class='client-info'>
						<div class="client-info-title">총 대화방</div>
						<div class="client-info-count"><c:out value="${totalChat}"></c:out> 방</div>
					</div>
					
					<div class='client-info'>
						<div class="client-info-title"><strong>유효한 대화방</strong></div>
						<div class="client-info-count"><strong><c:out value="${validChat}"></c:out> 방</strong></div>
					</div>
					
					<div class='client-info'>
						<div class="client-info-title">무효한 대화방</div>
						<div class="client-info-count"><c:out value="${invalidChat}"></c:out> 방</div>
					</div>
				</div>
				
				<div class="info-block">
					<div class='client-info'>
						<div class="client-info-title">총 사용자</div>
						<div class="client-info-count"><c:out value="${totalUser}"></c:out> 명</div>
					</div>
					
					<div class='client-info'>
						<div class="client-info-title"><strong>유효한 사용자</strong></div>
						<div class="client-info-count"><strong><c:out value="${validUser}"></c:out> 명</strong></div>
					</div>
					
					<div class='client-info'>
						<div class="client-info-title">무효한 사용자</div>
						<div class="client-info-count"><c:out value="${totalUser - validUser}"></c:out> 명</div>
					</div>
				</div>
				<div class="info-block"></div>
			</div> 
			
			<div class="wrap-client-data">
				<div class="client-data-day">
					<table id="dg1" style="width:100%; height:100%"></table>
				</div>
				
				<div class="client-data-month">
					<table id="dg2" style="width:100%; height:100%"></table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>