<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- Spring Form 表單標籤 -->
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="/css/basic.css">
		<link rel="stylesheet" href="/css/common.css">
		<link rel="stylesheet" href="/css/behavior.css">
		<title>OrniBase</title>
		<link rel="icon" type="image/png" href="/images/icon.png">
	</head>
	<body>
		<!-- menu bar include -->
		<%@ include file="/WEB-INF/view/menu.jspf"%>
	
		<div class="right-section fontstyle">
			<div class="form-title">行為紀錄</div>
			<p>
				<sp:form method="post" modelAttribute="behaviorDTO"
					action="${pageContext.request.contextPath}/bbd/discuss/behavior/${discussDTO.discussId}">
					<div class="form-inline">
						<label for="date">日期：</label> <input class="date" type="date"
							name="date" />
					</div>
	
					<label>時間區間：</label>
					<input type="time" name="startTime" id="startTime"
						style="width: 120px;" />
					<span>～</span>
					<input type="time" name="endTime" id="endTime" style="width: 120px;" />
	
					<select name="subject">
						<option value="">請選擇對象</option>
						<option value="成鳥A">成鳥A</option>
						<option value="成鳥B">成鳥B</option>
						<option value="幼鳥A">幼鳥A</option>
					</select>
	
					<select name="action">
						<option value="">請選擇行為</option>
						<option value="飛行">飛行</option>
						<option value="進食">進食</option>
						<option value="其他">其他</option>
					</select>
	
					<select name="food">
						<option value="">請選擇食物</option>
						<option value="哺乳類">哺乳類</option>
						<option value="鳥類">鳥類</option>
						<option value="魚類">魚類</option>
						<option value="爬蟲類">爬蟲類</option>
						<option value="兩生類">兩生類</option>
					</select>
	
					<div class="form-row">
						<div class="form-group">
							<input type="text" name="temperature" placeholder="輸入溫度(℃):" />
						</div>
					</div>
	
					<label>備註：</label>
					<textarea name="note" maxlength="100" placeholder="限100字內"></textarea>
					<div class="button-group">
						<button type="submit">送出</button>
					</div>
				</sp:form>
				<div class="button-group">
					<a class="button-link" href="${pageContext.request.contextPath}/bbd/discuss/behavior/${discussDTO.discussId}/list">查看紀錄清單</a>
				</div>
			</div>
			<script>
					const startTimeInput = document.getElementById("startTime");
					const endTimeInput = document.getElementById("endTime");
			
				 	startTimeInput.addEventListener("change", function () {
			  		if (startTimeInput.value) {
			    		endTimeInput.focus();  // 自動跳到下一欄
			  		}
					});
			</script>
	</body>
</html>