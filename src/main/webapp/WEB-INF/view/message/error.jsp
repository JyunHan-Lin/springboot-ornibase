<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8"
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
		<%@ include file="/WEB-INF/view/menu.jspf" %>	
		<div class="blur-overlay">
			<!-- body content -->
			<div style="padding: 15px">
				<fieldset class="user">
					${ message }
					<%=exception %><p>
					<a href="/bbd">回到首頁</a>
				</fieldset>	
			</div>		
		</div>
	</body>
</html>