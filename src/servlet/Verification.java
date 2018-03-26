package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pac.Do;

import tools.KeyCheck;
import tools.PostSth;
import tools.Send;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Verification extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String code=req.getParameter("code");
		String id=req.getParameter("id");
		
		if (!KeyCheck.check(req)&&!KeyCheck.check2(req,id)) {
			Send.other(resp, 1);
			return;
		}
		
		String statu;
		try {
			statu = checkStatu(code);
		} catch (Exception e) {
			Send.other(resp, 1);
			return;
		}
		if (statu.equals("NORMAL")) {
			try {
				cancel(code);
			} catch (Exception e) {
				Send.other(resp, 1);
				return;
			}
			Send.to(resp, id);
		}else {
			Send.errorPage(resp, statu);
			return;
		}
	}

	private static String checkStatu(String code)throws Exception {
		JSONObject jObject = new JSONObject();
		jObject.put("code", code);

		try {
			JSONObject jsonObject = JSONObject.fromObject(PostSth.postJson(
					"https://api.weixin.qq.com/card/code/get?access_token="
							+ Do.getToken(), jObject.toString()));
			if (!jsonObject.get("errcode").toString().equals("0")) {
				return jsonObject.get("errmsg").toString();
			}
			return jsonObject.get("user_card_status").toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "ÎÞÐ§µÄcodeÂë";
	}

	private static String cancel(String code)throws Exception {
		JSONObject jObject = new JSONObject();
		jObject.put("code", code);

		try {
			JSONObject jsonObject = JSONObject.fromObject(PostSth.postJson(
					"https://api.weixin.qq.com/card/code/consume?access_token="
							+ Do.getToken(), jObject.toString()));

			return jsonObject.get("errmsg").toString();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";

	}

	private static void whiteList(String openids[]) {
		JSONArray jArray = new JSONArray();
		for (String openid:openids) {
			jArray.add(openid);
		}

		JSONObject jObject = new JSONObject();
		jObject.put("openid", jArray);
		String appid="***";
		String secret="***";
		try {
			System.out.println(PostSth.postJson(
					"https://api.weixin.qq.com/card/testwhitelist/set?access_token="
							+ Do.getToken(appid,secret), jObject.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String[] aStrings={"***","***","***","***"};
		whiteList(aStrings);
			
	}
}





