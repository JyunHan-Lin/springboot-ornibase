<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
				<%@ include file="/WEB-INF/view/charts/googlecharts-body.jspf"%>
				<div class="charts">
					<div>
						<select id="subjectSelect">
						  <option value="幼鳥A" selected>幼鳥A</option>
				          <option value="成鳥A">成鳥A</option>
				          <option value="成鳥B">成鳥B</option>
				          <option value="幼鳥B">幼鳥B</option>
				          <option value="幼鳥C">幼鳥C</option>
				          <option value="幼鳥D">幼鳥D</option>
				          <option value="幼鳥E">幼鳥E</option>
				          <option value="鄰居">鄰居</option>
						</select>
						<div id="piechart" style="width: 370px; height: 210px"></div>
					</div>
				
		
					<div>
					  <input type="date" id="dateSelector">					  
					  <div id="timeline_div" style="width: 370px; height: 210px"></div>
					</div>
					<div>
					  <select id="subjectBarSelector">
					  	  <option value="幼鳥A" selected>幼鳥A</option>
				          <option value="成鳥A">成鳥A</option>
				          <option value="成鳥B">成鳥B</option>
				          <option value="幼鳥B">幼鳥B</option>
				          <option value="幼鳥C">幼鳥C</option>
				          <option value="幼鳥D">幼鳥D</option>
				          <option value="幼鳥E">幼鳥E</option>
				          <option value="鄰居">鄰居</option>
					  </select>
					  <div id="bar_chart_div" style="width: 370px; height: 210px"></div>
					</div>
					<div>
					  <input type="date" id="foodFromDate">
					  <div id="food_chart_div" style="width: 370px; height: 210px">
					</div>
				
					
				</div>
			</div>
		</div>
		<!-- 右側區域：行為紀錄表單 -->
		<!-- Behavior -->
		<%@ include file="/WEB-INF/view/behavior/behavior-form.jspf"%>
	</div>
</body>
</html>

