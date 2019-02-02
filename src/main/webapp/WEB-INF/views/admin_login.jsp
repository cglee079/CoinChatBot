<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<style>
	html, body, .wrapper{
		height : 100%;
	}
	
	.wrap-form{
		height : 100%;
		display : flex;
		justify-content : center;
		align-items : center;
	}
	
	.title{
		color : #24A;
	}
	
	.tb-admin-login{
		margin : 0.2rem;
	}
	
	.tb-admin-login tr td{
		padding : 0.3rem;
	}
</style>
</head>

<body>
	<div class="wrapper">
		<div class="wrap-form">
			<form id="form-admin-login" action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
				<h1 class="title">Coin.CHATBOT</h1>
				
				<table class="tb-admin-login">
				<tr>
				<td>USERNAME</td>
				<td><input type="text" name="username" /></td>
				</tr>
				<tr>
				<td>PASSWORD</td>
				<td><input type="password" name="password"/></td>
				</tr>
				<tr>
				<td></td>
				<td style="text-align: right;"  ><input type="submit" value="로그인"/></td>
				</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>