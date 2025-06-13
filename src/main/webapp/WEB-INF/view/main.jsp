<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">		
		<link rel="stylesheet" href="/css/basic.css">
		<link rel="stylesheet" href="/css/common.css">		
		<link rel="stylesheet" href="/css/main.css">
		<title>OrniBase</title>
		<link rel="icon" type="image/png" href="/images/icon.png">
	</head>
	<body class="fontstyle">
	
		<!-- menu bar include -->
		<%@ include file="/WEB-INF/view/menu-login.jspf" %>
										
		<!-- 導覽列下方內容 -->
		  <div class="image-area">
		    <img src="/images/index.jpg" alt="展示照片">
		  </div>
		
		<div class="main-content">
		  <div class="latest-header">
			<h2>最新上架</h2>
			<div class="search-container">
			  <form class="search-form" method="get" action="/ornibase/discuss/search">
			    <input type="text" name="keyword" placeholder="輸入關鍵字搜尋討論串..." class="search-input"/>
			    <button type="submit" class="search-button">搜尋</button>
			   </form>
			 </div>
		   </div>	
		</div>
		<div class="video-section">
			<c:choose>			  	
		  		<c:when test="${not empty discussList}">
		    		<c:forEach var="discussDTO" items="${discussList}">
		      		<!-- 卡片區 -->
		    		</c:forEach>
		  		</c:when>
			  		<c:otherwise>
			    	<p style="color: gray;">目前尚無筆記本，請先建立一個。</p>
		  		</c:otherwise>
			</c:choose>			
		</div>
	</body>
</html>