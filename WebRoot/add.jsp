<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*" import="pac.*"
	import="tools.*" pageEncoding="utf-8"%>
<%
	if (!KeyCheck.isLegal(request, response)) {
		return;
	}
	if(Query.cardNum==-1){
			Send.other(response, 1);
			return;
	}
	String admin = KeyCheck.getValue(request.getCookies(), "Kaquan");

%>
<!DOCTYPE html>
<html>
  <head>
	<title>新建</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <link rel="stylesheet" type="text/css" href="style/list.css">
	<link rel="stylesheet" type="text/css" href="style/AddnEdit.css">
</head>
<body>
	<header id="header">
      <img id="logo" src="src/images/logo-red.png" alt="JT logo" height=80px width=100px>
      <h1>新建卡券</h1>
      <p>
        <a href="https://www.online.sdu.edu.cn/" target="_blank">来自 学生在线</a>
      </p>
    </header>
	<div id="wrapper">
		<form name="add" action="Add" enctype="multipart/form-data" method="post">
			<p>
				<label for="username" class="uname"> 上传logo </label> <input
					id="logo" name="logo" required="required" type="file"
					placeholder="SDUonline" />
			</p>
			<p>
				<label for="username" class="uname"> 举办方 (不允许修改)</label> <input
					id="username" name="brand_name" required="required" type="text"
					placeholder="SDUonline" />
			</p>
			<p>
				<label for="username" class="uname"> 活动名称 (不允许修改)</label> <input
					id="username" name="name" required="required" type="text"
					placeholder="SDUonline" />
			</p>
			<p>
				<label for="username" class="uname"> 副标题 (不允许修改)</label> <input
					id="username" name="sub_title" required="required" type="text"
					placeholder="SDUonline" />
			</p>
			<p>
				<label for="username" class="uname"> 提示 </label> <input
					id="username" name="notice" required="required" type="text"
					placeholder="SDUonline" />
			</p>
			<p>
				<label for="username" class="uname"> 卡券类型 </label> <select
					name="c-type" size="1">
					<option value="0">门票</option>
					<option value="1">兑换券</option>
				</select>
			</p>
			<p>
				<label for="username" class="uname"> 卡券颜色 </label> <select
					name="color" size="1">
					<option value="Color010" style="background-color:#63b359;">#63b359</option>
					<option value="Color020" style="background-color:#2c9f67;">#2c9f67</option>
					<option value="Color030" style="background-color:#509fc9;">#509fc9</option>
					<option value="Color040" style="background-color:#5885cf;">#5885cf</option>
					<option value="Color050" style="background-color:#9062c0;">#9062c0</option>
					<option value="Color060" style="background-color:#d09a45;">#d09a45</option>
					<option value="Color070" style="background-color:#e4b138;">#e4b138</option>
					<option value="Color080" style="background-color:#ee903c;">#ee903c</option>
					<option value="Color081" style="background-color:#f08500;">#f08500</option>
					<option value="Color082" style="background-color:#a9d92d;">#a9d92d</option>
					<option value="Color090" style="background-color:#dd6549;">#dd6549</option>
					<option value="Color100" style="background-color:#cc463d;">#cc463d</option>
					<option value="Color101" style="background-color:#cf3e36;">#cf3e36</option>
					<option value="Color102" style="background-color:#5E6671;">#5E6671</option>
				</select>
			</p>
			<p>
				<label for="username" class="uname"> 卡券数量 </label> <input
					id="username" name="quantity" required="required" type="text"
					placeholder="SDUonline" />
			</p>
			<p>
				<label for="username" class="uname"> 每人限领数量</label> <input
					id="username" name="get_limit" required="required" type="text" value=1
					placeholder="SDUonline" />
			</p>
			<p>
				<label for="password" class="youpasswd"> 活动开始时间(请按照格式)</label> <input
					id="password" name="time1" required="required" type="text"
					placeholder="SDUonline" value="2016-09-16 00:00:00"/>
			</p>
			<p>
				<label for="password" class="youpasswd"> 活动结束时间 </label> <input
					id="password" name="time2" required="required" type="text"
					placeholder="SDUonline" value="2016-09-16 00:00:00"/>
			</p>
			
			<p class="login button">
				<input type="submit" value="创建" />
			</p>
			<p class="login button">
				<a href="list.jsp?page_num=<%=request.getSession().getAttribute("page_num") %>">返回</a>
			</p>
		</form>
		
	</div>
</body>
</html>

