<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*" import="pac.*"
	import="tools.*" pageEncoding="utf-8"%>
<%
	
	String card_id = "";
	try {
		card_id = request.getParameter("card_id");
	} catch (Exception e) {
	}
	if (!KeyCheck.check(request)&&!KeyCheck.check2(request,card_id)) {
		Send.other(response, 1);
		return;
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
	
	long time=System.currentTimeMillis()/1000;
	String nonceStr = RandomString.randomString(16);
	String url = request.getScheme()+"://"+ request.getServerName()+":8080"+request.getRequestURI()+"?"+request.getQueryString(); 
	String signature=Do.getSignature(time, nonceStr,url);
	
%>
<!DOCTYPE html>
<html>
<head>
<title>卡券详情</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<link rel="stylesheet" type="text/css" href="style/AddnEdit.css">
<link rel="stylesheet" type="text/css" href="style/list.css">
 <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

<script>
function jsApi(){ 
	wxConfigData={
    debug:false, 
    appId:'<%=Do.a%>', 
    timestamp:'<%=time%>', 
    nonceStr:'<%=nonceStr%>', 
    signature:'<%=signature%>',
    jsApiList:['scanQRCode'] 
	};
	wx.config(wxConfigData);
}
function verification(){
scanQRCodeData={
    needResult: 1, 
    scanType: ["qrCode","barCode"], 
    success: function (res) {
    	var result = res.resultStr; 
    	window.location.href = "Verification?code="+result+"&id="+"<%=card_id%>";
    	return;
    }
};
wx.scanQRCode(scanQRCodeData);
}
</script>
</head>
<body>
<header id="header">
	<img id="logo" src="src/images/logo-red.png" alt="JT logo" height=80px width=100px>
	<h1>卡券核销</h1>
	<p>
		<a href="https://www.online.sdu.edu.cn/" target="_blank">来自 学生在线</a>
	</p>
</header>
<div id="wrapper">
	
		<p>
			<img src="<%=card.logo_url%>" width=150 height=150> <img
				src="<%=card.qrcode_url%>" width=150 height=150>
		</p>
		<br>
		<p>
				<label for="username" class="uname"> 举办方:<%=card.brand_name%> </label>
		</p>
		<p>
			<label for="username" class="uname"> 活动名称: <strong><%=card.title%></strong></label>
		</p>
		<p>
			<label for="username" class="uname"> 副标题:<%=card.title%> </label>
		</p>
		<p>
			<label for="username" class="uname"> 卡券数量:<%=card.quantity%>/<%=card.total_quantity%></label>
		</p>
		<p>
			<label for="username" class="uname"> 卡券id: <%=card_id%></label>
		</p>
		<br>
		<br>
		<label for="username" class="uname">扫码核销:</label> 
		<br>
		<button onclick="jsApi();">申请</button>　　　<button onclick="verification();">扫码</button>
		<br>
		<label for="username" class="uname">说明1:第一次核销时点申请，之后不用点</label> <br>
		<label for="username" class="uname">说明2:扫码后若返回此页面，说明核销成功，不成功会跳转到错误页面</label> 
		<br>
		<br>
		<br>
		<label for="username" class="uname">直接核销:</label> 
		<p>
		<form name="add" action="Verification" method="post">
		<input type="hidden" name="id" value="<%=card.id%>" />
			<label for="username" class="uname"> 输入卡券code码: </label> <input
				id="username" name="code" required="required" type="text"
				placeholder="SDUonline" />
		<p class="login button">
			<input type="submit" value="核销" />
		</p>
		<p class="login button">
			<a href="list.jsp?page_num=<%=request.getSession().getAttribute("page_num") %>">返回</a>
		</p>
	</form>
</div>
</body>
</html>
