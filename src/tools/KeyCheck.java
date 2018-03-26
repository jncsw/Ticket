package tools;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KeyCheck {

	public static boolean isLegal(HttpServletRequest req, HttpServletResponse resp) {
		if(check(req)){
			return true;
		}else {
			Send.other(resp, 1);
			return false;
		}
	}
	public static boolean isLegal2(HttpServletRequest req, HttpServletResponse resp,String id) {
		if(check2(req,id)){
			return true;
		}else {
			Send.other(resp, 1);
			return false;
		}
	}
	public static boolean check(HttpServletRequest req) {
		try{
			final String cookieName="Kaquan";
			Cookie[] cookies = req.getCookies();
			
			String adminId=getValue(cookies,cookieName);
			String key=getValue(cookies,adminId);
			
			HttpSession session=req.getSession();
			String key_r=(String) session.getAttribute(adminId);
			
			if(key_r.equals(key)){
				return true;
			}
			
		}catch (Exception e) {
			return false;
		}
		return false;
	}
	public static boolean check2(HttpServletRequest req,String id) {
		try{
			final String cookieName="Kaquan2";
			Cookie[] cookies = req.getCookies();
			
			String adminId=getValue(cookies,cookieName);
			String key=getValue(cookies,adminId);
			
			HttpSession session=req.getSession();
			String key_r=(String) session.getAttribute(adminId);
			
			if(key_r.equals(key)&&adminId.equals(MD5.MD5(id).substring(0,5))){
				return true;
			}
			
		}catch (Exception e) {
			return false;
		}
		return false;
	}
	public static String getValue(Cookie[] cookies, String name) {
		String value = "";
		try {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName()) && cookie.getValue() != null) {
					value = cookie.getValue();
					break;
				}
			}
		} catch (Exception e) {

		}
		return value;
	}
	
}
