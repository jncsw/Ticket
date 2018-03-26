package servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pac.Do;
import pac.Query;

import tools.Download;
import tools.KeyCheck;
import tools.MD5;
import tools.Password;
import tools.RandomString;
import tools.Send;

public class Login extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	public void init(ServletConfig config) {
		ServletContext sc = config.getServletContext();
		Download.relPath=sc.getRealPath("/");
		Do.a=config.getInitParameter("appId");
		Do.s=config.getInitParameter("secret");
		Query.init();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String admin = req.getParameter("username");
		String password = req.getParameter("password2");
		
		String password_1 = MD5.MD5(Password.getPassword(admin));
		String password_2 = MD5.MD5(Password.getPassword2(admin));
		
		if (!password.equals(password_1)||password.equals("")||password==null) {
			if (!password.equals(password_2)||password.equals("")||password==null) {
				Send.other(resp, 1);
				return;
			}else {
				String key = Password.getCardId(admin);

				Cookie cookie1 = new Cookie("Kaquan2", admin);
				Cookie cookie2 = new Cookie(admin, key);
				resp.addCookie(cookie1);
				resp.addCookie(cookie2);

				HttpSession session = req.getSession();
				session.setAttribute(admin, key);

				Send.to(resp, key);
			}
		}else {
			String key = RandomString.randomString(30);

			Cookie cookie1 = new Cookie("Kaquan", admin);
			Cookie cookie2 = new Cookie(admin, key);
			resp.addCookie(cookie1);
			resp.addCookie(cookie2);

			HttpSession session = req.getSession();
			session.setAttribute(admin, key);

			Send.other(resp, 0);
		}
		
		
	}

}
