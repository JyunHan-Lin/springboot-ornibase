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
		  <fieldset class="discussion-form">
			<input type="hidden" name="_method" value="PUT" />
		    <legend class="title">編輯筆記本</legend>
				標題: <sp:input type="text" path="title" placeholder="請輸入標題" required="required" />
	    		描述: <sp:input type="text" path="description" placeholder="請描述內容" required="required" />
	    	
	    		<select name="tag">
					<option value="">請選擇標籤</option>
					<option value="猛禽">猛禽</option>
					<option value="燕">燕</option>
					<option value="雀">雀</option>
					<option value="鵲">鵲</option>
					<option value="海鷗">海鷗</option>
					<option value="蜂鳥">蜂鳥</option>
	
				</select>
	    	
	    		Youtube連結: <sp:input type="text" path="youtubeVideoId" placeholder="請輸入連結中... v= 後面 11 位數" />
	    		
	    		<div class="button-wrapper">
	    	
	    			是否公開：<input type="checkbox" name="isPublic"><br>
	    	
					<button type="reset">重置</button>
			    	<button type="submit">建立</button>
		    	</div>

		  </fieldset>
		</sp:form>
	</body>
</html>