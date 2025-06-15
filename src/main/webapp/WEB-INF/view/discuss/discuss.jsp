<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="/css/basic.css">
		<link rel="stylesheet" href="/css/common.css">
		<link rel="stylesheet" href="/css/discuss.css">
		<link rel="stylesheet" href="/css/behavior.css">
		<link rel="stylesheet" href="/css/charts.css">
		<title>OrniBase</title>
		<link rel="icon" type="image/png" href="/images/icon.png">
		<%@ include file="/WEB-INF/view/charts/googlecharts-head.jspf" %>	
		<script src="/js/time.js"></script>
		<!-- 從 Google 的 CDN 載入 Google Charts 套件, 引入 Google Charts-->
		<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
		
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
					<span class="username">username</span> 
					<h2 class="title-text">${discussDTO.title}</h2>
					<span class="tag-label">tag</span>
					<span class="tag-label">私人</span>
				</div>
				
				<div class="btn-group">
					<a href="/ornibase/discuss/update/${discussDTO.discussId}"
						class="btn btn-danger">編輯</a>
					<form method="post"
						action="/ornibase/discuss/delete/${discussDTO.discussId}">
						<input type="hidden" name="_method" value="DELETE" />
						<button type="submit" class="btn btn-danger">刪除</button>
					</form>
				</div>				
			</div>

			<div class="header-bottom">
				<p class="description-text">${discussDTO.description}</p>
				<span class="createtime">建立時間：createtime</span>
			</div>

			<!-- YouTube 嵌入影片 -->
			<iframe class="video-frame"
				src="https://www.youtube.com/embed/${discussDTO.youtubeVideoId}"
				frameborder="0" allowfullscreen></iframe>

			<!-- 圖表區 -->
			<div class="placeholder-box">
				<!-- menu bar include -->
				<%@ include file="/WEB-INF/view/charts/google-charts-body.jspf"%>
				<select id="subjectSelect">
				  <option value="幼鳥A" selected>幼鳥A</option>
				  <option value="幼鳥B">幼鳥B</option>
				  <option value="成鳥A">成鳥A</option>
				  <option value="成鳥B">成鳥B</option>
				</select>
				<div class="charts">
					<div id="piechartA" style="width: 370px; height: 250px"></div>
					<div id="timeline" style="width: 370px; height: 250px"></div>
					<div id="barchart" style="width: 370px; height: 250px"></div>
				</div>
			</div>
		</div>
		<!-- 右側區域：行為紀錄表單 -->
		<!-- Behavior -->
		<%@ include file="/WEB-INF/view/behavior/behavior-form.jspf"%>
	</div>
</body>
</html>

