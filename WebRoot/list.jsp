<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*" import="pac.*"
	import="tools.*" pageEncoding="utf-8"%>
<%
	if (!KeyCheck.isLegal(request, response)) {
		return;
	}
	String admin = KeyCheck.getValue(request.getCookies(), "Kaquan");

    
	int page_num = 0;
	try {
		page_num = Integer.parseInt(request.getParameter("page_num"));
	} catch (Exception e) {
	}
		try{
			request.getSession().removeAttribute("page_num");
		}catch(Exception e){
		}
		
		request.getSession().setAttribute("page_num", page_num);
	ArrayList<Card> cards=null;
	
	try {
		cards = Query.getPageOfCard(page_num);
		if(Query.cardNum==-1){
			throw new Exception();
		}
	}catch (Exception e) {
		Send.other(response, 1);
		return;
	}
%>
<!DOCTYPE html>
<html>
<head>
<title>列表页</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<link rel="stylesheet" type="text/css" href="style/list.css">
</head>
<body>
	
	<header id="header">
      <img id="logo" src="src/images/logo-red.png" alt="JT logo" height=80px width=100px>
      <h1>i山大微信电子卡券管理系统</h1>
      <p>
        <a href="https://www.online.sdu.edu.cn/" target="_blank">来自 学生在线</a>
      </p>
    </header>
	<div id="menu"  style="float:left;">
		<div id="add">
			<a href="Refresh">刷新</a>
		</div>
		<div id="add">
			<a href="add.jsp">新建卡券</a>
		</div>
		<div id="logout">
			<a href="Quit?id=<%=admin%>">退出登录</a>
		</div>
		<br><h2>第<%=page_num+1%>页/共<%=Query.pageNum%>页</h2>
			<br>
			<%
				if(page_num!=0)	{
			%>
			<a href="list.jsp?page_num=<%=page_num-1%>">上一页</a>
			<%
				}
			%>
			
			<%
				if(page_num!=Query.pageNum-1)	{
			%>
			<a href="list.jsp?page_num=<%=page_num+1%>">下一页</a>
			<%
				}
			%>
	</div>
	<div id="list">
		
			<table id="cards">
				<tr>
					
					<th>LOGO</th>
					<th>活动名称</th>
					<th>卡券类型</th>
					<th>时间</th>
					<th>数量</th>
					<th>状态</th>
					<th></th>
					<th></th>
				</tr>
				<%
					for (Card card : cards) {
				%>
				<tr>
					<td class="logo"><img src="<%=card.logo_url%>" width=100
						height=100>
					</td>
					<td><%=card.title%></td>
					<td><%=card.description%></td>
					<td><%=card.begin_timestamp%><br>-<br><%=card.end_timestamp%></td>
					<td><%=card.quantity%>/<%=card.total_quantity%></td>
					<td><%=Card.names.get(card.status)%></td>
					<td><a href="detail.jsp?card_id=<%=card.id%>"><strong>详情</strong></a></td>
					<td><a href="verification.jsp?card_id=<%=card.id%>"><strong>核销</strong></a></td>
					<th><form name="add" action="Delete" method="post"><input type="submit" value="删除卡券" /><input type="hidden" name="id" value="<%=card.id%>"/></form>
					</th>
				</tr>
				<%
					}
				%>
			</table>
			
		<br><br><br><br><br><br><br><br><br><br>
	</div>
	</main>

</body>
</html>
