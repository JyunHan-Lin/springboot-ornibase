<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="icon" type="image/png" href="/images/icon.png">
		<title>${resultTitle}</title>
		<link rel="stylesheet" href="/css/basic.css">
		<link rel="stylesheet" href="/css/common.css">
		<link rel="stylesheet" href="/css/user.css">	
	</head>
	<body class="view fontstyle">
		<!-- menu bar include -->
		<%@ include file="/WEB-INF/view/menu-pre.jspf" %>							
		<div class="container">			
		  <div class="content-wrapper">
	 			<fieldset class="index-message">
					<!-- content -->
					<div>
						<h2 class="h2">${resultMessage}</h2>
						<a href="/ornibase">回到首頁</a>
					</div>
		 		</fieldset>
	 		</div>
	 	</div>
	</body>
</html>