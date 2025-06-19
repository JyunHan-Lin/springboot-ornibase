<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>OrniBase</title>
		<link rel="icon" type="image/png" href="/images/icon.png">
		<link rel="stylesheet" href="/css/basic.css">
		<link rel="stylesheet" href="/css/common.css">
		<link rel="stylesheet" href="/css/discuss.css">
		<link rel="stylesheet" href="/css/behavior.css">
		<link rel="stylesheet" href="/css/charts.css">
		<!-- 從 Google 的 CDN 載入 Google Charts 套件, 引入 Google Charts-->
		<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
		<script src="/js/discuss-delete.js"></script>
	</head>
<body class="fontstyle">
	<!-- menu bar include -->
	<%@ include file="/WEB-INF/view/menu-mini.jspf"%>
	<!-- 外層容器 -->
	<div class="page-container">
		<!-- 左側區域：標題、影片、留白 -->
		<div class="left-section">
			<!-- 標題與按鈕列 -->
			<div class="header-row">
			
				<div class="header-top-left">
					<div class="user-circle" title="建立者：${creatorName}">
					  ${creatorName.substring(0,1).toUpperCase()}
					</div>
				
					<h2 class="title-text">${discussDTO.title}</h2>
					<span class="tag-label">${discussDTO.tag}</span>
					<span class="tag-label">${discussDTO.isPublic ? '公開' : '私人'}</span>
				</div>
				
				<div class="btn-group">
				
					<c:choose>
						<c:when test="${privilegeLevel == 3}">
							<!-- 建立者 -->
							<a href="/ornibase/discuss/update/${discussDTO.discussId}"
								class="btn btn-danger">編輯</a>
							<form method="post"
								action="/ornibase/discuss/delete/${discussDTO.discussId}">
								<input type="hidden" name="_method" value="DELETE" />
								<button type="submit" class="btn btn-danger" onclick="return confirmDelete();">刪除</button>
							</form>
						</c:when>

						<c:when test="${privilegeLevel == 2}">
							<!-- 非建立者，但已收藏：可送出行為 -->
						    <form method="post" action="/ornibase/discuss/favorite/${discussDTO.discussId}/delete">
						      <button type="submit" class="btn btn-danger">取消收藏</button>
						    </form>
						</c:when>

						<c:otherwise>
					    <!-- 權限 1：只能閱覽 -->
					    <p>若想記錄行為或留言，請先收藏</p>
					    <form method="post" action="/ornibase/discuss/favorite/${discussDTO.discussId}">
					      <button type="submit" class="btn btn-danger">收藏</button>
					    </form>
					  </c:otherwise>
					</c:choose>
				</div>

			</div>

			<div class="header-bottom">
				<p class="description-text">${discussDTO.description}</p>
				<span class="createtime">建立時間：${discussDTO.formattedCreatedTime}</span>
			</div>

			<!-- YouTube 嵌入影片 -->
			<iframe class="video-frame"
				src="https://www.youtube.com/embed/${discussDTO.youtubeVideoId}"
				frameborder="0" allowfullscreen></iframe>

			<!-- 圖表區 -->
			<div class="placeholder-box">
				<!-- menu bar include -->
				<%@ include file="/WEB-INF/view/charts/googlecharts-body.jspf"%>
				<!-- 圖表1：時間軸 -->
				<h3>行為時間軸（可選日期）</h3>
				<div id="timeline-chart" data-discuss-id="${discussDTO.discussId}" style="height: 300px;"></div>
			</div>
								
			</div>
		<!-- 右側區域：行為紀錄表單 -->
		<!-- Behavior -->
		<c:if test="${privilegeLevel >=2 }">
			<%@ include file="/WEB-INF/view/behavior/behavior-form.jspf"%>
		</c:if>
	</div>
</body>
</html>

