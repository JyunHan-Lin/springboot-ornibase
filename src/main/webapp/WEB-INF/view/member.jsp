<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="/css/basic.css">
		<link rel="stylesheet" href="/css/common.css">
		<link rel="stylesheet" href="/css/form.css">
		<title>OrniBase</title>
		<link rel="icon" type="image/png" href="/images/icon.png">
	</head>
	<body class="fontstyle">
		<!-- menu bar include -->
		<%@ include file="/WEB-INF/view/menu-login.jspf" %>
		
		<form method="POST" action="/bbd/codeedit">
 			<fieldset class="discussion-form">
 				<legend class="h2">會員資料變更</legend>
 				
 				<div class="title">密碼變更</div>
 					<p>
 					<div>
					 	<label for="password">輸入舊密碼：</label>
					 	<input type="password" id="oldpassword" name="oldPassword" placeholder="請輸入舊密碼" required>
					 	
					 	<label for="password">輸入新密碼：</label>
					 	<input type="password" id="newpassword1" name="newPassword" placeholder="請輸入新密碼" required>
					 	
					 	<label for="password">再輸入一次新密碼：</label>
					 	<input type="password" id="newpassword2" name="confirmPassword" placeholder="再輸入一次新密碼" required>
					 	<p>
					 	<div class="button-wrapper">
							<button type="reset" >重置</button>
						 	<button type="submit">確認送出</button>
					 	</div>
 					</div>
 			</fieldset>
 		</form>
	</body>
</html>