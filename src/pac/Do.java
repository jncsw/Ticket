package pac;

import java.io.File;
import java.io.IOException;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import tools.SHA1;

import net.sf.json.JSONObject;

public class Do {
	static String ss = "";
	static long time = 0;
	static long now = 0;
	static long tm = 0;

	static String ticket = "ticket";
	static long time2 = 0;
	static long now2 = 0;
	static long tm2 = 0;

	public static String a = "appid";
	public static String s = "secret";

	public static String getToken() throws Exception {
		if (a.equals("appid")) {
			throw new Exception();
		}
		now = System.currentTimeMillis();
		if (time == 0 || (now - time) > (tm - 10000)) {
			time = now;
			String str = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
					+ a + "&secret=" + s;
			Document doc = Jsoup.connect(str).ignoreContentType(true).get();
			String st = doc.body().text();
			JSONObject jsonObj = JSONObject.fromObject(st);
			ss = jsonObj.getString("access_token");
			tm = Integer.parseInt(jsonObj.getString("expires_in") + "000");
		}
		String te = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token="
				+ ss;
		Document doc = Jsoup.connect(te).ignoreContentType(true).get();
		String st = doc.body().text();
		JSONObject jsonObj = JSONObject.fromObject(st);
		if (jsonObj.toString().contains("access_token is invalid") || (jsonObj.containsKey("errcode"))&&!jsonObj.get("errcode").toString().equals("45009")) {
			time = now;
			s = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
					+ a + "&secret=" + s;
			doc = Jsoup.connect(s).ignoreContentType(true).get();
			st = doc.body().text();
			jsonObj = JSONObject.fromObject(st);
			ss = jsonObj.getString("access_token");
			tm = Integer.parseInt(jsonObj.getString("expires_in") + "000");
		}
		return ss;
	}

	public static String getToken(String a, String s) throws Exception {
		if (a.equals("appid")) {
			throw new Exception();
		}
		now = System.currentTimeMillis();
		if (time == 0 || (now - time) > (tm - 10000)) {
			time = now;
			s = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
					+ a + "&secret=" + s;
			Document doc = Jsoup.connect(s).ignoreContentType(true).get();
			String st = doc.body().text();
			JSONObject jsonObj = JSONObject.fromObject(st);
			ss = jsonObj.getString("access_token");
			tm = Integer.parseInt(jsonObj.getString("expires_in") + "000");
		}

		String te = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token="
				+ ss;
		Document doc = Jsoup.connect(te).ignoreContentType(true).get();
		String st = doc.body().text();
		JSONObject jsonObj = JSONObject.fromObject(st);
		if ((jsonObj.containsKey("errcode"))&&!jsonObj.get("errcode").toString().equals("45009")) {
			time = now;
			s = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
					+ a + "&secret=" + s;
			doc = Jsoup.connect(s).ignoreContentType(true).get();
			st = doc.body().text();
			jsonObj = JSONObject.fromObject(st);
			ss = jsonObj.getString("access_token");
			tm = Integer.parseInt(jsonObj.getString("expires_in") + "000");
		}
		return ss;
	}

	public static String getTicket() {
		now2 = System.currentTimeMillis();
		if (time2 == 0 || (now2 - time2) > (tm2 - 10000)) {
			time2 = now2;
			Document doc = null;
			try {
				String str = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+getToken()+"&type=jsapi";
				doc = Jsoup.connect(str).ignoreContentType(true).get();
				String st = doc.body().text();
				JSONObject jsonObj = JSONObject.fromObject(st);
				ticket = jsonObj.getString("ticket");
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
			
		}
		return ticket;
	}

	public static String getTicket(String token) {
		now2 = System.currentTimeMillis();
		if (time2 == 0 || (now2 - time2) > (tm2 - 10000)) {
			time2 = now2;
			Document doc = null;
			try {
				String str = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+token+"&type=jsapi";
				doc = Jsoup.connect(str).ignoreContentType(true).get();
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
			String st = doc.body().text();
			JSONObject jsonObj = JSONObject.fromObject(st);
			ticket = jsonObj.getString("ticket");
		}
		return ticket;
	}                                     

	public static String getSignature(long timestamp,String noncestr,String url){
		String jsapi_ticket=getTicket();
		String timesString=timestamp+"";
		String[][] strings={
				{"url",url},
				{"timestamp",timesString},
				{"jsapi_ticket",jsapi_ticket},
				{"noncestr",noncestr},
		};
		for (int i = 0; i < 3; i++) {
			for (int j = i; j < 4; j++) {
				if(strings[i][0].compareTo(strings[j][0])>0){
					String[] asStrings=strings[i];
					strings[i]=strings[j];
					strings[j]=asStrings;
				}
			}
		}
		String returnString="";
		for (int i = 0; i < 4; i++) {
			returnString+=strings[i][0]+"="+strings[i][1];
			if(i!=3){
				returnString+="&";
			}
		}
		return SHA1.encode(returnString);
		
	}
	
	public static String getSignature(long timestamp,String noncestr,String url,String ticket){
		timestamp/=1000;
		String jsapi_ticket=ticket;
		String timesString=timestamp+"";
		String[][] strings={
				{"url",url},
				{"timestamp",timesString},
				{"jsapi_ticket",jsapi_ticket},
				{"noncestr",noncestr},
		};
		for (int i = 0; i < 3; i++) {
			for (int j = i; j < 4; j++) {
				if(strings[i][0].compareTo(strings[j][0])>0){
					String[] asStrings=strings[i];
					strings[i]=strings[j];
					strings[j]=asStrings;
				}
			}
		}
		String returnString="";
		for (int i = 0; i < 4; i++) {
			returnString+=strings[i][0]+"="+strings[i][1];
			if(i!=3){
				returnString+="&";
			}
		}
		
		return SHA1.encode(returnString);
	}
	public static void main(String[] args) {
		
	}
	
}




