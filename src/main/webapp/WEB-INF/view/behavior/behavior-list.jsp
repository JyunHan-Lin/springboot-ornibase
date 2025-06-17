<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- Spring Form 表單標籤 -->
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="/css/basic.css">
		<link rel="stylesheet" href="/css/common.css">
		<link rel="stylesheet" href="/css/behavior-list.css">
		<title>OrniBase</title>
		<!-- LOGO -->
		<link rel="icon" type="image/png" href="/images/icon.png">
		<!-- DataTables -->
		<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
		<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
		<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>		
	</head>
	<body class="fontstyle">
		<!-- menu bar include -->
		<%@ include file="/WEB-INF/view/menu-mini.jspf" %>	
		<div>
			<fieldset  class="behavior-form">
				<legend class="title">行為紀錄</legend>
				<table>
					<thead>
						<tr>
							<th>記錄者</th>
							<th>行為日期</th>
							<th>開始時間</th>
							<th>結束時間</th>
							<th>對象</th>
							<th>行為</th>
							<th>食物</th>
							<th>溫度</th>
							<th>備註</th>
							<th>記錄日期</th>
							<th>編輯</th>
							<th>刪除</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="behaviorDTO" items="${behaviorList}">
							<tr>
								<td>${ behaviorDTO.creatorName}</td>
								<td>${ behaviorDTO.date }</td>
								<td>${ behaviorDTO.startTime }</td>
								<td>${ behaviorDTO.endTime }</td>
								<td>${ behaviorDTO.subject }</td>
								<td>${ behaviorDTO.action }</td>
								<td>${ behaviorDTO.food }</td>
								<td>${ behaviorDTO.temperature }</td>
								<td>${ behaviorDTO.note }</td>
								<td>${ behaviorDTO.formattedCreatedTime }</td>
								
								<td>
								<a href="/ornibase/discuss/behavior/${ behaviorDTO.discussId }/edit/${ behaviorDTO.behaviorId }" class="btn btn-danger">編輯</a>
								</td>
								<td>
									<form method="post" action="/ornibase/discuss/behavior/${ behaviorDTO.discussId }/delete/${ behaviorDTO.behaviorId }">
										<input type="hidden" name="_method" value="DELETE" />
										<button type="submit" class="btn btn-danger">刪除</button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</fieldset>
		</div>
		
		<!-- DataTables 初始化腳本 -->
		<script>
		  $(document).ready(function() {
		    $('table').DataTable({
		      "language": {
		        "url": "//cdn.datatables.net/plug-ins/1.13.6/i18n/zh-HANT.json"
		      }
		    });
		  });
		</script>
	</body>
</html>
