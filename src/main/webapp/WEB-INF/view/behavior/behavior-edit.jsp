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
	<body class="fontstyle">
		<!-- menu bar include -->
		<%@ include file="/WEB-INF/view/menu-mini.jspf"%>
	
		<div class="edit-wrapper">
		  <!-- 行為紀錄區 -->
		  <fieldset class="form-box">
		    <h2 class="h2">編輯行為紀錄</h2>
		    <sp:form method="post" modelAttribute="behaviorDTO" action="/ornibase/discuss/behavior/${behaviorDTO.discussId}/edit/${behaviorDTO.behaviorId}">
            <input type="hidden" name="_method" value="PUT" />  
		      <span class="alert">* 必填欄位</span>

		      <fieldset class="alert-section">

			      <div class="form-row-inline">
			        <label for="date">日期</label>
			        <sp:input path="date" class="date" required="required"/>
			      </div>
			      
			      <div class="form-row-inline">
			        <label>時間</label>
			        <div class="time-group">
				        <sp:input path="startTime" type="time" id="startTime" required="required" />
			    	    <span>～</span>
			        	<sp:input path="endTime" type="time" id="endTime" required="required" />
			        </div>
			      </div>
			      
			      <div class="form-row">
			        <sp:select path="subject" required="required" >
			          <sp:option value="" label="請選擇對象" />
			          <sp:option value="母鳥" />
			          <sp:option value="公鳥" />
			          <sp:option value="幼鳥A" />
			          <sp:option value="幼鳥B" />
			          <sp:option value="幼鳥C" />
	                  <sp:option value="幼鳥D" />
	                  <sp:option value="幼鳥E" />
			          <sp:option value="鄰居" />
			        </sp:select>
			      </div>
			      <div class="form-row">
			        <sp:select path="action" required="required" >
			          <sp:option value="" lable="請選擇行為" />
			          <sp:option value="飛行" />
		   	          <sp:option value="餵食" />
			          <sp:option value="獨自進食" />
			          <sp:option value="睡覺" />
			          <sp:option value="排遺" />
			          <sp:option value="進巢" />
			          <sp:option value="出巢" />
			          <sp:option value="送餐" />
			          <sp:option value="開傘" />
			          <sp:option value="警戒" />
			          <sp:option value="其他" />
			        </sp:select>
			      </div>
		      </fieldset>
		
		      <br>
		      <fieldset class="normal-section">
			      <div class="form-row">
			        <sp:select path="food">
			          <sp:option value="" label="請選擇食物" />
			          <sp:option value="哺乳類" />
			          <sp:option value="鳥類" />
			          <sp:option value="魚類" />
			          <sp:option value="爬蟲類" />
			          <sp:option value="兩生類" />
			          <sp:option value="昆蟲" />
			          <sp:option value="節肢動物" />
			          <sp:option value="軟體動物" />
			          <sp:option value="果實" />
			          <sp:option value="種子" />
			        </sp:select>
			      </div>
			      
			      <div class="form-row">
			        <sp:input type="text" path="temperature" placeholder="輸入溫度(℃)" />
			      </div>
			      
			      <div class="form-row">
			        <label>備註</label>
			        <sp:textarea path="note" maxlength="100" placeholder="限100字內" />
			      </div>
		      </fieldset>
		      
		      <div class="form-row button-group">
		        <button type="submit">送出</button>
		        <a href="/ornibase/discuss/behavior/${discussDTO.discussId}/list">查看紀錄清單</a>
		      </div>
		    </sp:form>
		  </fieldset>
		</div>
	</body>
</html>