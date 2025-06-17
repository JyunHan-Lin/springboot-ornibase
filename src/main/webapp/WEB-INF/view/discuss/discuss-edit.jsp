<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Spring Form 表單標籤 -->
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags/form" %>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>OrniBase</title>
		<link rel="icon" type="image/png" href="/images/icon.png">
		<link rel="stylesheet" href="/css/basic.css">
		<link rel="stylesheet" href="/css/common.css">
		<link rel="stylesheet" href="/css/form.css">
	</head>
	<body>
		<!-- menu bar include -->
		<%@ include file="/WEB-INF/view/menu-mini.jspf" %>								
		
		<sp:form class="fontstyle" method="post" modelAttribute="discussDTO" action="/ornibase/discuss/update/${ discussDTO.discussId }" >
        <input type="hidden" name="_method" value="PUT" />
			<fieldset class="discussion-form">
				<legend class="h2">編輯筆記本</legend>
				<div class="alert alert-note">* 表示必填欄位</div><p>
				<label>標題<span class="alert">*</span>
				</label>
				<sp:input type="text" path="title" placeholder="請輸入標題"
					required="required" />
		
				<label>描述<span class="alert">*</span></label>
				<sp:input type="text" path="description" placeholder="請描述內容"
					required="required" />
		
				<label>標籤<span class="alert">*</span></label> <select name="tag" required>
					<option value="">請選擇標籤</option>
					<option value="猛禽">猛禽</option>
					<option value="燕">燕</option>
					<option value="雀">雀</option>
					<option value="鵲">鵲</option>
					<option value="海鷗">海鷗</option>
					<option value="蜂鳥">蜂鳥</option>
				</select> <label>Youtube連結<span class="alert">*</span></label>
				<sp:input type="text" path="youtubeVideoId"
					placeholder="請輸入連結中... v= 後面 11 位數" />
				
				<div class="button-row">
					<div class="checkbox-group">
						<label>
							<input type="checkbox" name="isPublic" value="true" checked>是否公開
							<input type="hidden" name="isPublic" value="false">
						</label>					
					</div>
			
					<div class="button-wrapper">
						<button type="reset">重置</button>
						<button type="submit">建立</button>
					</div>
				</div>
			</fieldset>
		</sp:form>
	</body>
</html>