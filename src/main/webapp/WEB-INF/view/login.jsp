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
	</head>
	<body class="fontstyle">
		<!-- menu bar include -->
		<%@ include file="/WEB-INF/view/menu-pre.jspf" %>	
									
		<div class="container">
			<div class="left-section-login"></div>
			
			<div class="right-section">
				  <div class="content-wrapper">
					<h1 class="h1">OrniBase</h1>
		 			<fieldset class="index">
				 		<legend class="h2">&nbsp;登入或<a href="/register">創建帳號&nbsp;</a></legend>
			 				<form class="user" method="POST" action="/login">
						 		<input type="text" name="username" placeholder="請輸入帳號" required/><p/>
						 		<input type="password" name="password" placeholder="請輸入密碼" required/><p/>
						 
							 	<div class="button-group">
				  					<input type="text" name="authcode" placeholder="請輸入驗證碼" required>
				  					<img src="/user/authcode" title="驗證碼" alt="驗證碼" />
								</div>
							
								<script>
								  document.querySelector(".button-group img").addEventListener("click", function() {
								    this.src = "/user/authcode?" + new Date().getTime(); // 防止快取
								  });
								</script>
								
								<div class="button-group">
									<button type="reset" >重置</button>
							 		<button type="submit">登入</button>
								</div>
			 				</form>
			 		</fieldset>
				</div>
			</div>
		</div>
	</body>
</html>