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
		<link rel="stylesheet" href="/css/discuss-list.css">
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
			<h2>個人書架</h2>
		   </div>	
			<!-- 列表 -->
			<div class="video-list-container">
				<fieldset>
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
		</div>	
	</body>
</html>
