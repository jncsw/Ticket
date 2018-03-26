package servlet;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;



import pac.Do;
import pac.Query;
import tools.KeyCheck;
import tools.PostSth;
import tools.Send;
import tools.TimeFormat;
import net.sf.json.JSONObject;

public class Add extends HttpServlet {
	private ServletContext sc;
	private String savePath;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	public void init(ServletConfig config) {
		savePath = "imgs";
		sc = config.getServletContext();
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		if (!KeyCheck.isLegal(req, resp)) {
			return;
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		Hashtable hashList = new Hashtable();
		try {
			List items = upload.parseRequest(req);
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (item.isFormField()) {
					if ((item.getString("UTF-8") == null || item.getString(
							"UTF-8").equals(""))) {
						return;
					}
					if (item.getFieldName().equals("c-type")) {
						String string=item.getString("UTF-8");
						hashList.put("description", (string.equals("0")?"√≈∆±":"∂“ªª»Ø"));
						continue;
					}
					hashList.put(item.getFieldName(), item.getString("UTF-8"));
				} else {
					hashList.put("logo_url",img(item));
				}
			}
			
			
			JSONObject base_info = new JSONObject();
			base_info.put("logo_url", hashList.get("logo_url").toString());
			base_info.put("code_type", "CODE_TYPE_QRCODE");
			base_info.put("brand_name", hashList.get("brand_name").toString());
			base_info.put("get_limit", hashList.get("get_limit").toString());
			base_info.put("title", hashList.get("name").toString());
			base_info.put("sub_title", hashList.get("sub_title").toString());
			base_info.put("color", hashList.get("color").toString());
			base_info.put("notice", hashList.get("notice").toString());
			base_info.put("description", hashList.get("description").toString());
			JSONObject sku=new JSONObject();
			sku.put("quantity", hashList.get("quantity").toString());
			base_info.put("sku",sku);
			
			JSONObject time= new JSONObject();
			time.put("type", "DATE_TYPE_FIX_TIME_RANGE");
			time.put("begin_timestamp", TimeFormat.date2TimeStamp(hashList.get("time1").toString()).getTime()/1000);
			time.put("end_timestamp", TimeFormat.date2TimeStamp(hashList.get("time2").toString()).getTime()/1000);
			base_info.put("date_info", time);
			
			JSONObject gift=new JSONObject();
			gift.put("base_info", base_info);
			
			JSONObject card= new JSONObject();
			card.put("gift", gift);
			card.put("card_type", "GIFT");
			
			JSONObject jsonObj =new JSONObject();
			jsonObj.put("card", card);
			
			JSONObject returnJsonObject = JSONObject.fromObject(PostSth.postJson("https://api.weixin.qq.com/card/create?access_token="+Do.getToken(), jsonObj.toString()));
			if(!returnJsonObject.get("errcode").toString().equals("0")){
				Send.errorPage(resp, returnJsonObject.get("errmsg").toString());
				return;
			}
		} catch (Exception e) {
			Send.other(resp, 1);
			return;
		}
		Query.init();
		Send.other(resp, 0);
	}
	public String img(FileItem item) {
		try {
			if (item.getName() != null && !item.getName().equals("")&&!item.isFormField()) {

				File tempFile = new File(item.getName());
				String urlString = sc.getRealPath("/") + savePath+"\\"+ tempFile.getName();
				
				File file = new File(urlString);
				item.write(file);
				
				return PostSth.send(Do.getToken(),urlString);
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}
}
