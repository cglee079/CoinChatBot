<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@include file = "../included/included_head.jsp" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/message/message-stat.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/message/message-stat.js"></script>
<script>
var totalMsgCnt 		= ${totalMsgCnt};
var coins 				= ${coins};
var msgCntByDay 		= ${msgCntByDay};
var msgCntByMonth 		= ${msgCntByMonth};
var msgUserCntByDay 	= ${msgUserCntByDay};
</script>
</head>
<body>
	<div class="wrapper">
		<%@include file = "../included/included_nav.jsp" %>
		
		<div class="wp-message-stat">
			<div class="wrap-msg-datagrid">
				<div class="msg-datagrid msg-usercnt-day">
					<table id="dg3" style="width:100%; height:100%"></table>
				</div>
				
				<div class="msg-datagrid msg-cnt-day">
					<table id="dg1" style="width:100%; height:100%"></table>
				</div>
				
				<div class="msg-datagrid msg-cnt-month">
					<table id="dg2" style="width:100%; height:100%"></table>
				</div>
			</div>
			<div class="wrap-msg-menu">
				<a class="btn msg-menu-clear" onClick="doClearMyMessage()">내 메세지 삭제</a>
			</div>
		</div>
	</div>
</body>
</html>