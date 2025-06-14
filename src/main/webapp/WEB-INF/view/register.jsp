<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="/css/basic.css">
		<link rel="stylesheet" href="/css/common.css">
		<link rel="stylesheet" href="/css/user.css">
		<title>OrniBase</title>
		<link rel="icon" type="image/png" href="/images/icon.png">
		<script src="/js/register.js"></script>
	</head>
	<body class="fontstyle" >
		<!-- menu bar include -->
		<%@ include file="/WEB-INF/view/menu-pre.jspf" %>	
									
		<div class="container">
			<div class="content-wrapper">
				<h1 class="h1">OrniBase</h1>
					<fieldset class="index">
						<legend class="h2">&nbsp;會員註冊&nbsp;</legend>
							<form class="user" method="POST" action="/register">
								<input type="text" name="username" placeholder="請輸入4位數以上帳號" required/><p>
								<input type="password" name="password" placeholder="請輸入6位數以上密碼" required/><p>
								<input type="password" name="confirmPassword" placeholder="請再輸入一次密碼" required/><p>
								<input type="email" name="email" placeholder="請輸入電子郵件" required/><p>
								<%--
								<button type="submit">寄送驗證碼</button>
								<input type="text" name="passcode" placeholder="請輸入驗證碼" required/><p>										
								--%>
								<div class="button-group">
									<button type="reset">重置</button>
									<button type="submit">傳送</button>
								</div>
								<a href="/login">回登入頁</a>
							</form>
						</fieldset>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>