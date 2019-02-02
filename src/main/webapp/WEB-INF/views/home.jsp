<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@include file = "included/included_head.jsp" %>
<script>
$(document).ready(function(){
	onClickMenu(0);
})
</script>

<style>
.wp-home {
	height : calc(100% - var(--nav-height));
}

.wrap-client-cnt{
	display: flex;
	align-items : center;
	justify-content : center;
	
	font-size: 10rem;
	font-weight: bold;
	height: 100%;
}


</style>
</head>
<body>
	<div class="wrapper">
		<%@include file = "included/included_nav.jsp" %>
		
		<div class="wp-home">
			<div class="wrap-client-cnt">
				<c:out value="${user}"/> 명
			</div>
		</div>
	</div>
</body>
</html>