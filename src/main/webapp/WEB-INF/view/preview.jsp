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
		<script>
    		window.notLoggedIn = ${empty userCert};
  		</script>
		<script src="/js/preview.js"></script>
	</head>
	<body class="fontstyle">
	
		<!-- menu bar include -->
		<%@ include file="/WEB-INF/view/menu-pre.jspf" %>	
		
		<!-- 導覽列下方內容 -->
		<div class="image-area"></div>
		
		<div class="main-content">
		  <div class="latest-header">
			<span class="latest">公開群組</span>
			<div class="search-container">
			  <form class="search-form" method="get" action="/preview/search">
			    <input type="text" name="keyword" placeholder="輸入關鍵字搜尋筆記..." class="search-input"/>
			    <button type="submit" class="search-button">搜尋</button>
			  </form>
			</div>
		  </div>
		
		  <!-- 列表 -->
		  <div class="video-list-container">
				<div class="video-list">
				 	<c:choose>			  	
					 	<c:when test="${not empty discussList}">
							<c:forEach var="discussDTO" items="${discussList}">
								 <a href="/ornibase/discuss/${discussDTO.discussId}" class="video-card">
								 	<c:if test="${discussDTO.userId != userCert.userId}">
										<span class="favorite-tag 
													${favoriteDiscussIds.contains(discussDTO.discussId) 
	                 								? 'favorited' 
	                 								: 'not-favorited'}" 
	                 								data-discussid="${discussDTO.discussId}"></span>
								 	</c:if>
						    	
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
							<p style="color: gray;">目前尚無群組，請先建立一個。</p>
					 	</c:otherwise>
			    	</c:choose>
			 	</div>
	   		</div>	
		</div>
	</body>
</html>