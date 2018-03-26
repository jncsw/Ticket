package tools;

import javax.servlet.http.HttpServletResponse;

public class Send {

	public static void other(HttpServletResponse response,int id){
		try {
			String url="";
			switch (id) {
			case 0:
				url="list.jsp";
				break;
			case 1:
				url="login.html";
				break;
			case 2:
				url="errorPage.html";
				break;
			default:
				break;
			}
			response.sendRedirect("/Kaquannnn/"+url);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void to(HttpServletResponse response,String id){
		try {
			String url="verification.jsp?card_id="+id;
			
			response.sendRedirect("/Kaquannnn/"+url);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void errorPage(HttpServletResponse response,String error){
		try {
			String url="errorPage.jsp?error="+error;
			
			response.sendRedirect("/Kaquannnn/"+url);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
