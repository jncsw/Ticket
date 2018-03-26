package servlet;


import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pac.Query;
import tools.Download;
import tools.KeyCheck;
import tools.Send;

public class Refresh extends HttpServlet {

	public void init(ServletConfig config) {
		ServletContext sc = config.getServletContext();
		Download.relPath=sc.getRealPath("/");
	}
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
		
		Query.init();
		Send.other(resp, 0);
	}
}
