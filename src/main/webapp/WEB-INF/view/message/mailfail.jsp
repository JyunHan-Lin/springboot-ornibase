<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>OrniBase</title>
		<link rel="icon" type="image/png" href="/images/icon.png">
		<link rel="stylesheet" href="/css/basic.css">
		<link rel="stylesheet" href="/css/message.css">	
	</head>
	<body class="view fontstyle">
		<!-- menu bar include -->
		<%@ include file="/WEB-INF/view/menu-pre.jspf" %>	
		<div class="blur-overlay">
			<fieldset class="user">
				<!-- content -->
				<div>
					<h2 class="h2">認證失敗...請重新註冊</h2>	
					<a href="/register">回註冊頁</a>
				</div>
			</fieldset>
		</div>
	</body>
</html>