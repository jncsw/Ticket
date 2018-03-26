package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tools.KeyCheck;
import tools.Send;

public class Quit extends HttpServlet{
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
		try {
			String user=req.getParameter("id");
			HttpSession session=req.getSession();
			session.removeAttribute(user);
		} catch (Exception e) {
		}
		
		Send.other(resp, 1);
		
	}
}
