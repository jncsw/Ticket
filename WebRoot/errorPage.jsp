<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*" import="pac.*"
	import="tools.*" pageEncoding="utf-8"%>
<%
	if (!KeyCheck.isLegal(request, response)) {
		return;
	}
	String admin = KeyCheck.getValue(request.getCookies(), "Kaquan");

	String error = "";
	try {
		error = request.getParameter("error");
	} catch (Exception e) {
	}
%>
<!DOCTYPE html>
<html>
<head>
<title>errorPage</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />

<link rel="stylesheet" type="text/css" href="style/list.css">
<link rel="stylesheet" type="text/css" href="style/AddnEdit.css">

</head>
<body>
	<header id="header">
      <img id="logo" src="src/images/logo-red.png" alt="JT logo" height=80px width=100px>
      <h1>出错啦</h1>
      <p>
        <a href="https://www.online.sdu.edu.cn/" target="_blank">来自 学生在线</a>
      </p>
    </header>
	<div id="wrapper">
		<p>
		<br><br>
			<label for="username" class="uname"><strong><%=error%></strong>
			</label>
			<br>
				<br>
					<br>
			<button onclick="javascript:window.history.back();">返回</button>
		</p>
	</div>
</body>
</html>
