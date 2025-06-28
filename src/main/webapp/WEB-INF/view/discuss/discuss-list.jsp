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
		<div class="image-area"></div>
										
		<div class="main-content">		  
		  <!-- 列表 -->
		  <div class="video-list-container">
			  	<div class="latest-header">
					<span class="latest">建立的群組</span>
			  	</div>
		  			<div class="video-list">
					 	<c:choose>			  	
						 	<c:when test="${not empty myDiscussList}">
								<c:forEach var="discussDTO" items="${myDiscussList}">
									 <a href="/ornibase/discuss/${discussDTO.discussId}" class="video-card">
							    		<img src="https://img.youtube.com/vi/${discussDTO.youtubeVideoId}/mqdefault.jpg" alt="YouTube封面" />
					  				
						  				<span class="video-title-row">					  				
							  				<span class="video-title">
							        	 			${discussDTO.title}
							    			</span>
							      				
											<span class="video-meta">
												<span class="emoji">📄</span>
												<span class="count">${behaviorCountMap[discussDTO.discussId]}</span>
											</span>	
						  				</span>
			      					</a>
							 	</c:forEach>
						 	</c:when>
						 	<c:otherwise>
								<p style="color: gray;">目前尚無群組</p>
						 	</c:otherwise>
				    	</c:choose>
			 	</div>
			 	
			  	<div class="latest-header">
					<span class="latest">追蹤名單</span>
			  	</div>
				<div class="video-list">
				 	<c:choose>			  	
					 	<c:when test="${not empty favoriteDiscussList}">
							<c:forEach var="discussDTO" items="${favoriteDiscussList}">
								 <a href="/ornibase/discuss/${discussDTO.discussId}" class="video-card">
						    		<img src="https://img.youtube.com/vi/${discussDTO.youtubeVideoId}/mqdefault.jpg" alt="YouTube封面" />
				  				
					  				<span class="video-title-row">
										<span class="emoji">
											<img src="/images/user.png" width="12px" height="12px">
										</span>
					  					<span class="favorite-count">
											${favoriteCountMap[discussDTO.discussId]}
					  					</span>
					  				
						  				<span class="video-title">
						        	 			${discussDTO.title}
						    			</span>
						      				
										<span class="video-meta">
											<span class="emoji">📄</span>
											<span class="count">${behaviorCountMap[discussDTO.discussId]}</span>
										</span>	
					  				</span>
		      					</a>
						 	</c:forEach>
					 	</c:when>
					 	<c:otherwise>
							<p style="color: gray;">目前尚無收藏筆記本</p>
					 	</c:otherwise>
			    	</c:choose>
			 	</div>
	   		</div>	
		</div>
	</body>
</html>
