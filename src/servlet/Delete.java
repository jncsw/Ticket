package servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pac.Do;
import pac.Query;
import tools.KeyCheck;
import tools.PostSth;
import tools.Send;
import net.sf.json.JSONObject;

@SuppressWarnings("serial")
public class Delete extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		if (!KeyCheck.isLegal(req, resp)) {
			return;
		}
		
		String deleteId=req.getParameter("id");
		JSONObject jo= new JSONObject();
		jo.put("card_id", deleteId);
		
		String url;
		try {
			url = "https://api.weixin.qq.com/card/delete?access_token="+Do.getToken();
		} catch (Exception e) {
			Send.other(resp, 1);
			return;
		}
		JSONObject returnJsonObject = JSONObject.fromObject(PostSth.postJson(url, jo.toString()));
		if(!returnJsonObject.get("errcode").toString().equals("0")){
			Send.errorPage(resp, returnJsonObject.get("errmsg").toString());
			return;
		}
		Query.init();
		Send.other(resp, 0);
		
	}
	public static void main(String[] args) {
		
	}
}
