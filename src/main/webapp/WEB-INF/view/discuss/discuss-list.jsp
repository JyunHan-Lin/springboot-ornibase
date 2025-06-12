<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Jakarta JSTL 表單標籤 -->
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
										
		
		<!-- 列表 -->
		<div class="video-list-container">
			<fieldset>
			<legend class="h2">已建立的筆記本列表</legend>
				<div class="video-list">
					<c:forEach var="discussDTO" items="${discussList}">
					  <div class="video-card">
					    <img src="https://img.youtube.com/vi/${discussDTO.youtubeVideoId}/mqdefault.jpg" alt="YouTube封面" />
					    <div class="video-title">
					      <a href="/ornibase/discuss/${discussDTO.discussId}">
					         ${discussDTO.title}
					      </a>
					    </div>
					    <div class="video-description">${ discussDTO.description }</div>
					  </div>
					</c:forEach>
					
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
			</fieldset>
		</div>			
	</body>
</html>
