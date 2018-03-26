<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*" import="pac.*"
	import="tools.*" pageEncoding="utf-8"%>
<%
	if (!KeyCheck.isLegal(request, response)) {
		return;
	}
	String admin = KeyCheck.getValue(request.getCookies(), "Kaquan");

	String card_id = "";
	try {
		card_id = request.getParameter("card_id");
	} catch (Exception e) {
	}
	
	Card card ;
	try {
		card = Query.getCard(card_id);
	}
	catch (Exception e) {
			Send.other(response, 1);
			return;
	}
	if (card == null) {
		Send.other(response, 2);
		return;
	}
%>
<!DOCTYPE html>
<html>
<head>
<title>卡券详情</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<link rel="stylesheet" type="text/css" href="style/list.css">
<link rel="stylesheet" type="text/css" href="style/AddnEdit.css">
</head>
<body>
	<header id="header">
		<img id="logo" src="src/images/logo-red.png" alt="JT logo" height=80px
			width=100px>
		<h1>卡券详情</h1>
		<p>
			<a href="https://www.online.sdu.edu.cn/" target="_blank">来自 学生在线</a>
		</p>
	</header>
	<div id="wrapper">
		<form name="add" action="Alter" enctype="multipart/form-data"
			method="post">
			<input type="hidden" name="id" value="<%=card.id%>" />
			<p>
				<img src="<%=card.logo_url%>" width=150 height=150>
			</p>
			<p>
				<label for="username" class="uname"> 更换logo </label> <input
					id="logo" name="logo" type="file" placeholder="SDUonline" />
			</p>
			<p>
				<label for="username" class="uname"> 举办方:<%=card.brand_name%> </label>
			</p>
			<p>
				<label for="username" class="uname"> 活动名称:<%=card.title%> </label>
			</p>
			<p>
				<label for="username" class="uname"> 副标题:<%=card.title%> </label>
			</p>
			<p>
				<label for="username" class="uname"> 卡券数量:<%=card.quantity%>/<%=card.total_quantity%></label>
			</p>
			<br>
			<p>
				<label for="username" class="uname"> 负责人id: <%=card.user%> </label>
			</p>
			<p>
				<label for="username" class="uname"> 负责人密码: <%=card.password%> </label>
			</p>
			<br>
			<p>
				<label for="username" class="uname"> 提示 </label> <input
					id="username" name="notice" required="required" type="text"
					placeholder="SDUonline" value="<%=card.notice%>" />
			</p>
			<p>
				<label for="username" class="uname"> 卡券类型:<%=card.description%></label>
				<select name="c-type" size="1">
					<option value="0">门票</option>
					<option value="1">兑换券</option>
				</select>
			</p>
			<p>
				<label for="username" class="uname"> 卡券颜色:<label
					for="username" class="uname"
					style="background-color:<%=card.color%>;"> </label> </label> <select
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
				<label for="password" class="youpasswd">
					活动开始时间(更改活动时间仅支持有效区间的扩大) </label> <input id="password" name="time1"
					required="required" type="text" placeholder="SDUonline"
					value="<%=card.begin_timestamp%>" />
			</p>
			<p>
				<label for="password" class="youpasswd"> 活动结束时间 </label> <input
					id="password" name="time2" required="required" type="text"
					placeholder="SDUonline" value="<%=card.end_timestamp%>" />
			</p>

			<p class="login button">
				<input type="submit" value="更改" />
			</p>
			<p class="login button">
				<a href="list.jsp?page_num=<%=request.getSession().getAttribute("page_num") %>">返回</a>
			</p>
		</form>
	</div>
</body>
</html>
